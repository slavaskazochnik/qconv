package by.parfen.disptaxi.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import by.parfen.disptaxi.datamodel.Order;
import by.parfen.disptaxi.datamodel.OrderTimetable;

public interface OrderTimetableService {

	OrderTimetable get(Long id);

	@Transactional
	void create(OrderTimetable orderTimetable);

	@Transactional
	void update(OrderTimetable orderTimetable);

	@Transactional
	void delete(OrderTimetable orderTimetable);

	@Transactional
	void deleteAll();

	Long getCount();

	List<OrderTimetable> getAll();

	List<OrderTimetable> getAllByOrder(Order order);

}
