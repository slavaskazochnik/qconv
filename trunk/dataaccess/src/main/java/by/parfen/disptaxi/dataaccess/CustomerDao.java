package by.parfen.disptaxi.dataaccess;

import java.util.List;

import by.parfen.disptaxi.datamodel.Customer;

public interface CustomerDao extends AbstractDao<Long, Customer> {

	Long getCount();

	List<Customer> getAllWithDetails();
}
