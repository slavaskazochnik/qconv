package by.parfen.disptaxi.dataaccess.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import by.parfen.disptaxi.dataaccess.PointDao;
import by.parfen.disptaxi.datamodel.Point;
import by.parfen.disptaxi.datamodel.Point_;
import by.parfen.disptaxi.datamodel.Street;

@Repository
public class PointDaoImpl extends AbstractDaoImpl<Long, Point> implements PointDao {

	protected PointDaoImpl() {
		super(Point.class);
	}

	@Override
	public Long getCount() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Long> criteria = cBuilder.createQuery(Long.class);
		Root<Point> root = criteria.from(Point.class);

		criteria.select(cBuilder.count(root));

		TypedQuery<Long> query = getEm().createQuery(criteria);
		return query.getSingleResult();
	}

	@Override
	public List<Point> getAll() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Point> criteria = cBuilder.createQuery(Point.class);
		Root<Point> root = criteria.from(Point.class);

		criteria.select(root);

		TypedQuery<Point> query = getEm().createQuery(criteria);
		List<Point> results = query.getResultList();
		return results;
	}

	@Override
	public List<Point> getAll(SingularAttribute<Point, ?> attr, boolean ascending, int startRecord, int pageSize) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Point> criteria = cBuilder.createQuery(Point.class);
		Root<Point> root = criteria.from(Point.class);

		criteria.select(root);
		criteria.orderBy(new OrderImpl(root.get(attr), ascending));

		TypedQuery<Point> query = getEm().createQuery(criteria);
		query.setFirstResult(startRecord);
		query.setMaxResults(pageSize);

		List<Point> results = query.getResultList();
		return results;
	}

	@Override
	public List<Point> getAllByName(String name) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Point> criteria = cBuilder.createQuery(Point.class);
		Root<Point> root = criteria.from(Point.class);

		criteria.select(root);

		criteria.where(cBuilder.equal(root.get(Point_.name), name));

		TypedQuery<Point> query = getEm().createQuery(criteria);
		List<Point> results = query.getResultList();
		return results;
	}

	@Override
	public List<Point> getAllByStreet(Street street) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Point> criteria = cBuilder.createQuery(Point.class);
		Root<Point> root = criteria.from(Point.class);
		criteria.where(cBuilder.equal(root.get(Point_.street), street));

		criteria.select(root);

		TypedQuery<Point> query = getEm().createQuery(criteria);
		List<Point> results = query.getResultList();
		return results;
	}

}
