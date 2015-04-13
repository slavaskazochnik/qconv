package by.parfen.disptaxi.services.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.parfen.disptaxi.dataaccess.StreetDao;
import by.parfen.disptaxi.datamodel.City;
import by.parfen.disptaxi.datamodel.Street;
import by.parfen.disptaxi.services.StreetService;

@Service
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
		Validate.notNull(street, "Street object is null!");
		Validate.notNull(street.getName(), "Street name is null!");
		Validate.isTrue(street.getId() == null, "Use update for existing point!");
		if (city != null) {
			LOGGER.debug("Set city for street: {}", city);
			street.setCity(city);
		}
		LOGGER.debug("Save new: {}", street);
		dao.insert(street);
	}

	@Override
	public void update(Street street) {
		LOGGER.debug("Update: {}", street);
		dao.update(street);
	}

	@Override
	public void delete(Street street) {
		LOGGER.debug("Update: {}", street);
		dao.delete(street.getId());
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

	@Override
	public List<Street> getAllByName(String name) {
		return dao.getAllByName(name);
	}

	@Override
	public List<Street> getAll(SingularAttribute<Street, ?> attr, boolean ascending, int startRecord,
			int pageSize) {
		return dao.getAll(attr, ascending, startRecord, pageSize);
	}

	@Override
	public List<Street> getAllByCity(City city) {
		return dao.getAllByCity(city);
	}

	@Override
	public List<Street> getAllByCityAndName(City city, String name) {
		return dao.getAllByCityAndName(city, name);
	}

}
