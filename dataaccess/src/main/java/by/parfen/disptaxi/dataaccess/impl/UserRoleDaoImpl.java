package by.parfen.disptaxi.dataaccess.impl;

import org.springframework.stereotype.Repository;

import by.parfen.disptaxi.dataaccess.UserRoleDao;
import by.parfen.disptaxi.datamodel.UserRole;

@Repository
public class UserRoleDaoImpl extends AbstractDaoImpl<Long, UserRole> implements UserRoleDao {

	protected UserRoleDaoImpl() {
		super(UserRole.class);
	}
}
