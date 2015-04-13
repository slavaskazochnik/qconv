package by.parfen.disptaxi.dataaccess;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import by.parfen.disptaxi.datamodel.City;
import by.parfen.disptaxi.datamodel.Street;

public interface StreetDao extends AbstractDao<Long, Street> {

	Long getCount();

	List<Street> getAll(SingularAttribute<Street, ?> attr, boolean ascending, int startRecord, int pageSize);

	List<Street> getAllByCityAndName(City city, String name);

	List<Street> getAllByName(String name);

	List<Street> getAllByCity(City city);
}
