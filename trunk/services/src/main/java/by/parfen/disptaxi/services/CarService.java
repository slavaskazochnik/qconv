package by.parfen.disptaxi.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import by.parfen.disptaxi.datamodel.Car;

public interface CarService {

	Car get(Long id);

	@Transactional
	void create(Car car);

	@Transactional
	void update(Car car);

	@Transactional
	void delete(Car car);

	@Transactional
	void deleteAll();

	Long getCount();

	List<Car> getAll();
}
