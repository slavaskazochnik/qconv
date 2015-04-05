package by.parfen.disptaxi.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import by.parfen.disptaxi.datamodel.UserAccount;
import by.parfen.disptaxi.datamodel.UserProfile;

public interface UserAccountService {

	UserAccount get(Long id);

	@Transactional
	void create(UserAccount userAccount, UserProfile userProfile);

	@Transactional
	void update(UserAccount userAccount);

	@Transactional
	void delete(UserAccount userAccount);

	@Transactional
	void deleteAll();

	Long getCount();

	List<UserAccount> getAll();

}
