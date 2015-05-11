package by.parfen.disptaxi.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

@Entity
public class Point extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Street.class)
	private Street street;
	@Column
	@NotNull
	@Size(max = 10)
	private String name;
	@Column
	@Range(min = -90, max = 90)
	private String pointLat;
	@Column
	@Range(min = -180, max = 180)
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
