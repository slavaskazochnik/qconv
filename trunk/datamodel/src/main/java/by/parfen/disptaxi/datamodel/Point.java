package by.parfen.disptaxi.datamodel;

public class Point {

	private Long id;
	private Street street;
	private String houseNum;
	private Long houseLoc;
	private Long positionLat;
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
