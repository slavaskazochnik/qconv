package by.parfen.disptaxi;

import javax.inject.Inject;

import org.junit.Before;
import org.springframework.stereotype.Service;

import by.parfen.disptaxi.services.AutoService;
import by.parfen.disptaxi.services.CarService;
import by.parfen.disptaxi.services.CityService;
import by.parfen.disptaxi.services.CustomerService;
import by.parfen.disptaxi.services.DriverService;
import by.parfen.disptaxi.services.OrderService;
import by.parfen.disptaxi.services.PointService;
import by.parfen.disptaxi.services.PriceService;
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
	private AutoService autoService;
	@Inject
	private CarService carService;

	@Inject
	private PointService pointService;
	@Inject
	private StreetService streetService;
	@Inject
	private CityService cityService;

	@Inject
	private PriceService priceService;
	@Inject
	private OrderService orderService;

	@Before
	public void cleanUpData() {

		orderService.deleteAll();
		priceService.deleteAll();

		autoService.deleteAll();
		carService.deleteAll();

		driverService.deleteAll();
		customerService.deleteAll();
		userAccountService.deleteAll();
		userRoleService.deleteAll();
		userProfileService.deleteAll();

		pointService.deleteAll();
		streetService.deleteAll();
		cityService.deleteAll();

	}
}
