package by.parfen.disptaxi.datamodel;

import java.util.Date;

public class Orders {

	private Long id;
	private Customer customer;
	/*private Route route;*/
	private Autos autos;
	private OrdersState ordersState;
	private OrdersResult ordersResult;
	private Long routeLength;
	private Long orderPrice;
	private Long customerRating;
	private Long driverRating;
	private Date dCreate;

}
