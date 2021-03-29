package com.specmate.emfrest.authentication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.specmate.connectors.api.IProjectService;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.rest.RestResult;

@Component(service = IRestService.class)
public class LoginPoints extends RestServiceBase {
	public static final String SERVICE_NAME = "loginpoints";
	private IProjectService projectService;

	@Override
	public String getServiceName() {
		return SERVICE_NAME;
	}

	@Override
	public boolean canGet(Object target) {
		return (target instanceof Resource);
	}

	@Override
	public RestResult<?> get(Object object, MultivaluedMap<String, String> queryParams, String token) {

		Map<String, String> loginPoints = projectService.getLoginPoints();
		
//		GenericEntity entity = new GenericEntity<Map<String, String>>(loginPoints){};
//		return new RestResult<>(Response.Status.OK, entity);
		
		return new RestResult<>(Response.Status.OK, loginPoints );
	}

	@Reference
	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}
}
