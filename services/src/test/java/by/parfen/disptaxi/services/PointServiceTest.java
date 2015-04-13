package by.parfen.disptaxi.services;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.PersistenceException;

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
import by.parfen.disptaxi.datamodel.City;
import by.parfen.disptaxi.datamodel.Point;
import by.parfen.disptaxi.datamodel.Street;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class PointServiceTest extends AbstractServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(PointServiceTest.class);

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
		LOGGER.info("Instance of UserProfileService is injected. Class is: {}", pointService.getClass().getName());
		dbUtils.cleanUpData();
	}

	@Test
	public void createPointWithStreetAndCity() {

		final City city = createCity();
		cityService.saveOrUpdate(city);
		final City cityFromDB = cityService.get(city.getId());
		Assert.assertNotNull(cityFromDB);
		LOGGER.debug("Created city {}", cityFromDB);

		final Street street = createStreet();
		streetService.create(street, cityFromDB);

		final Street createdStreet = streetService.get(street.getId());
		Assert.assertNotNull(createdStreet);
		LOGGER.debug("Created street, id = {}", createdStreet.getId());
		// TODO check equals

		Point point = createPoint(null);
		pointService.create(point, createdStreet);
		final Point createdPoint = pointService.get(point.getId());
		Assert.assertNotNull(createdPoint);
		LOGGER.debug("Created point, id = {}", createdPoint.getId());

		pointService.delete(createdPoint);
		streetService.delete(createdStreet);
		cityService.delete(cityFromDB);
	}

	@Test
	public void uniqueConstraintsTest() {
		final City firstCity = createCity();
		cityService.saveOrUpdate(firstCity);

		final Street firstStreet = createStreet();
		firstStreet.setCity(firstCity);
		streetService.create(firstStreet, firstCity);

		final String pointName = "123/4";
		final Point firstPoint = createPoint(null);
		firstPoint.setStreet(firstStreet);
		pointService.create(firstPoint, firstStreet);
		Assert.assertNotNull("The point not saved!", firstPoint.getId());

		final List<Point> pointsOnStreet = pointService.getAllByStreet(firstStreet);
		Assert.assertTrue("Can't find any point on the street!", pointsOnStreet.size() > 0);
		LOGGER.debug("Founded points on the street count: {}", pointsOnStreet.size());

		firstPoint.setName(pointName);
		pointService.update(firstPoint);
		Assert.assertEquals("Can't update street name!", firstPoint.getName(), pointName);

		// check LIKE in WHERE
		String pointNameMask = pointName.substring(0, 2) + "%";
		final List<Point> pointsOnStreetByName = pointService.getAllByStreetAndName(firstStreet, pointNameMask);
		Assert.assertTrue("Can't find any point on the street!", pointsOnStreetByName.size() > 0);
		LOGGER.debug("Founded points on the street count: {} with name LIKE '{}'", pointsOnStreetByName.size(),
				pointNameMask);

		final Point secondPoint = createPoint(null);
		secondPoint.setName(pointName);
		try {
			pointService.create(secondPoint, firstStreet);
			Assert.fail("Not unique street point name can't be created!");
		} catch (final PersistenceException e) {
			LOGGER.debug("Duplicate point not created. Ok.");
		}
		secondPoint.setName("321a");
		pointService.create(secondPoint, firstStreet);

		secondPoint.setName(pointName);
		try {
			pointService.update(secondPoint);
			Assert.fail("Not unique street point name can't be saved!");
		} catch (final PersistenceException e) {
			LOGGER.debug("Duplicate point not saved. Ok.");
		}

		final Street secondStreet = createStreet();
		secondStreet.setName("Second Street");
		streetService.create(secondStreet, firstCity);

		secondPoint.setStreet(secondStreet);
		pointService.update(secondPoint);

		secondPoint.setStreet(firstStreet);
		try {
			pointService.update(secondPoint);
			Assert.fail("Not unique street point name can't be saved!");
		} catch (final PersistenceException e) {
			LOGGER.debug("Duplicate point not saved. Ok.");
		}
	}

}
