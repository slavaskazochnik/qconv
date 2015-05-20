package by.parfen.disptaxi.dataaccess.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import by.parfen.disptaxi.dataaccess.OrderDao;
import by.parfen.disptaxi.datamodel.Auto;
import by.parfen.disptaxi.datamodel.Auto_;
import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.datamodel.Order;
import by.parfen.disptaxi.datamodel.Order_;

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

	@Override
	public List<Order> getAll(SingularAttribute<Order, ?> attr, boolean ascending, int startRecord, int pageSize) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Order> criteria = cBuilder.createQuery(Order.class);
		Root<Order> root = criteria.from(Order.class);

		criteria.select(root);
		if (attr != null) {
			criteria.orderBy(new OrderImpl(root.get(attr), ascending));
		}

		TypedQuery<Order> query = getEm().createQuery(criteria);
		query.setFirstResult(startRecord);
		query.setMaxResults(pageSize);

		List<Order> results = query.getResultList();
		return results;
	}

	@Override
	public List<Order> getAllByDriver(Driver driver) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Order> criteria = cBuilder.createQuery(Order.class);
		Root<Order> root = criteria.from(Order.class);
		Join<Order, Auto> details = root.join(Order_.auto);
		criteria.where(cBuilder.equal(details.get(Auto_.driver), driver));

		criteria.select(root);
		criteria.orderBy(cBuilder.asc(root.get(Order_.creationDate)));

		TypedQuery<Order> query = getEm().createQuery(criteria);
		List<Order> results = query.getResultList();

		return results;
	}

	@Override
	public List<Order> getAllByCustomer(Customer customer) {
		return getAllByFieldRestriction(Order_.customer, customer);
	}

	@Override
	public Driver getOrderDriver(Order order) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Order> criteria = cBuilder.createQuery(Order.class);
		Root<Order> root = criteria.from(Order.class);
		Fetch autos = root.fetch(Order_.auto);
		Fetch drivers = autos.fetch(Auto_.driver);
		criteria.where(cBuilder.equal(root, order));

		criteria.select(root);

		TypedQuery<Order> query = getEm().createQuery(criteria);
		Order results = query.getSingleResult();

		return results.getAuto().getDriver();
	}

	@Override
	public Order getWithDetails(Order order) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Order> criteria = cBuilder.createQuery(Order.class);
		Root<Order> root = criteria.from(Order.class);
		Fetch customer = root.fetch(Order_.customer);
		Fetch auto = root.fetch(Order_.auto);
		Fetch driver = auto.fetch(Auto_.driver);
		criteria.where(cBuilder.equal(root, order));

		criteria.select(root);

		TypedQuery<Order> query = getEm().createQuery(criteria);
		Order results = query.getSingleResult();

		return results;
	}

}
