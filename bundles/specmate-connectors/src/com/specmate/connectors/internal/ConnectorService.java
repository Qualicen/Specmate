package com.specmate.connectors.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.cdo.common.id.CDOWithID;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerFactory;

import com.specmate.common.exception.SpecmateException;
import com.specmate.connectors.api.IConnector;
import com.specmate.connectors.internal.config.ConnectorServiceConfig;
import com.specmate.connectors.internal.config.PollKeys;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;
import com.specmate.persistency.validation.TopLevelValidator;
import com.specmate.scheduler.Scheduler;
import com.specmate.scheduler.SchedulerIteratorFactory;
import com.specmate.scheduler.SchedulerTask;
import com.specmate.search.api.IModelSearchService;

@Component(immediate = true, configurationPid = ConnectorServiceConfig.PID, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class ConnectorService {
	CDOWithID id;
	List<IConnector> connectors = new ArrayList<>();
	/** Reference to the log service */
	@Reference(service = LoggerFactory.class)
	private Logger logger;
	private IPersistencyService persistencyService;
	private IModelSearchService modelSearchService;
	private ITransaction transaction;

	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateException {
		validateConfig(properties);
		
		String schedule = (String) properties.get(PollKeys.KEY_POLL_SCHEDULE);
		if (schedule == null) {
			logger.info("Polling interval '" + PollKeys.KEY_POLL_SCHEDULE + "' not set.");
			return;
		}

		this.transaction = this.persistencyService.openTransaction();
		this.transaction.removeValidator(TopLevelValidator.class.getName());

		ConnectorService connectorService = this;

		new Thread(new Runnable() {
			@Override
			public void run() {

				// Ensure that requirements source are loaded.
				while (connectors.size() == 0) {
					try {
						logger.info("No connectors here yet. Waiting.");
						// Connectors could be added after the component is activated
						Thread.sleep(20 * 1000);
					} catch (InterruptedException e) {
						logger.error(e.getMessage());
					}
				}

				try {
					SchedulerTask connectorRunnable = new ConnectorTask(connectorService, transaction, logger);
					connectorRunnable.run();
					modelSearchService.startReIndex();
					Scheduler scheduler = new Scheduler(logger);
					scheduler.schedule(connectorRunnable, SchedulerIteratorFactory.create(schedule));
				} catch (SpecmateException e) {
					e.printStackTrace();
					logger.error("Could not create schedule iterator.", e);
				}
			}
		}, "connector-service-initializer").start();
	}

	private void validateConfig(Map<String, Object> properties) throws SpecmateException {
		SchedulerIteratorFactory.validate((String) properties.get(PollKeys.KEY_POLL_SCHEDULE));
		logger.debug("Connector service config validated.");
	}

	@Deactivate
	public void deactivate() {
		transaction.close();
	}

	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	public void addConnector(IConnector source) {
		this.connectors.add(source);
	}

	public void removeConnector(IConnector source) {
		this.connectors.remove(source);
	}

	public List<IConnector> getConnectors() {
		return List.copyOf(connectors);
	}

	@Reference
	public void setPersistency(IPersistencyService persistencyService) {
		this.persistencyService = persistencyService;
	}

	@Reference
	public void setModelSearchService(IModelSearchService modelSearchService) {
		this.modelSearchService = modelSearchService;
	}

	public void unsetPersistency(IPersistencyService persistencyService) {
		this.persistencyService = null;
	}
}
