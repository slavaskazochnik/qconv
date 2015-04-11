package by.parfen.disptaxi.services;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import org.springframework.transaction.annotation.Transactional;

import by.parfen.disptaxi.datamodel.Order;

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

	List<Order> getAll();

	// List<Orders> getAllByDriverId(Long Id);

	List<Order> getAll(SingularAttribute<Order, ?> attr, boolean ascending, int startRecord, int pageSize);
}
