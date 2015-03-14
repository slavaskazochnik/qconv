package by.parfen.disptaxi.datamodel;

import java.util.Date;

public class Orders {

	private Long id;
	private Customer customer;
	private Route route;
	private Autos autos;
	private OrdersState ordersState;
	private OrdersResult ordersResult;
	private Long routeLength;
	private Long orderPrice;
	private Long customerRating;
	private Long driverRating;
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
	public OrdersState getOrdersState() {
		return ordersState;
	}
	public void setOrdersState(OrdersState ordersState) {
		this.ordersState = ordersState;
	}
	public OrdersResult getOrdersResult() {
		return ordersResult;
	}
	public void setOrdersResult(OrdersResult ordersResult) {
		this.ordersResult = ordersResult;
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
