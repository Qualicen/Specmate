package com.specmate.connectors.fileconnector.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerFactory;

import com.google.common.io.PatternFilenameFilter;
import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.connectors.api.ConnectorBase;
import com.specmate.connectors.api.ConnectorUtil;
import com.specmate.connectors.api.IConnector;
import com.specmate.connectors.api.IProject;
import com.specmate.connectors.api.IProjectConfigService;
import com.specmate.connectors.fileconnector.internal.config.FileConnectorConfig;
import com.specmate.model.administration.ErrorCode;
import com.specmate.model.base.BaseFactory;
import com.specmate.model.base.Folder;
import com.specmate.model.base.IContainer;
import com.specmate.model.requirements.Requirement;
import com.specmate.model.requirements.RequirementsFactory;

/** Connector to the HP Proxy server. */
@Component(service = IConnector.class, immediate = true, configurationPid = FileConnectorConfig.PID, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class FileConnector extends ConnectorBase {

	/** Reference to the log service */
	@Reference(service = LoggerFactory.class)
	private Logger logger;

	/** The folder where to look for requirements */
	private String folder;

	/** Default sub folder where all requirements go to */
	private Folder defaultFolder;

	/** User name for the connector */
	private String user;

	/** Password for the connector */
	private String password;

	/** id of the project folder */
	private String id;

	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateException {
		validateConfig(properties);
		folder = (String) properties.get(FileConnectorConfig.KEY_FOLDER);
		user = (String) properties.get(FileConnectorConfig.KEY_USER);
		password = (String) properties.get(FileConnectorConfig.KEY_PASSWORD);
		id = (String) properties.get(IProjectConfigService.KEY_CONNECTOR_ID);
		defaultFolder = BaseFactory.eINSTANCE.createFolder();
		defaultFolder.setId("default");
		defaultFolder.setName("default");
		logger.info("Initialized file connector with " + properties.toString() + ".");
	}

	private void validateConfig(Map<String, Object> properties) throws SpecmateException {
		String folderName = (String) properties.get(FileConnectorConfig.KEY_FOLDER);
		if (StringUtils.isEmpty(folderName)) {
			throw new SpecmateInternalException(ErrorCode.CONFIGURATION, "Empty folder path.");
		}
		File file = new File(folderName);
		if (!file.exists() || !file.isDirectory()) {
			throw new SpecmateInternalException(ErrorCode.CONFIGURATION,
					"Folder with path " + folderName + " does not exist.");
		}
	}

	@Override
	public Collection<Requirement> getRequirements() {
		List<Requirement> requirements = new ArrayList<>();
		File file = new File(folder);
		if (file.isDirectory()) {
			FilenameFilter filter = new PatternFilenameFilter(".*\\.txt");
			File[] files = file.listFiles(filter);
			for (int i = 0; i < files.length; i++) {
				requirements.addAll(scanRequirementsFile(files[i]));
			}
		}
		return requirements;
	}

	private Collection<? extends Requirement> scanRequirementsFile(File file) {
		List<Requirement> requirements = new ArrayList<>();
		BufferedReader buffReader = null;
		try {
			FileReader reader = new FileReader(file);
			buffReader = new BufferedReader(reader);

		} catch (FileNotFoundException e) {
			logger.error("File not found " + file.getAbsolutePath() + ".");
		}

		String line;
		Requirement currentRequirement = RequirementsFactory.eINSTANCE.createRequirement();
		EScanState state = EScanState.TITLE;
		StringBuilder descriptionBuffer = new StringBuilder();
		try {
			line = buffReader.readLine();
			while (line != null) {
				switch (state) {
				case TITLE:
					if (!StringUtils.isEmpty(line)) {
						currentRequirement.setName(line);
						currentRequirement.setId(ConnectorUtil.toId(line));
						currentRequirement.setExtId(ConnectorUtil.toId(line));
						state = EScanState.DESCRIPTION;
					}
					break;
				case DESCRIPTION:
					if (StringUtils.isEmpty(line)) {
						currentRequirement.setDescription(descriptionBuffer.toString());
						requirements.add(currentRequirement);
						currentRequirement = RequirementsFactory.eINSTANCE.createRequirement();
						descriptionBuffer.setLength(0);
						descriptionBuffer.trimToSize();
						state = EScanState.TITLE;
					} else {
						descriptionBuffer.append(line);
					}
				}
				line = buffReader.readLine();
			}
			if (state == EScanState.DESCRIPTION) {
				currentRequirement.setDescription(descriptionBuffer.toString());
				requirements.add(currentRequirement);
			}
			return requirements;
		} catch (IOException e) {
			logger.error("Could not read from file " + file.getAbsolutePath() + ".");
			return Collections.emptyList();
		} finally {
			if (buffReader != null) {
				try {
					buffReader.close();
				} catch (IOException e) {
					logger.error("Could not close file stream to " + file.getAbsolutePath() + ".");
				}
			}
		}
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public IContainer getContainerForRequirement(Requirement requirement) {
		return defaultFolder;
	}

	private enum EScanState {
		TITLE, DESCRIPTION
	}

	@Override
	public Set<IProject> authenticate(String username, String password) {
		if (username.equals(user) && password.equals(this.password)) {
			return new HashSet<IProject>(Arrays.asList(getProject()));
		} else {
			return new HashSet<IProject>();
		}
	}

	@Override
	public Requirement getRequirementById(String id) throws SpecmateException {
		return null;
	}
}
