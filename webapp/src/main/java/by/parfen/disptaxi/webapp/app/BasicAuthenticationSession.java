package by.parfen.disptaxi.webapp.app;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.parfen.disptaxi.datamodel.UserAccount;
import by.parfen.disptaxi.datamodel.enums.AppRole;
import by.parfen.disptaxi.services.UserAccountService;

public class BasicAuthenticationSession extends AuthenticatedWebSession {

	private static final Logger LOGGER = LoggerFactory.getLogger(BasicAuthenticationSession.class);

	public static final String ROLE_SIGNED_IN = "SIGNED_IN";
	private UserAccount user;

	private Roles resultRoles;

	@Inject
	private UserAccountService userService;

	public BasicAuthenticationSession(final Request request) {
		super(request);
		Injector.get().inject(this);
	}

	public static BasicAuthenticationSession get() {
		return (BasicAuthenticationSession) Session.get();
	}

	@Override
	public boolean authenticate(final String userName, final String password) {
		boolean authenticationResult = false;
		final UserAccount account = userService.getAccountByEmail(userName);
		if (account != null && account.getPassw().equals(password)) {
			this.user = account;
			authenticationResult = true;
		}
		return authenticationResult;
	}

	@Override
	public Roles getRoles() {
		if (isSignedIn() && (resultRoles == null)) {
			resultRoles = new Roles();
			List<AppRole> roles = userService.getRoles(user.getId());
			for (AppRole role : roles) {
				resultRoles.add(role.name());
			}
		}
		return resultRoles;
	}

	@Override
	public void signOut() {
		super.signOut();
		user = null;
	}

	public UserAccount getUser() {
		return user;
	}

}
