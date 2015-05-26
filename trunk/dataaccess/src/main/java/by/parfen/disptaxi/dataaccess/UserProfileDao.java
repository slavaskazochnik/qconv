package by.parfen.disptaxi.dataaccess;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import by.parfen.disptaxi.datamodel.UserProfile;
import by.parfen.disptaxi.datamodel.filter.FilterUserProfile;

public interface UserProfileDao extends AbstractDao<Long, UserProfile> {

	Long getCount();

	Long getCount(FilterUserProfile filterUserProfile);

	List<UserProfile> getAll(SingularAttribute<UserProfile, ?> attr, boolean ascending, int startRecord, int pageSize);

	List<UserProfile> getAll(SingularAttribute<UserProfile, ?> attr, boolean ascending, int startRecord, int pageSize,
			FilterUserProfile filterUserProfile);

	List<UserProfile> getAllByFirstName(String firstName);

}
