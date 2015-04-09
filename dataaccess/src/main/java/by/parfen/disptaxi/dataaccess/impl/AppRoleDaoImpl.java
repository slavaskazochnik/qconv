package by.parfen.disptaxi.dataaccess.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import by.parfen.disptaxi.dataaccess.AppRoleDao;
import by.parfen.disptaxi.datamodel.AppRole;

@Repository
public class AppRoleDaoImpl extends AbstractDaoImpl<Long, AppRole> implements AppRoleDao {

	protected AppRoleDaoImpl() {
		super(AppRole.class);
	}

	@Override
	public Long getCount() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<Long> criteria = cBuilder.createQuery(Long.class);
		Root<AppRole> root = criteria.from(AppRole.class);

		criteria.select(cBuilder.count(root));

		TypedQuery<Long> query = getEm().createQuery(criteria);
		return query.getSingleResult();
	}

	@Override
	public List<AppRole> getAll() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<AppRole> criteria = cBuilder.createQuery(AppRole.class);
		Root<AppRole> root = criteria.from(AppRole.class);

		criteria.select(root);

		TypedQuery<AppRole> query = getEm().createQuery(criteria);
		List<AppRole> results = query.getResultList();
		return results;
	}
}
