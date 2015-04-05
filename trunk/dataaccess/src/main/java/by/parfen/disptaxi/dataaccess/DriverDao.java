package by.parfen.disptaxi.dataaccess;

import by.parfen.disptaxi.datamodel.Driver;

public interface DriverDao extends AbstractDao<Long, Driver> {

	Long getCount();

}
