package by.parfen.disptaxi.dataaccess.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import by.parfen.disptaxi.dataaccess.UserAccountDao;
import by.parfen.disptaxi.datamodel.UserAccount;
import by.parfen.disptaxi.datamodel.UserAccount_;

@Repository
public class UserAccountDaoImpl extends AbstractDaoImpl<Long, UserAccount> implements UserAccountDao {

	protected UserAccountDaoImpl() {
		super(UserAccount.class);
	}

	@Override
	public List<UserAccount> getAll() {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<UserAccount> criteria = cBuilder.createQuery(UserAccount.class);
		Root<UserAccount> root = criteria.from(UserAccount.class);

		criteria.select(root);

		TypedQuery<UserAccount> query = getEm().createQuery(criteria);
		List<UserAccount> results = query.getResultList();
		return results;
	}

	@Override
	public UserAccount getWithDetails(UserAccount userAccount) {
		CriteriaBuilder cBuilder = getEm().getCriteriaBuilder();

		CriteriaQuery<UserAccount> criteria = cBuilder.createQuery(UserAccount.class);
		Root<UserAccount> root = criteria.from(UserAccount.class);
		root.fetch(UserAccount_.userRole);

		criteria.where(cBuilder.equal(root.get(UserAccount_.id), userAccount.getId()));

		criteria.select(root);

		TypedQuery<UserAccount> query = getEm().createQuery(criteria);
		UserAccount result = query.getSingleResult();
		return result;
	}

}
