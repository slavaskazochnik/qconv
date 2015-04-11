package by.parfen.disptaxi.dataaccess.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.stereotype.Repository;

import by.parfen.disptaxi.dataaccess.OrderDao;
import by.parfen.disptaxi.datamodel.Order;

@Repository
public class OrderDaoImpl extends AbstractDaoImpl<Long, Order> implements OrderDao {

	protected OrderDaoImpl() {
		super(Order.class);
	}

	@Override
	public Long getCount() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Long> criteria = cBuilder.createQuery(Long.class);
		Root<Order> root = criteria.from(Order.class);

		criteria.select(cBuilder.count(root));

		TypedQuery<Long> query = getEm().createQuery(criteria);
		return query.getSingleResult();
	}

	// @Override
	// public List<Orders> getAllByDriverId(Long Id) {
	// CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();
	//
	// CriteriaQuery<Orders> criteria = cBuilder.createQuery(Orders.class);
	// Root<Orders> root = criteria.from(Orders.class);
	//
	// criteria.select(root);
	// criteria.where(cBuilder.equals(criteria.get(Orders_.)));
	//
	// TypedQuery<Orders> query = getEm().createQuery(criteria);
	// List<Orders> results = query.getResultList();
	//
	// return results;
	// }

	@Override
	public List<Order> getAll(SingularAttribute<Order, ?> attr, boolean ascending, int startRecord, int pageSize) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Order> criteria = cBuilder.createQuery(Order.class);
		Root<Order> root = criteria.from(Order.class);

		criteria.select(root);

		TypedQuery<Order> query = getEm().createQuery(criteria);
		List<Order> results = query.getResultList();
		return results;
	}

}
