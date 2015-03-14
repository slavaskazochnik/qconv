package by.parfen.disptaxi.datamodel;

import java.util.Date;

public class OrdersTimetable {

	private Long id;
	private Orders orders;
	private OrdersState ordersState;
	private Date dCreate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Orders getOrders() {
		return orders;
	}
	public void setOrders(Orders orders) {
		this.orders = orders;
	}
	public OrdersState getOrdersState() {
		return ordersState;
	}
	public void setOrdersState(OrdersState ordersState) {
		this.ordersState = ordersState;
	}
	public Date getdCreate() {
		return dCreate;
	}
	public void setdCreate(Date dCreate) {
		this.dCreate = dCreate;
	}
	
	
}
