package by.parfen.disptaxi.webapp.choosecity;

import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.injection.Injector;

import by.parfen.disptaxi.datamodel.City;
import by.parfen.disptaxi.datamodel.Point;
import by.parfen.disptaxi.datamodel.Price;
import by.parfen.disptaxi.datamodel.Street;
import by.parfen.disptaxi.services.CityService;
import by.parfen.disptaxi.services.PointService;
import by.parfen.disptaxi.services.PriceService;
import by.parfen.disptaxi.services.StreetService;

public final class ChooseCity {

	private AjaxRequestTarget target;

	@Inject
	private CityService cityService;
	@Inject
	private StreetService streetService;
	@Inject
	private PointService pointService;
	@Inject
	private PriceService priceService;

	private City currentCity;

	private String selectedStreet;
	private Street currentStreet;

	private String selectedPoint;
	private Point currentPoint;

	private List<Street> streetList;
	private List<Point> pointList;

	private Price price;

	public ChooseCity() {
		Injector.get().inject(this);
		currentStreet = new Street();
		currentPoint = new Point();
	}

	public City getCurrentCity() {
		return currentCity;
	}

	public void setCurrentCity(City currentCity) {
		this.currentCity = currentCity;
		if (currentCity != null) {
			streetList = streetService.getAllByCity(currentCity);
		} else {
			streetList = null;
		}
	}

	public String getSelectedStreet() {
		return selectedStreet;
	}

	public String getSelectedPoint() {
		return selectedPoint;
	}

	public List<Street> getStreetList() {
		return streetList;
	}

	public void setCurrentCityByName(String cityName) {
		setCurrentCity(searchCityByName(cityName));
	}

	private City searchCityByName(String cityName) {
		City result = new City();
		List<City> cities = cityService.getAllByName(cityName);
		if (cities != null && cities.size() > 0) {
			result = cities.get(0);
		}
		return result;
	}

	public List<Point> getPointList() {
		return pointList;
	}

	public Street getCurrentStreet() {
		return currentStreet;
	}

	public void setCurrentStreet(Street currentStreet) {
		this.currentStreet = currentStreet;
		if (this.currentStreet != null && this.currentStreet.getId() != null) {
			pointList = pointService.getAllByStreet(this.currentStreet);
		} else {
			pointList = null;
		}
	}

	public void setCurrentStreetByName(String streetName) {
		selectedStreet = streetName;
		setCurrentStreet(searchStreetByName(selectedStreet));
	}

	public Street searchStreetByName(String streetName) {
		Street result = new Street();
		result.setName(streetName);
		result.setCity(currentCity);
		List<Street> streets = streetService.getAllByName(streetName);
		if (streets != null && streets.size() == 1) {
			result = streets.get(0);
		}
		return result;
	}

	public Point getCurrentPoint() {
		return currentPoint;
	}

	public void setCurrentPoint(Point currentPoint) {
		this.currentPoint = currentPoint;
	}

	public void setCurrentPointByName(String pointName) {
		selectedPoint = pointName;
		setCurrentPoint(searchPointByName(selectedPoint));
	}

	public Point searchPointByName(String pointName) {
		Point result = new Point();
		result.setName(pointName);
		if (currentStreet != null && currentStreet.getId() != null) {
			List<Point> points = pointService.getAllByStreetAndName(currentStreet, pointName);
			if (points != null && points.size() == 1) {
				result = points.get(0);
			}
		}
		return result;
	}

	public AjaxRequestTarget getTarget() {
		return target;
	}

	public void setTarget(AjaxRequestTarget target) {
		this.target = target;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public void searchPrice() {
		List<Price> prices = priceService.getAll();
		if (prices != null && prices.size() > 0) {
			setPrice(prices.get(0));
		}
	}

}
