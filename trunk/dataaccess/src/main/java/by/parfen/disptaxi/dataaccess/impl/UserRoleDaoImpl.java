package by.parfen.disptaxi.dataaccess.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import by.parfen.disptaxi.dataaccess.UserRoleDao;
import by.parfen.disptaxi.datamodel.UserRole;

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

	@Override
	public List<UserRole> getAll() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<UserRole> criteria = cBuilder.createQuery(UserRole.class);
		Root<UserRole> root = criteria.from(UserRole.class);

		criteria.select(root);

		TypedQuery<UserRole> query = getEm().createQuery(criteria);
		List<UserRole> results = query.getResultList();
		return results;
	}
}
