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
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.datamodel.UserRole;
import by.parfen.disptaxi.datamodel.enums.AppRole;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class UserRoleServiceTest extends AbstractServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleServiceTest.class);

	@Inject
	private UserRoleService userRoleService;
	@Inject
	private UserProfileService userProfileService;

	@Inject
	private DbUtilsServiceTest dbUtils;

	@Before
	public void cleanUpData() {
		LOGGER.info("Instance of UserRoleService is injected. Class is: {}", userRoleService.getClass().getName());
		dbUtils.cleanUpData();
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

		LOGGER.debug("Create user role for created profile and role.");
		UserRole userRole1 = createUserRole();
		userRole1.setAppRole(AppRole.CUSTOMER_ROLE);
		userRoleService.create(userRole1, userProfile);

	}

}
