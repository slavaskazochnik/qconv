package by.parfen.disptaxi.webapp.address;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.injection.Injector;

import by.parfen.disptaxi.datamodel.City;
import by.parfen.disptaxi.datamodel.Point;
import by.parfen.disptaxi.datamodel.Street;
import by.parfen.disptaxi.services.CityService;
import by.parfen.disptaxi.services.PointService;
import by.parfen.disptaxi.services.StreetService;
import by.parfen.disptaxi.webapp.app.BasicAuthenticationSession;

public final class Address {

	private AjaxRequestTarget target;

	@Inject
	private CityService cityService;
	@Inject
	private StreetService streetService;
	@Inject
	private PointService pointService;

	private String address;

	private City city;

	private String streetName;
	private Street street;

	private String pointName;
	private Point point;

	private String pointLat;
	private String pointLng;

	private List<Street> streetList;
	private List<Point> pointList;

	public Address() {
		Injector.get().inject(this);
		setCity(BasicAuthenticationSession.get().getCity());
		setStreet(null);
		setPoint(null);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		if (city == null) {
			// default value as 1-st item
			List<City> cities = cityService.getAll();
			if (cities != null && cities.size() > 0) {
				this.city = cities.get(0);
			}
		} else {
			this.city = city;
		}
		if (this.city != null) {
			streetList = streetService.getAllByCity(this.city);
		} else {
			streetList = new ArrayList<Street>();
		}
	}

	public String getCityName() {
		return city.getName();
	}

	public String getStreetName() {
		return streetName == null ? "" : streetName;
	}

	private void setStreetName(String streetName) {
		this.streetName = streetName;
		this.address = streetName;
	}

	public String getPointName() {
		return pointName == null ? "" : pointName;
	}

	private void setPointName(String pointName) {
		this.pointName = pointName;
		this.address = getStreetName() + ", " + getPointName();
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		if (point == null) {
			this.point = new Point();
		} else {
			this.point = point;
			this.setPointLat(point.getPositionLat());
			this.setPointLng(point.getPositionLng());
		}
	}

	public List<Street> getStreetList() {
		return streetList;
	}

	public List<Point> getPointList() {
		return pointList;
	}

	public Street getStreet() {
		return street;
	}

	public void setStreet(Street street) {
		if (street == null) {
			this.street = new Street();
			this.street.setCity(city);
		} else {
			this.street = street;
		}
		if (this.street != null && this.street.getId() != null) {
			pointList = pointService.getAllByStreet(this.street);
		} else {
			pointList = new ArrayList<Point>();
		}
		setPoint(null);
	}

	public void setStreetByName(String streetName) {
		setStreetName(streetName);
		setStreet(searchStreetByName(getStreetName()));
	}

	public Street searchStreetByName(String streetName) {
		Street result = new Street();
		result.setName(streetName);
		result.setCity(city);
		List<Street> streets = streetService.getAllByName(streetName);
		if (streets != null && streets.size() == 1) {
			result = streets.get(0);
		}
		return result;
	}

	public void setPointByName(String pointName) {
		setPointName(pointName);
		setPoint(searchPointByName(getPointName()));
	}

	public Point searchPointByName(String pointName) {
		Point result = new Point();
		result.setName(pointName);
		if (street != null && street.getId() != null) {
			List<Point> points = pointService.getAllByStreetAndName(street, pointName);
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

	public String getPointLat() {
		return pointLat;
	}

	public void setPointLat(String pointLat) {
		this.pointLat = pointLat;
	}

	public String getPointLng() {
		return pointLng;
	}

	public void setPointLng(String pointLng) {
		this.pointLng = pointLng;
	}

}
