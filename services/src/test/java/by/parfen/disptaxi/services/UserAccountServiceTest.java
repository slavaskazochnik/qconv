package by.parfen.disptaxi.services;

import java.util.HashSet;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.parfen.disptaxi.AbstractServiceTest;
import by.parfen.disptaxi.datamodel.AppRole;
import by.parfen.disptaxi.datamodel.UserAccount;
import by.parfen.disptaxi.datamodel.UserProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class UserAccountServiceTest extends AbstractServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserAccountServiceTest.class);

	@Inject
	private UserAccountService userAccountService;
	@Inject
	private UserProfileService userProfileService;
	@Inject
	private AppRoleService appRoleService;

	@Before
	public void cleanUpData() {
		LOGGER.info("Instance of UserAccountService is injected. Class is: {}", userAccountService.getClass().getName());
		userAccountService.deleteAll();
		userProfileService.deleteAll();
		appRoleService.deleteAll();
	}

	// @Test
	public void test1() {
		LOGGER.warn("Test log message in test1().");
		Assert.assertNotNull(userAccountService);
	}

	// @Test
	public void basicCRUDTest() {
		LOGGER.warn("Test basicCRUDTest.");
		LOGGER.debug("Create user Account.");
		UserAccount userAccount = createUserAccount();
		userAccountService.create(userAccount, null);
		Assert.assertNotNull(userAccount.getId());
		LOGGER.debug("Account id = {}", userAccount.getId());

		UserAccount userAccountFromDb = userAccountService.get(userAccount.getId());
		Assert.assertNotNull(userAccountFromDb);
		Assert.assertEquals(userAccountFromDb.getName(), userAccount.getName());

		userAccountFromDb.setName("newName");
		userAccountService.update(userAccountFromDb);
		UserAccount userAccountFromDbUpdated = userAccountService.get(userAccount.getId());
		Assert.assertEquals(userAccountFromDbUpdated.getName(), userAccountFromDb.getName());
		Assert.assertNotEquals(userAccountFromDbUpdated.getName(), userAccount.getName());

		userAccountService.delete(userAccountFromDbUpdated);
		Assert.assertNull(userAccountService.get(userAccount.getId()));
	}

	@Test
	public void createUserProfileAndAccount() {
		LOGGER.warn("Test createUserProfileAndAccount.");

		LOGGER.debug("Create user profile.");
		UserProfile userProfile = createUserProfile();

		LOGGER.debug("Create user Accounts.");
		// int randomTestObjectsCount = randomInteger(2, 3);
		// HashSet<UserAccount> accounts = new HashSet<UserAccount>();
		HashSet<AppRole> roles = new HashSet<AppRole>();
		// for (int i = 0; i < randomTestObjectsCount; i++) {
		// UserAccount userAccount = createUserAccount();
		// userAccountService.create(userAccount, null);
		// LOGGER.debug("Account id = {}", userAccount.getId());
		// accounts.add(userAccount);
		// }
		// userProfile.setUserAccounts(accounts);
		AppRole appRole1 = createAppRoleOperator();
		appRoleService.create(appRole1);
		LOGGER.debug("AppRole id = {}", appRole1.getId());
		roles.add(appRole1);

		AppRole appRole2 = createAppRoleDriver();
		appRoleService.create(appRole2);
		LOGGER.debug("AppRole id = {}", appRole2.getId());
		roles.add(appRole2);

		userProfile.setAppRoles(roles);
		userProfileService.saveOrUpdate(userProfile);

		// test that saved
		Assert.assertNotNull(userProfile.getId());
		LOGGER.debug("Profile id = {}", userProfile.getId());

	}

}
