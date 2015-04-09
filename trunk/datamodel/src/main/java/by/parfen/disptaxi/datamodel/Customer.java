package by.parfen.disptaxi.datamodel;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@MapsId
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(updatable = false, name = "id")
	private UserProfile userProfile;

	@Column
	private Long avgRating;
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

	public Long getAvgRating() {
		return avgRating;
	}

	public void setAvgRating(Long avgRating) {
		this.avgRating = avgRating;
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

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", userProfile=" + userProfile + ", avgRating=" + avgRating + ", signActive="
				+ signActive + ", dCreate=" + dCreate + "]";
	}

}