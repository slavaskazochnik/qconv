package by.parfen.disptaxi.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.datamodel.UserProfile;

public interface CustomerService {

	Customer get(Long id);

	@Transactional
	void create(Customer customer, UserProfile userProfile);

	@Transactional
	void update(Customer customer);

	@Transactional
	void delete(Customer customer);

	@Transactional
	void deleteAll();

	Long getCount();

	List<Customer> getAll();

}
