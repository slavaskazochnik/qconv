package by.parfen.disptaxi.dataaccess;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.datamodel.Order;

public interface OrderDao extends AbstractDao<Long, Order> {

	Long getCount();

	List<Order> getAll(SingularAttribute<Order, ?> attr, boolean ascending, int startRecord, int pageSize);

	List<Order> getAllByDriver(Driver driver);

	List<Order> getAllByCustomer(Customer customer);

	Driver getOrderDriver(Order order);

}
