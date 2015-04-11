package by.parfen.disptaxi.dataaccess.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import by.parfen.disptaxi.dataaccess.CityDao;
import by.parfen.disptaxi.datamodel.City;
import by.parfen.disptaxi.datamodel.City_;

@Repository
public class CityDaoImpl extends AbstractDaoImpl<Long, City> implements CityDao {

	protected CityDaoImpl() {
		super(City.class);
	}

	@Override
	public Long getCount() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Long> criteria = cBuilder.createQuery(Long.class);
		Root<City> root = criteria.from(City.class);

		criteria.select(cBuilder.count(root));

		TypedQuery<Long> query = getEm().createQuery(criteria);
		return query.getSingleResult();
	}

	@Override
	public List<City> getAll() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<City> criteria = cBuilder.createQuery(City.class);
		Root<City> root = criteria.from(City.class);

		criteria.select(root);

		TypedQuery<City> query = getEm().createQuery(criteria);
		List<City> results = query.getResultList();
		return results;
	}

	@Override
	public List<City> getAllByName(String name) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<City> criteria = cBuilder.createQuery(City.class);
		Root<City> root = criteria.from(City.class);

		criteria.select(root);

		criteria.where(cBuilder.equal(root.get(City_.name), name));

		TypedQuery<City> query = getEm().createQuery(criteria);
		List<City> results = query.getResultList();
		return results;
	}
}
