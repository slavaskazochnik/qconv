package by.parfen.disptaxi.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Route {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Order.class)
	private Order order;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Point.class)
	private Point srcPoint;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Point.class)
	private Point dstPoint;
	@Column
	private Long estLength;
	@Column
	private Long factLenght;
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
