package by.parfen.disptaxi.services;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import org.springframework.transaction.annotation.Transactional;

import by.parfen.disptaxi.datamodel.UserProfile;

public interface UserProfileService {

	UserProfile get(Long id);

	@Transactional
	void saveOrUpdate(UserProfile userProfile);

	@Transactional
	void delete(UserProfile userProfile);

	@Transactional
	void deleteAll();

	Long getCount();

	List<UserProfile> getAll();

	List<UserProfile> getAllByFirstName(String firstName);

	List<UserProfile> getAll(SingularAttribute<UserProfile, ?> attr, boolean ascending, int startRecord, int pageSize);
}
