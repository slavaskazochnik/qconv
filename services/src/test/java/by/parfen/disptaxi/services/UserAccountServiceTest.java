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
import by.parfen.disptaxi.datamodel.UserAccount;
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.datamodel.UserRole;
import by.parfen.disptaxi.datamodel.enums.AppRole;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class UserAccountServiceTest extends AbstractServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserAccountServiceTest.class);

	@Inject
	private UserAccountService userAccountService;
	@Inject
	private UserRoleService userRoleService;
	@Inject
	private UserProfileService userProfileService;

	@Inject
	private DbUtilsServiceTest dbUtils;

	@Before
	public void cleanUpData() {
		LOGGER.info("Instance of UserAccountService is injected. Class is: {}", userAccountService.getClass().getName());
		dbUtils.cleanUpData();
	}

	@Test
	public void createAccountTest() {
		LOGGER.warn("Test createUserProfileAndAccount.");

		LOGGER.debug("Create user profile.");
		UserProfile userProfile = createUserProfile();
		userProfileService.saveOrUpdate(userProfile);
		Assert.assertNotNull(userProfile.getId());
		LOGGER.debug("Profile id = {}", userProfile.getId());

		LOGGER.debug("Create user roles for created profile and roles.");
		UserRole userRole1 = createUserRole();
		userRole1.setAppRole(AppRole.OPERATOR_ROLE);
		userRoleService.create(userRole1, userProfile);

		UserRole userRole2 = createUserRole();
		userRole2.setAppRole(AppRole.CUSTOMER_ROLE);
		userRoleService.create(userRole2, userProfile);

		UserRole userRoleFromDb = userRoleService.get(userRole1.getId());
		LOGGER.debug("Founded user role1 with id = {}", userRoleFromDb.getId());

		LOGGER.debug("Create account1 for created user role1.");
		UserAccount userAccount1 = createUserAccount();
		userAccount1.setUserRole(userRoleFromDb);
		userRoleFromDb.setUserAccount(userAccount1);
		userRoleService.update(userRoleFromDb);
		LOGGER.debug("userAccount1 = {}", userAccount1);

		userRoleFromDb = userRoleService.get(userRole2.getId());
		LOGGER.debug("Founded user role2 with id = {}", userRoleFromDb.getId());

		LOGGER.debug("Create account2 for created user role2.");
		UserAccount userAccount2 = createUserAccount();
		userAccount2.setUserRole(userRoleFromDb);
		userRoleFromDb.setUserAccount(userAccount2);
		userRoleService.update(userRoleFromDb);
		LOGGER.debug("userAccount2 = {}", userAccount2);

	}

}
