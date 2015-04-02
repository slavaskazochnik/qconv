package by.parfen.disptaxi.dataaccess.impl;

import org.springframework.stereotype.Repository;

import by.parfen.disptaxi.dataaccess.CityDao;
import by.parfen.disptaxi.datamodel.City;

@Repository
public class CityDaoImpl extends AbstractDaoImpl<Long, City> implements CityDao {

	protected CityDaoImpl() {
		super(City.class);
	}

}
