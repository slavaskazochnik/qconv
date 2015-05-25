package by.parfen.disptaxi.services.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.parfen.disptaxi.dataaccess.RouteDao;
import by.parfen.disptaxi.datamodel.Order;
import by.parfen.disptaxi.datamodel.Route;
import by.parfen.disptaxi.services.RouteService;

@Service
public class RouteServiceImpl implements RouteService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RouteServiceImpl.class);

	@Inject
	private RouteDao dao;

	@PostConstruct
	private void init() {
		// this method will be called by Spring after bean instantiation. Can be
		// used for any initialization process.
		LOGGER.info("Instance of RouteService is created. Class is: {}", getClass().getName());
	}

	@Override
	public Route get(Long id) {
		Route entity = dao.getById(id);
		return entity;
	}

	@Override
	public void create(Route route) {
		Validate.isTrue(route.getId() == null,
				"This method should be called for 'not saved yet' record only. Use UPDATE instead");
		LOGGER.debug("Insert: {}", route);
		dao.insert(route);
	}

	@Override
	public void update(Route route) {
		LOGGER.debug("Update: {}", route);
		dao.update(route);
	}

	@Override
	public void delete(Route route) {
		LOGGER.debug("Remove: {}", route);
		dao.delete(route.getId());
	}

	@Override
	public void deleteAll() {
		LOGGER.debug("Remove all routes");
		dao.deleteAll();
	}

	@Override
	public Long getCount() {
		return dao.getCount();
	};

	@Override
	public List<Route> getAll() {
		return dao.getAll();
	}

	@Override
	public List<Route> getAllByOrder(Order order) {
		return dao.getAllByOrder(order);
	}

	@Override
	public void deleteByOrderId(Long orderId) {
		dao.deleteByOrderId(orderId);
	}

	@Override
	public Route getRouteByIndex(Order order, int pointIndex) {
		Route result = null;
		List<Route> routes = dao.getAllByOrder(order);
		if (routes.size() > 0 && pointIndex < routes.size()) {
			result = routes.get(pointIndex);
		}
		return result;
	}

}
