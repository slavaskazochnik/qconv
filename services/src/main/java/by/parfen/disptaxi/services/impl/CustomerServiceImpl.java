package by.parfen.disptaxi.services.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.parfen.disptaxi.dataaccess.CustomerDao;
import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.services.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Inject
	private CustomerDao dao;

	@PostConstruct
	private void init() {
		// this method will be called by Spring after bean instantiation. Can be
		// used for any initialization process.
		LOGGER.info("Instance of DriverService is created. Class is: {}", getClass().getName());
	}

	@Override
	public Customer get(Long id) {
		Customer entity = dao.getById(id);
		return entity;
	}

	@Override
	public void create(Customer customer, UserProfile userProfile) {
		/*
		 * Validate.isTrue(account.getId() == null,
		 * "This method should be called for 'not saved yet' account only. Use UPDATE instead"
		 * ); accountDao.insert(account);
		 */

		Validate.isTrue(customer.getId() == null,
				"This method should be called for 'not saved yet' profile only. Use UPDATE instead");
		customer.setPerson(userProfile);
		LOGGER.debug("Insert: {}", customer);
		dao.insert(customer);
	}

	@Override
	public void update(Customer customer) {
		LOGGER.debug("Update: {}", customer);
		dao.update(customer);
	}

	@Override
	public void delete(Customer customer) {
		LOGGER.debug("Remove: {}", customer);
		dao.delete(customer.getId());
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
	public List<Customer> getAll() {
		return dao.getAll();
	}
}
