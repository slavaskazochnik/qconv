package by.parfen.disptaxi.dataaccess;

import by.parfen.disptaxi.datamodel.AppRole;

public interface AppRoleDao extends AbstractDao<Long, AppRole> {

	Long getCount();
}
