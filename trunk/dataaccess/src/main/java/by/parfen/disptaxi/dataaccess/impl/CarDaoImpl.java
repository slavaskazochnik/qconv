package by.parfen.disptaxi.dataaccess.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import by.parfen.disptaxi.dataaccess.CarDao;
import by.parfen.disptaxi.datamodel.Car;
import by.parfen.disptaxi.datamodel.Car_;

@Repository
public class CarDaoImpl extends AbstractDaoImpl<Long, Car> implements CarDao {

	protected CarDaoImpl() {
		super(Car.class);
	}

	@Override
	public Long getCount() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Long> criteria = cBuilder.createQuery(Long.class);
		Root<Car> root = criteria.from(Car.class);

		criteria.select(cBuilder.count(root));

		TypedQuery<Long> query = getEm().createQuery(criteria);
		return query.getSingleResult();
	}

	@Override
	public List<Car> getAll() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Car> criteria = cBuilder.createQuery(Car.class);
		Root<Car> root = criteria.from(Car.class);

		criteria.select(root);

		TypedQuery<Car> query = getEm().createQuery(criteria);
		List<Car> results = query.getResultList();
		return results;
	}

	@Override
	public List<Car> getAll(SingularAttribute<Car, ?> attr, boolean ascending, int startRecord, int pageSize) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Car> criteria = cBuilder.createQuery(Car.class);
		Root<Car> root = criteria.from(Car.class);

		criteria.select(root);
		criteria.orderBy(new OrderImpl(root.get(attr), ascending));

		TypedQuery<Car> query = getEm().createQuery(criteria);
		query.setFirstResult(startRecord);
		query.setMaxResults(pageSize);

		List<Car> results = query.getResultList();
		return results;
	}

	@Override
	public List<Car> getAllByRegNum(String regNum) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Car> root = cBuilder.createQuery(Car.class);
		Root<Car> criteria = root.from(Car.class);

		root.select(criteria);

		root.where(cBuilder.equal(criteria.get(Car_.regNum), regNum));

		TypedQuery<Car> query = getEm().createQuery(root);
		List<Car> results = query.getResultList();
		return results;
	}

}
