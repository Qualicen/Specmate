package com.specmate.connectors.internal;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.eclipse.emf.ecore.EObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerFactory;

import com.specmate.common.exception.SpecmateException;
import com.specmate.connectors.api.IConnector;
import com.specmate.connectors.api.IProject;
import com.specmate.connectors.api.IProjectService;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.model.base.Folder;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;
import com.specmate.persistency.validation.TopLevelValidator;
import com.specmate.rest.RestResult;

@Component(immediate = true, service = IRestService.class)
public class SyncSingleRequirementService extends RestServiceBase {

	/** Reference to the log service */
	@Reference(service = LoggerFactory.class)
	private Logger logger;

	/** The persistence service */
	private IPersistencyService persistencyService;

	/** The transaction */
	private ITransaction transaction;

	/** The project service */
	private IProjectService projectService;

	@Override
	public String getServiceName() {
		return "syncrequirement";
	}

	@Override
	public boolean canGet(Object target) {
		return target instanceof Folder;
	}

	@Override
	public RestResult<?> get(Object object, MultivaluedMap<String, String> queryParams, String token)
			throws SpecmateException {
		if (queryParams.get("id") != null) {
			String id = queryParams.get("id").get(0);
			this.transaction = this.persistencyService.openTransaction();
			this.transaction.removeValidator(TopLevelValidator.class.getName());

			String projectId = SpecmateEcoreUtil.getProjectId((EObject) object);
			IProject project = projectService.getProject(projectId);
			IConnector source = project.getConnector();

			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						ConnectorUtil.syncRequirementById(id, source, transaction, logger);
					} finally {
						transaction.close();
					}
				}
			}, "sync-single-requirement-service-task").start();

			return new RestResult<>(Response.Status.OK,
					"This function is executed asynchronously. The OK does not indicate whether the sync was successful or not.");
		}
		return new RestResult<>(Response.Status.BAD_REQUEST);
	}

	@Reference
	public void setPersistency(IPersistencyService persistencyService) {
		this.persistencyService = persistencyService;
	}

	@Reference
	public void setProjectService(IProjectService projectService) {
		this.projectService = projectService;
	}
}
