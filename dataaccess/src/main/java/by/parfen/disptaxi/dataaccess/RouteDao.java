package by.parfen.disptaxi.dataaccess;

import by.parfen.disptaxi.datamodel.Route;

public interface RouteDao extends AbstractDao<Long, Route> {

	Long getCount();

}
