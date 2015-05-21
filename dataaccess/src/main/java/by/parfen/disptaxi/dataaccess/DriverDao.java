package by.parfen.disptaxi.dataaccess;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import by.parfen.disptaxi.datamodel.Driver;

public interface DriverDao extends AbstractDao<Long, Driver> {

	Long getCount();

	List<Driver> getAllWithDetails();

	List<Driver> getAllWithDetails(SingularAttribute<Driver, ?> attr, boolean ascending, int startRecord, int pageSize);
}
