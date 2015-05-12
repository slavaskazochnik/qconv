package by.parfen.disptaxi.datamodel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import by.parfen.disptaxi.datamodel.enums.OrderResult;
import by.parfen.disptaxi.datamodel.enums.OrderStatus;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Customer.class)
	private Customer customer;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Auto.class)
	@JoinColumn(name = "autos_id")
	private Auto auto;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Price.class)
	private Price price;
	@Column
	@Enumerated(EnumType.ORDINAL)
	private OrderStatus orderStatus;
	@Column
	@Enumerated(EnumType.ORDINAL)
	private OrderResult orderResult;
	@Column
	private Long routeLength;
	@Column
	private Long orderPrice;
	@Column
	private Long customerRating;
	@Column
	private Long driverRating;
	@Column
	private Date creationDate;
	@Column
	private Date beginDate;
	@Column
	private Date endDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Auto getAuto() {
		return auto;
	}

	public void setAuto(Auto auto) {
		this.auto = auto;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public OrderResult getOrderResult() {
		return orderResult;
	}

	public void setOrderResult(OrderResult orderResult) {
		this.orderResult = orderResult;
	}

	public Long getRouteLength() {
		return routeLength;
	}

	public void setRouteLength(Long routeLength) {
		this.routeLength = routeLength;
	}

	public Long getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(Long orderPrice) {
		this.orderPrice = orderPrice;
	}

	public Long getCustomerRating() {
		return customerRating;
	}

	public void setCustomerRating(Long customerRating) {
		this.customerRating = customerRating;
	}

	public Long getDriverRating() {
		return driverRating;
	}

	public void setDriverRating(Long driverRating) {
		this.driverRating = driverRating;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", orderStatus=" + orderStatus + ", orderResult=" + orderResult + ", routeLength="
				+ routeLength + ", orderPrice=" + orderPrice + ", customerRating=" + customerRating + ", driverRating="
				+ driverRating + ", creationDate=" + creationDate + "]";
	}

}
