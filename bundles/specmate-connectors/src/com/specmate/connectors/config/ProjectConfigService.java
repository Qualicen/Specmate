package com.specmate.connectors.config;

import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerFactory;

import com.specmate.common.OSGiUtil;
import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.config.api.IConfigService;
import com.specmate.connectors.api.Configurable;
import com.specmate.connectors.api.IProjectConfigService;
import com.specmate.model.administration.ErrorCode;
import com.specmate.model.base.BaseFactory;
import com.specmate.model.base.Folder;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.IChange;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;
import com.specmate.persistency.validation.TopLevelValidator;

/**
 * Service that configures connectors, exporters and top-level library folders
 * based on configured projects
 */
@Component(immediate = true)
public class ProjectConfigService implements IProjectConfigService {
	/** The config service */
	private IConfigService configService;

	/** The config admin service. */
	private ConfigurationAdmin configAdmin;

	/** Reference to the log service */
	@Reference(service = LoggerFactory.class)
	private Logger logger;

	/** The persistency service to access the model data */
	private IPersistencyService persistencyService;

	@Activate
	public void activate() throws SpecmateException {
		String[] projectsIDs = configService.getConfigurationPropertyArray(KEY_PROJECT_IDS);
		if (projectsIDs != null) {
			configureProjects(projectsIDs);
		}

		String[] multiProjectsIDs = configService.getConfigurationPropertyArray(KEY_MULTIPROJECT_IDS);
		if (multiProjectsIDs != null) {
			configureMultiProjects(multiProjectsIDs);
		}
	}

	@Override
	public void configureProjects(String[] projectsIDs) throws SpecmateException {
		for (int i = 0; i < projectsIDs.length; i++) {
			String projectID = projectsIDs[i];
			try {
				String projectPrefix = PROJECT_PREFIX + projectID;

				Configurable connector = createConnector(projectPrefix, projectID);
				if (connector != null) {
					configureConfigurable(connector);
				}
				Configurable exporter = createExporter(projectPrefix, projectID);
				if (exporter != null) {
					configureConfigurable(exporter);
				}

				ensureProjectFolder(projectID);
				configureProject(projectID, connector, exporter);
				bootstrapProjectLibrary(projectID);
			} catch (SpecmateException e) {
				logger.error("Could not create project " + projectID, e);
			}
		}
	}

	@Override
	public void configureMultiProjects(String[] multiProjectsIDs) throws SpecmateException {
		for (int i = 0; i < multiProjectsIDs.length; i++) {
			String multiProjectID = multiProjectsIDs[i];
			try {
				String multiProjectPrefix = MULTIPROJECT_PREFIX + multiProjectID;

				Configurable multiConnector = createMultiConnector(multiProjectPrefix, multiProjectID);
				if (multiConnector != null) {
					configureConfigurable(multiConnector);
				}
				configureMultiProject(multiProjectID, multiConnector);
			} catch (SpecmateException e) {
				logger.error("Could not create multi project " + multiProjectID, e);
			}
		}
	}

	private void ensureProjectFolder(String projectID) throws SpecmateException {
		ITransaction trans = null;

		try {
			trans = persistencyService.openTransaction();
			trans.removeValidator(TopLevelValidator.class.getName());
			EList<EObject> projects = trans.getResource().getContents();

			EObject obj = SpecmateEcoreUtil.getEObjectWithId(projectID, projects);
			if (obj == null || !(obj instanceof Folder)) {

				trans.doAndCommit(() -> {
					Folder folder = BaseFactory.eINSTANCE.createFolder();
					folder.setName(projectID);
					folder.setId(projectID);
					projects.add(folder);
					return null;
				});
			}

		} finally {
			if (trans != null) {
				trans.close();
			}
		}
	}

	/**
	 * Configures a single project with a given connector and exporter description
	 */
	private void configureProject(String projectID, Configurable connector, Configurable exporter)
			throws SpecmateException {
		String exporterFilter;
		if (exporter != null) {
			exporterFilter = "(" + KEY_EXPORTER_ID + "=" + projectID + ")";
		} else {
			exporterFilter = "(" + KEY_EXPORTER_ID + "= NO_ID)";
		}

		String connectorFilter;
		if (connector != null) {
			connectorFilter = "(" + KEY_CONNECTOR_ID + "=" + projectID + ")";
		} else {
			connectorFilter = "(" + KEY_CONNECTOR_ID + "= NO_ID)";
		}

		Hashtable<String, Object> projectConfig = new Hashtable<String, Object>();
		projectConfig.put(KEY_PROJECT_ID, projectID);

		// Set the target of the 'exporter' reference in the Project.
		// This ensures that the right exporter will be bound to the project.
		projectConfig.put("exporter.target", exporterFilter);

		// Set the target of the 'connector' reference in the Project.
		// This ensures that the right connector will be bound to the project.
		projectConfig.put("connector.target", connectorFilter);

		String projectLibraryKey = PROJECT_PREFIX + projectID + KEY_PROJECT_LIBRARY;
		String[] libraryFolders = configService.getConfigurationPropertyArray(projectLibraryKey);
		if (libraryFolders != null) {
			projectConfig.put(KEY_PROJECT_LIBRARY_FOLDERS, libraryFolders);
		}

		OSGiUtil.configureFactory(configAdmin, PROJECT_CONFIG_FACTORY_PID, projectConfig);
	}

	/**
	 * Configures a single multi project
	 */
	private void configureMultiProject(String multiProjectID, Configurable multiConnector) throws SpecmateException {

		String multiConnectorFilter;
		if (multiConnector != null) {
			multiConnectorFilter = "(" + KEY_CONNECTOR_ID + "=" + multiProjectID + ")";
		} else {
			multiConnectorFilter = "(" + KEY_CONNECTOR_ID + "= NO_ID)";
		}

		Hashtable<String, Object> multiProjectConfig = new Hashtable<String, Object>();
		multiProjectConfig.put(KEY_PROJECT_ID, multiProjectID);

		// Set the target of the 'multiconnector' reference in the Project.
		// This ensures that the right multiconnector will be bound to the project.
		multiProjectConfig.put("multiconnector.target", multiConnectorFilter);

		// multiproject.<projectname>.projectnamepattern
		String projectNamePattern = configService.getConfigurationProperty(IProjectConfigService.MULTIPROJECT_PREFIX
				+ multiProjectID + "." + IProjectConfigService.KEY_MULTIPROJECT_PROJECTNAMEPATTERN);
		if (projectNamePattern != null) {
			multiProjectConfig.put(IProjectConfigService.KEY_MULTIPROJECT_PROJECTNAMEPATTERN, projectNamePattern);
		}

		// multiproject.<projectname>.maxprojects
		String maxprojects = configService.getConfigurationProperty(IProjectConfigService.MULTIPROJECT_PREFIX
				+ multiProjectID + "." + IProjectConfigService.KEY_MULTIPROJECT_MAXNUMBEROFPROJECTS);
		if (maxprojects != null) {
			multiProjectConfig.put(IProjectConfigService.KEY_MULTIPROJECT_MAXNUMBEROFPROJECTS, maxprojects);
		}

		// multiproject.<projectname>.template.*
		Set<Entry<Object, Object>> templateConfigEntries = configService
				.getConfigurationProperties(IProjectConfigService.MULTIPROJECT_PREFIX + multiProjectID + "."
						+ IProjectConfigService.KEY_MULTIPROJECT_TEMPLATE);
		addConfigEntriesToConfig(multiProjectConfig, templateConfigEntries);

		OSGiUtil.configureFactory(configAdmin, MULTIPROJECT_CONFIG_FACTORY_PID, multiProjectConfig);
	}

	/**
	 * Adds a set of config entries to a given project config. Checks for
	 * duplicates.
	 * 
	 * @param projectConfig
	 * @param configEntriesToAdd
	 */
	private void addConfigEntriesToConfig(Hashtable<String, Object> projectConfig,
			Set<Entry<Object, Object>> configEntriesToAdd) {

		for (Entry<Object, Object> newConfigEntry : configEntriesToAdd) {
			Object newConfigEntryKey = newConfigEntry.getKey();

			if (!projectConfig.containsKey(newConfigEntryKey)) {
				projectConfig.put((String) newConfigEntry.getKey(), (String) newConfigEntry.getValue());
			} else {
				logger.warn("Duplicated config entries for key " + newConfigEntryKey);
			}
		}
	}

	/**
	 * Creates an exporter from the config for the project given by the config
	 * prefix.
	 *
	 * @param projectID
	 */
	private Configurable createExporter(String projectPrefix, String projectID) {
		String exporterPrefix = projectPrefix + "." + "exporter";
		Configurable exporter = new Configurable();
		exporter = fillConfigurable(exporter, exporterPrefix);
		if (exporter == null) {
			return null;
		}
		exporter.addConfigValue(KEY_EXPORTER_ID, projectID);
		return exporter;
	}

	/**
	 * Creates an connector from the config for the project given by the config
	 * prefix.
	 *
	 * @param projectID
	 */
	private Configurable createConnector(String projectPrefix, String projectID) {
		String connectorPrefix = projectPrefix + "." + "connector";
		Configurable connector = new Configurable();
		connector = fillConfigurable(connector, connectorPrefix);
		if (connector == null) {
			return null;
		}
		connector.addConfigValue(KEY_CONNECTOR_ID, projectID);
		return connector;
	}

	/**
	 * Creates an connector from the config for the project given by the config
	 * prefix.
	 *
	 * @param multiProjectID
	 */
	private Configurable createMultiConnector(String multiProjectPrefix, String multiProjectID) {
		String connectorPrefix = multiProjectPrefix + "." + "multiconnector";
		Configurable connector = new Configurable();
		connector = fillConfigurable(connector, connectorPrefix);
		if (connector == null) {
			return null;
		}
		connector.addConfigValue(KEY_CONNECTOR_ID, multiProjectID);
		return connector;
	}

	/** Configures a configurable with the ConfigAdmin */
	private void configureConfigurable(Configurable configurable) {
		try {
			OSGiUtil.configureFactory(configAdmin, configurable.getPid(), configurable.getConfig());
		} catch (SpecmateException e) {
			logger.error("Failed attempt to configure " + configurable.getPid() + " with config "
					+ OSGiUtil.configDictionaryToString(configurable.getConfig()), e);
		}
	}

	/** Fills the config entries into the configurable object. */
	private <T extends Configurable> T fillConfigurable(T configurable, String prefix) {

		Set<Entry<Object, Object>> config = configService.getConfigurationProperties(prefix);
		if (config == null || config.isEmpty()) {
			return null;
		}

		Hashtable<String, Object> configTable = new Hashtable<>();
		for (Entry<Object, Object> configEntry : config) {
			String key = (String) configEntry.getKey();
			String connectorConfigKey = key.substring(prefix.length() + 1);
			String pidKey = "pid";
			if (connectorConfigKey.equals(pidKey)) {
				configurable.setPid((String) configEntry.getValue());
			} else {
				configTable.put(connectorConfigKey, configEntry.getValue());
			}
		}
		configurable.setConfig(configTable);
		return configurable;
	}

	/** Creates top-level library folders, if necessary */
	private void bootstrapProjectLibrary(String projectID) throws SpecmateException {
		ITransaction trans = null;

		try {
			trans = persistencyService.openTransaction();
			trans.removeValidator(TopLevelValidator.class.getName());
			EList<EObject> projects = trans.getResource().getContents();
			if (projects == null || projects.size() == 0) {
				return;
			}

			EObject obj = SpecmateEcoreUtil.getEObjectWithId(projectID, projects);
			if (obj == null || !(obj instanceof Folder)) {
				throw new SpecmateInternalException(ErrorCode.CONFIGURATION,
						"Expected project " + projectID + " not found in database");
			}

			trans.doAndCommit(new LibraryFolderUpdater((Folder) obj));

		} finally {
			if (trans != null) {
				trans.close();
			}
		}
	}

	private class LibraryFolderUpdater implements IChange<Object> {
		private Folder projectFolder;

		public LibraryFolderUpdater(Folder projectFolder) {
			this.projectFolder = projectFolder;
		}

		@Override
		public Object doChange() throws SpecmateException {
			String projectID = projectFolder.getId();
			String projectLibraryKey = PROJECT_PREFIX + projectID + KEY_PROJECT_LIBRARY;
			String[] libraryFolders = configService.getConfigurationPropertyArray(projectLibraryKey);
			if (libraryFolders != null) {
				for (int i = 0; i < libraryFolders.length; i++) {
					String projectLibraryId = libraryFolders[i];
					String libraryName = configService.getConfigurationProperty(
							projectLibraryKey + "." + projectLibraryId + KEY_PROJECT_LIBRARY_NAME);
					String libraryDescription = configService.getConfigurationProperty(
							projectLibraryKey + "." + projectLibraryId + KEY_PROJECT_LIBRARY_DESCRIPTION);

					EObject obj = SpecmateEcoreUtil.getEObjectWithId(projectLibraryId, projectFolder.eContents());
					Folder libraryFolder = null;
					if (obj == null) {
						libraryFolder = BaseFactory.eINSTANCE.createFolder();
						libraryFolder.setId(projectLibraryId);
						libraryFolder.setLibrary(true);
						projectFolder.getContents().add(libraryFolder);
					} else {
						assert (obj instanceof Folder);
						libraryFolder = (Folder) obj;
						libraryFolder.setId(projectLibraryId);
					}

					libraryFolder.setName(libraryName);
					libraryFolder.setDescription(libraryDescription);
				}
			}

			return null;
		}
	}

	@Reference
	public void setConfigService(IConfigService configService) {
		this.configService = configService;
	}

	@Reference
	public void setConfigurationAdmin(ConfigurationAdmin configAdmin) {
		this.configAdmin = configAdmin;
	}

	@Reference
	public void setPersistencyService(IPersistencyService persistencyService) {
		this.persistencyService = persistencyService;
	}
}
