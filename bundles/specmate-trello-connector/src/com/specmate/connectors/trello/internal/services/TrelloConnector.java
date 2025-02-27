package com.specmate.connectors.trello.internal.services;

import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.Logger;
import org.osgi.service.log.LoggerFactory;

import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.connectors.api.ConnectorBase;
import com.specmate.connectors.api.IConnector;
import com.specmate.connectors.api.IProject;
import com.specmate.connectors.api.IProjectConfigService;
import com.specmate.connectors.trello.config.TrelloConnectorConfig;
import com.specmate.model.administration.ErrorCode;
import com.specmate.model.base.BaseFactory;
import com.specmate.model.base.Folder;
import com.specmate.model.base.IContainer;
import com.specmate.model.requirements.Requirement;
import com.specmate.model.requirements.RequirementsFactory;
import com.specmate.rest.RestClient;
import com.specmate.rest.RestResult;

@Component(immediate = true, service = IConnector.class, configurationPid = TrelloConnectorConfig.PID, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class TrelloConnector extends ConnectorBase {

	private static final String TRELLO_API_BASE_URL = "https://api.trello.com";
	private static final int TIMEOUT = 5000;
	/** Reference to the log service */
	@Reference(service = LoggerFactory.class)
	private Logger logger;
	private RestClient restClient;
	private String boardId;
	private String key;
	private String token;
	private String id;

	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateException {
		validateConfig(properties);
		boardId = (String) properties.get(TrelloConnectorConfig.KEY_BOARD_ID);
		key = (String) properties.get(TrelloConnectorConfig.KEY_TRELLO_KEY);
		token = (String) properties.get(TrelloConnectorConfig.KEY_TRELLO_TOKEN);
		id = (String) properties.get(IProjectConfigService.KEY_CONNECTOR_ID);
		restClient = new RestClient(TRELLO_API_BASE_URL, TIMEOUT, logger);
	}

	private void validateConfig(Map<String, Object> properties) throws SpecmateException {
		String aBoardId = (String) properties.get(TrelloConnectorConfig.KEY_BOARD_ID);
		String aKey = (String) properties.get(TrelloConnectorConfig.KEY_TRELLO_KEY);
		String aToken = (String) properties.get(TrelloConnectorConfig.KEY_TRELLO_TOKEN);

		if (isEmpty(aBoardId) || isEmpty(aKey) || isEmpty(aToken)) {
			throw new SpecmateInternalException(ErrorCode.CONFIGURATION, "Trello Connector is not well configured.");
		}
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public Collection<Requirement> getRequirements() throws SpecmateException {
		RestResult<JSONArray> restResult = restClient.getList("/1/boards/" + boardId + "/cards",
				Map.of("key", key, "token", token));
		if (restResult.getResponse().getStatus() == Status.OK.getStatusCode()) {
			restResult.getResponse().close();
			List<Requirement> requirements = new ArrayList<>();
			JSONArray cardsArray = restResult.getPayload();
			for (int i = 0; i < cardsArray.length(); i++) {
				JSONObject cardObject = cardsArray.getJSONObject(i);
				requirements.add(makeRequirementFromCard(cardObject));
			}
			return requirements;
		} else {
			restResult.getResponse().close();
			throw new SpecmateInternalException(ErrorCode.TRELLO, "Could not retrieve list of trello cards.");
		}
	}

	@Override
	public IContainer getContainerForRequirement(Requirement requirement) throws SpecmateException {
		RestResult<JSONObject> restResult = restClient.get("/1/cards/" + requirement.getExtId2() + "/list",
				Map.of("key", key, "token", token));
		if (restResult.getResponse().getStatus() == Status.OK.getStatusCode()) {
			JSONObject listObject = restResult.getPayload();
			return makeFolderFromList(listObject);
		} else {
			throw new SpecmateInternalException(ErrorCode.TRELLO,
					"Could not retrieve list for trello card: " + requirement.getExtId2() + ".");
		}
	}

	public List<Folder> getLists() throws SpecmateException {
		RestResult<JSONArray> restResult = restClient.getList("/1/boards/" + boardId + "/lists",
				Map.of("cards", "open", "key", key, "token", token));
		if (restResult.getResponse().getStatus() == Status.OK.getStatusCode()) {
			restResult.getResponse().close();
			List<Folder> folders = new ArrayList<>();
			JSONArray listsArray = restResult.getPayload();
			for (int i = 0; i < listsArray.length(); i++) {
				JSONObject listObject = listsArray.getJSONObject(i);
				Folder folder = makeFolderFromList(listObject);
				folders.add(folder);
			}
			return folders;
		}
		restResult.getResponse().close();
		throw new SpecmateInternalException(ErrorCode.TRELLO, "Could not load Trello Lists.");
	}

	private Folder makeFolderFromList(JSONObject listObject) {
		Folder folder = BaseFactory.eINSTANCE.createFolder();
		folder.setId(listObject.getString("id"));
		folder.setName(listObject.getString("name"));
		return folder;
	}

	private Requirement makeRequirementFromCard(JSONObject cardObject) {
		Requirement requirement = RequirementsFactory.eINSTANCE.createRequirement();
		String idShort = Integer.toString(cardObject.getInt("idShort"));
		String id = cardObject.getString("id");
		requirement.setId(idShort);
		requirement.setExtId(idShort);
		requirement.setExtId2(id);
		requirement.setName(cardObject.getString("name"));
		requirement.setDescription(cardObject.getString("desc"));
		return requirement;
	}

	@Override
	public Set<IProject> authenticate(String username, String password) throws SpecmateException {
		return new HashSet<IProject>(Arrays.asList(getProject()));
	}

	@Override
	public Requirement getRequirementById(String id) throws SpecmateException {
		return null;
	}
}
