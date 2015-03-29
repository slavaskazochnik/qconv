package by.parfen.disptaxi.services.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import by.parfen.disptaxi.dataaccess.AppRoleDao;
import by.parfen.disptaxi.datamodel.AppRole;
import by.parfen.disptaxi.services.AppRoleService;

@Service
public class AppRoleServiceImpl implements AppRoleService {

	@Inject
	private AppRoleDao dao;

	@Override
	public AppRole get(Long id) {
		AppRole entity = dao.getById(id);
		return entity;
	}
	
	@Override
	public void save(AppRole appRole) {
		// TODO Auto-generated method stub
		dao.save(appRole);
	}
}
