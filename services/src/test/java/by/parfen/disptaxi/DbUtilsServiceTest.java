package by.parfen.disptaxi;

import javax.inject.Inject;

import org.junit.Before;
import org.springframework.stereotype.Service;

import by.parfen.disptaxi.services.AppRoleService;
import by.parfen.disptaxi.services.AutosService;
import by.parfen.disptaxi.services.CarService;
import by.parfen.disptaxi.services.CarsTypeService;
import by.parfen.disptaxi.services.CityService;
import by.parfen.disptaxi.services.CustomerService;
import by.parfen.disptaxi.services.DriverService;
import by.parfen.disptaxi.services.PointService;
import by.parfen.disptaxi.services.StreetService;
import by.parfen.disptaxi.services.UserAccountService;
import by.parfen.disptaxi.services.UserProfileService;
import by.parfen.disptaxi.services.UserRoleService;

@Service
public class DbUtilsServiceTest {
	@Inject
	private DriverService driverService;
	@Inject
	private CustomerService customerService;
	@Inject
	private UserProfileService userProfileService;
	@Inject
	private UserAccountService userAccountService;
	@Inject
	private UserRoleService userRoleService;
	@Inject
	private AppRoleService appRoleService;

	@Inject
	private AutosService autosService;
	@Inject
	private CarService carService;
	@Inject
	private CarsTypeService carsTypeService;

	@Inject
	private PointService pointService;
	@Inject
	private StreetService streetService;
	@Inject
	private CityService cityService;

	@Before
	public void cleanUpData() {
		autosService.deleteAll();
		carService.deleteAll();
		carsTypeService.deleteAll();

		driverService.deleteAll();
		customerService.deleteAll();
		userAccountService.deleteAll();
		userRoleService.deleteAll();
		userProfileService.deleteAll();
		appRoleService.deleteAll();

		pointService.deleteAll();
		streetService.deleteAll();
		cityService.deleteAll();
	}
}
