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
import by.parfen.disptaxi.datamodel.AppRole;
import by.parfen.disptaxi.datamodel.enums.AppRoleId;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class AppRoleServiceTest extends AbstractServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppRoleServiceTest.class);

	@Inject
	private AppRoleService appRoleService;

	@Before
	public void cleanUpData() {
		LOGGER.info("Instance of AppRoleService is injected. Class is: {}", appRoleService.getClass().getName());
		appRoleService.deleteAll();
	}

	@Test
	public void test1() {
		LOGGER.warn("Test log message in test1().");
		Assert.assertNotNull(appRoleService);
	}

	@Test
	public void test2() {
		LOGGER.info("Test started");
		Assert.assertNotNull(appRoleService);

		AppRole appRole = new AppRole();
		Assert.assertNotNull(appRole);
		// appRole.setAppRoleId(AppRoleId.ADMIN_ROLE);

		AppRoleId appRoleId;
		appRoleId = AppRoleId.ADMIN_ROLE;
		appRole.setName(appRoleId.toString());
		appRoleService.create(appRole);
		LOGGER.info(appRole.toString());

		appRole = new AppRole();
		appRoleId = AppRoleId.OPERATOR_ROLE;
		appRole.setName(appRoleId.toString());
		appRoleService.create(appRole);
		LOGGER.info(appRole.toString());

		AppRole appRoleFromDb = appRoleService.get(appRole.getId());
		Assert.assertEquals(appRoleFromDb.getId(), appRole.getId());
		LOGGER.info("Role created with ID = " + appRoleFromDb.getId());

		appRoleService.delete(appRoleFromDb);
	}

	@Test
	public void testEnums() {
		AppRoleId appRoleId = AppRoleId.ADMIN_ROLE;
		LOGGER.debug("appRoleId.toString(): " + appRoleId.toString());
		LOGGER.debug("appRoleId.valueOf(): " + appRoleId.getValue());
	}

}
