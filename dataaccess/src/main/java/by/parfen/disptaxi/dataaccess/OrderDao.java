package by.parfen.disptaxi.dataaccess;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import by.parfen.disptaxi.datamodel.Order;

public interface OrderDao extends AbstractDao<Long, Order> {

	Long getCount();

	// List<Orders> getAllByDriverId(Long Id);

	List<Order> getAll(SingularAttribute<Order, ?> attr, boolean ascending, int startRecord, int pageSize);

}
