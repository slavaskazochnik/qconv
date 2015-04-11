package by.parfen.disptaxi.dataaccess.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import by.parfen.disptaxi.dataaccess.AutoDao;
import by.parfen.disptaxi.datamodel.Auto;
import by.parfen.disptaxi.datamodel.Auto_;
import by.parfen.disptaxi.datamodel.Car;
import by.parfen.disptaxi.datamodel.Car_;
import by.parfen.disptaxi.datamodel.enums.CarType;

@Repository
public class AutoDaoImpl extends AbstractDaoImpl<Long, Auto> implements AutoDao {

	protected AutoDaoImpl() {
		super(Auto.class);
	}

	@Override
	public Long getCount() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Long> criteria = cBuilder.createQuery(Long.class);
		Root<Auto> root = criteria.from(Auto.class);

		criteria.select(cBuilder.count(root));

		TypedQuery<Long> query = getEm().createQuery(criteria);
		return query.getSingleResult();
	}

	@Override
	public List<Auto> getAll() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Auto> criteria = cBuilder.createQuery(Auto.class);
		Root<Auto> root = criteria.from(Auto.class);

		criteria.select(root);

		TypedQuery<Auto> query = getEm().createQuery(criteria);
		List<Auto> results = query.getResultList();
		return results;
	}

	@Override
	public List<Auto> getAll(SingularAttribute<Auto, ?> attr, boolean ascending, int startRecord, int pageSize) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Auto> criteria = cBuilder.createQuery(Auto.class);
		Root<Auto> root = criteria.from(Auto.class);

		criteria.select(root);
		criteria.orderBy(new OrderImpl(root.get(attr), ascending));

		TypedQuery<Auto> query = getEm().createQuery(criteria);
		query.setFirstResult(startRecord);
		query.setMaxResults(pageSize);

		List<Auto> results = query.getResultList();
		return results;
	}

	@Override
	public List<Auto> getAllByCarType(CarType carType) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Auto> criteria = cBuilder.createQuery(Auto.class);
		Root<Auto> root = criteria.from(Auto.class);
		Join<Auto, Car> details = root.join(Auto_.car);
		criteria.where(cBuilder.equal(details.get(Car_.carType), carType));

		criteria.select(root);
		criteria.orderBy(cBuilder.asc(details.get(Car_.carType)));

		TypedQuery<Auto> query = getEm().createQuery(criteria);

		List<Auto> results = query.getResultList();
		return results;
	}

}
