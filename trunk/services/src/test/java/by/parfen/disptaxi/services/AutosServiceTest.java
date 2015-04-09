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
import by.parfen.disptaxi.datamodel.Autos;
import by.parfen.disptaxi.datamodel.Car;
import by.parfen.disptaxi.datamodel.CarsType;
import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.datamodel.UserProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class AutosServiceTest extends AbstractServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(AutosServiceTest.class);

	@Inject
	private AutosService autosService;
	@Inject
	private CarService carService;
	@Inject
	private CarsTypeService carsTypeService;
	@Inject
	private UserProfileService userProfileService;
	@Inject
	private DriverService driverService;

	@Before
	public void cleanUpData() {
		LOGGER.info("Instance of AutosService is injected. Class is: {}", autosService.getClass().getName());
		autosService.deleteAll();
		carService.deleteAll();
		carsTypeService.deleteAll();
		driverService.deleteAll();
		userProfileService.deleteAll();
	}

	@Test
	public void basicCRUDTest() {
		final Car car = createCar();
		final CarsType carsType = createCarsType();
		carsTypeService.create(carsType);
		LOGGER.debug(carsType.toString());
		carService.create(car, carsType);

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

		final Autos autos = createAutos();
		autos.setDriver(createdDriver);
		autosService.create(autos, car);

		Autos autosFromDb = autosService.get(autos.getId());
		Assert.assertNotNull(autosFromDb);
		// TODO check other fields

	}

}