package by.parfen.disptaxi.datamodel;

import java.util.Date;
import java.util.List;

public class Person {

	private Long id;
	private String firstName;
	private String lastName;
	private String telNum;
	private Date dCreate;
	
	//private List<Driver> drivers;
	//private List<Customer> customers;
	//private List<UsersAccount> usersAccounts;
	
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


}
