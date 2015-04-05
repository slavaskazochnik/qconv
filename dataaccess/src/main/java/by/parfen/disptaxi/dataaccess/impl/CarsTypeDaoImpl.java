package by.parfen.disptaxi.dataaccess.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import by.parfen.disptaxi.dataaccess.CarsTypeDao;
import by.parfen.disptaxi.datamodel.CarsType;

@Repository
public class CarsTypeDaoImpl extends AbstractDaoImpl<Long, CarsType> implements CarsTypeDao {

	protected CarsTypeDaoImpl() {
		super(CarsType.class);
	}

	@Override
	public Long getCount() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Long> criteria = cBuilder.createQuery(Long.class);
		Root<CarsType> root = criteria.from(CarsType.class);

		criteria.select(cBuilder.count(root));

		TypedQuery<Long> query = getEm().createQuery(criteria);
		return query.getSingleResult();
	}

	@Override
	public List<CarsType> getAll() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<CarsType> criteria = cBuilder.createQuery(CarsType.class);
		Root<CarsType> root = criteria.from(CarsType.class);

		criteria.select(root);

		TypedQuery<CarsType> query = getEm().createQuery(criteria);
		List<CarsType> results = query.getResultList();
		return results;
	}
}
