package by.parfen.disptaxi.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Point extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Street.class)
	private Street street;
	@Column
	private String name;
	@Column
	private String pointLat;
	@Column
	private String pointLng;

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

	public String getPositionLat() {
		return pointLat;
	}

	public void setPositionLat(String pointLat) {
		this.pointLat = pointLat;
	}

	public String getPositionLng() {
		return pointLng;
	}

	public void setPositionLng(String pointLng) {
		this.pointLng = pointLng;
	}

	@Override
	public String toString() {
		return "Point [id=" + id + ", name=" + name + ", pointLat=" + pointLat + ", pointLng=" + pointLng + "]";
	}

}
