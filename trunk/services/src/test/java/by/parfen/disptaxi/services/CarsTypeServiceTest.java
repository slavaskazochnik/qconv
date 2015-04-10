package by.parfen.disptaxi.services;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.parfen.disptaxi.AbstractServiceTest;
import by.parfen.disptaxi.DbUtilsServiceTest;
import by.parfen.disptaxi.datamodel.CarsType;

@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class CarsTypeServiceTest extends AbstractServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(CarsTypeServiceTest.class);

	@Inject
	private CarsTypeService carsTypeService;

	@Inject
	private DbUtilsServiceTest dbUtils;

	@Before
	public void cleanUpData() {
		LOGGER
				.info("Instance of CarsTypeService is injected. Class is: {}", carsTypeService.getClass().getName());
		dbUtils.cleanUpData();
	}

	@Test
	public void test1() {
		LOGGER.warn("Test log message in test1().");
		Assert.assertNotNull(carsTypeService);
	}

	@Test
	public void basicCRUDTest() {
		CarsType carsType = createCarsType();
		carsTypeService.create(carsType);

		CarsType carsTypeFromDb = carsTypeService.get(carsType.getId());
		Assert.assertNotNull(carsTypeFromDb);
		Assert.assertEquals(carsTypeFromDb.getName(), carsType.getName());
		// TODO check other fields

		carsTypeFromDb.setName(randomString("NEW-TYPE-"));
		carsTypeService.update(carsTypeFromDb);
		CarsType carsTypeFromDbUpdated = carsTypeService.get(carsType.getId());
		Assert.assertEquals(carsTypeFromDbUpdated.getName(), carsTypeFromDb.getName());
		Assert.assertNotEquals(carsTypeFromDbUpdated.getName(), carsType.getName());

		carsTypeService.delete(carsTypeFromDbUpdated);
		Assert.assertNull(carsTypeService.get(carsType.getId()));
	}

}
