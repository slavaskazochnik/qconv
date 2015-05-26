package by.parfen.disptaxi.services.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.parfen.disptaxi.dataaccess.UserProfileDao;
import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.datamodel.filter.FilterUserProfile;
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
	public Long getCount() {
		LOGGER.debug("Count user profiles");
		return dao.getCount();
	}

	@Override
	public void deleteAll() {
		LOGGER.debug("Remove all user profiles");
		dao.deleteAll();
	}

	@Override
	public List<UserProfile> getAll() {
		return dao.getAll();
	}

	@Override
	public List<UserProfile> getAllByFirstName(String firstName) {
		return dao.getAllByFirstName(firstName);
	}

	@Override
	public List<UserProfile> getAll(SingularAttribute<UserProfile, ?> attr, boolean ascending, int startRecord,
			int pageSize) {
		return dao.getAll(attr, ascending, startRecord, pageSize);
	}

	@Override
	public List<UserProfile> getAll(SingularAttribute<UserProfile, ?> attr, boolean ascending, int startRecord,
			int pageSize, FilterUserProfile filterUserProfile) {
		return dao.getAll(attr, ascending, startRecord, pageSize, filterUserProfile);
	}

	@Override
	public Long getCount(FilterUserProfile filterUserProfile) {
		return dao.getCount(filterUserProfile);
	}

}
