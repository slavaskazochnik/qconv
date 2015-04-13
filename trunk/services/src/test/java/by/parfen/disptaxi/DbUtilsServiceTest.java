package by.parfen.disptaxi;

import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.parfen.disptaxi.datamodel.Auto;
import by.parfen.disptaxi.datamodel.Car;
import by.parfen.disptaxi.datamodel.City;
import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.datamodel.Point;
import by.parfen.disptaxi.datamodel.Street;
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.datamodel.enums.CarType;
import by.parfen.disptaxi.datamodel.enums.SignActive;
import by.parfen.disptaxi.services.AutoService;
import by.parfen.disptaxi.services.CarService;
import by.parfen.disptaxi.services.CityService;
import by.parfen.disptaxi.services.CustomerService;
import by.parfen.disptaxi.services.DriverService;
import by.parfen.disptaxi.services.OrderService;
import by.parfen.disptaxi.services.PointService;
import by.parfen.disptaxi.services.PriceService;
import by.parfen.disptaxi.services.RouteService;
import by.parfen.disptaxi.services.StreetService;
import by.parfen.disptaxi.services.UserAccountService;
import by.parfen.disptaxi.services.UserProfileService;
import by.parfen.disptaxi.services.UserRoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
@Service
public class DbUtilsServiceTest extends AbstractServiceTest {
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
	private AutoService autoService;
	@Inject
	private CarService carService;

	@Inject
	private PointService pointService;
	@Inject
	private StreetService streetService;
	@Inject
	private CityService cityService;

	@Inject
	private PriceService priceService;
	@Inject
	private OrderService orderService;

	@Inject
	private RouteService routeService;

	/*
	 * Clear all tables before testing
	 */
	public void cleanUpData() {

		routeService.deleteAll();

		orderService.deleteAll();
		routeService.deleteAll();
		priceService.deleteAll();

		autoService.deleteAll();
		carService.deleteAll();

		driverService.deleteAll();
		customerService.deleteAll();
		userAccountService.deleteAll();
		userRoleService.deleteAll();
		userProfileService.deleteAll();

		pointService.deleteAll();
		streetService.deleteAll();
		cityService.deleteAll();

	}

	/*
	 * Create points for route planning.
	 * 
	 * Create records in the City table. Create streets for the cities. Create
	 * points for the street.
	 */
	public void CreateBulkDataForRoute() {
		int i;
		final int maxCityQuan = 1;
		final int maxStreetsQuan = 20;
		final int maxPointsQuan = 20;

		final int cityQuan = maxCityQuan;
		for (i = 1; i <= cityQuan; i++) {
			cityService.saveOrUpdate(createCity());
		}
		final List<City> cities = cityService.getAll();
		Assert.assertTrue("Number of created users is different form planned!", cities.size() == cityQuan);

		for (City city : cities) {
			int streetsQuan = randomInteger(10, maxStreetsQuan);
			for (i = 1; i <= streetsQuan; i++) {
				streetService.create(createStreet(), city);
			}
			List<Street> streets = streetService.getAll();
			Assert.assertTrue("Number of created users is different form planned!", streets.size() == streetsQuan);
		}

		List<Street> streets = streetService.getAll();
		for (Street street : streets) {
			int pointsQuan = randomInteger(1, maxPointsQuan);
			for (i = 1; i <= pointsQuan; i++) {
				Point point = createPoint("" + i);
				point.setPositionLat(getRandomLat());
				point.setPositionLng(getRandomLng());
				pointService.create(point, street);
			}
			List<Point> points = pointService.getAllByStreet(street);
			Assert.assertTrue("Number of created points is different form planned!", points.size() == pointsQuan);
		}
	}

	/*
	 * Create records in tables with UserProfile. Then create from UserProfile the
	 * Customer or the Driver or both.
	 * 
	 * The next. Create records in the Car table. Then choose the Car and the
	 * Driver and create record in the Auto table with random signActive.
	 * 
	 * Create current prices for all types of car.
	 */
	public void CreateBulkDataForOrders() {
		int i;
		final int maxProfilesQuan = 20;
		final int maxCarsQuan = 50;

		// Create UserProfile
		int profilesQuan = randomInteger(3, maxProfilesQuan);
		for (i = 1; i <= profilesQuan; i++) {
			userProfileService.saveOrUpdate(createUserProfile());
		}
		final List<UserProfile> userProfiles = userProfileService.getAll();
		Assert.assertTrue("Number of created users is different form planned!",
				userProfiles.size() == profilesQuan);

		// Create Customers and Drivers
		for (UserProfile userProfile : userProfiles) {
			int sign_customer = randomInteger(0, 10);
			if (sign_customer <= 5) {
				Customer customer = createCustomer();
				customer.setUserProfile(userProfile);
				userProfile.setCustomer(customer);
			}
			if (sign_customer >= 5) {
				Driver driver = createDriver();
				driver.setUserProfile(userProfile);
				userProfile.setDriver(driver);
			}
			userProfileService.saveOrUpdate(userProfile);
		}

		// Create Cars
		int carsQuan = randomInteger(3, maxCarsQuan);
		for (i = 1; i <= carsQuan; i++) {
			int signCustomer = randomInteger(0, 10);
			if (signCustomer >= 5) {
				carService.create(createCar(CarType.CARTYPE_SEDAN));
			} else {
				carService.create(createCar(CarType.CARTYPE_VAN));
			}
		}

		final List<Car> cars = carService.getAll();
		Assert.assertTrue("Can't find any car!", cars.size() > 0);
		final List<Driver> drivers = driverService.getAll();
		Assert.assertTrue("Can't find any driver!", drivers.size() > 0);
		for (Car car : cars) {
			Driver driver = drivers.get(randomInteger(0, drivers.size() - 1));
			Auto auto = createAuto();
			auto.setDriver(driver);
			auto.setPositionLat(getRandomLat());
			auto.setPositionLng(getRandomLng());
			int sign = randomInteger(0, 1);
			SignActive signActive = SignActive.SIGNACTIVE_YES;
			if (sign == 0) {
				signActive = SignActive.SIGNACTIVE_NO;
			}
			auto.setSignActive(signActive);
			autoService.create(auto, car);
		}

		priceService.create(createPrice(CarType.CARTYPE_SEDAN));
		priceService.create(createPrice(CarType.CARTYPE_VAN));
	}

	@Test
	public void runTest() {
		cleanUpData();
	}
}
