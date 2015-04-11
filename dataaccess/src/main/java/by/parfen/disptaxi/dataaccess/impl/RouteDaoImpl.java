package by.parfen.disptaxi.dataaccess.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import by.parfen.disptaxi.dataaccess.RouteDao;
import by.parfen.disptaxi.datamodel.Route;

@Repository
public class RouteDaoImpl extends AbstractDaoImpl<Long, Route> implements RouteDao {

	protected RouteDaoImpl() {
		super(Route.class);
	}

	@Override
	public Long getCount() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Long> criteria = cBuilder.createQuery(Long.class);
		Root<Route> root = criteria.from(Route.class);

		criteria.select(cBuilder.count(root));

		TypedQuery<Long> query = getEm().createQuery(criteria);
		return query.getSingleResult();
	}

	@Override
	public List<Route> getAll() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Route> criteria = cBuilder.createQuery(Route.class);
		Root<Route> root = criteria.from(Route.class);

		criteria.select(root);

		TypedQuery<Route> query = getEm().createQuery(criteria);
		List<Route> results = query.getResultList();
		return results;
	}
}
