package by.parfen.disptaxi.services.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.parfen.disptaxi.dataaccess.UserAccountDao;
import by.parfen.disptaxi.dataaccess.UserRoleDao;
import by.parfen.disptaxi.datamodel.UserAccount;
import by.parfen.disptaxi.datamodel.UserAccount_;
import by.parfen.disptaxi.datamodel.UserRole;
import by.parfen.disptaxi.datamodel.enums.AppRole;
import by.parfen.disptaxi.services.UserAccountService;

@Service
public class UserAccountServiceImpl implements UserAccountService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileServiceImpl.class);

	@Inject
	private UserAccountDao dao;
	@Inject
	private UserRoleDao daoRoles;

	@PostConstruct
	private void init() {
		// this method will be called by Spring after bean instantiation. Can be
		// used for any initialization process.
		LOGGER.info("Instance of UserAccountService is created. Class is: {}", getClass().getName());
	}

	@Override
	public UserAccount get(Long id) {
		UserAccount entity = dao.getById(id);
		return entity;
	}

	@Override
	public void create(UserAccount userAccount, UserRole userRole) {
		Validate.isTrue(userAccount.getId() == null,
				"This method should be called for 'not saved yet' userAccount only. Use UPDATE instead");
		LOGGER.debug("Set user role: {}", userRole);
		userAccount.setUserRole(userRole);
		LOGGER.debug("Insert: {}", userAccount);
		dao.insert(userAccount);
	}

	@Override
	public void update(UserAccount userAccount) {
		LOGGER.debug("Update: {}", userAccount);
		dao.update(userAccount);
	}

	@Override
	public void delete(UserAccount userAccount) {
		LOGGER.debug("Remove: {}", userAccount);
		dao.delete(userAccount.getId());
	}

	@Override
	public void deleteAll() {
		LOGGER.debug("Remove all user user accounts");
		dao.deleteAll();
	}

	@Override
	public List<UserAccount> getAll() {
		return dao.getAll();
	}

	@Override
	public UserAccount getAccountByEmail(String userName) {
		final List<UserAccount> allByFieldRestriction = dao.getAllByFieldRestriction(UserAccount_.email, userName);
		return !allByFieldRestriction.isEmpty() ? allByFieldRestriction.get(0) : null;
	}

	@Override
	public List<AppRole> getRoles(Long userAccountId) {
		// FIXME replace with call to DB
		AppRole result = AppRole.CUSTOMER_ROLE;
		if (userAccountId != null) {
			result = daoRoles.getById(userAccountId).getAppRole();
		}
		return Arrays.asList(result);
	}

}
