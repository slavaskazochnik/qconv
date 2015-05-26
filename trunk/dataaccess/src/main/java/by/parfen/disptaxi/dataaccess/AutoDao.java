package by.parfen.disptaxi.dataaccess;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import by.parfen.disptaxi.datamodel.Auto;
import by.parfen.disptaxi.datamodel.enums.CarType;
import by.parfen.disptaxi.datamodel.filter.FilterAuto;

public interface AutoDao extends AbstractDao<Long, Auto> {

	Long getCount();

	List<Auto> getAll(SingularAttribute<Auto, ?> attr, boolean ascending, int startRecord, int pageSize);

	List<Auto> getAllByCarType(CarType carType);

	List<Auto> getAllActive();

	List<Auto> getAllActiveByCarType(CarType carType);

	List<Auto> getAllWithDetails();

	List<Auto> getAllWithDetails(FilterAuto filterAuto);

	Auto getWithDetails(Auto auto);
}
