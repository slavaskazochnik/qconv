package by.parfen.disptaxi.dataaccess;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import by.parfen.disptaxi.datamodel.UserProfile;

public interface UserProfileDao extends AbstractDao<Long, UserProfile> {

	Long getCount();

	List<UserProfile> getAll(SingularAttribute<UserProfile, ?> attr, boolean ascending, int startRecord, int pageSize);

	List<UserProfile> getAllByFirstName(String firstName);

}
