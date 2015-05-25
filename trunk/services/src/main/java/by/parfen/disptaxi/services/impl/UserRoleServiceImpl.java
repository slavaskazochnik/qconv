package by.parfen.disptaxi.services.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.parfen.disptaxi.dataaccess.UserRoleDao;
import by.parfen.disptaxi.datamodel.UserProfile;
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
		LOGGER.info("Instance of UserRoleService is created. Class is: {}", getClass().getName());
	}

	@Override
	public UserRole get(Long id) {
		UserRole entity = dao.getById(id);
		return entity;
	}

	@Override
	public void create(UserRole userRole, UserProfile userProfile) {
		Validate.isTrue(userRole.getId() == null,
				"This method should be called for 'not saved yet' userRole only. Use UPDATE instead");
		LOGGER.debug("set userProfile: {}", userProfile);
		userRole.setUserProfile(userProfile);
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
		LOGGER.debug("Remove all user user roles");
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

	@Override
	public List<UserRole> getAllByUserProfile(UserProfile userProfile) {
		return dao.getAllByUserProfile(userProfile);
	}

	@Override
	public UserRole getWithDetails(UserRole userRole) {
		return dao.getWithDetails(userRole);
	}

}
