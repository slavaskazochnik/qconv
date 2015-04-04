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
import by.parfen.disptaxi.datamodel.Car;
import by.parfen.disptaxi.datamodel.CarsType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class CarServiceTest extends AbstractServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(CarServiceTest.class);

	@Inject
	private CarService carService;
	@Inject
	private CarsTypeService carsTypeService;
	@Inject
	private CarsTypeServiceTest carsTypeServiceTest;

	@Before
	public void cleanUpData() {
		LOGGER.info("Instance of CarService is injected. Class is: {}", carService.getClass().getName());
		carService.deleteAll();
	}

	@Test
	public void test1() {
		LOGGER.warn("Test log message in test1().");
		Assert.assertNotNull(carService);
	}

	@Test
	public void basicCRUDTest() {
		Car car = createCar();
		CarsType carsType = carsTypeServiceTest.createCarsType();
		carsTypeService.create(carsType);
		LOGGER.debug(carsType.toString());
		carService.create(car, carsType);
		CarsType carsTypeFromCar = car.getCarsType();
		LOGGER.debug(carsTypeFromCar.toString());

		Car carFromDb = carService.get(car.getId());
		Assert.assertNotNull(carFromDb);
		Assert.assertEquals(carFromDb.getRegNum(), car.getRegNum());
		// TODO check other fields

		carFromDb.setRegNum(getRandomRegNum());
		// LOGGER.debug("Car.CarsType.name: " + carsType.getName());
		carService.update(carFromDb);
		Car carFromDbUpdated = carService.get(car.getId());
		Assert.assertEquals(carFromDbUpdated.getRegNum(), carFromDb.getRegNum());
		Assert.assertNotEquals(carFromDbUpdated.getRegNum(), car.getRegNum());

		carService.delete(carFromDbUpdated);
		Assert.assertNull(carService.get(car.getId()));
	}

	private String getRandomRegNum() {
		return randomInteger(1111, 9999) + " " + randomString().substring(1, 3).toUpperCase() + "-" + randomInteger(1, 7);
	}

	private Car createCar() {
		Car car = new Car();
		car.setRegNum(getRandomRegNum());
		car.setSeatsQuan(randomLong(1L, 12L));
		return car;
	}

}
