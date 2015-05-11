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

import by.parfen.disptaxi.dataaccess.StreetDao;
import by.parfen.disptaxi.datamodel.City;
import by.parfen.disptaxi.datamodel.Street;
import by.parfen.disptaxi.datamodel.Street_;

@Repository
public class StreetDaoImpl extends AbstractDaoImpl<Long, Street> implements StreetDao {

	protected StreetDaoImpl() {
		super(Street.class);
	}

	@Override
	public Long getCount() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Long> criteria = cBuilder.createQuery(Long.class);
		Root<Street> root = criteria.from(Street.class);

		criteria.select(cBuilder.count(root));

		TypedQuery<Long> query = getEm().createQuery(criteria);
		return query.getSingleResult();
	}

	@Override
	public List<Street> getAll() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Street> criteria = cBuilder.createQuery(Street.class);
		Root<Street> root = criteria.from(Street.class);

		criteria.select(root);
		root.fetch(Street_.city);

		TypedQuery<Street> query = getEm().createQuery(criteria);
		List<Street> results = query.getResultList();
		return results;
	}

	@Override
	public List<Street> getAll(SingularAttribute<Street, ?> attr, boolean ascending, int startRecord, int pageSize) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Street> criteria = cBuilder.createQuery(Street.class);
		Root<Street> root = criteria.from(Street.class);

		criteria.select(root);
		criteria.orderBy(new OrderImpl(root.get(attr), ascending));

		TypedQuery<Street> query = getEm().createQuery(criteria);
		query.setFirstResult(startRecord);
		query.setMaxResults(pageSize);

		List<Street> results = query.getResultList();
		return results;
	}

	@Override
	public List<Street> getAllByCityAndName(City city, String name) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Street> criteria = cBuilder.createQuery(Street.class);
		Root<Street> root = criteria.from(Street.class);

		List<Predicate> predicates = new ArrayList<Predicate>();
		if (city != null) {
			predicates.add(cBuilder.equal(root.get(Street_.city), city));
		}
		if (name != null) {
			// predicates.add(cBuilder.equal(root.get(Street_.name), name));
			predicates.add(cBuilder.like(root.get(Street_.name), name));
		}
		if (predicates.size() > 0) {
			criteria.where(predicates.toArray(new Predicate[] {}));
		}

		criteria.select(root);
		root.fetch(Street_.city);
		// criteria.where(cBuilder.equal(root.get(Street_.city), city));
		criteria.orderBy(cBuilder.asc(root.get(Street_.name)));

		TypedQuery<Street> query = getEm().createQuery(criteria);
		List<Street> results = query.getResultList();
		return results;
	}

	@Override
	public List<Street> getAllByName(String name) {
		List<Street> results = getAllByCityAndName(null, name);
		return results;
	}

	@Override
	public List<Street> getAllByCity(City city) {
		List<Street> results = getAllByCityAndName(city, null);
		return results;
	}

}
