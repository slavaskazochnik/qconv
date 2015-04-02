package by.parfen.disptaxi.datamodel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class UserAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String name;
	@Column
	private String passw;
	@Column
	private String email;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = AppRole.class)
	private AppRole appRole;
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = UserProfile.class)
	private UserProfile userProfile;
	@Column
	private Long signActive;
	@Column
	private Date dCreate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassw() {
		return passw;
	}

	public void setPassw(String passw) {
		this.passw = passw;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AppRole getAppRole() {
		return appRole;
	}

	public void setAppRole(AppRole appRole) {
		this.appRole = appRole;
	}

	public UserProfile getPerson() {
		return userProfile;
	}

	public void setPerson(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public Long getSignActive() {
		return signActive;
	}

	public void setSignActive(Long signActive) {
		this.signActive = signActive;
	}

	public Date getdCreate() {
		return dCreate;
	}

	public void setdCreate(Date dCreate) {
		this.dCreate = dCreate;
	}

}
