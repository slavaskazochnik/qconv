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
import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.datamodel.UserProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class DriverServiceTest extends AbstractServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(DriverServiceTest.class);

	@Inject
	private DriverService driverService;
	@Inject
	private CustomerService customerService;
	@Inject
	private UserProfileService userProfileService;
	@Inject
	private UserAccountService userAccountService;
	@Inject
	private UserRoleService userRoleService;
	@Inject
	private AppRoleService appRoleService;

	@Before
	public void cleanUpData() {
		LOGGER.info("Instance of UserProfileService is injected. Class is: {}", userProfileService.getClass().getName());
		driverService.deleteAll();
		customerService.deleteAll();
		userAccountService.deleteAll();
		userRoleService.deleteAll();
		userProfileService.deleteAll();
		appRoleService.deleteAll();
	}

	@Test
	public void createUserAndDriver() {

		final UserProfile profile = createUserProfile();
		userProfileService.saveOrUpdate(profile);
		UserProfile userProfileFromDB = userProfileService.get(profile.getId());
		// Assert.assertNull(userProfileFromDB);
		LOGGER.debug("Created user profile {}", userProfileFromDB);

		final Driver driver = createDriver();
		driver.setUserProfile(userProfileFromDB);
		userProfileFromDB.setDriver(driver);
		userProfileService.saveOrUpdate(userProfileFromDB);

		final Driver createdDriver = driverService.get(userProfileFromDB.getId());
		Assert.assertNotNull(createdDriver);
		LOGGER.debug("Created driver {}", userProfileFromDB);
		// TODO check equals

		driverService.delete(createdDriver);
		// Assert.assertNull(driverService.get(createdDriver.getId()));

		userProfileService.delete(userProfileFromDB);
		// Assert.assertNull(userProfileService.get(userProfileFromDB.getId()));
	}
	// @Test
	// public void uniqueConstraintsTest() {
	// final UserProfile profile = createUserProfile();
	// final String email = randomString("email");
	// final UserAccount account = createUserAccount();
	// account.setEmail(email);
	// profile.setUserAccount(account);
	// driverService.createNewUser(profile, account);
	//
	// final UserProfile duplicateProfile = createUserProfile();
	// final UserAccount duplicateAccount = createUserAccount();
	// duplicateAccount.setEmail(email);
	// duplicateProfile.setUserAccount(duplicateAccount);
	// try {
	// driverService.createNewUser(duplicateProfile, duplicateAccount);
	// Assert.fail("Not unique login can't be saved.");
	// } catch (final PersistenceException e) {
	// // expected
	// }
	//
	// // should be saved now
	// duplicateAccount.setEmail(randomString("email"));
	// driverService.createNewUser(duplicateProfile, duplicateAccount);
	// }

}
