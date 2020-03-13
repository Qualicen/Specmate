package com.specmate.auth.internal;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import com.specmate.auth.api.ISessionService;
import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.config.api.IConfigService;
import com.specmate.metrics.IUserMetricsService;
import com.specmate.model.administration.ErrorCode;
import com.specmate.persistency.IChange;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;
import com.specmate.persistency.IView;
import com.specmate.usermodel.AccessRights;
import com.specmate.usermodel.UserSession;
import com.specmate.usermodel.UsermodelFactory;

@Component(immediate = true, service = ISessionService.class, configurationPid = PersistentSessionService.PID, configurationPolicy = ConfigurationPolicy.REQUIRE, property = "impl=persistent")
public class PersistentSessionService extends BaseSessionService {

	/** The PID of the session service */
	public static final String PID = "com.specmate.auth.PersistentSessionService";

	private static final long SESSION_REFRESH_LIMIT = 1000L * 60; // 60 seconds
	private IPersistencyService persistencyService;
	private ITransaction sessionTransaction;
	private IView sessionView;
	private IUserMetricsService userMetricsService;

	@Override
	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateException {
		super.activate(properties);
		sessionTransaction = persistencyService.openTransaction();
		// Sessions do not adhere to the constraints of general specmate objects
		sessionTransaction.enableValidators(false);
		sessionView = persistencyService.openView();
	}

	@Deactivate
	public void deactivate() throws SpecmateException {
		if (sessionTransaction != null) {
			sessionTransaction.close();
		}

		if (sessionView != null) {
			sessionView.close();
		}
	}

	@Override
	public UserSession create(AccessRights source, AccessRights target, String userName, String password,
			String projectName) throws SpecmateException {

		userMetricsService.loginCounter(sessionView, userName);
		UserSession session = createSession(source, target, userName, password, sanitize(projectName));

		sessionTransaction.doAndCommit(new IChange<Object>() {
			@Override
			public Object doChange() throws SpecmateException {
				sessionTransaction.getResource().getContents().add(session);
				return null;
			}
		});

		return session;
	}

	@Override
	public UserSession create() {
		// Don't create unauthenticated sessions in persistent storage.
		UserSession session = UsermodelFactory.eINSTANCE.createUserSession();
		session.setAllowedPathPattern(null);
		session.setLastActive(0);
		session.setSourceSystem(AccessRights.NONE);
		session.setTargetSystem(AccessRights.NONE);
		session.setId("INVALID-SESSION");
		return session;
	}

	@Override
	public void refresh(String token) throws SpecmateException {
		UserSession session = (UserSession) sessionTransaction.getObjectById(getSessionID(token));
		long now = new Date().getTime();

		sessionTransaction.doAndCommit(new IChange<Object>() {
			@Override
			public Object doChange() throws SpecmateException {
				// If we let each request refresh the session, we get errors from CDO regarding
				// out-of-date revision changes.
				// Here we rate limit session refreshes. The better option would be to not store
				// revisions of UserSession objects, but this is a setting than can be only
				// applied on the
				// whole repository, which we don't want.
				// A third option would be to update sessions with an SQL query, circumventing
				// CDO and revisions altogether.
				if (session.getLastActive() - now > SESSION_REFRESH_LIMIT) {
					session.setLastActive(now);
				}

				return null;
			}
		});
	}

	@Override
	public void delete(String token) throws SpecmateException {
		UserSession session = (UserSession) sessionTransaction.getObjectById(getSessionID(token));

		sessionTransaction.doAndCommit(new IChange<Object>() {
			@Override
			public Object doChange() throws SpecmateException {
				// TODO: detach class should not delete session, just mark it as inactive
				// SpecmateEcoreUtil.detach(session);
				// Set Session isDeleted Property to true, to indicate that this session was
				// deleted
				session.setIsDeleted(true);
				return null;
			}
		});
	}

	@Override
	protected UserSession getSession(String token) throws SpecmateException {
		// Only get the active sessions, the deleted sessions are only used for the
		// login counter
		String query = "UserSession.allInstances()->select(u | u.id='" + token + "' and u.isDeleted=false)";

		List<Object> results = sessionView.query(query,
				UsermodelFactory.eINSTANCE.getUsermodelPackage().getUserSession());

		if (results.size() > 1) {
			throw new SpecmateInternalException(ErrorCode.USER_SESSION, "More than one session " + token + " found.");
		}

		if (results.size() == 1) {
			return (UserSession) results.get(0);
		} else {
			return null;
		}

	}

	private CDOID getSessionID(String token) throws SpecmateException {
		UserSession session = getSession(token);
		if (session == null) {
			throw new SpecmateInternalException(ErrorCode.USER_SESSION,
					"Invalid session when trying to retrieve session id.");
		}
		return session.cdoID();
	}

	@Reference
	public void setPersistencyService(IPersistencyService persistencyService) {
		this.persistencyService = persistencyService;
	}

	@Reference
	public void setConfigService(IConfigService configService) {
		this.configService = configService;
	}

	@Reference
	public void setUserMetricsService(IUserMetricsService userMetricsService) {
		this.userMetricsService = userMetricsService;
	}
}
