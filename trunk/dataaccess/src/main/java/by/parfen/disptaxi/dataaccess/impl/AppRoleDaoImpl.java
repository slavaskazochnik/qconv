package by.parfen.disptaxi.dataaccess.impl;

import org.springframework.stereotype.Repository;

import by.parfen.disptaxi.dataaccess.AppRoleDao;
import by.parfen.disptaxi.datamodel.AppRole;

@Repository
public class AppRoleDaoImpl extends AbstractDaoImpl<Long, AppRole> implements AppRoleDao {

	protected AppRoleDaoImpl() {
		super(AppRole.class);
	}

}
