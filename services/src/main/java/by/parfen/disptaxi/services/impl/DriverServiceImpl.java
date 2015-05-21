package by.parfen.disptaxi.services.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.parfen.disptaxi.dataaccess.DriverDao;
import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.services.DriverService;

@Service
public class DriverServiceImpl implements DriverService {
	private static final Logger LOGGER = LoggerFactory.getLogger(DriverServiceImpl.class);

	@Inject
	private DriverDao dao;

	@PostConstruct
	private void init() {
		// this method will be called by Spring after bean instantiation. Can be
		// used for any initialization process.
		LOGGER.info("Instance of DriverService is created. Class is: {}", getClass().getName());
	}

	@Override
	public Driver get(Long id) {
		Driver entity = dao.getById(id);
		return entity;
	}

	@Override
	public void create(Driver driver, UserProfile userProfile) {
		Validate.isTrue(driver.getId() == null,
				"This method should be called for 'not saved yet' record only. Use UPDATE instead");
		driver.setUserProfile(userProfile);
		LOGGER.debug("Insert: {}", driver);
		dao.insert(driver);
	}

	@Override
	public void update(Driver driver) {
		LOGGER.debug("Update: {}", driver);
		dao.update(driver);
	}

	@Override
	public void delete(Driver driver) {
		LOGGER.debug("Remove: {}", driver);
		dao.delete(driver.getId());
	}

	@Override
	public void deleteAll() {
		LOGGER.debug("Remove all drivers");
		dao.deleteAll();
	}

	@Override
	public Long getCount() {
		return dao.getCount();
	};

	@Override
	public List<Driver> getAll() {
		return dao.getAll();
	}

	@Override
	public List<Driver> getAllWithDetails() {
		return dao.getAllWithDetails();
	}

	@Override
	public List<Driver> getAllWithDetails(SingularAttribute<Driver, ?> attr, boolean ascending, int startRecord,
			int pageSize) {
		return dao.getAllWithDetails(attr, ascending, startRecord, pageSize);
	}
}
