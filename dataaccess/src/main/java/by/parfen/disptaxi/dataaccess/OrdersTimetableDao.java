package by.parfen.disptaxi.dataaccess;

import by.parfen.disptaxi.datamodel.OrdersTimetable;

public interface OrdersTimetableDao extends AbstractDao<Long, OrdersTimetable> {

	Long getCount();

}
