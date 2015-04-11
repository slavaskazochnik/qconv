package by.parfen.disptaxi.datamodel;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import by.parfen.disptaxi.datamodel.enums.AppRole;

@Entity
public class UserRole extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "userRole")
	private UserAccount userAccount;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity = UserProfile.class)
	private UserProfile userProfile;

	@Column
	@Enumerated(EnumType.ORDINAL)
	private AppRole appRole;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public AppRole getAppRole() {
		return appRole;
	}

	public void setAppRole(AppRole appRole) {
		this.appRole = appRole;
	}

	@Override
	public String toString() {
		return "UserRole [id=" + id + "]";
	}

}
