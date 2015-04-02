package by.parfen.disptaxi.datamodel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Autos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Car.class)
	private Car car;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Driver.class)
	private Driver driver;
	@Column
	private Long rWaiting;
	@Column
	private Long rRoute;
	@Column
	private Long positionLat;
	@Column
	private Long positionLng;
	@Column
	private Long signActive;
	@Column
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

	@Override
	public String toString() {
		return "Autos [id=" + id + ", car=" + car + ", driver=" + driver + ", rWaiting=" + rWaiting + ", rRoute=" + rRoute
				+ ", positionLat=" + positionLat + ", positionLng=" + positionLng + ", signActive=" + signActive + ", dCreate="
				+ dCreate + "]";
	}

}
