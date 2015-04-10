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
import by.parfen.disptaxi.datamodel.AppRole;
import by.parfen.disptaxi.datamodel.enums.AppRoleId;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class AppRoleServiceTest extends AbstractServiceTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppRoleServiceTest.class);

	@Inject
	private AppRoleService appRoleService;

	@Inject
	private DbUtilsServiceTest dbUtils;

	@Before
	public void cleanUpData() {
		LOGGER.info("Instance of AppRoleService is injected. Class is: {}", appRoleService.getClass().getName());
		dbUtils.cleanUpData();
	}

	@Test
	public void createRoles() {
		LOGGER.warn("Test createRoles()");
		Assert.assertNotNull(appRoleService);

		AppRole appRole = new AppRole();
		Assert.assertNotNull(appRole);

		AppRoleId appRoleId;
		appRoleId = AppRoleId.ADMIN_ROLE;
		// appRole.setId(appRoleId.getLongValue());
		appRole.setName(appRoleId.toString());
		appRoleService.create(appRole);
		LOGGER.debug("Created AppRole: {}", appRole);

		appRole = new AppRole();
		appRoleId = AppRoleId.OPERATOR_ROLE;
		appRole.setName(appRoleId.toString());
		appRoleService.create(appRole);
		LOGGER.debug("Created AppRole: {}", appRole);

		AppRole appRoleFromDb = appRoleService.get(appRole.getId());
		Assert.assertEquals(appRoleFromDb.getId(), appRole.getId());
		LOGGER.debug("Role created with ID = " + appRoleFromDb.getId());

		// Long id = appRoleId.getLongValue();
		// appRole.setId(id);
		// appRoleService.update(appRole);
		// LOGGER.debug("Updated AppRole: {}", appRole);

		appRoleService.delete(appRoleFromDb);
	}

	@Test
	public void testEnums() {
		AppRoleId appRoleId = AppRoleId.ADMIN_ROLE;
		LOGGER.debug("appRoleId.toString(): " + appRoleId.toString());
		LOGGER.debug("appRoleId.valueOf(): " + appRoleId.getValue());
	}

}
