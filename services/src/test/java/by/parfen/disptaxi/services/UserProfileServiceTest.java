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
import by.parfen.disptaxi.datamodel.UserProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class UserProfileServiceTest extends AbstractServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileServiceTest.class);

	@Inject
	private UserProfileService userProfileService;

	@Before
	public void cleanUpData() {
		LOGGER.info("Instance of UserProfileService is injected. Class is: {}", userProfileService.getClass().getName());
		userProfileService.deleteAll();
	}

	@Test
	public void test1() {
		LOGGER.warn("Test log message in test1().");
		Assert.assertNotNull(userProfileService);
	}

	@Test
	public void basicCRUDTest() {
		UserProfile userProfile = createUserProfile();
		userProfileService.saveOrUpdate(userProfile);

		UserProfile userProfileFromDb = userProfileService.get(userProfile.getId());
		Assert.assertNotNull(userProfileFromDb);
		Assert.assertEquals(userProfileFromDb.getFirstName(), userProfile.getFirstName());

		userProfileFromDb.setFirstName("newName");
		userProfileService.saveOrUpdate(userProfileFromDb);
		UserProfile userProfileFromDbUpdated = userProfileService.get(userProfile.getId());
		Assert.assertEquals(userProfileFromDbUpdated.getFirstName(), userProfileFromDb.getFirstName());
		Assert.assertNotEquals(userProfileFromDbUpdated.getFirstName(), userProfile.getFirstName());

		userProfileService.delete(userProfileFromDbUpdated);
		Assert.assertNull(userProfileService.get(userProfile.getId()));
	}

	@Test
	public void searchTest() {
		LOGGER.debug("Search test");
		UserProfile userProfile = createUserProfile();
		userProfileService.saveOrUpdate(userProfile);

		List<UserProfile> allUserProfiles = userProfileService.getAllUserProfiles();
		Assert.assertEquals(allUserProfiles.size(), 1);
	}

	@Test
	public void searchTestWithPagingAndSort() {
		// int pageSize = 10;
		// int randomTestObjectsCount = randomTestObjectsCount() + pageSize + 1;
		// for (int i = 0; i < randomTestObjectsCount; i++) {
		// Product product = createProduct();
		// productService.saveOrUpdate(product);
		// }
		//
		// // select first 10 products ordered by name
		// List<Product> first10Products =
		// productService.getAllProducts(Product_.name, true, 0, pageSize);
		// Assert.assertTrue(first10Products.size() == 10);
		//
	}

	@Test
	public void searchByNameTest() {
		// Product product = createProduct();
		// String name = randomString("name-");
		// product.setName(name);
		// productService.saveOrUpdate(product);
		//
		// Product anotherProduct = createProduct();
		// productService.saveOrUpdate(anotherProduct);
		//
		// List<Product> allProduct = productService.getAllProducts();
		// Assert.assertEquals(allProduct.size(), 2);
		//
		// List<Product> allProductByName =
		// productService.getAllProductsByName(name);
		// Assert.assertEquals(allProductByName.size(), 1);
		// Assert.assertEquals(allProductByName.get(0).getId(), product.getId());
	}
}
