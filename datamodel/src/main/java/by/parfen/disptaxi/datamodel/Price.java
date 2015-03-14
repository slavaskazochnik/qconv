package by.parfen.disptaxi.datamodel;

import java.util.Date;

public class Price {

	private Long id;
	private CarsType carsType;
	private Long costBefore;
	private Long costKm;
	private Date dBegin;
	private Date dEnd;
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
