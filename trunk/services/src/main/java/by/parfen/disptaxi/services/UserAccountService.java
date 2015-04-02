package by.parfen.disptaxi.services;

import org.springframework.transaction.annotation.Transactional;

import by.parfen.disptaxi.datamodel.UserAccount;

public interface UserAccountService {

	UserAccount get(Long id);

	@Transactional
	void saveOrUpdate(UserAccount userAccount);

	@Transactional
	void delete(UserAccount userAccount);

	@Transactional
	void deleteAll();
}
