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
import by.parfen.disptaxi.datamodel.City;
import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.datamodel.Order;
import by.parfen.disptaxi.datamodel.Point;
import by.parfen.disptaxi.datamodel.Price;
import by.parfen.disptaxi.datamodel.Route;
import by.parfen.disptaxi.datamodel.Street;
import by.parfen.disptaxi.datamodel.enums.CarType;
import by.parfen.disptaxi.datamodel.enums.OrderResult;
import by.parfen.disptaxi.datamodel.enums.OrderStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class OrderServiceTest extends AbstractServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceTest.class);

	// @Inject
	// private OrderTimetableService orderTimetableService;
	@Inject
	private OrderService orderService;
	@Inject
	private CustomerService customerService;
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

	private Route createRoute() {
		// Create Route
		// choose City
		final List<City> cities = cityService.getAll();
		Assert.assertTrue("Can't find any city!", cities.size() > 0);
		// can create in AbstractServiceTest
		// protected <T> randomListElement(List<T> list) {}
		// final City city = cities.get(randomInteger(0, cities.size() - 1));
		final City city = getRandomListElement(cities);

		// choose srcStreet and dstStreet
		final List<Street> streets = streetService.getAllByCity(city);
		Assert.assertTrue("Can't find any city!", streets.size() > 0);
		// final Street srcStreet = streets.get(randomInteger(0, streets.size() -
		// 1));
		final Street srcStreet = getRandomListElement(streets);
		// final Street dstStreet = streets.get(randomInteger(0, streets.size() -
		// 1));
		final Street dstStreet = getRandomListElement(streets);

		// choose srcPoint
		final List<Point> srcPoints = pointService.getAllByStreet(srcStreet);
		Assert.assertTrue("Can't find any city!", srcPoints.size() > 0);
		// final Point srcPoint = srcPoints.get(randomInteger(0, srcPoints.size() -
		// 1));
		final Point srcPoint = getRandomListElement(srcPoints);

		// choose dstPoint
		final List<Point> dstPoints = pointService.getAllByStreet(dstStreet);
		Assert.assertTrue("Can't find any city!", dstPoints.size() > 0);
		// final Point dstPoint = dstPoints.get(randomInteger(0, dstPoints.size() -
		// 1));
		final Point dstPoint = getRandomListElement(dstPoints);

		final Route route = new Route();
		route.setSrcPoint(srcPoint);
		route.setDstPoint(dstPoint);
		return route;
		// routeService.create(route, srcPoint, dstPoint);

		// final Route createdRoute = routeService.get(route.getId());
		// return createdRoute;
	}

	@Test
	public void createOrdersWithAttribs() {

		dbUtils.CreateBulkDataForRoute();
		dbUtils.CreateBulkDataForOrders();

		createOrderWithRoute();

	}

	private void createOrderWithRoute() {
		final List<Customer> customers = customerService.getAll();
		Assert.assertTrue("Can't find any customer!", customers.size() > 0);
		final Customer customer = getRandomListElement(customers);

		// Choose CarType
		CarType carType;
		final int signCarType = randomInteger(0, 1);
		if (signCarType == 0) {
			carType = CarType.SEDAN;
		} else {
			carType = CarType.VAN;
		}

		// find price
		final List<Price> prices = priceService.getAllByCarType(carType);
		Assert.assertTrue("Can't find any price!", prices.size() > 0);
		final Price price = getRandomListElement(prices);

		// TODO
		// ---- 1) Create Route
		// DONE 2) Lookup Auto
		// But now:
		// 1) Choose any active Auto without radius limitations
		// 2) Create Route with 1 path (srcPoint, dstPoint)

		// Choose any active Auto without radius limitations
		final List<Auto> autos = autoService.getAllActiveByCarType(carType);
		Assert.assertTrue("Can't find any car!", autos.size() > 0);
		final Auto auto = getRandomListElement(autos);

		final Order order = createOrder();
		order.setCustomer(customer);
		order.setPrice(price);
		order.setAuto(auto);
		orderService.create(order);

		final Route route = createRoute();
		route.setPointIndex(1L);
		route.setOrder(order);
		route.setEstLength(randomLong(1L, 20L));
		// (time to get in and out of the car) + LengthKm * SpeedPerMinute
		route.setEstTime(randomLong(5L, 10L) + route.getEstLength() * randomInteger(30, 60) / 60);
		routeService.create(route);

		// Lookup cars by Geo data
		Point srcPoint = route.getSrcPoint();
		List<Auto> carsByGeo = autoService.getAllActiveByCarTypeAndGeo(carType, srcPoint.getPositionLat(),
				srcPoint.getPositionLng());
		LOGGER.debug("Founed by geo cars count: {}", carsByGeo.size());

		// calc price
		final List<Route> routes = routeService.getAllByOrder(order);
		if (routes.size() > 0) {
			order.setRouteLength(randomLong(3L, 20L));
			order.setOrderPrice(orderService.calcOrderPrice(order));
			orderService.update(order);

			// calc price by Estimated values
			// order.setRouteLength(0L);
			// order.setOrderPrice(orderService.calcOrderPrice(order));
			// orderService.update(order);

			final List<Order> customerOrders = orderService.getAllByCustomer(customer);
			Assert.assertTrue("Can't find any order for customer!", customerOrders.size() > 0);
			LOGGER.debug("Customer orders count: {}", customerOrders.size());
			for (Order cOrder : customerOrders) {
				Order fetchedOrder = orderService.get(cOrder.getId());
				LOGGER.debug("Order: {}", fetchedOrder);
			}

			final Driver driver = orderService.getOrderDriver(order);
			Assert.assertTrue("Can't find any driver in order!", driver != null);
			LOGGER.debug("Order driver, id: {}", driver.getId());

			final List<Order> driverOrders = orderService.getAllByDriver(driver);
			Assert.assertTrue("Can't find any order for driver!", driverOrders.size() > 0);
			LOGGER.debug("Driver orders count: {}", driverOrders.size());
			for (Order dOrder : driverOrders) {
				Order fetchedOrder = orderService.get(dOrder.getId());
				LOGGER.debug("Order: {}", fetchedOrder);
			}

			OrderStatus orderStatus = OrderStatus.NEW;
			// manual insert
			// final OrderTimetable orderTimetable = new OrderTimetable();
			// orderTimetable.setOrder(order);
			// orderTimetable.setOrderStatus(orderStatus);
			// orderTimetableService.create(orderTimetable);

			// auto insert
			orderService.changeOrderStatus(order, orderStatus);
			orderService.changeOrderStatus(order, OrderStatus.ACCEPTED);
			orderService.changeOrderStatus(order, OrderStatus.ARRIVED);
			orderService.changeOrderStatus(order, OrderStatus.ON_WAY);
			orderService.changeOrderStatus(order, OrderStatus.DONE);

			order.setOrderResult(OrderResult.OK);
			orderService.update(order);
		} else {
			LOGGER.debug("Sorry, we can't find any car now!");
		}
	}
}
