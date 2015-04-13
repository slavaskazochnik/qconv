package by.parfen.disptaxi.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import by.parfen.disptaxi.datamodel.Point;
import by.parfen.disptaxi.datamodel.Street;

public interface PointService {

	Point get(Long id);

	@Transactional
	void create(Point point, Street street);

	@Transactional
	void update(Point point);

	@Transactional
	void delete(Point point);

	@Transactional
	void deleteAll();

	Long getCount();

	List<Point> getAllByStreetAndName(Street street, String name);

	List<Point> getAll();

	List<Point> getAllByName(String name);

	List<Point> getAllByStreet(Street street);

}
