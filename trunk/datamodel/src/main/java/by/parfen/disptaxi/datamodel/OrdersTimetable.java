package by.parfen.disptaxi.datamodel;

import java.util.Date;

import by.parfen.disptaxi.datamodel.enums.OrderStatus;

public class OrdersTimetable extends AbstractEntity {

	private Long id;
	private Orders orders;
	// @Column
	// @Enumerated(EnumType.ORDINAL)
	private OrderStatus orderStatus;
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

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getdCreate() {
		return dCreate;
	}

	public void setdCreate(Date dCreate) {
		this.dCreate = dCreate;
	}

}
