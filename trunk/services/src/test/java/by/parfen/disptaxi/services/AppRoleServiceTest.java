package by.parfen.disptaxi.services;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.parfen.disptaxi.datamodel.AppRole;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class AppRoleServiceTest {
	
	@Inject
	private AppRoleService appRoleService;
	
	private AppRole appRole;
	
	@Test
	public void test1(){
		Assert.assertNotNull(appRoleService);
	}
	
	@Test
	public void test2(){
		Assert.assertNotNull(appRoleService);
		appRoleService.save(appRole);
	}
	
}
