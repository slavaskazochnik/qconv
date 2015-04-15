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
	private Date beginDate;
	@Column
	private Date endDate;
	@Column
	private Date creationDate;

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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "Price [id=" + id + ", carType=" + carType + ", costBefore=" + costBefore + ", costKm=" + costKm
				+ ", beginDate=" + beginDate + ", endDate=" + endDate + ", creationDate=" + creationDate + "]";
	}

}
