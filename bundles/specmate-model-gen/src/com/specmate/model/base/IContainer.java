/**
 */
package com.specmate.model.base;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IContainer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.base.IContainer#getContents <em>Contents</em>}</li>
 * </ul>
 *
 * @see com.specmate.model.base.BasePackage#getIContainer()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface IContainer extends IContentElement {
	/**
	 * Returns the value of the '<em><b>Contents</b></em>' containment reference list.
	 * The list contents are of type {@link com.specmate.model.base.IContentElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contents</em>' containment reference list.
	 * @see com.specmate.model.base.BasePackage#getIContainer_Contents()
	 * @model containment="true"
	 * @generated
	 */
	EList<IContentElement> getContents();

} // IContainer
