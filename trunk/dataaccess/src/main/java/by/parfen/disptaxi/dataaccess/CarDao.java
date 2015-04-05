package by.parfen.disptaxi.dataaccess;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import by.parfen.disptaxi.datamodel.Car;

public interface CarDao extends AbstractDao<Long, Car> {

	Long getCount();

	List<Car> getAll(SingularAttribute<Car, ?> attr, boolean ascending, int startRecord, int pageSize);

	List<Car> getAllByRegNum(String regNum);
}
