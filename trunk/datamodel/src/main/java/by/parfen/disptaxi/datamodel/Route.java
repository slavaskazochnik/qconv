package by.parfen.disptaxi.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Route {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Order.class)
	@JoinColumn(name = "orders_id")
	private Order order;
	@Column
	private Long pointIndex;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Point.class)
	private Point srcPoint;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Point.class)
	private Point dstPoint;
	@Column
	private String srcPointAddress;
	@Column
	private String dstPointAddress;
	@Column
	private Long estLength;
	@Column
	private Long factLength;
	@Column
	private Long estTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Long getPointIndex() {
		return pointIndex;
	}

	public void setPointIndex(Long pointIndex) {
		this.pointIndex = pointIndex;
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

	public Long getFactLength() {
		return factLength;
	}

	public void setFactLength(Long factLength) {
		this.factLength = factLength;
	}

	public Long getEstTime() {
		return estTime;
	}

	public void setEstTime(Long estTime) {
		this.estTime = estTime;
	}

	public String getSrcPointAddress() {
		return srcPointAddress;
	}

	public void setSrcPointAddress(String srcPointAddress) {
		this.srcPointAddress = srcPointAddress;
	}

	public String getDstPointAddress() {
		return dstPointAddress;
	}

	public void setDstPointAddress(String dstPointAddress) {
		this.dstPointAddress = dstPointAddress;
	}

}
