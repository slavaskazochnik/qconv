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
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.datamodel.UserProfile_;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class UserProfileServiceTest extends AbstractServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileServiceTest.class);

	@Inject
	private UserProfileService userProfileService;

	@Inject
	private DbUtilsServiceTest dbUtils;

	@Before
	public void cleanUpData() {
		LOGGER.info("Instance of UserProfileService is injected. Class is: {}", userProfileService.getClass()
				.getName());
		dbUtils.cleanUpData();
	}

	@Test
	public void test1() {
		LOGGER.warn("Test log message in test1().");
		Assert.assertNotNull(userProfileService);
	}

	@Test
	public void basicCRUDTest() {
		LOGGER.warn("Test basicCRUDTest.");
		LOGGER.debug("Create user profile.");
		UserProfile userProfile = createUserProfile();
		userProfileService.saveOrUpdate(userProfile);

		LOGGER.debug("Try to get user profile with id = {}", userProfile.getId());
		UserProfile userProfileFromDb = userProfileService.get(userProfile.getId());
		Assert.assertNotNull(userProfileFromDb);
		LOGGER.debug("Record founded.");
		Assert.assertEquals(userProfileFromDb.getFirstName(), userProfile.getFirstName());
		LOGGER.debug("Record has the same FistName: {}.", userProfile.getFirstName());

		userProfileFromDb.setFirstName("newName");
		userProfileService.saveOrUpdate(userProfileFromDb);
		LOGGER.debug("First name updated");
		UserProfile userProfileFromDbUpdated = userProfileService.get(userProfile.getId());
		Assert.assertEquals(userProfileFromDbUpdated.getFirstName(), userProfileFromDb.getFirstName());
		Assert.assertNotEquals(userProfileFromDbUpdated.getFirstName(), userProfile.getFirstName());
		LOGGER.debug("Ok.");

		userProfileService.delete(userProfileFromDbUpdated);
		Assert.assertNull(userProfileService.get(userProfile.getId()));
	}

	@Test
	public void searchTest() {
		LOGGER.warn("Test searchTest");
		UserProfile userProfile = createUserProfile();
		userProfileService.saveOrUpdate(userProfile);
		LOGGER.debug("Created 1 record.");

		LOGGER.debug("Get all user profiles.");
		List<UserProfile> allUserProfiles = userProfileService.getAll();
		Assert.assertEquals(allUserProfiles.size(), 1);
		LOGGER.debug("Ok. Founded 1 record.");

		LOGGER.debug("Count user profiles.");
		Long count = userProfileService.getCount();
		Assert.assertEquals(count.intValue(), 1);
		LOGGER.debug("Count result:{}.", count);
	}

	@Test
	public void searchTestWithPagingAndSort() {
		LOGGER.warn("Test searchTestWithPagingAndSort");
		int pageSize = 10;
		int randomTestObjectsCount = randomTestObjectsCount() + pageSize + 1;
		LOGGER.debug("Create {} records of UserProfile", randomTestObjectsCount);
		for (int i = 0; i < randomTestObjectsCount; i++) {
			UserProfile userProfile = createUserProfile();
			userProfileService.saveOrUpdate(userProfile);
		}

		LOGGER.debug("Select {} UserProfile records", pageSize);
		List<UserProfile> first10Products = userProfileService.getAll(UserProfile_.firstName, true, 0, pageSize);
		Assert.assertTrue(first10Products.size() == pageSize);
		LOGGER.debug("Ok. Selected {} records", pageSize);

	}

	@Test
	public void searchByNameTest() {
		LOGGER.warn("Test searchByNameTest");
		UserProfile userProfile = createUserProfile();
		String firstName = randomString("firstName-");
		userProfile.setFirstName(firstName);
		userProfileService.saveOrUpdate(userProfile);
		LOGGER.debug("1-st userProfile created.");

		UserProfile anotherUserProfile = createUserProfile();
		userProfileService.saveOrUpdate(anotherUserProfile);
		LOGGER.debug("2-nd userProfile created.");

		List<UserProfile> allUserProfiles = userProfileService.getAll();
		Assert.assertEquals(allUserProfiles.size(), 2);
		LOGGER.debug("Ok. Founded 2 userProfiles.");

		LOGGER.debug("Search by name '{}'", firstName);
		List<UserProfile> allUserProfilesByName = userProfileService.getAllByFirstName(firstName);
		Assert.assertEquals(allUserProfilesByName.size(), 1);
		LOGGER.debug("Founded 1 record.");
		Assert.assertEquals(allUserProfilesByName.get(0).getId(), userProfile.getId());
		LOGGER.debug("This record have same id = {}.", userProfile.getId());
	}
}
