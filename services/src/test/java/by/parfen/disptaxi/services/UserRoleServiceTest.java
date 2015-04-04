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
import by.parfen.disptaxi.datamodel.UserProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class UserRoleServiceTest extends AbstractServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleServiceTest.class);

	@Inject
	private UserProfileService userProfileService;

	@Before
	public void cleanUpData() {
		LOGGER.info("Instance of UserProfileService is injected. Class is: {}", userProfileService.getClass().getName());
		// userProfileService.deleteAll();
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

}
