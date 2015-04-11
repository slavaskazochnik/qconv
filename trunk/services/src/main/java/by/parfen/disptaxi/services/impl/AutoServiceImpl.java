package by.parfen.disptaxi.services.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.parfen.disptaxi.dataaccess.AutoDao;
import by.parfen.disptaxi.datamodel.Auto;
import by.parfen.disptaxi.datamodel.Car;
import by.parfen.disptaxi.datamodel.enums.CarType;
import by.parfen.disptaxi.services.AutoService;

@Service
public class AutoServiceImpl implements AutoService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AutoServiceImpl.class);

	@Inject
	private AutoDao dao;

	@PostConstruct
	private void init() {
		// this method will be called by Spring after bean instantiation. Can be
		// used for any initialization process.
		LOGGER.info("Instance of CarService is created. Class is: {}", getClass().getName());
	}

	@Override
	public Auto get(Long id) {
		Auto entity = dao.getById(id);
		return entity;
	}

	@Override
	public void create(Auto auto, Car car) {
		Validate.isTrue(auto.getId() == null,
				"This method should be called for 'not saved yet' profile only. Use UPDATE instead");
		auto.setCar(car);
		LOGGER.debug("Insert: {}", auto);
		dao.insert(auto);
	}

	@Override
	public void update(Auto auto) {
		LOGGER.debug("Update: {}", auto);
		dao.update(auto);
	}

	@Override
	public void delete(Auto auto) {
		LOGGER.debug("Remove: {}", auto);
		dao.delete(auto.getId());
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
	public List<Auto> getAll() {
		return dao.getAll();
	}

	@Override
	public List<Auto> getAllByCarType(CarType carType) {
		return dao.getAllByCarType(carType);
	}

}
