package by.parfen.disptaxi.dataaccess;

import by.parfen.disptaxi.datamodel.OrderTimetable;

public interface OrdersTimetableDao extends AbstractDao<Long, OrderTimetable> {

	Long getCount();

}
