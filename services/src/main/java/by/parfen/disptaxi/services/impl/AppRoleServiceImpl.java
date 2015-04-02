package by.parfen.disptaxi.services.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import by.parfen.disptaxi.dataaccess.AppRoleDao;
import by.parfen.disptaxi.datamodel.AppRole;
import by.parfen.disptaxi.services.AppRoleService;

@Service
public class AppRoleServiceImpl implements AppRoleService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppRoleServiceImpl.class);

	@Inject
	private AppRoleDao dao;

	@PostConstruct
	private void init() {
		// this method will be called by Spring after bean instantiation. Can be
		// used for any initialization process.
		LOGGER.info("Instance of AppRoleService is created. Class is: {}", getClass().getName());
	}

	@Override
	public AppRole get(Long id) {
		AppRole entity = dao.getById(id);
		return entity;
	}

	@Override
	public void saveOrUpdate(AppRole appRole) {
		if (appRole.getId() == null) {
			LOGGER.debug("Save new: {}", appRole);
			dao.insert(appRole);
		} else {
			LOGGER.debug("Update: {}", appRole);
			dao.update(appRole);
		}
	}

	@Override
	public void delete(AppRole appRole) {
		LOGGER.debug("Remove: {}", appRole);
		dao.delete(appRole.getId());

	}

	@Override
	public void deleteAll() {
		LOGGER.debug("Remove all app roles");
		dao.deleteAll();
	}

}
