package by.parfen.disptaxi.dataaccess;

import java.util.List;

import by.parfen.disptaxi.datamodel.Driver;

public interface DriverDao extends AbstractDao<Long, Driver> {

	Long getCount();

	List<Driver> getAllWithDetails();
}
