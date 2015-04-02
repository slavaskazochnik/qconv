package by.parfen.disptaxi.dataaccess.impl;

import org.springframework.stereotype.Repository;

import by.parfen.disptaxi.dataaccess.DriverDao;
import by.parfen.disptaxi.datamodel.Driver;

@Repository
public class DriverDaoImpl extends AbstractDaoImpl<Long, Driver> implements DriverDao {

	protected DriverDaoImpl() {
		super(Driver.class);
	}
}
