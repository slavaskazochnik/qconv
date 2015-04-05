package by.parfen.disptaxi.dataaccess;

import java.util.List;

import by.parfen.disptaxi.datamodel.City;

public interface CityDao extends AbstractDao<Long, City> {

	Long getCount();

	List<City> getAllByName(String name);
}
