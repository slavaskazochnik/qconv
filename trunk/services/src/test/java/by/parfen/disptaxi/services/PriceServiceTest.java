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
import by.parfen.disptaxi.datamodel.Price;
import by.parfen.disptaxi.datamodel.enums.CarType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class PriceServiceTest extends AbstractServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(PriceServiceTest.class);

	@Inject
	private PriceService priceService;

	@Inject
	private DbUtilsServiceTest dbUtils;

	@Before
	public void cleanUpData() {
		LOGGER.info("Instance of PriceService is injected. Class is: {}", priceService.getClass().getName());
		dbUtils.cleanUpData();
	}

	@Test
	public void test1() {
		LOGGER.warn("Test log message in test1().");
		Assert.assertNotNull(priceService);
	}

	@Test
	public void basicCRUDTest() {
		Price price = createPrice(CarType.CARTYPE_SEDAN);
		Long costKm = 3500L;
		price.setCostKm(costKm);
		priceService.create(price);
		Assert.assertNotNull(price.getId());

		Price priceFromDb = priceService.get(price.getId());
		Assert.assertNotNull(priceFromDb);
		Assert.assertEquals(priceFromDb.getCostKm(), price.getCostKm());
		// TODO check other fields

		priceFromDb.setCostKm(0L);
		priceService.update(priceFromDb);
		Price priceFromDbUpdated = priceService.get(price.getId());
		Assert.assertEquals(priceFromDbUpdated.getCostKm(), priceFromDb.getCostKm());

		priceService.delete(priceFromDbUpdated);
		Assert.assertNull(priceService.get(price.getId()));
	}

}
