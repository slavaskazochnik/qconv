package by.parfen.disptaxi.datamodel;

import java.util.Date;

public class Car {

	private Long id;
	private String regNum;
	private Long seatsQuan;
	private Long childSeatsQuan;
	private CarsType carsType;
	private Long signActive;
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
	public CarsType getCarsType() {
		return carsType;
	}
	public void setCarsType(CarsType carsType) {
		this.carsType = carsType;
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
