package by.parfen.disptaxi.services.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.parfen.disptaxi.dataaccess.CityDao;
import by.parfen.disptaxi.datamodel.City;
import by.parfen.disptaxi.services.CityService;

@Service
public class CityServiceImpl implements CityService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CityServiceImpl.class);

	@Inject
	private CityDao dao;

	@PostConstruct
	private void init() {
		// this method will be called by Spring after bean instantiation. Can be
		// used for any initialization process.
		LOGGER.info("Instance of CityService is created. Class is: {}", getClass().getName());
	}

	@Override
	public City get(Long id) {
		City entity = dao.getById(id);
		return entity;
	}

	@Override
	public void saveOrUpdate(City city) {
		if (city.getId() == null) {
			LOGGER.debug("Save new: {}", city);
			dao.insert(city);
		} else {
			LOGGER.debug("Update: {}", city);
			dao.update(city);
		}
	}

	@Override
	public void delete(City city) {
		LOGGER.debug("Remove: {}", city);
		dao.delete(city.getId());
	}

	@Override
	public void deleteAll() {
		LOGGER.debug("Remove all cities");
		dao.deleteAll();
	}

	@Override
	public Long getCount() {
		return dao.getCount();
	};

	@Override
	public List<City> getAll() {
		return dao.getAll();
	}
}
