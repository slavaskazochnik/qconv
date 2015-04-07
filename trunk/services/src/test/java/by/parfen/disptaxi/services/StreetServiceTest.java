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
import by.parfen.disptaxi.datamodel.City;
import by.parfen.disptaxi.datamodel.Street;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class StreetServiceTest extends AbstractServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(StreetServiceTest.class);

	@Inject
	private StreetService streetService;
	@Inject
	private CityService cityService;

	@Before
	public void cleanUpData() {
		LOGGER.info("Instance of UserProfileService is injected. Class is: {}", streetService.getClass().getName());
		streetService.deleteAll();
		cityService.deleteAll();
	}

	@Test
	public void createStreetAndCity() {

		final City city = createCity();
		cityService.saveOrUpdate(city);
		final City cityFromDB = cityService.get(city.getId());
		// Assert.assertNull(userProfileFromDB);
		LOGGER.debug("Created city {}", cityFromDB);

		final Street street = createStreet();
		streetService.create(street, cityFromDB);

		final Street createdStreet = streetService.get(street.getId());
		Assert.assertNotNull(createdStreet);
		LOGGER.debug("Created street, id = {}", createdStreet.getId());
		// TODO check equals

		streetService.delete(createdStreet);

		cityService.delete(cityFromDB);
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
