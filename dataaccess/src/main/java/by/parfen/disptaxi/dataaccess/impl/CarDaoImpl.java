package by.parfen.disptaxi.dataaccess.impl;

import org.springframework.stereotype.Repository;

import by.parfen.disptaxi.dataaccess.CarDao;
import by.parfen.disptaxi.datamodel.Car;

@Repository
public class CarDaoImpl extends AbstractDaoImpl<Long, Car> implements CarDao {

	protected CarDaoImpl() {
		super(Car.class);
	}

}
