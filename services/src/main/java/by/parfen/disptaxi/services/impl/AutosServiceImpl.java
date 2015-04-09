package by.parfen.disptaxi.services.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.parfen.disptaxi.dataaccess.AutosDao;
import by.parfen.disptaxi.datamodel.Autos;
import by.parfen.disptaxi.datamodel.Car;
import by.parfen.disptaxi.services.AutosService;

@Service
public class AutosServiceImpl implements AutosService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AutosServiceImpl.class);

	@Inject
	private AutosDao dao;

	@PostConstruct
	private void init() {
		// this method will be called by Spring after bean instantiation. Can be
		// used for any initialization process.
		LOGGER.info("Instance of CarService is created. Class is: {}", getClass().getName());
	}

	@Override
	public Autos get(Long id) {
		Autos entity = dao.getById(id);
		return entity;
	}

	@Override
	public void create(Autos auto, Car car) {
		Validate.isTrue(auto.getId() == null,
				"This method should be called for 'not saved yet' profile only. Use UPDATE instead");
		auto.setCar(car);
		LOGGER.debug("Insert: {}", auto);
		dao.insert(auto);
	}

	@Override
	public void update(Autos auto) {
		LOGGER.debug("Update: {}", auto);
		dao.update(auto);
	}

	@Override
	public void delete(Autos auto) {
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
	public List<Autos> getAll() {
		return dao.getAll();
	}
}
