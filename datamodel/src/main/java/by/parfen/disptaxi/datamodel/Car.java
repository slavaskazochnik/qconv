package by.parfen.disptaxi.datamodel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import by.parfen.disptaxi.datamodel.enums.CarType;

@Entity
public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String regNum;
	@Column
	private Long seatsQuan;
	@Column
	private Long childSeatsQuan;
	@Column
	@Enumerated(EnumType.ORDINAL)
	private CarType carType;
	@Column
	private String carModel;

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

	public String getRegNum() {
		return regNum;
	}

	public void setRegNum(String regNum) {
		this.regNum = regNum;
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

	public CarType getCarType() {
		return carType;
	}

	public void setCarType(CarType carType) {
		this.carType = carType;
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

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", regNum=" + regNum + ", seatsQuan=" + seatsQuan + ", childSeatsQuan=" + childSeatsQuan
				+ ", carModel=" + carModel + ", signActive=" + signActive + ", dCreate=" + dCreate + "]";
	}

}
