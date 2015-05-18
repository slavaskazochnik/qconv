package by.parfen.disptaxi.datamodel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import by.parfen.disptaxi.datamodel.enums.CarType;

@Entity
public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	@NotNull
	private String regNum;
	@Column
	@NotNull
	private Long seatsQuan;
	@Column
	private Long childSeatsQuan;
	@Column
	@Enumerated(EnumType.ORDINAL)
	@NotNull
	private CarType carType;
	@Column
	private String carModel;

	@Column
	private Long signActive;
	@Column
	private Date creationDate;

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

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getCarInfo() {
		String result = getRegNum();
		if (getCarModel() != null) {
			result += ", " + getCarModel();
		}
		return result;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", regNum=" + regNum + ", seatsQuan=" + seatsQuan + ", childSeatsQuan=" + childSeatsQuan
				+ ", carModel=" + carModel + ", signActive=" + signActive + ", creationDate=" + creationDate + "]";
	}

}
