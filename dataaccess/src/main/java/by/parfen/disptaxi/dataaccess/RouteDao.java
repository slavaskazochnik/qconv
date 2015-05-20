package by.parfen.disptaxi.dataaccess;

import java.util.List;

import by.parfen.disptaxi.datamodel.Order;
import by.parfen.disptaxi.datamodel.Route;

public interface RouteDao extends AbstractDao<Long, Route> {

	Long getCount();

	List<Route> getAllByOrder(Order order);

	void deleteByOrderId(Long orderId);
}
