package by.parfen.disptaxi.services.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.parfen.disptaxi.dataaccess.UserRoleDao;
import by.parfen.disptaxi.datamodel.AppRole;
import by.parfen.disptaxi.datamodel.UserRole;
import by.parfen.disptaxi.services.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleServiceImpl.class);

	@Inject
	private UserRoleDao dao;

	@PostConstruct
	private void init() {
		// this method will be called by Spring after bean instantiation. Can be
		// used for any initialization process.
		LOGGER.info("Instance of UserProfileService is created. Class is: {}", getClass().getName());
	}

	@Override
	public UserRole get(Long id) {
		UserRole entity = dao.getById(id);
		return entity;
	}

	@Override
	public void create(UserRole userRole, AppRole appRole) {
		Validate.isTrue(userRole.getId() == null,
				"This method should be called for 'not saved yet' appRole only. Use UPDATE instead");
		userRole.setAppRole(appRole);
		LOGGER.debug("Insert: {}", userRole);
		dao.insert(userRole);
	}

	@Override
	public void update(UserRole userRole) {
		LOGGER.debug("Update: {}", userRole);
		dao.update(userRole);
	}

	@Override
	public void delete(UserRole userRole) {
		LOGGER.debug("Remove: {}", userRole);
		dao.delete(userRole.getId());
	}

	@Override
	public void deleteAll() {
		LOGGER.debug("Remove all user userRoles");
		dao.deleteAll();
	}

	@Override
	public Long getCount() {
		return dao.getCount();
	};

	@Override
	public List<UserRole> getAll() {
		return dao.getAll();
	}
}
