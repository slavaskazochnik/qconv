package by.parfen.disptaxi.dataaccess;

import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import by.parfen.disptaxi.datamodel.Customer;

public interface CustomerDao extends AbstractDao<Long, Customer> {

	Long getCount();

	List<Customer> getAllWithDetails();

	List<Customer> getAllWithDetails(SingularAttribute<Customer, ?> attr, boolean ascending, int startRecord, int pageSize);

	Customer getWithDetails(Customer customer);
}
