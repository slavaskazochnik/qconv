package by.parfen.disptaxi.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import by.parfen.disptaxi.datamodel.AppRole;

public interface AppRoleService {

	AppRole get(Long id);

	@Transactional
	void create(AppRole appRole);
	
	@Transactional
	void update(AppRole appRole);

	@Transactional
	void delete(AppRole appRole);

	@Transactional
	void deleteAll();

	@Transactional
	void fillByEnumRoles();

	Long getCount();

	List<AppRole> getAll();
}