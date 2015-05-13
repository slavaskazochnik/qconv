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
	public List<Point> getAllByStreetAndName(Street street, String name) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Point> criteria = cBuilder.createQuery(Point.class);
		Root<Point> root = criteria.from(Point.class);

		List<Predicate> predicates = new ArrayList<Predicate>();
		if (street != null) {
			predicates.add(cBuilder.equal(root.get(Point_.street), street));
		}
		if (name != null) {
			predicates.add(cBuilder.like(root.get(Point_.name), name));
		}
		if (predicates.size() > 0) {
			criteria.where(predicates.toArray(new Predicate[] {}));
		}

		criteria.select(root);
		root.fetch(Point_.street);
		criteria.orderBy(cBuilder.asc(root.get(Point_.name)));

		TypedQuery<Point> query = getEm().createQuery(criteria);
		List<Point> results = query.getResultList();
		return results;
	}

	@Override
	public List<Point> getAll() {
		return getAllByStreetAndName(null, null);
	}

	@Override
	public List<Point> getAllByName(String name) {
		return getAllByStreetAndName(null, name);
	}

	@Override
	public List<Point> getAllByStreet(Street street) {
		return getAllByStreetAndName(street, null);
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

}
