package by.parfen.disptaxi.datamodel;

public class Route {

	private Long id;
	private Point srcPoint;
	private Point dstPoint;
	private Long estLength;
	private Long factLenght;
	private Long estTime;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Point getSrcPoint() {
		return srcPoint;
	}
	public void setSrcPoint(Point srcPoint) {
		this.srcPoint = srcPoint;
	}
	public Point getDstPoint() {
		return dstPoint;
	}
	public void setDstPoint(Point dstPoint) {
		this.dstPoint = dstPoint;
	}
	public Long getEstLength() {
		return estLength;
	}
	public void setEstLength(Long estLength) {
		this.estLength = estLength;
	}
	public Long getFactLenght() {
		return factLenght;
	}
	public void setFactLenght(Long factLenght) {
		this.factLenght = factLenght;
	}
	public Long getEstTime() {
		return estTime;
	}
	public void setEstTime(Long estTime) {
		this.estTime = estTime;
	}
	
}
