package com.specmate.connectors.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Dictionary;

import org.junit.Test;
import org.mockito.Mockito;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.log.Logger;

import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateValidationException;
import com.specmate.config.api.IConfigService;
import com.specmate.connectors.internal.config.ConnectorServiceConfig;
import com.specmate.connectors.internal.config.PollKeys;

public class ConnectorServiceTest {

	@Test
	public void testConnectorServiceDisabling()
			throws SpecmateException, SpecmateValidationException, InterruptedException {
		ConnectorServiceConfig connectorConfig = new ConnectorServiceConfig();
		connectorConfig.setLogger(mock(Logger.class));

		IConfigService configServiceMock = mock(IConfigService.class);
		when(configServiceMock.getConfigurationProperty(PollKeys.KEY_POLL_SCHEDULE, PollKeys.DISABLED_STRING))
				.thenReturn(PollKeys.DISABLED_STRING);
		connectorConfig.setConfigurationService(configServiceMock);

		ConfigurationAdmin configAdminMock = mock(ConfigurationAdmin.class);
		connectorConfig.setConfigurationAdmin(configAdminMock);

		connectorConfig.configureConnectorService();

		verifyZeroInteractions(configAdminMock);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testConnectorServiceEnabling()
			throws SpecmateException, SpecmateValidationException, InterruptedException, IOException {
		ConnectorServiceConfig connectorConfig = new ConnectorServiceConfig();
		connectorConfig.setLogger(mock(Logger.class));

		IConfigService configServiceMock = mock(IConfigService.class);
		when(configServiceMock.getConfigurationProperty(PollKeys.KEY_POLL_SCHEDULE, PollKeys.DISABLED_STRING))
				.thenReturn("* * * * *");
		connectorConfig.setConfigurationService(configServiceMock);

		ConfigurationAdmin configAdminMock = mock(ConfigurationAdmin.class);
		Configuration configurationMock = mock(Configuration.class);
		when(configAdminMock.getConfiguration(Mockito.any(String.class))).thenReturn(configurationMock);
		connectorConfig.setConfigurationAdmin(configAdminMock);

		connectorConfig.configureConnectorService();

		verify(configurationMock, Mockito.atLeastOnce()).update(Mockito.any(Dictionary.class));
	}
}
