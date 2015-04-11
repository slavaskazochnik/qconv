package by.parfen.disptaxi;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.math3.random.RandomData;
import org.apache.commons.math3.random.RandomDataImpl;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.parfen.disptaxi.datamodel.Autos;
import by.parfen.disptaxi.datamodel.Car;
import by.parfen.disptaxi.datamodel.City;
import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.datamodel.Point;
import by.parfen.disptaxi.datamodel.Price;
import by.parfen.disptaxi.datamodel.Street;
import by.parfen.disptaxi.datamodel.UserAccount;
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.datamodel.UserRole;
import by.parfen.disptaxi.datamodel.enums.CarType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public abstract class AbstractServiceTest {

	private final static Random random = new Random();
	protected static final RandomData RANDOM_DATA = new RandomDataImpl();

	private static final int RANDOM_STRING_SIZE = 8;

	public static String randomString() {
		return RandomStringUtils.randomAlphabetic(RANDOM_STRING_SIZE);
	}

	public static String randomString(final String prefix) {
		return String.format("%s-%s", new Object[] { prefix, randomString() });
	}

	public static int randomTestObjectsCount() {
		return RANDOM_DATA.nextInt(0, 5) + 1;
	}

	public static int randomInteger() {
		return randomInteger(0, 9999);
	}

	public static int randomInteger(final int lower, final int upper) {
		return RANDOM_DATA.nextInt(lower, upper);
	}

	public static boolean randomBoolean() {
		return Math.random() < 0.5;
	}

	public static Long randomLong(final Long lower, final Long upper) {
		return RANDOM_DATA.nextLong(lower, upper);
	}

	public static long randomLong() {
		return RANDOM_DATA.nextLong(0, 9999999);
	}

	public static BigDecimal randomBigDecimal() {
		return new BigDecimal(randomDouble()).setScale(2, RoundingMode.HALF_UP);
	}

	public static double randomDouble() {
		final double value = random.nextDouble() + randomInteger();
		return Math.round(value * 1e2) / 1e2;

	}

	public static <T> T randomFromCollection(final Collection<T> all) {
		final int size = all.size();
		final int item = new Random().nextInt(size); // In real life, the Random
		// object should be
		// rather more shared
		// than this
		int i = 0;
		for (final T obj : all) {
			if (i == item) {
				return obj;
			}
			i = i + 1;
		}
		return null;
	}

	public static Date randomDate() {
		final int year = randBetween(1900, 2010);
		final GregorianCalendar gc = new GregorianCalendar();
		gc.set(Calendar.YEAR, year);
		final int dayOfYear = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));
		gc.set(Calendar.DAY_OF_YEAR, dayOfYear);
		return gc.getTime();
	}

	public static int randBetween(final int start, final int end) {
		return start + (int) Math.round(Math.random() * (end - start));
	}

	protected UserProfile createUserProfile() {
		final UserProfile userProfile = new UserProfile();
		userProfile.setFirstName(randomString("firstName-"));
		userProfile.setLastName(randomString("lastName-"));
		userProfile.setTelNum(randomString("+tel-num-"));
		userProfile.setdCreate(randomDate());
		return userProfile;
	}

	protected UserAccount createUserAccount() {
		final UserAccount userAccount = new UserAccount();
		userAccount.setName(randomString("login-"));
		userAccount.setEmail(randomString("email-") + '@' + randomString("domainname") + "." + randomString("sd"));
		userAccount.setPassw(randomString("password"));
		return userAccount;
	}

	protected UserRole createUserRole() {
		final UserRole userRole = new UserRole();
		return userRole;
	}

	protected Driver createDriver() {
		final Driver driver = new Driver();
		driver.setSignActive(0L);
		driver.setdCreate(new Date());
		return driver;
	}

	protected Customer createCustomer() {
		final Customer customer = new Customer();
		customer.setSignActive(0L);
		return customer;
	}

	protected City createCity() {
		City city = new City();
		city.setName(randomString("City-"));
		return city;
	}

	protected Street createStreet() {
		Street street = new Street();
		street.setName(randomString("Street-"));
		return street;
	}

	protected Point createPoint() {
		Point point = new Point();
		String pointName;
		pointName = "" + randomInteger(1, 80);
		int rand = randomInteger(1, 3);
		if (rand == 1) {
			pointName = pointName + randomString().substring(1, 1);
		} else if (rand == 2) {
			pointName = pointName + "/" + randomInteger(1, 999);
		}
		point.setName(pointName);
		return point;
	}

	protected String getRandomRegNum() {
		return randomInteger(1111, 9999) + " " + randomString().substring(1, 3).toUpperCase() + "-" + randomInteger(1, 7);
	}

	protected Car createCar() {
		Car car = new Car();
		car.setRegNum(getRandomRegNum());
		car.setSeatsQuan(randomLong(1L, 12L));
		return car;
	}

	protected Autos createAutos() {
		Autos autos = new Autos();
		autos.setdCreate(new Date());
		autos.setrRoute(randomLong(10L, 20L));
		autos.setrWaiting(randomLong(3L, 10L));
		return autos;
	}

	protected Price createPrice() {
		Price price = new Price();
		price.setCostBefore(randomLong(5000L, 10000L));
		price.setCostKm(randomLong(5000L, 10000L));
		price.setdBegin(new Date());
		price.setCarType(CarType.CARTYPE_SEDAN);
		price.setCostForWaiting(randomLong(1000L, 10000L));
		return price;
	}
}
