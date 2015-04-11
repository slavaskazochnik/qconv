package by.parfen.disptaxi.services.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.parfen.disptaxi.dataaccess.PriceDao;
import by.parfen.disptaxi.datamodel.Price;
import by.parfen.disptaxi.datamodel.enums.CarType;
import by.parfen.disptaxi.services.PriceService;

@Service
public class PriceServiceImpl implements PriceService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PriceServiceImpl.class);

	@Inject
	private PriceDao dao;

	@PostConstruct
	private void init() {
		// this method will be called by Spring after bean instantiation. Can be
		// used for any initialization process.
		LOGGER.info("Instance of CarService is created. Class is: {}", getClass().getName());
	}

	@Override
	public Price get(Long id) {
		Price entity = dao.getById(id);
		return entity;
	}

	@Override
	public void create(Price price) {
		Validate.isTrue(price.getId() == null,
				"This method should be called for 'not saved yet' profile only. Use UPDATE instead");
		LOGGER.debug("Insert: {}", price);
		dao.insert(price);
	}

	@Override
	public void update(Price price) {
		LOGGER.debug("Update: {}", price);
		dao.update(price);
	}

	@Override
	public void delete(Price price) {
		LOGGER.debug("Remove: {}", price);
		dao.delete(price.getId());
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
	public List<Price> getAll() {
		return dao.getAll();
	}

	@Override
	public List<Price> getAll(SingularAttribute<Price, ?> attr, boolean ascending, int startRecord, int pageSize) {
		return dao.getAll(attr, ascending, startRecord, pageSize);
	}

	@Override
	public List<Price> getAllByCarType(CarType carType) {
		return dao.getAllByCarType(carType);
	}

}
