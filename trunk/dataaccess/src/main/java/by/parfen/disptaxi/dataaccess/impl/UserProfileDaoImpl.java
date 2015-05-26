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

import by.parfen.disptaxi.dataaccess.UserProfileDao;
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.datamodel.UserProfile_;
import by.parfen.disptaxi.datamodel.filter.FilterUserProfile;

@Repository
public class UserProfileDaoImpl extends AbstractDaoImpl<Long, UserProfile> implements UserProfileDao {

	protected UserProfileDaoImpl() {
		super(UserProfile.class);
	}

	@Override
	public Long getCount() {
		// CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();
		//
		// CriteriaQuery<Long> criteria = cBuilder.createQuery(Long.class);
		// Root<UserProfile> root = criteria.from(UserProfile.class);
		//
		// criteria.select(cBuilder.count(root));
		//
		// TypedQuery<Long> query = getEm().createQuery(criteria);
		// return query.getSingleResult();
		return getCount(null);
	}

	private List<Predicate> getPrediactesByFilter(CriteriaBuilder cBuilder, Root<UserProfile> root,
			FilterUserProfile filterUserProfile) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filterUserProfile != null) {
			if (filterUserProfile.getTelNum() != null) {
				predicates.add(cBuilder.like(root.get(UserProfile_.telNum), "%" + filterUserProfile.getTelNum() + "%"));
			}
			if (filterUserProfile.getLastName() != null) {
				predicates.add(cBuilder.like(root.get(UserProfile_.lastName), "%" + filterUserProfile.getLastName() + "%"));
			}
		}
		return predicates;
	}

	@Override
	public Long getCount(FilterUserProfile filterUserProfile) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Long> criteria = cBuilder.createQuery(Long.class);
		Root<UserProfile> root = criteria.from(UserProfile.class);

		// if (filterUserProfile != null) {
		// List<Predicate> predicates = new ArrayList<Predicate>();
		// if (filterUserProfile.getTelNum() != null) {
		// predicates.add(cBuilder.like(root.get(UserProfile_.telNum), "%" +
		// filterUserProfile.getTelNum() + "%"));
		// }
		// if (filterUserProfile.getLastName() != null) {
		// predicates.add(cBuilder.like(root.get(UserProfile_.lastName), "%" +
		// filterUserProfile.getLastName() + "%"));
		// }
		// if (predicates.size() > 0) {
		// criteria.where(predicates.toArray(new Predicate[] {}));
		// }
		// }
		List<Predicate> predicates = getPrediactesByFilter(cBuilder, root, filterUserProfile);
		if (predicates.size() > 0) {
			criteria.where(predicates.toArray(new Predicate[] {}));
		}

		criteria.select(cBuilder.count(root));

		TypedQuery<Long> query = getEm().createQuery(criteria);
		return query.getSingleResult();
	}

	@Override
	public List<UserProfile> getAll() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<UserProfile> criteria = cBuilder.createQuery(UserProfile.class);
		Root<UserProfile> root = criteria.from(UserProfile.class);

		criteria.select(root);

		TypedQuery<UserProfile> query = getEm().createQuery(criteria);
		List<UserProfile> results = query.getResultList();
		return results;
	}

	@Override
	public List<UserProfile> getAll(SingularAttribute<UserProfile, ?> attr, boolean ascending, int startRecord,
			int pageSize, FilterUserProfile filterUserProfile) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<UserProfile> criteria = cBuilder.createQuery(UserProfile.class);
		Root<UserProfile> root = criteria.from(UserProfile.class);

		// if (filterUserProfile != null) {
		// List<Predicate> predicates = new ArrayList<Predicate>();
		// if (filterUserProfile.getTelNum() != null) {
		// predicates.add(cBuilder.like(root.get(UserProfile_.telNum), "%" +
		// filterUserProfile.getTelNum() + "%"));
		// }
		// if (filterUserProfile.getLastName() != null) {
		// predicates.add(cBuilder.like(root.get(UserProfile_.lastName), "%" +
		// filterUserProfile.getLastName() + "%"));
		// }
		// if (predicates.size() > 0) {
		// criteria.where(predicates.toArray(new Predicate[] {}));
		// }
		// }
		List<Predicate> predicates = getPrediactesByFilter(cBuilder, root, filterUserProfile);
		if (predicates.size() > 0) {
			criteria.where(predicates.toArray(new Predicate[] {}));
		}

		criteria.select(root);
		criteria.orderBy(new OrderImpl(root.get(attr), ascending));

		TypedQuery<UserProfile> query = getEm().createQuery(criteria);
		query.setFirstResult(startRecord);
		query.setMaxResults(pageSize);

		List<UserProfile> results = query.getResultList();
		return results;
	}

	@Override
	public List<UserProfile> getAll(SingularAttribute<UserProfile, ?> attr, boolean ascending, int startRecord,
			int pageSize) {
		return getAll(attr, ascending, startRecord, pageSize, null);
	}

	@Override
	public List<UserProfile> getAllByFirstName(String firstName) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<UserProfile> criteria = cBuilder.createQuery(UserProfile.class);
		Root<UserProfile> root = criteria.from(UserProfile.class);

		criteria.select(root);

		criteria.where(cBuilder.equal(root.get(UserProfile_.firstName), firstName));

		TypedQuery<UserProfile> query = getEm().createQuery(criteria);
		List<UserProfile> results = query.getResultList();
		return results;
	}

}
