package by.parfen.disptaxi.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.parfen.disptaxi.dataaccess.AutoDao;
import by.parfen.disptaxi.dataaccess.OrderDao;
import by.parfen.disptaxi.dataaccess.OrderTimetableDao;
import by.parfen.disptaxi.dataaccess.RouteDao;
import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.datamodel.Order;
import by.parfen.disptaxi.datamodel.OrderTimetable;
import by.parfen.disptaxi.datamodel.enums.OrderResult;
import by.parfen.disptaxi.datamodel.enums.OrderStatus;
import by.parfen.disptaxi.datamodel.enums.SignActive;
import by.parfen.disptaxi.datamodel.filter.FilterOrder;
import by.parfen.disptaxi.services.OrderService;
import by.parfen.disptaxi.services.OrderTimetableService;

@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DriverServiceImpl.class);

	@Inject
	private OrderDao dao;
	@Inject
	private RouteDao routeDao;
	@Inject
	private OrderTimetableDao ottDao;
	@Inject
	private AutoDao autoDao;

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
	public Long getCount(FilterOrder filterOrder) {
		return dao.getCount(filterOrder);
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
	public List<Order> getAll(SingularAttribute<Order, ?> attr, boolean ascending, int startRecord, int pageSize,
			FilterOrder filterOrder) {
		return dao.getAll(attr, ascending, startRecord, pageSize, filterOrder);
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
		result = order.getPrice().getCostBefore() + order.getRouteLength() * order.getPrice().getCostKm();
		return result;
	}

	@Override
	public void changeOrderStatus(Order order, OrderStatus orderStatus) {
		// TODO check valid order of setting the new value
		OrderTimetable ott = new OrderTimetable();
		ott.setOrderStatus(orderStatus);
		ott.setOrder(order);
		ott.setCreationDate(new Date(System.currentTimeMillis()));
		ottService.create(ott);
		if (orderStatus == OrderStatus.ACCEPTED) {
			order.getAuto().setSignActive(SignActive.NO);
			autoDao.update(order.getAuto());
		} else if (orderStatus == OrderStatus.DONE) {
			order.getAuto().setSignActive(SignActive.YES);
			autoDao.update(order.getAuto());
		}
		LOGGER.debug("Change order status to {}", orderStatus);
		order.setOrderStatus(orderStatus);
		dao.update(order);
	}

	@Override
	public void changeOrderResult(Order order, OrderResult orderResult) {
		order.setOrderResult(orderResult);
		if (orderResult.compareTo(OrderResult.NONE) > 0) {
			// TODO check valid order of setting the new value
			changeOrderStatus(order, OrderStatus.DONE);
		} else {
			dao.update(order);
		}
	}

	@Override
	public Order getWithDetails(Order order) {
		return dao.getWithDetails(order);
	}

	@Override
	public void deleteWithDetails(Order order) {
		routeDao.deleteByOrderId(order.getId());
		ottDao.deleteByOrderId(order.getId());
		delete(order);
	}

	@Override
	public OrderStatus getNextOrderStatus(OrderStatus currentOrderStatus) {
		OrderStatus result = OrderStatus.NEW;
		if (currentOrderStatus == OrderStatus.NEW) {
			result = OrderStatus.ACCEPTED;
		} else if (currentOrderStatus == OrderStatus.ACCEPTED) {
			result = OrderStatus.ARRIVED;
		} else if (currentOrderStatus == OrderStatus.ARRIVED) {
			result = OrderStatus.ON_WAY;
		}
		return result;
	}

	@Override
	public boolean isLastOrderStatus(OrderStatus currentOrderStatus) {
		return currentOrderStatus == OrderStatus.ON_WAY;
	}

	public BigDecimal getAvgRating(List<Long> ratingList) {
		BigDecimal result = null;
		double avg = 0;
		Long sum = 0L;
		int count = 0;
		for (Long rating : ratingList) {
			if (rating != null && rating > 0) {
				sum += rating;
				count++;
			}
		}
		if (count > 0) {
			avg = Math.round(sum / count * 10) / 10;
		}
		if (avg > 0) {
			result = BigDecimal.valueOf(avg);
		}
		return result;
	}

	public BigDecimal getUserAvgRating(Customer customer, Driver driver) {
		List<Long> ratingList = new ArrayList<Long>();
		List<Order> orders;
		if (customer != null) {
			orders = getAllByCustomer(customer);
		} else {
			orders = getAllByDriver(driver);
		}
		if (orders.size() > 0) {
			for (Order order : orders) {
				if (customer != null) {
					ratingList.add(order.getCustomerRating());
				} else {
					ratingList.add(order.getDriverRating());
				}
			}
		}
		return getAvgRating(ratingList);
	}

	@Override
	public BigDecimal getCustomerAvgRating(Customer customer) {
		return getUserAvgRating(customer, null);
	}

	@Override
	public BigDecimal getDriverAvgRating(Driver driver) {
		return getUserAvgRating(null, driver);
	}

}
