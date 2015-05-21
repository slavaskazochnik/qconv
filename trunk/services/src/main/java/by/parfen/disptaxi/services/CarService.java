package by.parfen.disptaxi.services;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import org.springframework.transaction.annotation.Transactional;

import by.parfen.disptaxi.datamodel.Car;
import by.parfen.disptaxi.datamodel.enums.CarType;

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

	List<Car> getAllByCarType(CarType carType);

	List<Car> getAll(SingularAttribute<Car, ?> attr, boolean ascending, int startRecord, int pageSize);
}
