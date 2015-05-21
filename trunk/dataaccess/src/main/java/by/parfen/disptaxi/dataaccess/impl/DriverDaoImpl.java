package by.parfen.disptaxi.dataaccess.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import by.parfen.disptaxi.dataaccess.DriverDao;
import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.datamodel.Driver_;

@Repository
public class DriverDaoImpl extends AbstractDaoImpl<Long, Driver> implements DriverDao {

	protected DriverDaoImpl() {
		super(Driver.class);
	}

	@Override
	public Long getCount() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Long> criteria = cBuilder.createQuery(Long.class);
		Root<Driver> root = criteria.from(Driver.class);

		criteria.select(cBuilder.count(root));

		TypedQuery<Long> query = getEm().createQuery(criteria);
		return query.getSingleResult();
	}

	public List<Driver> getAll(SingularAttribute<Driver, ?> attr, boolean ascending, int startRecord, int pageSize,
			Boolean withDetails) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Driver> criteria = cBuilder.createQuery(Driver.class);
		Root<Driver> root = criteria.from(Driver.class);

		criteria.select(root);
		if (withDetails) {
			root.fetch(Driver_.userProfile);
		}
		if (attr != null) {
			criteria.orderBy(new OrderImpl(root.get(attr), ascending));
		}
		TypedQuery<Driver> query = getEm().createQuery(criteria);
		if (startRecord > 0 && pageSize > 0) {
			query.setFirstResult(startRecord);
			query.setMaxResults(pageSize);
		}

		List<Driver> results = query.getResultList();
		return results;
	}

	private List<Driver> getAll(Boolean withDetails) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Driver> criteria = cBuilder.createQuery(Driver.class);
		Root<Driver> root = criteria.from(Driver.class);

		criteria.select(root);
		if (withDetails) {
			root.fetch(Driver_.userProfile);
		}

		TypedQuery<Driver> query = getEm().createQuery(criteria);
		List<Driver> results = query.getResultList();
		return results;
	}

	@Override
	public List<Driver> getAll() {
		return getAll(false);
	}

	@Override
	public List<Driver> getAllWithDetails() {
		return getAll(true);
	}

	@Override
	public List<Driver> getAllWithDetails(SingularAttribute<Driver, ?> attr, boolean ascending, int startRecord,
			int pageSize) {
		return getAll(attr, ascending, startRecord, pageSize, true);
	}
}
