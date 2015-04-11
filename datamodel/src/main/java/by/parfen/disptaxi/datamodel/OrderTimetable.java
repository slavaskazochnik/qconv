package by.parfen.disptaxi.datamodel;

import java.util.Date;

import by.parfen.disptaxi.datamodel.enums.OrderStatus;

public class OrderTimetable extends AbstractEntity {

	private Long id;
	private Order order;
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

	public Order getOrders() {
		return order;
	}

	public void setOrders(Order order) {
		this.order = order;
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
