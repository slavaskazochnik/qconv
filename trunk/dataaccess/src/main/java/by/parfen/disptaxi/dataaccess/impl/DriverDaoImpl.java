package by.parfen.disptaxi.dataaccess.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import by.parfen.disptaxi.dataaccess.DriverDao;
import by.parfen.disptaxi.datamodel.Driver;

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

	@Override
	public List<Driver> getAll() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Driver> criteria = cBuilder.createQuery(Driver.class);
		Root<Driver> root = criteria.from(Driver.class);

		criteria.select(root);

		TypedQuery<Driver> query = getEm().createQuery(criteria);
		List<Driver> results = query.getResultList();
		return results;
	}
}
