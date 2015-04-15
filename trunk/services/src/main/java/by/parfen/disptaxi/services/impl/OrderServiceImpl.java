package by.parfen.disptaxi.services.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.parfen.disptaxi.dataaccess.OrderDao;
import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.datamodel.Order;
import by.parfen.disptaxi.datamodel.OrderTimetable;
import by.parfen.disptaxi.datamodel.enums.OrderResult;
import by.parfen.disptaxi.datamodel.enums.OrderStatus;
import by.parfen.disptaxi.services.OrderService;
import by.parfen.disptaxi.services.OrderTimetableService;

@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DriverServiceImpl.class);

	@Inject
	private OrderDao dao;

	@Inject
	private OrderTimetableService ottService;

	@PostConstruct
	private void init() {
		// this method will be called by Spring after bean instantiation. Can be
		// used for any initialization process.
		LOGGER.info("Instance of OrdersService is created. Class is: {}", getClass().getName());
	}

	@Override
	public Order get(Long id) {
		return dao.getById(id);
	}

	@Override
	public Long getCount() {
		return dao.getCount();
	}

	@Override
	public void create(Order order) {
		Validate.isTrue(order.getId() == null,
				"This method should be called for 'not saved yet' record only. Use UPDATE instead");
		LOGGER.debug("Insert: {}", order);
		dao.insert(order);
	}

	@Override
	public void update(Order order) {
		dao.update(order);
	}

	@Override
	public void delete(Order order) {
		LOGGER.debug("Remove: {}", order);
		dao.delete(order.getId());
	}

	@Override
	public void deleteAll() {
		LOGGER.debug("Remove all orders");
		dao.deleteAll();
	}

	@Override
	public List<Order> getAll() {
		return dao.getAll();
	}

	@Override
	public List<Order> getAll(SingularAttribute<Order, ?> attr, boolean ascending, int startRecord, int pageSize) {
		return dao.getAll(attr, ascending, startRecord, pageSize);
	}

	@Override
	public List<Order> getAllByDriver(Driver driver) {
		return dao.getAllByDriver(driver);
	}

	@Override
	public List<Order> getAllByCustomer(Customer customer) {
		return dao.getAllByCustomer(customer);
	}

	@Override
	public Driver getOrderDriver(Order order) {
		return dao.getOrderDriver(order);
	}

	@Override
	public Long calcOrderPrice(Order order) {
		Long result = 0L;
		// simple calc by km
		// TODO
		// must add OrderTimetable info
		result = order.getRouteLength() * order.getPrice().getCostKm();
		return result;
	}

	@Override
	public void changeOrderStatus(Order order, OrderStatus orderStatus) {
		// TODO check valid order of setting the new value
		OrderTimetable ott = new OrderTimetable();
		ott.setOrderStatus(orderStatus);
		ott.setOrder(order);
		ott.setCreationDate(new Date());
		ottService.create(ott);
		LOGGER.debug("Change order status to {}", orderStatus);
		order.setOrderStatus(orderStatus);
		dao.update(order);
	}

	@Override
	public void changeOrderResult(Order order, OrderResult orderResult) {
		order.setOrderResult(orderResult);
		if (orderResult.compareTo(OrderResult.ORDERRESULT_NONE) > 0) {
			// TODO check valid order of setting the new value
			// orderTimetableService.saveOrUpdate(order,OrderStatus.ORDERSTATE_DONE);
		}
		dao.update(order);
	}

}
