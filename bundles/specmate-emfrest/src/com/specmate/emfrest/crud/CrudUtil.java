package com.specmate.emfrest.crud;

import static com.specmate.model.support.util.SpecmateEcoreUtil.getProjectId;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import com.google.common.collect.Lists;
import com.specmate.common.exception.SpecmateAuthorizationException;
import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.model.administration.ErrorCode;
import com.specmate.model.base.IContainer;
import com.specmate.model.base.IContentElement;
import com.specmate.model.base.IRecycled;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.rest.RestResult;

public class CrudUtil {
	private static final String CONTENTS = "contents";

	public static RestResult<?> create(Object parent, EObject toAddObj, String userName) throws SpecmateException {
		if (toAddObj != null && !isProjectModificationRequestAuthorized(parent, toAddObj, true)) {
			throw new SpecmateAuthorizationException("User " + userName + " is not authorized to create elements.");
		}
		EObject toAdd = toAddObj;
		if (parent instanceof Resource) {
			((Resource) parent).getContents().add(toAdd);
		} else if (parent instanceof EObject) {
			EObject eObjectParent = (EObject) parent;
			EStructuralFeature containmentFeature = eObjectParent.eClass().getEStructuralFeature(CONTENTS);
			if (containmentFeature.isMany()) {
				((EList<Object>) eObjectParent.eGet(containmentFeature)).add(toAdd);
			} else {
				eObjectParent.eSet(containmentFeature, toAdd);
			}
		}
		return new RestResult<>(Response.Status.OK, toAdd, userName);
	}

	public static RestResult<?> update(Object target, EObject update, String userName) throws SpecmateException {
		if (update != null && !isProjectModificationRequestAuthorized(target, update, true)) {
			throw new SpecmateAuthorizationException("User " + userName + " is not authorized to update elements.");
		}
		EObject theTarget = (EObject) target;
		EObject theObj = update;
		SpecmateEcoreUtil.copyAttributeValues(theObj, theTarget, true);
		SpecmateEcoreUtil.copyReferences(theObj, theTarget);
		SpecmateEcoreUtil.unsetAllReferences(theObj);
		return new RestResult<>(Response.Status.OK, target, userName);
	}

	public static RestResult<?> recycle(Object target, String userName) {
		if (target instanceof IRecycled) {
			IRecycled theTarget = (IRecycled) target;
			theTarget.setRecycled(true);
			theTarget.setHasRecycledChildren(true);
			ArrayList<EObject> children = Lists.newArrayList(theTarget.eAllContents());
			for (Iterator<EObject> iterator = children.iterator(); iterator.hasNext();) {
				EObject child = iterator.next();
				if (child instanceof IRecycled) {
					IRecycled theChild = (IRecycled) child;
					theChild.setRecycled(true);
					theChild.setHasRecycledChildren(true);
				}
			}

			EObject parent = SpecmateEcoreUtil.getParent(theTarget);
			while (parent != null && !SpecmateEcoreUtil.isProject(parent)) {
				if (parent instanceof IRecycled) {
					((IRecycled) parent).setHasRecycledChildren(true);
				}
				parent = SpecmateEcoreUtil.getParent(parent);
			}
			return new RestResult<>(Response.Status.OK, theTarget, userName);
		}
		return new RestResult<>(Response.Status.UNSUPPORTED_MEDIA_TYPE, target, userName);

	}

	public static RestResult<?> restore(Object target, String userName) {
		if (target instanceof IRecycled) {
			IRecycled theTarget = (IRecycled) target;
			theTarget.setRecycled(false);
			theTarget.setHasRecycledChildren(false);

			// Update all children
			ArrayList<EObject> children = Lists.newArrayList(theTarget.eAllContents());
			for (Iterator<EObject> iterator = children.iterator(); iterator.hasNext();) {
				EObject child = iterator.next();
				if (child instanceof IRecycled) {
					IRecycled theChild = (IRecycled) child;
					theChild.setRecycled(false);
					theChild.setHasRecycledChildren(false);
				}
			}

			// Update all parents
			SpecmateEcoreUtil.updateParentsOnRestore(SpecmateEcoreUtil.getParent(theTarget));
			return new RestResult<>(Response.Status.OK, target, userName);
		}
		return new RestResult<>(Response.Status.UNSUPPORTED_MEDIA_TYPE, target, userName);
	}

	/**
	 * Copies an object recursively with all children and adds the copy to the
	 * parent of the object. The duplicate gets a name that is guaranteed to be
	 * unique within the parent.
	 *
	 * @param target                The target object that shall be duplicated
	 * @param childrenCopyBlackList A list of element types. Child-Elements of
	 *                              target are only copied if the are of a type that
	 *                              is not on the blacklist
	 * @return
	 * @throws SpecmateException
	 */
	public static RestResult<?> duplicate(Object target, List<Class<? extends IContainer>> childrenCopyBlackList)
			throws SpecmateException {

		EObject original = (EObject) target;
		IContainer copy = filteredCopy(childrenCopyBlackList, original);
		IContainer parent = (IContainer) original.eContainer();
		setUniqueCopyId(copy, parent);
		parent.getContents().add(copy);

		return new RestResult<>(Response.Status.OK, target);
	}

	private static IContainer filteredCopy(List<Class<? extends IContainer>> avoidRecurse, EObject original) {
		IContainer copy = (IContainer) copyWithBidirectionalReferences(original);
		List<IContentElement> retain = copy.getContents().stream()
				.filter(el -> !avoidRecurse.stream().anyMatch(avoid -> avoid.isAssignableFrom(el.getClass())))
				.collect(Collectors.toList());
		copy.getContents().clear();
		copy.getContents().addAll(retain);
		return copy;
	}

	public static <T extends EObject> T copyWithBidirectionalReferences(T eObject) {
		CopierWithBidirectionalReferences copier = new CopierWithBidirectionalReferences();
		EObject result = copier.copy(eObject);
		copier.copyReferences();

		@SuppressWarnings("unchecked")
		T t = (T) result;
		return t;
	}

	private static void setUniqueCopyId(IContainer copy, IContainer parent) {
		EList<IContentElement> contents = parent.getContents();

		// Change ID
		String newID = SpecmateEcoreUtil.getIdForChild();
		copy.setId(newID);

		String name = copy.getName().replaceFirst("^Copy [0-9]+ of ", "");

		String prefix = "Copy ";
		String suffix = " of " + name;
		int copyNumber = 1;

		Set<String> names = contents.stream().map(e -> e.getName())
				.filter(e -> e.startsWith(prefix) && e.endsWith(suffix)).collect(Collectors.toSet());
		String newName = "";
		do {
			newName = prefix + copyNumber + suffix;
			copyNumber++;
		} while (names.contains(newName));

		copy.setName(newName);
	}

	public static RestResult<?> delete(Object target, String userName) throws SpecmateException {
		if (target instanceof EObject && !(target instanceof Resource)) {
			EObject parent = SpecmateEcoreUtil.getParent((EObject) target);
			SpecmateEcoreUtil.detach((EObject) target);
			SpecmateEcoreUtil.updateParentsOnRestore(parent);
			return new RestResult<>(Response.Status.OK, target, userName);
		} else {
			throw new SpecmateInternalException(ErrorCode.REST_SERVICE, "Attempt to delete non EObject.");
		}
	}

	/**
	 * Checks whether the update is either detached from any project or is part of
	 * the same project than the object represented by this resource.
	 *
	 * @param update  The update object for which to check the project
	 * @param recurse If true, also checks the projects for objects referenced by
	 *                the update
	 * @return
	 */
	private static boolean isProjectModificationRequestAuthorized(Object resourceObject, EObject update,
			boolean recurse) {

		if (!(resourceObject instanceof Resource) && resourceObject instanceof EObject) {
			EObject resourceEObject = (EObject) resourceObject;
			String currentProject = getProjectId(resourceEObject);
			String otherProject = getProjectId(update);
			if (!(otherProject == null || currentProject.equals(otherProject))) {
				return false;
			}
			if (recurse) {
				for (EReference reference : update.eClass().getEAllReferences()) {
					if (reference.isMany()) {
						for (EObject refObject : (List<EObject>) update.eGet(reference)) {
							if (!isProjectModificationRequestAuthorized(resourceObject, refObject, false)) {
								return false;
							}
						}
					} else {
						Object referenceObject = update.eGet(reference);
						if (referenceObject != null) {
							EObject referenceEObject = (EObject) referenceObject;
							if (!isProjectModificationRequestAuthorized(resourceObject, referenceEObject, false)) {
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}

	public static class CopierWithBidirectionalReferences extends EcoreUtil.Copier {

		// Copied from EcoreUtil.copyReference and removed check for bidirectional
		// references
		@Override
		protected void copyReference(EReference eReference, EObject eObject, EObject copyEObject) {
			if (eObject.eIsSet(eReference)) {
				EStructuralFeature.Setting setting = getTarget(eReference, eObject, copyEObject);
				if (setting != null) {
					Object value = eObject.eGet(eReference, resolveProxies);
					if (eReference.isMany()) {
						@SuppressWarnings("unchecked")
						InternalEList<EObject> source = (InternalEList<EObject>) value;
						@SuppressWarnings("unchecked")
						InternalEList<EObject> target = (InternalEList<EObject>) setting;
						if (source.isEmpty()) {
							target.clear();
						} else {
							boolean isBidirectional = eReference.getEOpposite() != null;
							int index = 0;
							for (Iterator<EObject> k = resolveProxies ? source.iterator() : source.basicIterator(); k
									.hasNext();) {
								EObject referencedEObject = k.next();
								EObject copyReferencedEObject = get(referencedEObject);
								if (copyReferencedEObject == null) {
									if (useOriginalReferences && !isBidirectional) {
										target.addUnique(index, referencedEObject);
										++index;
									}
								} else {
									if (isBidirectional) {
										int position = target.indexOf(copyReferencedEObject);
										if (position == -1) {
											target.addUnique(index, copyReferencedEObject);
										} else if (index != position) {
											target.move(index, copyReferencedEObject);
										}
									} else {
										target.addUnique(index, copyReferencedEObject);
									}
									++index;
								}
							}
						}
					} else {
						if (value == null) {
							setting.set(null);
						} else {
							Object copyReferencedEObject = get(value);
							if (copyReferencedEObject == null) {
								// Patched to enable bidirectional copies
								if (useOriginalReferences /* && eReference.getEOpposite() == null */) {
									setting.set(value);
								}
							} else {
								setting.set(copyReferencedEObject);
							}
						}
					}
				}
			}
		}

	}
}
