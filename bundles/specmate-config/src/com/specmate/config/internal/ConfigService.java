package com.specmate.config.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerFactory;

import com.specmate.config.api.IConfigService;

/** Service for providing configurations for other services. */
@Component(immediate = true)
public class ConfigService implements IConfigService {

	/** Command line switch to specify the configuration file. */
	private static final String CONFIGURATION_FILE = "configurationFile";

	/** Key for the command line arguments (bnd-specific) */
	private static final String LAUNCHER_ARGUMENTS = "launcher.arguments";

	/** The command line arguments */
	private String[] commandLineArguments;

	/** The current configuration */
	private Properties configuration = new Properties();

	/** Reference to the log service */
	@Reference(service = LoggerFactory.class)
	private Logger logger;

	/** The bundle context of the containing bundle */
	private BundleContext bundleContext;

	@Activate
	public void activate(BundleContext context) throws IOException {
		bundleContext = context;
		processCommandLineArguments();
		readConfigurationFile();
	}

	/** Reads the command line arguments into the configuration properties. */
	private void processCommandLineArguments() {
		String lastSwitch = null;
		for (int i = 0; i < commandLineArguments.length; i++) {
			String arg = commandLineArguments[i];
			if (arg.startsWith("--")) {
				lastSwitch = arg.substring(2);
				configuration.setProperty(lastSwitch, "");
			} else if (lastSwitch != null) {
				configuration.setProperty(lastSwitch, arg);
				lastSwitch = null;
			}
		}
	}

	/** Reads additional properties from a configuration file. */
	private void readConfigurationFile() {
		InputStream configInputStream = null;
		Properties properties = new Properties();
		String configurationFileLocation = configuration.getProperty(CONFIGURATION_FILE);
		try {
			if (!StringUtils.isEmpty(configurationFileLocation)) {
				File file = new File(configurationFileLocation);
				configInputStream = new FileInputStream(file);
			} else {
				URL configUrl = bundleContext.getBundle().getResource("config/specmate-config.properties");
				configInputStream = configUrl.openStream();
			}
			properties.load(new InputStreamReader(configInputStream, Charset.forName("UTF-8")));
		} catch (FileNotFoundException e) {
			logger.error("Configuration file " + configurationFileLocation
					+ " does not exist. Using default configuration.");
			return;
		} catch (IOException e) {
			logger.error("Configuration file " + configurationFileLocation
					+ " could not be read. Using default configuration.", e);
			return;
		} finally {
			if (configInputStream != null) {
				try {
					configInputStream.close();
				} catch (IOException e) {
					// stream could not be closed
				}
			}
		}
		properties.putAll(configuration);
		configuration = properties;
	}

	/** Retreives all configured properties with a given prefix */
	@Override
	public Set<Entry<Object, Object>> getConfigurationProperties(String prefix) {
		return configuration.entrySet().stream().filter(entry -> ((String) entry.getKey()).startsWith(prefix))
				.collect(Collectors.toSet());
	}

	/** Retreives a configured property. */
	@Override
	public String getConfigurationProperty(String key) {
		return configuration.getProperty(key);
	}

	/**
	 * Retreives a configured property. Retruns the default value if no entry is
	 * found in the configuration.
	 */
	@Override
	public String getConfigurationProperty(String key, String defaultValue) {
		return configuration.getProperty(key, defaultValue);
	}

	/** Retreives a configured property as integer. */
	@Override
	public Integer getConfigurationPropertyInt(String key) {
		String property = configuration.getProperty(key);
		if (property != null) {
			return Integer.parseInt(property);
		} else {
			return null;
		}
	}

	/**
	 * Retreives a configured property as an integer. Returns the default value if
	 * no entry is found in the configuration.
	 */
	@Override
	public Integer getConfigurationPropertyInt(String key, int defaultValue) {
		Integer property = getConfigurationPropertyInt(key);
		if (property == null) {
			return defaultValue;
		} else {
			return property;
		}
	}

	/** Retrieves a configured property as a list. */
	@Override
	public String[] getConfigurationPropertyArray(String key) {
		String property = configuration.getProperty(key);
		if (property != null) {
			String[] items = StringUtils.split(property, ",");
			for (int i = 0; i < items.length; i++) {
				items[i] = items[i].trim();
			}
			return items;
		} else {
			return null;
		}
	}

	/**
	 * Retrieves a configured property as a list. Returns the default value if no
	 * entry is found in the configuration.
	 */
	@Override
	public String[] getConfigurationPropertyArray(String key, String[] defaultValue) {
		String[] value = getConfigurationPropertyArray(key);

		if (value != null) {
			return value;
		} else {
			return defaultValue;
		}
	}

	/**
	 * The bnd launcher provides access to the command line arguments via the
	 * Launcher object. This object is registered under Object.
	 */
	@Reference
	public void setBndDoneObject(Object done, Map<String, Object> parameters) {
		String[] args = (String[]) parameters.get(LAUNCHER_ARGUMENTS);
		List<String> l = new ArrayList<>();

		// This makes sure that the command line arguments are in the form as expected
		// by the parser, i.e. switch and parameter are 2 elements in the array.
		for (int i = 0; i < args.length; i++) {
			l.addAll(Arrays.asList(args[i].split(" ")));
		}

		commandLineArguments = l.toArray(new String[0]);
	}

	/** Adds the given entries to the config */
	@Override
	public void addUpdateConfigurationProperties(Map<String, String> entries) {
		configuration.putAll(entries);
	}

	/** Adds a single entry to the config */
	@Override
	public void addUpdateConfigurationProperty(String key, String value) {
		configuration.put(key, value);
	}
}
