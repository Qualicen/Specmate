package com.specmate.connectors.internal;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.eclipse.emf.ecore.EObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerFactory;

import com.specmate.auth.api.IAuthenticationService;
import com.specmate.common.exception.SpecmateAuthorizationException;
import com.specmate.common.exception.SpecmateException;
import com.specmate.connectors.api.IProject;
import com.specmate.connectors.api.IProjectService;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.model.testspecification.TestProcedure;
import com.specmate.model.testspecification.TestSpecification;
import com.specmate.rest.RestResult;
import com.specmate.usermodel.AccessRights;

@Component(immediate = true, service = IRestService.class)
public class ALMExportService extends RestServiceBase {

	/** The log service */
	@Reference(service = LoggerFactory.class)
	private Logger logger;

	/** The project service */
	private IProjectService projectService;

	/** The authentication service */
	private IAuthenticationService authService;

	@Override
	public String getServiceName() {
		return "syncalm";
	}

	@Override
	public boolean canPost(Object target, Object object) {
		return target instanceof TestProcedure || target instanceof TestSpecification;
	}

	@Override
	public RestResult<?> post(Object target, Object object, MultivaluedMap<String, String> queryParams, String token)
			throws SpecmateException {
		if (isAuthorizedToExport(token)) {
			TestProcedure testProcedure = (TestProcedure) target;
			String projectName = SpecmateEcoreUtil.getProjectId((EObject) target);
			logger.info("Synchronizing test procedure " + testProcedure.getName());
			IProject project = projectService.getProject(projectName);
			project.getExporter().export(testProcedure);
			return new RestResult<>(Response.Status.OK, testProcedure);
		} else {
			throw new SpecmateAuthorizationException("User is not authorized to export.");
		}
	}

	@Reference
	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}

	@Reference
	public void setAuthService(IAuthenticationService authService) {
		this.authService = authService;
	}

	private boolean isAuthorizedToExport(String token) throws SpecmateException {
		AccessRights export = authService.getTargetAccessRights(token);
		return export.equals(AccessRights.ALL) || export.equals(AccessRights.WRITE);
	}
}
