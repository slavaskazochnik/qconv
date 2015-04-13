package by.parfen.disptaxi.datamodel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import by.parfen.disptaxi.datamodel.enums.SignActive;

@Entity
@Table(name = "autos")
public class Auto {

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
	private String positionLat;
	@Column
	private String positionLng;
	@Column
	@Enumerated(EnumType.ORDINAL)
	private SignActive signActive;
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

	public SignActive getSignActive() {
		return signActive;
	}

	public void setSignActive(SignActive signActive) {
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
		return "Autos [id=" + id + ", rWaiting=" + rWaiting + ", rRoute=" + rRoute + ", positionLat=" + positionLat
				+ ", positionLng=" + positionLng + ", signActive=" + signActive + ", dCreate=" + dCreate + "]";
	}

}
