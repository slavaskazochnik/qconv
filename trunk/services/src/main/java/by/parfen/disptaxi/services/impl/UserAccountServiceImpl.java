package by.parfen.disptaxi.services.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.parfen.disptaxi.dataaccess.UserAccountDao;
import by.parfen.disptaxi.datamodel.UserAccount;
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.services.UserAccountService;

@Service
public class UserAccountServiceImpl implements UserAccountService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileServiceImpl.class);

	@Inject
	private UserAccountDao dao;

	@PostConstruct
	private void init() {
		// this method will be called by Spring after bean instantiation. Can be
		// used for any initialization process.
		LOGGER.info("Instance of UserProfileService is created. Class is: {}", getClass().getName());
	}

	@Override
	public UserAccount get(Long id) {
		UserAccount entity = dao.getById(id);
		return entity;
	}

	@Override
	public void create(UserAccount userAccount, UserProfile userProfile) {
		Validate.isTrue(userAccount.getId() == null,
				"This method should be called for 'not saved yet' userAccount only. Use UPDATE instead");
		// userAccount.setUserProfile(userProfile);
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
		LOGGER.debug("Remove all user userRoles");
		dao.deleteAll();
	}

	@Override
	public Long getCount() {
		return dao.getCount();
	}

	@Override
	public List<UserAccount> getAll() {
		return dao.getAll();
	}

}
