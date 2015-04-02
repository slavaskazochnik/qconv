package by.parfen.disptaxi.services;

import org.springframework.transaction.annotation.Transactional;

import by.parfen.disptaxi.datamodel.Driver;
import by.parfen.disptaxi.datamodel.UserProfile;

public interface DriverService {

	Driver get(Long id);

	@Transactional
	void create(Driver driver, UserProfile userProfile);

	@Transactional
	void update(Driver driver);

	@Transactional
	void delete(Driver driver);

	@Transactional
	void deleteAll();
}
