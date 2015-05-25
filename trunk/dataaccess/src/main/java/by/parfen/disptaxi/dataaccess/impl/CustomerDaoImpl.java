package by.parfen.disptaxi.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import by.parfen.disptaxi.dataaccess.CustomerDao;
import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.datamodel.Customer_;

@Repository
public class CustomerDaoImpl extends AbstractDaoImpl<Long, Customer> implements CustomerDao {

	protected CustomerDaoImpl() {
		super(Customer.class);
	}

	@Override
	public Long getCount() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Long> criteria = cBuilder.createQuery(Long.class);
		Root<Customer> root = criteria.from(Customer.class);

		criteria.select(cBuilder.count(root));

		TypedQuery<Long> query = getEm().createQuery(criteria);
		return query.getSingleResult();
	}

	private List<Customer> getAll(Boolean withDetails, Customer customer) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Customer> criteria = cBuilder.createQuery(Customer.class);
		Root<Customer> root = criteria.from(Customer.class);

		List<Predicate> predicates = new ArrayList<Predicate>();
		if (customer != null) {
			predicates.add(cBuilder.equal(root.get(Customer_.id), customer.getId()));
		}
		if (predicates.size() > 0) {
			criteria.where(predicates.toArray(new Predicate[] {}));
		}

		criteria.select(root);
		if (withDetails) {
			root.fetch(Customer_.userProfile);
		}
		TypedQuery<Customer> query = getEm().createQuery(criteria);
		List<Customer> results = query.getResultList();
		return results;
	}

	@Override
	public List<Customer> getAll() {
		return getAll(false, null);
	}

	@Override
	public List<Customer> getAllWithDetails() {
		return getAll(true, null);
	}

	public List<Customer> getAll(SingularAttribute<Customer, ?> attr, boolean ascending, int startRecord, int pageSize,
			Boolean withDetails) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Customer> criteria = cBuilder.createQuery(Customer.class);
		Root<Customer> root = criteria.from(Customer.class);

		criteria.select(root);
		if (withDetails) {
			root.fetch(Customer_.userProfile);
		}
		if (attr != null) {
			criteria.orderBy(new OrderImpl(root.get(attr), ascending));
		}
		TypedQuery<Customer> query = getEm().createQuery(criteria);
		if (startRecord > 0 && pageSize > 0) {
			query.setFirstResult(startRecord);
			query.setMaxResults(pageSize);
		}

		List<Customer> results = query.getResultList();
		return results;
	}

	@Override
	public List<Customer> getAllWithDetails(SingularAttribute<Customer, ?> attr, boolean ascending, int startRecord,
			int pageSize) {
		return getAll(attr, ascending, startRecord, pageSize, true);
	}

	@Override
	public Customer getWithDetails(Customer customer) {
		Customer result = null;
		List<Customer> customers = getAll(true, customer);
		if (customers.size() == 1) {
			result = customers.get(0);
		}
		return result;
	}
}
