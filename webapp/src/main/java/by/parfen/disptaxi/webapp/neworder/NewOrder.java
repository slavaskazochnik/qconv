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
import by.parfen.disptaxi.datamodel.enums.SignActive;
import by.parfen.disptaxi.datamodel.filter.FilterAuto;
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
		setCarType(CarType.SEDAN);
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
		if (auto != null && auto.getCar() != null) {
			return getPrice(auto.getCar().getCarType());
		} else {
			return null;
		}
	}

	private List<Auto> getAllAuto(FilterAuto filterAuto) {
		return autoService.getAllWithDetails(filterAuto);
	}

	@Override
	public List<Auto> getAllAuto(CarType carType) {
		// FilterAuto filterAuto = new FilterAuto();
		// filterAuto.setCarType(carType);
		filterAuto.setSignActive(SignActive.YES);
		return getAllAuto(filterAuto);
		// return autoService.getAllActiveByCarType(carType);
	}

	@Override
	public List<Auto> getAllAuto(Route route, CarType carType) {
		FilterAuto filterAuto = new FilterAuto();
		filterAuto.setCarType(carType);
		filterAuto.setSignActive(SignActive.YES);
		if (route != null && route.getSrcPoint() != null) {
			filterAuto.setPositionLat(route.getSrcPoint().getPositionLat());
			filterAuto.setPositionLng(route.getSrcPoint().getPositionLng());
		}
		return getAllAuto(filterAuto);
		// if (route != null && route.getSrcPoint() != null) {
		// return autoService.getAllActiveByCarTypeAndGeo(carType,
		// route.getSrcPoint().getPositionLat(), route.getSrcPoint()
		// .getPositionLng());
		// } else {
		// return autoService.getAllActiveByCarType(carType);
		// }
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
		order.setRouteLength(routeDistance / 1000);
		order.setOrderPrice(getCost());
		order.setOrderStatus(OrderStatus.NEW);
		order.setOrderResult(OrderResult.NONE);
		if (order.getId() == null) {
			order.setCreationDate(new Date());
			orderService.create(order);
			orderService.changeOrderStatus(order, OrderStatus.NEW);
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
