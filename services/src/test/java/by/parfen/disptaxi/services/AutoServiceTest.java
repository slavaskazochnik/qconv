package by.parfen.disptaxi.services;

import java.util.List;

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
import by.parfen.disptaxi.datamodel.Auto;
import by.parfen.disptaxi.datamodel.Car;
import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.datamodel.enums.CarType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class AutoServiceTest extends AbstractServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(AutoServiceTest.class);

	@Inject
	private AutoService autoService;
	@Inject
	private CarService carService;
	@Inject
	private UserProfileService userProfileService;
	@Inject
	private DriverService driverService;

	@Inject
	private DbUtilsServiceTest dbUtils;

	@Before
	public void cleanUpData() {
		LOGGER.info("Instance of AutosService is injected. Class is: {}", autoService.getClass().getName());
		dbUtils.cleanUpData();
	}

	@Test
	public void basicCRUDTest() {
		final Car car = createCar(CarType.CARTYPE_SEDAN);
		carService.create(car);

		final UserProfile userProfile = createUserProfile();
		userProfileService.saveOrUpdate(userProfile);

		final UserProfile createdProfile = userProfileService.get(userProfile.getId());
		Assert.assertNotNull("Can't find user profile by Id!", createdProfile);

		final Driver driver = createDriver();
		createdProfile.setDriver(driver);
		driver.setUserProfile(createdProfile);
		userProfileService.saveOrUpdate(createdProfile);
		// driverService.create(driver, createdProfile);

		final Driver createdDriver = driverService.get(createdProfile.getId());

		final Auto auto = createAuto();
		auto.setDriver(createdDriver);
		autoService.create(auto, car);

		Auto autosFromDb = autoService.get(auto.getId());
		Assert.assertNotNull(autosFromDb);
		// TODO check other fields

		List<Auto> autosWithDetails = autoService.getAllWithDetails();
		Assert.assertTrue("Can't list any car with detail info!", autosWithDetails.size() > 0);
		LOGGER.debug("Autos with detail info count: {}", autosWithDetails.size());
		for (Auto autoWithDetails : autosWithDetails) {
			LOGGER.debug("Auto   : {}", autoWithDetails);
			LOGGER.debug("Car    : {}", autoWithDetails.getCar());
			LOGGER.debug("Driver : {}", autoWithDetails.getDriver());
		}

	}

}
