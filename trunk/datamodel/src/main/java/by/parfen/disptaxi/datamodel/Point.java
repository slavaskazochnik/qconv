package by.parfen.disptaxi.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Point {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Street.class)
	private Street street;
	@Column
	private String name;
	@Column
	private Long pointLat;
	@Column
	private Long pointLng;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Street getStreet() {
		return street;
	}

	public void setStreet(Street street) {
		this.street = street;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPositionLat() {
		return pointLat;
	}

	public void setPositionLat(Long pointLat) {
		this.pointLat = pointLat;
	}

	public Long getPositionLng() {
		return pointLng;
	}

	public void setPositionLng(Long pointLng) {
		this.pointLng = pointLng;
	}

	@Override
	public String toString() {
		return "Point [id=" + id + ", street=" + street + ", name=" + name + ", pointLat=" + pointLat + ", pointLng="
				+ pointLng + "]";
	}

}
