package by.parfen.disptaxi.services;

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
import by.parfen.disptaxi.DbUtilsServiceTest;
import by.parfen.disptaxi.datamodel.AppRole;
import by.parfen.disptaxi.datamodel.UserAccount;
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.datamodel.UserRole;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class UserAccountServiceTest extends AbstractServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserAccountServiceTest.class);

	@Inject
	private UserAccountService userAccountService;
	@Inject
	private UserRoleService userRoleService;
	@Inject
	private UserProfileService userProfileService;
	@Inject
	private AppRoleService appRoleService;

	@Inject
	private DbUtilsServiceTest dbUtils;

	@Before
	public void cleanUpData() {
		LOGGER.info("Instance of UserAccountService is injected. Class is: {}", userAccountService.getClass()
				.getName());
		dbUtils.cleanUpData();
	}

	@Test
	public void createAccountTest() {
		LOGGER.warn("Test createUserProfileAndAccount.");

		LOGGER.debug("Create application roles.");
		AppRole appRole1 = createAppRoleOperator();
		appRoleService.create(appRole1);
		Assert.assertNotNull(appRole1.getId());
		LOGGER.debug("appRole1.id = {}", appRole1.getId());

		AppRole appRole2 = createAppRoleCustomer();
		appRoleService.create(appRole2);
		Assert.assertNotNull(appRole2.getId());
		LOGGER.debug("appRole2.id = {}", appRole2.getId());

		LOGGER.debug("Create user profile.");
		UserProfile userProfile = createUserProfile();
		userProfileService.saveOrUpdate(userProfile);
		Assert.assertNotNull(userProfile.getId());
		LOGGER.debug("Profile id = {}", userProfile.getId());

		LOGGER.debug("Create user roles for created profile and roles.");
		UserRole userRole1 = createUserRole();
		userRoleService.create(userRole1, userProfile, appRole1);
		Assert.assertNotNull(appRole1.getId());
		LOGGER.debug("appRole1.id = {}", appRole1.getId());

		UserRole userRole2 = createUserRole();
		userRoleService.create(userRole2, userProfile, appRole1);
		Assert.assertNotNull(appRole2.getId());
		LOGGER.debug("appRole2.id = {}", appRole2.getId());

		UserRole userRoleFromDb = userRoleService.get(userRole1.getId());
		LOGGER.debug("Founded user role1 with id = {}", userRoleFromDb.getId());

		LOGGER.debug("Create account1 for created user role1.");
		UserAccount userAccount1 = createUserAccount();
		userAccount1.setUserRole(userRoleFromDb);
		userRoleFromDb.setUserAccount(userAccount1);
		userRoleService.update(userRoleFromDb);
		LOGGER.debug("userAccount1 = {}", userAccount1);

		userRoleFromDb = userRoleService.get(userRole2.getId());
		LOGGER.debug("Founded user role2 with id = {}", userRoleFromDb.getId());

		LOGGER.debug("Create account2 for created user role2.");
		UserAccount userAccount2 = createUserAccount();
		userAccount2.setUserRole(userRoleFromDb);
		userRoleFromDb.setUserAccount(userAccount2);
		userRoleService.update(userRoleFromDb);
		LOGGER.debug("userAccount2 = {}", userAccount2);

	}

}
