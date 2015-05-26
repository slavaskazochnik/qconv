package by.parfen.disptaxi.datamodel.filter;

import java.io.Serializable;

import by.parfen.disptaxi.datamodel.Auto;
import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.datamodel.enums.CarType;
import by.parfen.disptaxi.datamodel.enums.SignActive;

public class FilterAuto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Auto auto;
	private Driver driver;
	private SignActive signActive;
	private CarType carType;
	private String positionLat;
	private String positionLng;
	private Long seatsQuan;
	private Long childSeatsQuan;

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public SignActive getSignActive() {
		return signActive;
	}

	public void setSignActive(SignActive signActive) {
		this.signActive = signActive;
	}

	public CarType getCarType() {
		return carType;
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

	public String getPositionLat() {
		return positionLat;
	}

	public void setPositionLat(String positionLat) {
		this.positionLat = positionLat;
	}

	public String getPositionLng() {
		return positionLng;
	}

	public void setPositionLng(String positionLng) {
		this.positionLng = positionLng;
	}

	public Long getSeatsQuan() {
		return seatsQuan;
	}

	public void setSeatsQuan(Long seatsQuan) {
		this.seatsQuan = seatsQuan;
	}

	public Long getChildSeatsQuan() {
		return childSeatsQuan;
	}

	public void setChildSeatsQuan(Long childSeatsQuan) {
		this.childSeatsQuan = childSeatsQuan;
	}

}
