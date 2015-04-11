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
import javax.persistence.ManyToOne;

import by.parfen.disptaxi.datamodel.enums.OrderResult;
import by.parfen.disptaxi.datamodel.enums.OrderStatus;

@Entity
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Customer.class)
	private Customer customer;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Route.class)
	private Route route;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Autos.class)
	private Autos autos;
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
	private Date dCreate;

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

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Autos getAutos() {
		return autos;
	}

	public void setAutos(Autos autos) {
		this.autos = autos;
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

	public Date getdCreate() {
		return dCreate;
	}

	public void setdCreate(Date dCreate) {
		this.dCreate = dCreate;
	}

}
