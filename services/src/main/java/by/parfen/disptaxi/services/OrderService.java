package by.parfen.disptaxi.services;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import org.springframework.transaction.annotation.Transactional;

import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.datamodel.Order;
import by.parfen.disptaxi.datamodel.enums.OrderResult;
import by.parfen.disptaxi.datamodel.enums.OrderStatus;
import by.parfen.disptaxi.datamodel.filter.FilterOrder;

public interface OrderService {

	Order get(Long id);

	Long getCount();

	@Transactional
	void create(Order order);

	@Transactional
	void update(Order order);

	@Transactional
	void delete(Order order);

	@Transactional
	void deleteAll();

	@Transactional
	void deleteWithDetails(Order order);

	List<Order> getAll();

	List<Order> getAll(SingularAttribute<Order, ?> attr, boolean ascending, int startRecord, int pageSize);

	List<Order> getAll(SingularAttribute<Order, ?> attr, boolean ascending, int startRecord, int pageSize,
			FilterOrder filterOrder);

	List<Order> getAllByDriver(Driver driver);

	List<Order> getAllByCustomer(Customer customer);

	Driver getOrderDriver(Order order);

	Long calcOrderPrice(Order order);

	Order getWithDetails(Order order);

	@Transactional
	void changeOrderStatus(Order order, OrderStatus orderStatus);

	@Transactional
	void changeOrderResult(Order order, OrderResult orderResult);

	OrderStatus getNextOrderStatus(OrderStatus currentOrderStatus);

	boolean isLastOrderStatus(OrderStatus currentOrderStatus);

	BigDecimal getCustomerAvgRating(Customer customer);

	BigDecimal getDriverAvgRating(Driver driver);

}
