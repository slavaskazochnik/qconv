package by.parfen.disptaxi.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import by.parfen.disptaxi.datamodel.UserAccount;
import by.parfen.disptaxi.datamodel.UserRole;

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

	Long getCount();

	List<UserAccount> getAll();

}
