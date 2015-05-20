package by.parfen.disptaxi.webapp.neworder;

import java.util.List;

import by.parfen.disptaxi.datamodel.Auto;
import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.datamodel.Price;
import by.parfen.disptaxi.datamodel.Route;
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.datamodel.enums.CarType;

public interface NewOrderService {

	/*
	 * Search price by Route, CarType
	 */
	Price getPrice(CarType carType);

	/*
	 * Search price by Route, Auto
	 */
	Price getPrice(Auto auto);

	List<Auto> getAllAuto(CarType carType);

	List<Auto> getAllAuto(Route route, CarType carType);

	Auto getAutoWithDetails(Auto auto);

	void addAddressToRoute(String address);

	UserProfile getUserProfile(Long userId);

	void insertOrderIntoDB();

	List<Customer> getAllCustomersWithDetails();
}
