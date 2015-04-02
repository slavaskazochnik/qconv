package by.parfen.disptaxi.dataaccess.impl;

import by.parfen.disptaxi.dataaccess.CustomerDao;
import by.parfen.disptaxi.datamodel.Customer;

public class CustomerDaoImpl extends AbstractDaoImpl<Long, Customer> implements CustomerDao {

	protected CustomerDaoImpl() {
		super(Customer.class);
	}

}
