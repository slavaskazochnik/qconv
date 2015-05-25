package by.parfen.disptaxi.services.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.parfen.disptaxi.dataaccess.OrderDao;
import by.parfen.disptaxi.dataaccess.OrderTimetableDao;
import by.parfen.disptaxi.datamodel.Order;
import by.parfen.disptaxi.datamodel.OrderTimetable;
import by.parfen.disptaxi.services.OrderTimetableService;

@Service
public class OrderTimetableServiceImpl implements OrderTimetableService {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderTimetableServiceImpl.class);

	@Inject
	private OrderTimetableDao dao;
	@Inject
	private OrderDao orderDao;

	@PostConstruct
	private void init() {
		// this method will be called by Spring after bean instantiation. Can be
		// used for any initialization process.
		LOGGER.info("Instance of OrderTimetableService is created. Class is: {}", getClass().getName());
	}

	@Override
	public OrderTimetable get(Long id) {
		OrderTimetable entity = dao.getById(id);
		return entity;
	}

	@Override
	public void create(OrderTimetable orderTimetable) {
		Validate.isTrue(orderTimetable.getId() == null,
				"This method should be called for 'not saved yet' record only. Use UPDATE instead");
		LOGGER.debug("Insert: {}", orderTimetable);
		dao.insert(orderTimetable);
	}

	@Override
	public void update(OrderTimetable orderTimetable) {
		LOGGER.debug("Update: {}", orderTimetable);
		dao.update(orderTimetable);
	}

	@Override
	public void delete(OrderTimetable orderTimetable) {
		LOGGER.debug("Remove: {}", orderTimetable);
		dao.delete(orderTimetable.getId());
	}

	@Override
	public void deleteAll() {
		LOGGER.debug("Remove all order timetables");
		dao.deleteAll();
	}

	@Override
	public Long getCount() {
		return dao.getCount();
	}

	@Override
	public List<OrderTimetable> getAll() {
		return dao.getAll();
	}

	@Override
	public List<OrderTimetable> getAllByOrder(Order order) {
		return dao.getAllByOrder(order);
	}

}
