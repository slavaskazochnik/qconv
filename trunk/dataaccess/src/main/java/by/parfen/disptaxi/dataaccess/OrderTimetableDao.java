package by.parfen.disptaxi.dataaccess;

import java.util.List;

import by.parfen.disptaxi.datamodel.Order;
import by.parfen.disptaxi.datamodel.OrderTimetable;

public interface OrderTimetableDao extends AbstractDao<Long, OrderTimetable> {

	Long getCount();

	List<OrderTimetable> getAllByOrder(Order order);

}
