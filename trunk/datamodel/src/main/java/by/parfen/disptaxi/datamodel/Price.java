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
public class Price extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	@Enumerated(EnumType.ORDINAL)
	private CarType carType;
	@Column
	private Long costBefore;
	@Column
	private Long costKm;
	@Column
	private Long costForWaiting;
	@Column
	private Date dBegin;
	@Column
	private Date dEnd;
	@Column
	private Date dCreate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCostBefore() {
		return costBefore;
	}

	public void setCostBefore(Long costBefore) {
		this.costBefore = costBefore;
	}

	public Long getCostKm() {
		return costKm;
	}

	public void setCostKm(Long costKm) {
		this.costKm = costKm;
	}

	public CarType getCarType() {
		return carType;
	}

	public void setCarType(CarType carType) {
		this.carType = carType;
	}

	public Long getCostForWaiting() {
		return costForWaiting;
	}

	public void setCostForWaiting(Long costForWaiting) {
		this.costForWaiting = costForWaiting;
	}

	public Date getdBegin() {
		return dBegin;
	}

	public void setdBegin(Date dBegin) {
		this.dBegin = dBegin;
	}

	public Date getdEnd() {
		return dEnd;
	}

	public void setdEnd(Date dEnd) {
		this.dEnd = dEnd;
	}

	public Date getdCreate() {
		return dCreate;
	}

	public void setdCreate(Date dCreate) {
		this.dCreate = dCreate;
	}

	@Override
	public String toString() {
		return "Price [id=" + id + ", carType=" + carType + ", costBefore=" + costBefore + ", costKm=" + costKm
				+ ", dBegin=" + dBegin + ", dEnd=" + dEnd + ", dCreate=" + dCreate + "]";
	}

}
