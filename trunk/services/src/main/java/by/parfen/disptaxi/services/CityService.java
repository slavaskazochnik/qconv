package by.parfen.disptaxi.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import by.parfen.disptaxi.datamodel.City;

public interface CityService {

	City get(Long id);

	@Transactional
	void saveOrUpdate(City city);

	@Transactional
	void delete(City city);

	@Transactional
	void deleteAll();

	Long getCount();

	List<City> getAll();
}
