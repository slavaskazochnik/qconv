package by.parfen.disptaxi.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import by.parfen.disptaxi.datamodel.Autos;
import by.parfen.disptaxi.datamodel.Car;

public interface AutosService {

	Autos get(Long id);

	@Transactional
	void create(Autos autos, Car car);

	@Transactional
	void update(Autos autos);

	@Transactional
	void delete(Autos autos);

	@Transactional
	void deleteAll();

	Long getCount();

	List<Autos> getAll();
}
