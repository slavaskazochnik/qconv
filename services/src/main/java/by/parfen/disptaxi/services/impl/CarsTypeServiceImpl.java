package by.parfen.disptaxi.services.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.parfen.disptaxi.dataaccess.CarsTypeDao;
import by.parfen.disptaxi.datamodel.CarsType;
import by.parfen.disptaxi.services.CarsTypeService;

@Service
public class CarsTypeServiceImpl implements CarsTypeService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CarServiceImpl.class);

	@Inject
	private CarsTypeDao dao;

	@PostConstruct
	private void init() {
		// this method will be called by Spring after bean instantiation. Can be
		// used for any initialization process.
		LOGGER.info("Instance of CarsTypeService is created. Class is: {}", getClass().getName());
	}

	@Override
	public CarsType get(Long id) {
		CarsType entity = dao.getById(id);
		return entity;
	}

	@Override
	public void create(CarsType carsType) {
		Validate.isTrue(carsType.getId() == null,
				"This method should be called for 'not saved yet' profile only. Use UPDATE instead");
		LOGGER.debug("Insert: {}", carsType);
		dao.insert(carsType);
	}

	@Override
	public void update(CarsType carsType) {
		LOGGER.debug("Update: {}", carsType);
		dao.update(carsType);
	}

	@Override
	public void delete(CarsType carsType) {
		LOGGER.debug("Remove: {}", carsType);
		dao.delete(carsType.getId());
	}

	@Override
	public void deleteAll() {
		LOGGER.debug("Remove all cars types");
		dao.deleteAll();
		;
	}

}
