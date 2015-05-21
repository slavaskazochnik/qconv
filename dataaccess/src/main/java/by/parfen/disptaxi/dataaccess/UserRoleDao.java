package by.parfen.disptaxi.dataaccess;

import java.util.List;

import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.datamodel.UserRole;

public interface UserRoleDao extends AbstractDao<Long, UserRole> {

	Long getCount();

	List<UserRole> getAllByUserProfile(UserProfile userProfile);
}
