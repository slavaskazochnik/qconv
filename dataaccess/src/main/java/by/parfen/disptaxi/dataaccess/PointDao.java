package by.parfen.disptaxi.dataaccess;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import by.parfen.disptaxi.datamodel.Point;
import by.parfen.disptaxi.datamodel.Street;

public interface PointDao extends AbstractDao<Long, Point> {

	Long getCount();

	List<Point> getAllByStreetAndName(Street street, String name);

	List<Point> getAllByName(String name);

	List<Point> getAllByStreet(Street street);

	List<Point> getAll(SingularAttribute<Point, ?> attr, boolean ascending, int startRecord, int pageSize);

}
