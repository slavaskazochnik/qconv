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
import by.parfen.disptaxi.datamodel.Street;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class StreetServiceTest extends AbstractServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(StreetServiceTest.class);

	@Inject
	private StreetService streetService;
	@Inject
	private CityService cityService;

	@Inject
	private DbUtilsServiceTest dbUtils;

	@Before
	public void cleanUpData() {
		LOGGER.info("Instance of UserProfileService is injected. Class is: {}", streetService.getClass().getName());
		dbUtils.cleanUpData();
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

	@Test
	public void uniqueConstraintsTest() {
		final City firstCity = createCity();
		firstCity.setName("First City");
		cityService.saveOrUpdate(firstCity);

		final String streetName = "Duplicate Street Name";
		final Street firstStreet = createStreet();
		firstStreet.setCity(firstCity);
		streetService.create(firstStreet, firstCity);
		// final UserProfile profile = createUserProfile();
		Assert.assertNotNull("The street not saved!", firstStreet.getId());

		firstStreet.setName(streetName);
		streetService.update(firstStreet);
		Assert.assertEquals("Can't update street name!", firstStreet.getName(), streetName);

		final String streetNameMask = streetName.substring(0, 5) + "%";
		List<Street> streets = streetService.getAllByName(streetNameMask);
		Assert.assertTrue("Can't find street with name criteria!", streets.size() > 0);
		LOGGER.debug("Founded streets count: {} with name LIKE '{}'", streets.size(), streetNameMask);

		final Street secondStreet = createStreet();
		secondStreet.setName(streetName);
		try {
			streetService.create(secondStreet, firstCity);
			Assert.fail("Not unique city street name can't be created!");
		} catch (final PersistenceException e) {
			LOGGER.debug("Duplicate street not created. Ok.");
		}
		secondStreet.setName("First Street");
		streetService.create(secondStreet, firstCity);

		secondStreet.setName(streetName);
		try {
			streetService.update(secondStreet);
			Assert.fail("Not unique city street name can't be saved!");
		} catch (final PersistenceException e) {
			LOGGER.debug("Duplicate street not saved. Ok.");
		}

		final City secondCity = createCity();
		secondCity.setName("Second City");
		cityService.saveOrUpdate(secondCity);

		secondStreet.setCity(secondCity);
		streetService.update(secondStreet);

		secondStreet.setCity(firstCity);
		try {
			streetService.update(secondStreet);
			Assert.fail("Not unique city street name can't be saved!");
		} catch (final PersistenceException e) {
			LOGGER.debug("Duplicate street not saved. Ok.");
		}
	}

}
