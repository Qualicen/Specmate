package com.specmate.emfrest.authentication;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerFactory;

import com.specmate.auth.api.IAuthenticationService;
import com.specmate.common.exception.SpecmateException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.rest.RestResult;

@Component(service = IRestService.class)
public class Logout extends RestServiceBase {
	public static final String SERVICE_NAME = "logout";
	private IAuthenticationService authService;

	/** Reference to the log service */
	@Reference(service = LoggerFactory.class)
	private Logger logger;

	@Override
	public String getServiceName() {
		return SERVICE_NAME;
	}

	@Override
	public boolean canGet(Object target) {
		return true;
	}

	@Override
	public RestResult<?> get(Object object, MultivaluedMap<String, String> queryParams, String token)
			throws SpecmateException {

		authService.deauthenticate(token);
		logger.info("Session " + token + " deleted.");

		return new RestResult<>(Response.Status.OK, token);
	}

	@Reference
	public void setAuthService(IAuthenticationService authService) {
		this.authService = authService;
	}
}
