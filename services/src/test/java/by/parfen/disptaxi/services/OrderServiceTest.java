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
import by.parfen.disptaxi.datamodel.City;
import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.datamodel.Order;
import by.parfen.disptaxi.datamodel.Point;
import by.parfen.disptaxi.datamodel.Price;
import by.parfen.disptaxi.datamodel.Route;
import by.parfen.disptaxi.datamodel.Street;
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.datamodel.enums.CarType;
import by.parfen.disptaxi.datamodel.enums.SignActive;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class OrderServiceTest extends AbstractServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceTest.class);

	@Inject
	private OrderService orderService;
	@Inject
	private UserProfileService userProfileService;
	@Inject
	private CustomerService customerService;
	@Inject
	private DriverService driverService;
	@Inject
	private CarService carService;
	@Inject
	private PriceService priceService;
	@Inject
	private AutoService autoService;

	@Inject
	private RouteService routeService;
	@Inject
	private PointService pointService;
	@Inject
	private StreetService streetService;
	@Inject
	private CityService cityService;

	@Inject
	private DbUtilsServiceTest dbUtils;

	@Before
	public void cleanUpData() {
		LOGGER.info("Instance of OrdersService is injected. Class is: {}", orderService.getClass().getName());
		dbUtils.cleanUpData();
	}

	private void CreateBulkDataForRoute() {
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
				pointService.create(createPoint("" + i), street);
			}
			List<Point> points = pointService.getAllByStreet(street);
			Assert.assertTrue("Number of created points is different form planned!", points.size() == pointsQuan);
		}
	}

	private void CreateBulkDataForOrders() {
		int i;
		final int maxProfilesQuan = 20;
		final int maxCarsQuan = 20;

		// Create UserProfile
		int profilesQuan = randomInteger(3, maxProfilesQuan);
		for (i = 1; i <= profilesQuan; i++) {
			userProfileService.saveOrUpdate(createUserProfile());
		}
		final List<UserProfile> userProfiles = userProfileService.getAll();
		Assert.assertTrue("Number of created users is different form planned!", userProfiles.size() == profilesQuan);

		// Create Customers and Drivers
		for (UserProfile userProfile : userProfiles) {
			int sign_customer = randomInteger(0, 2);
			if (sign_customer <= 1) {
				Customer customer = createCustomer();
				customer.setUserProfile(userProfile);
				userProfile.setCustomer(customer);
			}
			if (sign_customer >= 1) {
				Driver driver = createDriver();
				driver.setUserProfile(userProfile);
				userProfile.setDriver(driver);
			}
			userProfileService.saveOrUpdate(userProfile);
		}

		// Create Cars
		int carsQuan = randomInteger(3, maxCarsQuan);
		for (i = 1; i <= carsQuan; i++) {
			int signCustomer = randomInteger(0, 1);
			if (signCustomer == 1) {
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

	private Route createRoute() {
		// Create Route
		// choose City
		final List<City> cities = cityService.getAll();
		Assert.assertTrue("Can't find any city!", cities.size() > 0);
		// can create in AbstractServiceTest
		// protected <T> randomListElement(List<T> list) {}
		final City city = cities.get(randomInteger(0, cities.size() - 1));

		// choose srcStreet and dstStreet
		final List<Street> streets = streetService.getAllByCity(city);
		Assert.assertTrue("Can't find any city!", streets.size() > 0);
		final Street srcStreet = streets.get(randomInteger(0, streets.size() - 1));
		final Street dstStreet = streets.get(randomInteger(0, streets.size() - 1));

		// choose srcPoint
		final List<Point> srcPoints = pointService.getAllByStreet(srcStreet);
		Assert.assertTrue("Can't find any city!", srcPoints.size() > 0);
		final Point srcPoint = srcPoints.get(randomInteger(0, srcPoints.size() - 1));

		// choose dstPoint
		final List<Point> dstPoints = pointService.getAllByStreet(dstStreet);
		Assert.assertTrue("Can't find any city!", dstPoints.size() > 0);
		final Point dstPoint = dstPoints.get(randomInteger(0, dstPoints.size() - 1));

		final Route route = new Route();
		route.setSrcPoint(srcPoint);
		route.setDstPoint(dstPoint);
		routeService.create(route, srcPoint, dstPoint);

		final Route createdRoute = routeService.get(route.getId());
		return createdRoute;
	}

	@Test
	public void createOrdersWithAttribs() {

		CreateBulkDataForRoute();
		CreateBulkDataForOrders();

		final List<Customer> customers = customerService.getAll();
		Assert.assertTrue("Can't find any customer!", customers.size() > 0);
		final Customer customer = customers.get(0);

		// Choose CarType
		CarType carType;
		final int signCarType = randomInteger(0, 1);
		if (signCarType == 0) {
			carType = CarType.CARTYPE_SEDAN;
		} else {
			carType = CarType.CARTYPE_VAN;
		}

		// find price
		final List<Price> prices = priceService.getAllByCarType(carType);
		Assert.assertTrue("Can't find any price!", prices.size() > 0);
		final Price price = prices.get(0);

		// TODO
		// 1) Create Route
		// 2) Lookup Auto
		// But now:
		// 1) Choose Auto without limitations
		// 2) Create Route
		final List<Auto> autos = autoService.getAllByCarType(carType);
		Assert.assertTrue("Can't find any car!", autos.size() > 0);
		final Auto auto = autos.get(0);

		// final Route route = createRoute();

		final Order order = createOrder();
		order.setCustomer(customer);
		order.setPrice(price);
		order.setAuto(auto);
		// order.setRoute(route);
		orderService.create(order);

	}
}
