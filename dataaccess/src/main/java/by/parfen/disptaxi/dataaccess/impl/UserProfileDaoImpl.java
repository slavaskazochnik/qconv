package by.parfen.disptaxi.dataaccess.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.hibernate.jpa.criteria.OrderImpl;
import org.springframework.stereotype.Repository;

import by.parfen.disptaxi.dataaccess.UserProfileDao;
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.datamodel.UserProfile_;

@Repository
public class UserProfileDaoImpl extends AbstractDaoImpl<Long, UserProfile> implements UserProfileDao {

	protected UserProfileDaoImpl() {
		super(UserProfile.class);
	}

	@Override
	public Long getCount() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Long> criteria = cBuilder.createQuery(Long.class);
		Root<UserProfile> root = criteria.from(UserProfile.class);

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
			int pageSize) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<UserProfile> criteria = cBuilder.createQuery(UserProfile.class);
		Root<UserProfile> root = criteria.from(UserProfile.class);

		criteria.select(root);
		criteria.orderBy(new OrderImpl(root.get(attr), ascending));

		TypedQuery<UserProfile> query = getEm().createQuery(criteria);
		query.setFirstResult(startRecord);
		query.setMaxResults(pageSize);

		List<UserProfile> results = query.getResultList();
		return results;
	}

	@Override
	public List<UserProfile> getAllByFirstName(String firstName) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<UserProfile> root = cBuilder.createQuery(UserProfile.class);
		Root<UserProfile> criteria = root.from(UserProfile.class);

		root.select(criteria);

		root.where(cBuilder.equal(criteria.get(UserProfile_.firstName), firstName));

		TypedQuery<UserProfile> query = getEm().createQuery(root);
		List<UserProfile> results = query.getResultList();
		return results;
	}

}
