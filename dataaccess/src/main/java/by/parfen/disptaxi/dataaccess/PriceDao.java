package by.parfen.disptaxi.dataaccess;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import by.parfen.disptaxi.datamodel.Price;
import by.parfen.disptaxi.datamodel.enums.CarType;

public interface PriceDao extends AbstractDao<Long, Price> {

	Long getCount();

	List<Price> getAll(SingularAttribute<Price, ?> attr, boolean ascending, int startRecord, int pageSize);

	List<Price> getAllByCarType(CarType carType);

}
