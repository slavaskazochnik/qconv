package by.parfen.disptaxi.services;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import org.springframework.transaction.annotation.Transactional;

import by.parfen.disptaxi.datamodel.Price;
import by.parfen.disptaxi.datamodel.enums.CarType;

public interface PriceService {

	Price get(Long id);

	@Transactional
	void create(Price price);

	@Transactional
	void update(Price price);

	@Transactional
	void delete(Price price);

	@Transactional
	void deleteAll();

	Long getCount();

	List<Price> getAll();

	List<Price> getAll(SingularAttribute<Price, ?> attr, boolean ascending, int startRecord, int pageSize);

	List<Price> getAllByCarType(CarType carType);
}
