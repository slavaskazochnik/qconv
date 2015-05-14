package by.parfen.disptaxi.webapp.etc;

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

public class SampleEvent {

	private AjaxRequestTarget target;

	private String newLabel;

	private City selectedCity;
	private String selectedStreetName;
	private Street selectedStreet;
	private String selectedPointName;
	private Point selectedPoint;

	private List<Street> streetsList;
	private List<Point> pointsList;

	@Inject
	private CityService cityService;
	@Inject
	private StreetService streetService;
	@Inject
	private PointService pointService;

	public AjaxRequestTarget getTarget() {
		return target;
	}

	public void setTarget(AjaxRequestTarget target) {
		this.target = target;
	}

	public SampleEvent() {
		Injector.get().inject(this);
	}

	public String getNewLabel() {
		return newLabel;
	}

	public void setNewLabel(String newLabel) {
		this.newLabel = newLabel;
	}

	public City getSelectedCity() {
		return selectedCity;
	}

	public void setSelectedCity(City selectedCity) {
		this.selectedCity = selectedCity;
		setStreetsList(streetService.getAllByCityAndName(selectedCity, null));
	}

	public String getSelectedStreetName() {
		return selectedStreetName;
	}

	public Street getSelectedStreet() {
		return selectedStreet;
	}

	public void setSelectedStreet(Street selectedStreet) {
		this.selectedStreet = selectedStreet;
		if (selectedStreet != null && selectedStreet.getId() != null) {
			setPointsList(pointService.getAllByStreet(selectedStreet));
		}
	}

	public void setSelectedStreetName(String selectedStreetName) {
		this.selectedStreetName = selectedStreetName;
		List<Street> streetList = streetService.getAllByCityAndName(selectedCity, selectedStreetName);
		if (streetList.size() == 1) {
			setSelectedStreet(streetList.get(0));
		} else {
			Street newStreet = new Street();
			newStreet.setCity(selectedCity);
			newStreet.setName(selectedStreetName);
			setSelectedStreet(newStreet);
		}
	}

	public City getCityById(Long id) {
		return cityService.get(60L);
	}

	public void setCityById(Long id) {
		setSelectedCity(cityService.get(60L));
	}

	public List<Street> getStreets() {
		return streetService.getAllByCity(selectedCity);
	}

	public List<Point> getPointsList() {
		return pointsList;
	}

	public void setPointsList(List<Point> pointsList) {
		this.pointsList = pointsList;
	}

	public List<Street> getStreetsList() {
		return streetsList;
	}

	public void setStreetsList(List<Street> streetsList) {
		this.streetsList = streetsList;
		this.pointsList = null;
	}

	public Point getSelectedPoint() {
		return selectedPoint;
	}

	public void setSelectedPoint(Point selectedPoint) {
		this.selectedPoint = selectedPoint;
	}

	public String getSelectedPointName() {
		return selectedPointName;
	}

	public void setSelectedPointName(String selectedPointName) {
		this.selectedPointName = selectedPointName;
	}

}
