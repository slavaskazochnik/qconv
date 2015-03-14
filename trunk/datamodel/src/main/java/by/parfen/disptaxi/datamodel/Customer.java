package by.parfen.disptaxi.datamodel;

import java.util.Date;

public class Customer {

	private Long id;
	private Person person;
	private Long avgRating;
	private Long signActive;
	private Date dCreate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Long getAvgRating() {
		return avgRating;
	}
	public void setAvgRating(Long avgRating) {
		this.avgRating = avgRating;
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
