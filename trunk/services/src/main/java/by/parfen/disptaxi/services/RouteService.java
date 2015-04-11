package by.parfen.disptaxi.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import by.parfen.disptaxi.datamodel.Point;
import by.parfen.disptaxi.datamodel.Route;

public interface RouteService {

	Route get(Long id);

	@Transactional
	void create(Route route, Point srcPoint, Point dstPoint);

	@Transactional
	void update(Route route);

	@Transactional
	void delete(Route route);

	@Transactional
	void deleteAll();

	Long getCount();

	List<Route> getAll();

}
