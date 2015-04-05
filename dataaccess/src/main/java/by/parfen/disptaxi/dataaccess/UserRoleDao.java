package by.parfen.disptaxi.dataaccess;

import by.parfen.disptaxi.datamodel.UserRole;

public interface UserRoleDao extends AbstractDao<Long, UserRole> {

	Long getCount();
}
