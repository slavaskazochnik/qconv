package by.parfen.disptaxi.datamodel;

import java.util.Date;

public class Autos {

	private Long id;
	private Car car;
	private Driver driver;
	private Long rWaiting;
	private Long rRoute;
	private Long positionLat;
	private Long positionLng;
	private Long signActive;
	private Date dCreate;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
	public Driver getDriver() {
		return driver;
	}
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	public Long getrWaiting() {
		return rWaiting;
	}
	public void setrWaiting(Long rWaiting) {
		this.rWaiting = rWaiting;
	}
	public Long getrRoute() {
		return rRoute;
	}
	public void setrRoute(Long rRoute) {
		this.rRoute = rRoute;
	}
	public Long getPositionLat() {
		return positionLat;
	}
	public void setPositionLat(Long positionLat) {
		this.positionLat = positionLat;
	}
	public Long getPositionLng() {
		return positionLng;
	}
	public void setPositionLng(Long positionLng) {
		this.positionLng = positionLng;
	}
	public Long getSignActive() {
		return signActive;
	}
	public void setSignActive(Long signActive) {
		this.signActive = signActive;
	}
	public Date getdCreate() {
		return dCreate;
	}
	public void setdCreate(Date dCreate) {
		this.dCreate = dCreate;
	}

}
