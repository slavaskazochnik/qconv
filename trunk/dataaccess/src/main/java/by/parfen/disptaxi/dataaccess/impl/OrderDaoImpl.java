package by.parfen.disptaxi.dataaccess.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
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
import by.parfen.disptaxi.datamodel.OrderTimetable;
import by.parfen.disptaxi.datamodel.OrderTimetable_;
import by.parfen.disptaxi.datamodel.Order_;
import by.parfen.disptaxi.datamodel.enums.OrderStatus;
import by.parfen.disptaxi.datamodel.filter.FilterOrder;

@Repository
public class OrderDaoImpl extends AbstractDaoImpl<Long, Order> implements OrderDao {

	protected OrderDaoImpl() {
		super(Order.class);
	}

	@Override
	public Long getCount() {
		return getCount(null);
	}

	@Override
	public Long getCount(FilterOrder filterOrder) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Long> criteria = cBuilder.createQuery(Long.class);
		Root<Order> root = criteria.from(Order.class);

		// use Filter
		if (filterOrder != null) {
			List<Predicate> predicates = new ArrayList<Predicate>();
			if (filterOrder.getCustomer() != null) {
				predicates.add(cBuilder.equal(root.get(Order_.customer), filterOrder.getCustomer()));
			}
			// add detail table Auto for Driver
			if (filterOrder.getDriver() != null) {
				Join<Order, Auto> details = root.join(Order_.auto);
				predicates.add(cBuilder.equal(details.get(Auto_.driver), filterOrder.getDriver()));
			}
			if (filterOrder.getFromDate() != null) {
				predicates.add(cBuilder.greaterThanOrEqualTo(root.get(Order_.creationDate), filterOrder.getFromDate()));
			}
			if (filterOrder.getToDate() != null) {
				Date toDate = filterOrder.getToDate();
				Calendar c = Calendar.getInstance();
				c.setTime(toDate);
				c.add(Calendar.DATE, 1);
				predicates.add(cBuilder.lessThan(root.get(Order_.creationDate), c.getTime()));
				// predicates.add(cBuilder.lessThanOrEqualTo(root.get(Order_.creationDate),
				// filterOrder.getToDate()));
			}
			if (predicates.size() > 0) {
				criteria.where(predicates.toArray(new Predicate[] {}));
			}
		}

		criteria.select(cBuilder.count(root));

		TypedQuery<Long> query = getEm().createQuery(criteria);
		return query.getSingleResult();
	}

	@Override
	public List<Order> getAll(SingularAttribute<Order, ?> attr, boolean ascending, int startRecord, int pageSize,
			FilterOrder filterOrder) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Order> criteria = cBuilder.createQuery(Order.class);
		Root<Order> root = criteria.from(Order.class);

		// use Filter
		if (filterOrder != null) {
			List<Predicate> predicates = new ArrayList<Predicate>();
			if (filterOrder.getCustomer() != null) {
				predicates.add(cBuilder.equal(root.get(Order_.customer), filterOrder.getCustomer()));
			}
			// add detail table Auto for Driver
			if (filterOrder.getDriver() != null) {
				Join<Order, Auto> details = root.join(Order_.auto);
				predicates.add(cBuilder.equal(details.get(Auto_.driver), filterOrder.getDriver()));
			}
			if (filterOrder.getFromDate() != null) {
				predicates.add(cBuilder.greaterThanOrEqualTo(root.get(Order_.creationDate), filterOrder.getFromDate()));
			}
			if (filterOrder.getToDate() != null) {
				Date toDate = filterOrder.getToDate();
				Calendar c = Calendar.getInstance();
				c.setTime(toDate);
				c.add(Calendar.DATE, 1);
				predicates.add(cBuilder.lessThan(root.get(Order_.creationDate), c.getTime()));
				// predicates.add(cBuilder.lessThanOrEqualTo(root.get(Order_.creationDate),
				// filterOrder.getToDate()));
			}
			if (predicates.size() > 0) {
				criteria.where(predicates.toArray(new Predicate[] {}));
			}
		}

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
	public List<Order> getAll(SingularAttribute<Order, ?> attr, boolean ascending, int startRecord, int pageSize) {
		return getAll(attr, ascending, startRecord, pageSize, null);
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
		Fetch price = root.fetch(Order_.price);
		criteria.where(cBuilder.equal(root, order));

		criteria.select(root);

		TypedQuery<Order> query = getEm().createQuery(criteria);
		Order results = query.getSingleResult();

		return results;
	}

	@Override
	public OrderStatus getLastOrderStatusInTimetable(Order order) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<OrderStatus> criteria = cBuilder.createQuery(OrderStatus.class);
		Root<OrderTimetable> root = criteria.from(OrderTimetable.class);

		criteria.where(cBuilder.equal(root.get(OrderTimetable_.order), order));

		criteria.multiselect(cBuilder.greatest(root.get(OrderTimetable_.orderStatus)));

		TypedQuery<OrderStatus> query = getEm().createQuery(criteria);
		OrderStatus result = query.getSingleResult();

		return result == null ? OrderStatus.NEW : result;
	}
}
