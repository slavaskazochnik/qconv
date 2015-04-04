package by.parfen.disptaxi.services;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.parfen.disptaxi.AbstractServiceTest;
import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.datamodel.UserProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class DriverServiceTest extends AbstractServiceTest {

	@Inject
	private DriverService driverService;
	@Inject
	private UserProfileService userProfileService;

	@Test
	public void createUserAndDriver() {

		final UserProfile profile = createUserProfile();
		final Driver driver = createDriver();
		driverService.create(driver, profile);

		final Driver createdDriver = driverService.get(driver.getId());
		Assert.assertNotNull(createdDriver);
		// TODO check equals

		driverService.delete(driver);
		Assert.assertNull(driverService.get(driver.getId()));

		userProfileService.delete(profile);
		Assert.assertNull(userProfileService.get(profile.getId()));
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
