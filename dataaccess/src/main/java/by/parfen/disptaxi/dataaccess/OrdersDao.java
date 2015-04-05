package by.parfen.disptaxi.dataaccess;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import by.parfen.disptaxi.datamodel.Orders;

public interface OrdersDao extends AbstractDao<Long, Orders> {

	Long getCount();

	List<Orders> getAll(SingularAttribute<Orders, ?> attr, boolean ascending, int startRecord, int pageSize);

}
