package by.parfen.disptaxi.services.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.parfen.disptaxi.dataaccess.CarDao;
import by.parfen.disptaxi.datamodel.Car;
import by.parfen.disptaxi.datamodel.Car_;
import by.parfen.disptaxi.datamodel.enums.CarType;
import by.parfen.disptaxi.services.CarService;

@Service
public class CarServiceImpl implements CarService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CarServiceImpl.class);

	@Inject
	private CarDao dao;

	@PostConstruct
	private void init() {
		// this method will be called by Spring after bean instantiation. Can be
		// used for any initialization process.
		LOGGER.info("Instance of CarService is created. Class is: {}", getClass().getName());
	}

	@Override
	public Car get(Long id) {
		Car entity = dao.getById(id);
		return entity;
	}

	@Override
	public void create(Car car) {
		Validate.isTrue(car.getId() == null,
				"This method should be called for 'not saved yet' record only. Use UPDATE instead");
		LOGGER.debug("Insert: {}", car);
		dao.insert(car);
	}

	@Override
	public void update(Car car) {
		LOGGER.debug("Update: {}", car);
		dao.update(car);
	}

	@Override
	public void delete(Car car) {
		LOGGER.debug("Remove: {}", car);
		dao.delete(car.getId());
	}

	@Override
	public void deleteAll() {
		LOGGER.debug("Remove all cars");
		dao.deleteAll();

	}

	@Override
	public Long getCount() {
		return dao.getCount();
	};

	@Override
	public List<Car> getAll() {
		return dao.getAll();
	}

	@Override
	public List<Car> getAllByCarType(CarType carType) {
		// return dao.getAllByCarType(carType);
		return dao.getAllByFieldRestriction(Car_.carType, carType);
	}

	@Override
	public List<Car> getAll(SingularAttribute<Car, ?> attr, boolean ascending, int startRecord, int pageSize) {
		return dao.getAll(attr, ascending, startRecord, pageSize);
	}

}
