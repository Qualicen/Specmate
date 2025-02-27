package com.specmate.emfrest.crud;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.specmate.auth.api.IAuthenticationService;
import com.specmate.common.exception.SpecmateException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.rest.RestResult;

@Component(immediate = true, service = IRestService.class)
public class ListService extends RestServiceBase {
	private static final String NOTLOADINGANNOTATIONURL = "http://specmate.com/notLoadingOnList";

	private IAuthenticationService authService;

	@Override
	public String getServiceName() {
		return "list";
	}

	@Override
	public boolean canGet(Object target) {
		return (target instanceof EObject || target instanceof Resource);
	}

	@Override
	public RestResult<?> get(Object target, MultivaluedMap<String, String> queryParams, String token)
			throws SpecmateException {
		List<EObject> children = SpecmateEcoreUtil.getChildren(target);
		children = children.stream().filter(element -> element.eClass().getEAnnotation(NOTLOADINGANNOTATIONURL) == null)
				.collect(Collectors.toList());
		return new RestResult<>(Response.Status.OK, children);
	}

	@Override
	public boolean canPost(Object parent, Object toAdd) {
		return (parent instanceof EObject || parent instanceof Resource) && (toAdd instanceof EObject);
	}

	@Override
	public RestResult<?> post(Object parent, Object toAdd, MultivaluedMap<String, String> queryParams, String token)
			throws SpecmateException {
		return CrudUtil.create(parent, (EObject) toAdd, authService.getUserName(token));
	}

	@Reference
	public void setAuthService(IAuthenticationService authService) {
		this.authService = authService;
	}
}
