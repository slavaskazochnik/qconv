package by.parfen.disptaxi.dataaccess;

import java.util.List;

import by.parfen.disptaxi.datamodel.Point;
import by.parfen.disptaxi.datamodel.UserProfile;

public interface PointDao extends AbstractDao<Long, Point> {

	Long getCount();

	List<UserProfile> getAllByName(String name);
}
