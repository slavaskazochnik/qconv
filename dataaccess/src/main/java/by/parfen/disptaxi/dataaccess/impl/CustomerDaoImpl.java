package by.parfen.disptaxi.dataaccess.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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

	@Override
	public List<Customer> getAll() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Customer> criteria = cBuilder.createQuery(Customer.class);
		Root<Customer> root = criteria.from(Customer.class);

		criteria.select(root);

		TypedQuery<Customer> query = getEm().createQuery(criteria);
		List<Customer> results = query.getResultList();
		return results;
	}

	@Override
	public List<Customer> getAllWithDetails() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Customer> criteria = cBuilder.createQuery(Customer.class);
		Root<Customer> root = criteria.from(Customer.class);

		criteria.select(root);
		root.fetch(Customer_.userProfile);

		TypedQuery<Customer> query = getEm().createQuery(criteria);
		List<Customer> results = query.getResultList();
		return results;
	}
}
