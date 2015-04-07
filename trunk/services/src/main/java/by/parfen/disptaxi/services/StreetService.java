package by.parfen.disptaxi.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import by.parfen.disptaxi.datamodel.City;
import by.parfen.disptaxi.datamodel.Street;

public interface StreetService {

	Street get(Long id);

	@Transactional
	void create(Street street, City city);

	@Transactional
	void update(Street street);

	@Transactional
	void delete(Street street);

	@Transactional
	void deleteAll();

	Long getCount();

	List<Street> getAll();

}
