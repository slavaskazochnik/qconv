package by.parfen.disptaxi.dataaccess.impl;

import org.springframework.stereotype.Repository;

import by.parfen.disptaxi.dataaccess.CarsTypeDao;
import by.parfen.disptaxi.datamodel.CarsType;

@Repository
public class CarsTypeDaoImpl extends AbstractDaoImpl<Long, CarsType> implements CarsTypeDao {

	protected CarsTypeDaoImpl() {
		super(CarsType.class);
	}

}
