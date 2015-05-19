package by.parfen.disptaxi.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import by.parfen.disptaxi.datamodel.UserAccount;
import by.parfen.disptaxi.datamodel.UserRole;
import by.parfen.disptaxi.datamodel.enums.AppRole;

public interface UserAccountService {

	UserAccount get(Long id);

	@Transactional
	void create(UserAccount userAccount, UserRole userRole);

	@Transactional
	void update(UserAccount userAccount);

	@Transactional
	void delete(UserAccount userAccount);

	@Transactional
	void deleteAll();

	List<UserAccount> getAll();

	UserAccount getAccountByEmail(String userName);

	List<AppRole> getRoles(Long userAccountId);
}
