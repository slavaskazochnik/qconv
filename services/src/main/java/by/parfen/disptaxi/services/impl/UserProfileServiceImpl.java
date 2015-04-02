package by.parfen.disptaxi.services.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.parfen.disptaxi.dataaccess.UserProfileDao;
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.services.UserProfileService;

@Service
public class UserProfileServiceImpl implements UserProfileService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileServiceImpl.class);

	@Inject
	private UserProfileDao dao;

	@PostConstruct
	private void init() {
		// this method will be called by Spring after bean instantiation. Can be
		// used for any initialization process.
		LOGGER.info("Instance of UserProfileService is created. Class is: {}", getClass().getName());
	}

	@Override
	public UserProfile get(Long id) {
		UserProfile entity = dao.getById(id);
		return entity;
	}

	@Override
	public void saveOrUpdate(UserProfile userProfile) {
		if (userProfile.getId() == null) {
			LOGGER.debug("Save new: {}", userProfile);
			dao.insert(userProfile);
		} else {
			LOGGER.debug("Update: {}", userProfile);
			dao.update(userProfile);
		}
	}

	@Override
	public void delete(UserProfile userProfile) {
		LOGGER.debug("Remove: {}", userProfile);
		dao.delete(userProfile.getId());
	}

	@Override
	public void deleteAll() {
		LOGGER.debug("Remove all user profiles");
		dao.deleteAll();
	}

}
