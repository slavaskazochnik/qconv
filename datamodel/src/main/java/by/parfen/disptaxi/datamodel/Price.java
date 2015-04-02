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
public class Price {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = CarsType.class)
	private CarsType carsType;
	@Column
	private Long costBefore;
	@Column
	private Long costKm;
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

	public CarsType getCarsType() {
		return carsType;
	}

	public void setCarsType(CarsType carsType) {
		this.carsType = carsType;
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

}
