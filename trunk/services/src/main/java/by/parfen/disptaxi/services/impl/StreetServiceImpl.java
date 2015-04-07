package by.parfen.disptaxi.services.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import by.parfen.disptaxi.dataaccess.StreetDao;
import by.parfen.disptaxi.datamodel.City;
import by.parfen.disptaxi.datamodel.Street;
import by.parfen.disptaxi.services.StreetService;

public class StreetServiceImpl implements StreetService {
	private static final Logger LOGGER = LoggerFactory.getLogger(StreetServiceImpl.class);

	@Inject
	private StreetDao dao;

	@PostConstruct
	private void init() {
		// this method will be called by Spring after bean instantiation. Can be
		// used for any initialization process.
		LOGGER.info("Instance of DriverService is created. Class is: {}", getClass().getName());
	}

	@Override
	public Street get(Long id) {
		Street entity = dao.getById(id);
		return entity;
	}

	@Override
	public void create(Street street, City city) {
		if (city.getId() == null) {
			LOGGER.debug("Save new: {}", street);
			street.setCity(city);
			dao.insert(street);
		} else {
			LOGGER.debug("Update: {}", street);
			dao.update(street);
		}
	}

	@Override
	public void update(Street street) {
		LOGGER.debug("Update: {}", street);
		dao.update(street);
	}

	@Override
	public void delete(Street street) {
		LOGGER.debug("Update: {}", street);
		dao.update(street);
	}

	@Override
	public void deleteAll() {
		dao.deleteAll();
	}

	@Override
	public Long getCount() {
		return dao.getCount();
	}

	@Override
	public List<Street> getAll() {
		return dao.getAll();
	}

}
