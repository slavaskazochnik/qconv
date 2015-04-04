package by.parfen.disptaxi.services;

import org.springframework.transaction.annotation.Transactional;

import by.parfen.disptaxi.datamodel.AppRole;
import by.parfen.disptaxi.datamodel.UserRole;

public interface UserRoleService {

	UserRole get(Long id);

	@Transactional
	void create(UserRole userRole, AppRole appRole);

	@Transactional
	void update(UserRole userRole);

	@Transactional
	void delete(UserRole userRole);

	@Transactional
	void deleteAll();
}
