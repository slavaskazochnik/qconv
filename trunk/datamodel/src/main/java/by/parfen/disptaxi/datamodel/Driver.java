package by.parfen.disptaxi.datamodel;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class Driver {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private Long avgRating;
	@Column
	private Long signActive;
	@Column
	private Date dCreate;

	@MapsId
	@OneToOne(/* fetch = FetchType.LAZY, */cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(updatable = false, name = "id")
	private UserProfile userProfile;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserProfile getPerson() {
		return userProfile;
	}

	public void setPerson(UserProfile userProfile) {
		this.userProfile = userProfile;
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

	@Override
	public String toString() {
		return "Driver [id=" + id + ", avgRating=" + avgRating + ", signActive=" + signActive + ", dCreate=" + dCreate
				+ ", userProfile=" + userProfile + "]";
	}

}
