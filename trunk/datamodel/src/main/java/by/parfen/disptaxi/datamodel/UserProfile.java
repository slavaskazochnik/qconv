package by.parfen.disptaxi.datamodel;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class UserProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String firstName;
	@Column
	private String lastName;
	@Column
	private String telNum;
	@Column
	private Date dCreate;

	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_profile_id") }, inverseJoinColumns = { @JoinColumn(name = "app_role_id") })
	@ManyToMany(targetEntity = AppRole.class, fetch = FetchType.LAZY)
	private Set<AppRole> appRoles;

	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_profile_id") }, inverseJoinColumns = { @JoinColumn(name = "user_account_id") })
	@ManyToMany(targetEntity = UserAccount.class, fetch = FetchType.LAZY)
	private Set<UserAccount> userAccounts;

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

	public Set<AppRole> getAppRoles() {
		return appRoles;
	}

	public void setAppRoles(Set<AppRole> appRoles) {
		this.appRoles = appRoles;
	}

	public Set<UserAccount> getUserAccounts() {
		return userAccounts;
	}

	public void setUserAccounts(Set<UserAccount> userAccounts) {
		this.userAccounts = userAccounts;
	}

	@Override
	public String toString() {
		return "UserProfile [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", telNum=" + telNum
				+ ", dCreate=" + dCreate + "]";
	}

}
