package by.parfen.disptaxi.webapp.neworder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.model.ResourceModel;

import by.parfen.disptaxi.datamodel.Auto;
import by.parfen.disptaxi.datamodel.City;
import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.datamodel.Order;
import by.parfen.disptaxi.datamodel.Price;
import by.parfen.disptaxi.datamodel.Route;
import by.parfen.disptaxi.datamodel.enums.CarType;

public class NewOrderClass {
	//
	// Create only one Route item now.
	// But must be modified for building the route with more than two points.
	//

	// current City
	City city;
	Order order;
	// Route info
	Route route;
	List<Route> routeList;
	List<String> routePoints;
	// selected CarType
	CarType carType;
	// selected Auto, Price, Driver, Customer
	Auto auto;
	Price price;
	Driver driver;
	Customer customer;

	Long routeDistance;
	Long routeDuration;

	public NewOrderClass() {
		route = new Route();
		routeList = new ArrayList<Route>();
		routePoints = new ArrayList<String>();
	}

	public void setCity(City city) {
		this.city = city;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public void setCarType(CarType carType) {
		this.carType = carType;
	}

	public Auto getAuto() {
		return auto;
	}

	public void setAuto(Auto auto) {
		this.auto = auto;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public void addAddressToRoute(String address) {
		routePoints.add(address);
	}

	public String getSrcAddress() {
		if (routePoints.size() > 0) {
			return routePoints.get(0);
		}
		return null;
	}

	public String getDstAddress() {
		if (routePoints.size() > 1) {
			return routePoints.get(routePoints.size() - 1);
		}
		return null;
	}

	public void setSrcAddress(String address) {
		if (routePoints.size() == 0) {
			addAddressToRoute(address);
		} else {
			routePoints.set(0, address);
		}
	}

	public void setDstAddress(String address) {
		if (routePoints.size() > 1) {
			routePoints.set(routePoints.size() - 1, address);
		} else {
			addAddressToRoute(address);
		}
	}

	public Long getRouteDistance() {
		return routeDistance;
	}

	public void setRouteDistance(Long routeDistance) {
		this.routeDistance = routeDistance;
		route.setEstLength(routeDistance / 1000);
	}

	public Long getRouteDuration() {
		return routeDuration;
	}

	public void setRouteDuration(Long routeDuration) {
		this.routeDuration = routeDuration;
		route.setEstTime(routeDuration);
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getCustomerInfo() {
		String result;
		result = customer.getUserProfile().getUserInfo();
		return result;
	}

	public String getPriceInfo() {
		String result;
		final String currency = (new ResourceModel("p.price.currency")).getObject();
		result = price.getCostBefore() + " " + currency + " + " + price.getCostKm() + " " + currency + "/"
				+ (new ResourceModel("p.route.length.km")).getObject();
		return result;
	}

	public String getTotalInfo() {
		// number of `routeList` items = 2 (now)
		// use `route` instead `routeList`
		String result;
		final String currency = (new ResourceModel("p.price.currency")).getObject();
		result = route.getEstLength() + " " + (new ResourceModel("p.route.length.km")).getObject();
		result += ", " + route.getEstTime().toString() + (new ResourceModel("p.route.time.measure")).getObject();
		Long cost = price.getCostBefore() + price.getCostKm() * route.getEstLength();
		result += " " + cost + " " + currency;
		return result;
	}

	public String getRouteInfo() {
		String result = null;
		if (routePoints.size() > 1) {
			result = (new ResourceModel("p.neworder.fromTitle")).getObject() + " " + routePoints.get(0);
			result += " " + (new ResourceModel("p.neworder.toTitle")).getObject() + " "
					+ routePoints.get(routePoints.size() - 1);
		}
		return result;
	}

	public void fillRouteByPoints() {
		// number of items = 2 (now)
		if (routePoints != null) {
			Iterator<String> iterator = routePoints.iterator();
			String currAddress = null, prevAddress = null;
			int pointIndex = 0;
			while (iterator.hasNext() && pointIndex < 2) {
				if (pointIndex > 0) {
					prevAddress = currAddress;
				}
				currAddress = iterator.next();
				if (pointIndex > 0) {
					Route routeItem = new Route();
					routeItem.setPointIndex(Long.valueOf(pointIndex));
					routeItem.setSrcPointAddress(prevAddress);
					routeItem.setDstPointAddress(currAddress);
					routeList.add(routeItem);
				}
				pointIndex++;
			}
		}
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}
