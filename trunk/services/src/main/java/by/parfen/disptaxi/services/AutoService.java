package by.parfen.disptaxi.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import by.parfen.disptaxi.datamodel.Auto;
import by.parfen.disptaxi.datamodel.Car;
import by.parfen.disptaxi.datamodel.enums.CarType;
import by.parfen.disptaxi.datamodel.filter.FilterAuto;

public interface AutoService {

	Auto get(Long id);

	@Transactional
	void create(Auto auto, Car car);

	@Transactional
	void update(Auto auto);

	@Transactional
	void delete(Auto auto);

	@Transactional
	void deleteAll();

	Long getCount();

	List<Auto> getAll();

	List<Auto> getAllByCarType(CarType carType);

	List<Auto> getAllActive();

	List<Auto> getAllActiveByCarType(CarType carType);

	List<Auto> getAllActiveByCarTypeAndGeo(CarType carType, String lat, String lng);

	List<Auto> getAllWithDetails();

	List<Auto> getAllWithDetails(FilterAuto filterAuto);

	Auto getWithDetails(Auto auto);
}
