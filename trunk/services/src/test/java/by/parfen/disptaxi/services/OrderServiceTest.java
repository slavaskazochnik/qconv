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
import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.datamodel.Order;
import by.parfen.disptaxi.datamodel.Price;
import by.parfen.disptaxi.datamodel.enums.CarType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class OrderServiceTest extends AbstractServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceTest.class);

	@Inject
	private OrderService orderService;
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
	private DbUtilsServiceTest dbUtils;

	@Before
	public void cleanUpData() {
		LOGGER.info("Instance of OrdersService is injected. Class is: {}", orderService.getClass().getName());
		dbUtils.cleanUpData();
	}

	private void CreateDataForOrders() {
		customerService.create(createCustomer(), createUserProfile());

		driverService.create(createDriver(), createUserProfile());
		driverService.create(createDriver(), createUserProfile());

		priceService.create(createPrice(CarType.CARTYPE_SEDAN));
		priceService.create(createPrice(CarType.CARTYPE_VAN));

		carService.create(createCar(CarType.CARTYPE_SEDAN));
		carService.create(createCar(CarType.CARTYPE_VAN));

		final List<Car> cars = carService.getAll();
		Assert.assertTrue("Can't find any car!", cars.size() > 0);
		final Car car = cars.get(randomInteger(0, cars.size() - 1));

		final List<Driver> drivers = driverService.getAll();
		Assert.assertTrue("Can't find any driver!", drivers.size() > 0);
		final Driver driver = drivers.get(randomInteger(0, drivers.size() - 1));

		final Auto auto = createAuto();
		auto.setDriver(driver);
		autoService.create(auto, car);

	}

	@Test
	public void createOrdersWithAttribs() {

		CreateDataForOrders();

		final List<Customer> customers = customerService.getAll();
		Assert.assertTrue("Can't find any customer!", customers.size() > 0);
		final Customer customer = customers.get(0);

		CarType carType = CarType.CARTYPE_SEDAN;

		final List<Auto> autos = autoService.getAllByCarType(carType);
		Assert.assertTrue("Can't find any car!", autos.size() > 0);
		final Auto auto = autos.get(0);

		final List<Price> prices = priceService.getAllByCarType(carType);
		Assert.assertTrue("Can't find any price!", prices.size() > 0);
		final Price price = prices.get(0);

		final Order order = createOrder();
		order.setCustomer(customer);
		order.setPrice(price);
		order.setAuto(auto);
		orderService.create(order);

	}

}
