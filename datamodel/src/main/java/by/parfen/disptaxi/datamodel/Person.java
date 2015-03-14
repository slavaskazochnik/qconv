package by.parfen.disptaxi.datamodel;

import java.util.Date;
import java.util.List;

public class Person {

	private Long id;
	private String firstName;
	private String lastName;
	private String telNum;
	private Date dCreate;
	
	private List drivers;
	private List customers;
	private List usersAccounts;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getTelNum() {
		return telNum;
	}
	public void setTelNum(String telNum) {
		this.telNum = telNum;
	}
	public Date getdCreate() {
		return dCreate;
	}
	public void setdCreate(Date dCreate) {
		this.dCreate = dCreate;
	}

	public List getDrivers() {
		return drivers;
	}
	public void setDrivers(List drivers) {
		this.drivers = drivers;
	}
	public List getCustomers() {
		return customers;
	}
	public void setCustomers(List customers) {
		this.customers = customers;
	}
	public List getUsersAccounts() {
		return usersAccounts;
	}
	public void setUsersAccounts(List usersAccounts) {
		this.usersAccounts = usersAccounts;
	}
	

}
