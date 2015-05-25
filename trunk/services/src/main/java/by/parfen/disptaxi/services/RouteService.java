package by.parfen.disptaxi.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import by.parfen.disptaxi.datamodel.Order;
import by.parfen.disptaxi.datamodel.Route;

public interface RouteService {

	Route get(Long id);

	@Transactional
	// void create(Route route, Point srcPoint, Point dstPoint);
	void create(Route route);

	@Transactional
	void update(Route route);

	@Transactional
	void delete(Route route);

	@Transactional
	void deleteAll();

	@Transactional
	void deleteByOrderId(Long orderId);

	Long getCount();

	List<Route> getAll();

	List<Route> getAllByOrder(Order order);

	Route getRouteByIndex(Order order, int pointIndex);

}
