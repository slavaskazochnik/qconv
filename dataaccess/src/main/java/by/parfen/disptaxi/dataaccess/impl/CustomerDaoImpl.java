package by.parfen.disptaxi.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import by.parfen.disptaxi.dataaccess.CustomerDao;
import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.datamodel.Customer_;
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.datamodel.UserProfile_;
import by.parfen.disptaxi.datamodel.filter.FilterUserProfile;

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
			Boolean withDetails, FilterUserProfile filterUserProfile) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Customer> criteria = cBuilder.createQuery(Customer.class);
		Root<Customer> root = criteria.from(Customer.class);

		// use Filter
		if (withDetails) {
			// SELECT customer.*, user_profile.* FROM customer JOIN user_profile
			root.fetch(Customer_.userProfile);
		}
		if (filterUserProfile != null) {
			List<Predicate> predicates = new ArrayList<Predicate>();
			// add detail table
			if (filterUserProfile.getTelNum() != null || filterUserProfile.getLastName() != null) {
				// simple Join give:
				// SELECT customer.* FROM customer JOIN user_profile
				// using fetch with Join type
				// SELECT customer.*, user_profile.* FROM customer JOIN user_profile
				Join<Customer, UserProfile> details = (Join<Customer, UserProfile>) root.fetch(Customer_.userProfile);
				root.fetch(Customer_.userProfile);
				if (filterUserProfile.getTelNum() != null) {
					predicates.add(cBuilder.like(details.get(UserProfile_.telNum), "%" + filterUserProfile.getTelNum() + "%"));
				}
				if (filterUserProfile.getLastName() != null) {
					predicates
							.add(cBuilder.like(details.get(UserProfile_.lastName), "%" + filterUserProfile.getLastName() + "%"));
				}
			}
			if (predicates.size() > 0) {
				criteria.where(predicates.toArray(new Predicate[] {}));
			}
		}
		criteria.select(root);
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
			int pageSize, FilterUserProfile filterUserProfile) {
		return getAll(attr, ascending, startRecord, pageSize, true, filterUserProfile);
	}

	@Override
	public List<Customer> getAllWithDetails(SingularAttribute<Customer, ?> attr, boolean ascending, int startRecord,
			int pageSize) {
		return getAll(attr, ascending, startRecord, pageSize, true, null);
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