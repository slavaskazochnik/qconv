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
	private String houseNum;
	@Column
	private Long houseLoc;
	@Column
	private Long positionLat;
	@Column
	private Long positionLng;

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

	public String getHouseNum() {
		return houseNum;
	}

	public void setHouseNum(String houseNum) {
		this.houseNum = houseNum;
	}

	public Long getHouseLoc() {
		return houseLoc;
	}

	public void setHouseLoc(Long houseLoc) {
		this.houseLoc = houseLoc;
	}

	public Long getPositionLat() {
		return positionLat;
	}

	public void setPositionLat(Long positionLat) {
		this.positionLat = positionLat;
	}

	public Long getPositionLng() {
		return positionLng;
	}

	public void setPositionLng(Long positionLng) {
		this.positionLng = positionLng;
	}

}
