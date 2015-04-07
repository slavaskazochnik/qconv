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
import by.parfen.disptaxi.datamodel.AppRole;
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.datamodel.UserRole;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class UserRoleServiceTest extends AbstractServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleServiceTest.class);

	@Inject
	private UserRoleService userRoleService;
	@Inject
	private UserAccountService userAccountService;
	@Inject
	private UserProfileService userProfileService;
	@Inject
	private AppRoleService appRoleService;

	@Before
	public void cleanUpData() {
		LOGGER.info("Instance of UserRoleService is injected. Class is: {}", userRoleService.getClass().getName());
		userAccountService.deleteAll();
		userRoleService.deleteAll();
		userProfileService.deleteAll();
		appRoleService.deleteAll();
	}

	// @Test
	public void test1() {
		LOGGER.warn("Test log message in test1().");
		Assert.assertNotNull(userProfileService);
	}

	// @Test
	public void basicCRUDTest() {
		UserProfile userProfile = createUserProfile();
		userProfileService.saveOrUpdate(userProfile);

		UserProfile userProfileFromDb = userProfileService.get(userProfile.getId());
		Assert.assertNotNull(userProfileFromDb);
		Assert.assertEquals(userProfileFromDb.getFirstName(), userProfile.getFirstName());

		userProfileFromDb.setFirstName("newName");
		userProfileService.saveOrUpdate(userProfileFromDb);
		UserProfile userProfileFromDbUpdated = userProfileService.get(userProfile.getId());
		Assert.assertEquals(userProfileFromDbUpdated.getFirstName(), userProfileFromDb.getFirstName());
		Assert.assertNotEquals(userProfileFromDbUpdated.getFirstName(), userProfile.getFirstName());

		userProfileService.delete(userProfileFromDbUpdated);
		Assert.assertNull(userProfileService.get(userProfile.getId()));
	}

	@Test
	public void createUserRoleTest() {
		LOGGER.warn("Test createUserProfileAndAccount.");

		LOGGER.debug("Create user profile.");
		UserProfile userProfile = createUserProfile();
		userProfileService.saveOrUpdate(userProfile);
		Assert.assertNotNull(userProfile.getId());
		LOGGER.debug("Profile id = {}", userProfile.getId());

		LOGGER.debug("Create application role.");
		AppRole appRole1 = createAppRoleOperator();
		appRoleService.create(appRole1);
		Assert.assertNotNull(appRole1.getId());
		LOGGER.debug("appRole1.id = {}", appRole1.getId());

		LOGGER.debug("Create user role for created profile and role.");
		UserRole userRole1 = createUserRole();
		userRoleService.create(userRole1, userProfile, appRole1);
		Assert.assertNotNull(appRole1.getId());
		LOGGER.debug("appRole1.id = {}", appRole1.getId());

		LOGGER.debug("userProfile.setAppRoles(roles)");

	}

}
