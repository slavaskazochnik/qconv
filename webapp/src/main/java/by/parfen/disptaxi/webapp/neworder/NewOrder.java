package by.parfen.disptaxi.webapp.neworder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.injection.Injector;

import by.parfen.disptaxi.datamodel.Auto;
import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.datamodel.Order;
import by.parfen.disptaxi.datamodel.Price;
import by.parfen.disptaxi.datamodel.Route;
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.datamodel.enums.CarType;
import by.parfen.disptaxi.datamodel.enums.OrderResult;
import by.parfen.disptaxi.datamodel.enums.OrderStatus;
import by.parfen.disptaxi.services.AutoService;
import by.parfen.disptaxi.services.CustomerService;
import by.parfen.disptaxi.services.OrderService;
import by.parfen.disptaxi.services.PriceService;
import by.parfen.disptaxi.services.RouteService;
import by.parfen.disptaxi.services.UserProfileService;

public class NewOrder extends NewOrderClass implements NewOrderService, Serializable {

	@Inject
	private OrderService orderService;
	@Inject
	private AutoService autoService;
	@Inject
	private PriceService priceService;
	@Inject
	private UserProfileService userProfileService;
	@Inject
	private CustomerService customerService;
	@Inject
	private RouteService routeService;

	public NewOrder() {
		super();
		Injector.get().inject(this);
	}

	@Override
	public Price getPrice(CarType carType) {
		Price result = null;
		List<Price> prices;
		prices = priceService.getAllByCarType(carType);
		if (prices != null && prices.size() > 0) {
			result = prices.get(0);
		}
		return result;
	}

	@Override
	public Price getPrice(Auto auto) {
		return getPrice(auto.getCar().getCarType());
	}

	@Override
	public List<Auto> getAllAuto(CarType carType) {
		return autoService.getAllActiveByCarType(carType);
	}

	@Override
	public List<Auto> getAllAuto(Route route, CarType carType) {
		if (route != null && route.getSrcPoint() != null) {
			return autoService.getAllActiveByCarTypeAndGeo(carType, route.getSrcPoint().getPositionLat(), route.getSrcPoint()
					.getPositionLng());
		} else {
			return autoService.getAllActiveByCarType(carType);
		}
	}

	@Override
	public Auto getAutoWithDetails(Auto auto) {
		return autoService.getWithDetails(auto);
	}

	@Override
	public UserProfile getUserProfile(Long userId) {
		return userProfileService.get(userId);
	}

	@Override
	public void insertOrderIntoDB() {
		final Order order = new Order();
		if (customer.getId() == null) {
			// TODO create Customer
		}
		order.setCustomer(customer);
		order.setAuto(auto);
		order.setPrice(price);
		order.setCreationDate(new Date());
		order.setRouteLength(routeDistance);
		order.setOrderPrice(getCost());
		order.setOrderStatus(OrderStatus.NEW);
		order.setOrderResult(OrderResult.NONE);
		if (order.getId() == null) {
			order.setCreationDate(new Date());
			orderService.create(order);
		} else {
			orderService.update(order);
		}
		setOrder(order);
		fillRouteByPoints();
		for (Route route : routeList) {
			route.setOrder(order);
			if (route.getId() == null) {
				routeService.create(route);
			} else {
				routeService.update(route);
			}
			setRoute(route);
		}
	}

	@Override
	public List<Customer> getAllCustomersWithDetails() {
		return customerService.getAllWithDetails();
	}

}
