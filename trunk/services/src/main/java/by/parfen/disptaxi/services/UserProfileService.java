package by.parfen.disptaxi.services;

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
}
