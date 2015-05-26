package by.parfen.disptaxi.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import by.parfen.disptaxi.dataaccess.AutoDao;
import by.parfen.disptaxi.datamodel.Auto;
import by.parfen.disptaxi.datamodel.Auto_;
import by.parfen.disptaxi.datamodel.Car;
import by.parfen.disptaxi.datamodel.Car_;
import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.datamodel.Driver_;
import by.parfen.disptaxi.datamodel.enums.CarType;
import by.parfen.disptaxi.datamodel.enums.SignActive;
import by.parfen.disptaxi.datamodel.filter.FilterAuto;

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

	private List<Auto> getAll(CarType carType, SignActive signActive) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Auto> criteria = cBuilder.createQuery(Auto.class);
		Root<Auto> root = criteria.from(Auto.class);
		Join<Auto, Car> details = root.join(Auto_.car);

		List<Predicate> predicates = new ArrayList<Predicate>();
		if (carType != null) {
			predicates.add(cBuilder.equal(details.get(Car_.carType), carType));
		}
		if (signActive != null) {
			predicates.add(cBuilder.equal(root.get(Auto_.signActive), signActive));
		}
		if (predicates.size() > 0) {
			criteria.where(predicates.toArray(new Predicate[] {}));
		}

		criteria.select(root);
		criteria.orderBy(cBuilder.asc(details.get(Car_.carType)));

		TypedQuery<Auto> query = getEm().createQuery(criteria);

		List<Auto> results = query.getResultList();
		return results;
	}

	@Override
	public List<Auto> getAllByCarType(CarType carType) {
		return getAll(carType, null);
	}

	@Override
	public List<Auto> getAllActiveByCarType(CarType carType) {
		return getAll(carType, SignActive.YES);
	}

	@Override
	public List<Auto> getAllActive() {
		return getAll(null, SignActive.YES);
	}

	public List<Auto> getAllWithDetailsOld(Auto auto) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Auto> criteria = cBuilder.createQuery(Auto.class);
		Root<Auto> root = criteria.from(Auto.class);

		List<Predicate> predicates = new ArrayList<Predicate>();
		if (auto != null) {
			predicates.add(cBuilder.equal(root.get(Auto_.id), auto.getId()));
		}
		if (predicates.size() > 0) {
			criteria.where(predicates.toArray(new Predicate[] {}));
		}

		// final int detailsMethod = 1;
		// if (detailsMethod == 0) {
		// // Ok
		// criteria.select(root);
		// root.fetch(Auto_.car);
		// root.fetch(Auto_.driver);
		// criteria.orderBy(cBuilder.asc(root.get(Auto_.id)));
		// } else if (detailsMethod == 1) {
		// Ok
		@SuppressWarnings("unused")
		Fetch<Auto, Car> cars = root.fetch(Auto_.car);
		@SuppressWarnings("unused")
		Fetch<Auto, Driver> drivers = root.fetch(Auto_.driver);

		criteria.select(root);
		criteria.orderBy(cBuilder.asc(root.get(Auto_.id)));
		// } else if (detailsMethod == 2) {
		// // details are empty
		// Join<Auto, Car> cars = root.join(Auto_.car);
		// Join<Auto, Driver> drivers = root.join(Auto_.driver);
		//
		// criteria.select(root);
		// criteria.orderBy(cBuilder.asc(cars.get(Car_.id)));
		// }

		TypedQuery<Auto> query = getEm().createQuery(criteria);

		List<Auto> results = query.getResultList();
		return results;
	}

	@Override
	public List<Auto> getAllWithDetails(FilterAuto filterAuto) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Auto> criteria = cBuilder.createQuery(Auto.class);
		Root<Auto> root = criteria.from(Auto.class);

		@SuppressWarnings("unchecked")
		Join<Auto, Car> cars = (Join<Auto, Car>) root.fetch(Auto_.car);
		@SuppressWarnings("unchecked")
		Join<Auto, Driver> drivers = (Join<Auto, Driver>) root.fetch(Auto_.driver);

		if (filterAuto != null) {
			List<Predicate> predicates = new ArrayList<Predicate>();
			if (filterAuto.getAuto() != null) {
				predicates.add(cBuilder.equal(root.get(Auto_.id), filterAuto.getAuto().getId()));
			}
			if (filterAuto.getDriver() != null) {
				// predicates.add(cBuilder.equal(root.get(Auto_.driver),
				// filterAuto.getDriver()));
				predicates.add(cBuilder.equal(drivers.get(Driver_.id), filterAuto.getDriver().getId()));
			}
			if (filterAuto.getCarType() != null) {
				predicates.add(cBuilder.equal(cars.get(Car_.carType), filterAuto.getCarType()));
			}
			if (filterAuto.getSignActive() != null) {
				predicates.add(cBuilder.equal(root.get(Auto_.signActive), filterAuto.getSignActive().ordinal()));
			}
			if (predicates.size() > 0) {
				criteria.where(predicates.toArray(new Predicate[] {}));
			}
		}

		criteria.select(root);
		criteria.orderBy(cBuilder.asc(root.get(Auto_.id)));

		TypedQuery<Auto> query = getEm().createQuery(criteria);

		List<Auto> results = query.getResultList();
		return results;
	}

	@Override
	public List<Auto> getAllWithDetails() {
		return getAllWithDetails(null);
	}

	@Override
	public Auto getWithDetails(Auto auto) {
		// List<Auto> autosList = getAllWithDetails(auto);
		final FilterAuto filterAuto = new FilterAuto();
		filterAuto.setAuto(auto);
		List<Auto> autosList = getAllWithDetails(filterAuto);
		Auto result = null;
		if (autosList.size() > 0) {
			result = autosList.get(0);
		}
		return result;
	}

}
