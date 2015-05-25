package by.parfen.disptaxi.dataaccess.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import by.parfen.disptaxi.dataaccess.UserRoleDao;
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.datamodel.UserRole;
import by.parfen.disptaxi.datamodel.UserRole_;

@Repository
public class UserRoleDaoImpl extends AbstractDaoImpl<Long, UserRole> implements UserRoleDao {

	protected UserRoleDaoImpl() {
		super(UserRole.class);
	}

	@Override
	public Long getCount() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Long> criteria = cBuilder.createQuery(Long.class);
		Root<UserRole> root = criteria.from(UserRole.class);

		criteria.select(cBuilder.count(root));

		TypedQuery<Long> query = getEm().createQuery(criteria);
		return query.getSingleResult();
	}

	public List<UserRole> getAll(UserProfile userProfile) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<UserRole> criteria = cBuilder.createQuery(UserRole.class);
		Root<UserRole> root = criteria.from(UserRole.class);

		List<Predicate> predicates = new ArrayList<Predicate>();
		if (userProfile != null) {
			predicates.add(cBuilder.equal(root.get(UserRole_.userProfile), userProfile));
		}
		if (predicates.size() > 0) {
			criteria.where(predicates.toArray(new Predicate[] {}));
		}

		criteria.select(root);

		TypedQuery<UserRole> query = getEm().createQuery(criteria);
		List<UserRole> results = query.getResultList();
		return results;
	}

	@Override
	public List<UserRole> getAll() {
		// CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();
		//
		// CriteriaQuery<UserRole> criteria = cBuilder.createQuery(UserRole.class);
		// Root<UserRole> root = criteria.from(UserRole.class);
		//
		// criteria.select(root);
		//
		// TypedQuery<UserRole> query = getEm().createQuery(criteria);
		// List<UserRole> results = query.getResultList();
		// return results;
		return getAll(null);
	}

	@Override
	public List<UserRole> getAllByUserProfile(UserProfile userProfile) {
		return getAll(userProfile);
	}

	@Override
	public UserRole getWithDetails(UserRole userRole) {
		UserRole result = null;
		if (userRole != null) {
			CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

			CriteriaQuery<UserRole> criteria = cBuilder.createQuery(UserRole.class);
			Root<UserRole> root = criteria.from(UserRole.class);
			root.fetch(UserRole_.userProfile);

			List<Predicate> predicates = new ArrayList<Predicate>();
			predicates.add(cBuilder.equal(root.get(UserRole_.id), userRole.getId()));
			criteria.where(predicates.toArray(new Predicate[] {}));

			criteria.select(root);

			TypedQuery<UserRole> query = getEm().createQuery(criteria);
			result = query.getSingleResult();
		}
		return result;
	}

}
