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
import by.parfen.disptaxi.DbUtilsServiceTest;
import by.parfen.disptaxi.datamodel.City;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class CityServiceTest extends AbstractServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(CityServiceTest.class);

	@Inject
	private CityService cityService;

	@Inject
	private DbUtilsServiceTest dbUtils;

	@Before
	public void cleanUpData() {
		LOGGER.info("Instance of CityService is injected. Class is: {}", cityService.getClass().getName());
		dbUtils.cleanUpData();
	}

	@Test
	public void test1() {
		LOGGER.warn("Test log message in test1().");
		Assert.assertNotNull(cityService);
	}

	@Test
	public void basicCRUDTest() {
		City city = createCity();
		cityService.saveOrUpdate(city);

		City cityFromDb = cityService.get(city.getId());
		Assert.assertNotNull(cityFromDb);
		Assert.assertEquals(cityFromDb.getName(), city.getName());
		// TODO check other fields

		cityFromDb.setName("newName");
		cityService.saveOrUpdate(cityFromDb);
		City cityFromDbUpdated = cityService.get(city.getId());
		Assert.assertEquals(cityFromDbUpdated.getName(), cityFromDb.getName());
		Assert.assertNotEquals(cityFromDbUpdated.getName(), city.getName());

		cityService.delete(cityFromDbUpdated);
		Assert.assertNull(cityService.get(city.getId()));
	}

}
