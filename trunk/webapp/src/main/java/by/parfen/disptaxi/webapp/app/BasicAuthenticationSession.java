package by.parfen.disptaxi.webapp.app;

import javax.inject.Inject;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.parfen.disptaxi.datamodel.City;
import by.parfen.disptaxi.datamodel.UserAccount;
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.datamodel.UserRole;
import by.parfen.disptaxi.datamodel.enums.AppRole;
import by.parfen.disptaxi.services.UserAccountService;
import by.parfen.disptaxi.services.UserProfileService;
import by.parfen.disptaxi.services.UserRoleService;

public class BasicAuthenticationSession extends AuthenticatedWebSession {

	private static final Logger LOGGER = LoggerFactory.getLogger(BasicAuthenticationSession.class);

	public static final String ROLE_SIGNED_IN = "SIGNED_IN";

	private UserAccount user;

	private UserProfile userProfile;

	private AppRole userAppRole;

	private Roles resultRoles;

	private City city;

	@Inject
	private UserAccountService userAccountService;
	@Inject
	private UserProfileService userProfileService;
	@Inject
	private UserRoleService userRoleService;

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
		final UserAccount account = userAccountService.getAccountByEmail(userName);
		if (account != null && account.getPassw().equals(password)) {
			initFieldsByAccount(account);
			authenticationResult = true;
		}
		return authenticationResult;
	}

	private void initFieldsByAccount(UserAccount account) {
		user = account;
		userAppRole = userAccountService.getRole(account.getId());
		userProfile = null;
		final UserAccount userAccount = userAccountService.getWithDetails(user);
		if (userAccount != null) {
			final UserRole userRole = userRoleService.getWithDetails(userAccount.getUserRole());
			userProfile = userRole.getUserProfile();
		}
	}

	@Override
	public Roles getRoles() {
		if (isSignedIn() && (resultRoles == null)) {
			resultRoles = new Roles();
			AppRole role = userAccountService.getRole(user.getId());
			if (role != null) {
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

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public AppRole getUserAppRole() {
		if (userAppRole == null) {
			return AppRole.CUSTOMER_ROLE;
			// return AppRole.ADMIN_ROLE;
		} else {
			return userAppRole;
		}
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}
}
