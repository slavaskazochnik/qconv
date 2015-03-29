package by.parfen.disptaxi.services;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.parfen.disptaxi.datamodel.AppRole;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class AppRoleServiceTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AppRoleServiceTest.class);

	@Inject
	private AppRoleService appRoleService;
	
	//private AppRole appRole;
	
	@Test
	public void test1(){
		LOGGER.warn("Test log message in test1().");
		Assert.assertNotNull(appRoleService);
	}
	
	@Test
	public void test2() {
		LOGGER.info("Test started");
		Assert.assertNotNull(appRoleService);
		// appRoleService.save(appRole);
		AppRole appRole = appRoleService.get(1L);
		LOGGER.info(appRole.toString());
		Assert.assertNotNull(appRole);
	}
	
}
