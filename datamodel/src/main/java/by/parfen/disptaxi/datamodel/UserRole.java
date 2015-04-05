package by.parfen.disptaxi.datamodel;


//@Entity
public class UserRole {

	// @Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// @ManyToOne(fetch = FetchType.LAZY, targetEntity = UserProfile.class)
	// @MapsId
	// @OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,
	// CascadeType.MERGE })
	// @JoinColumn(updatable = false, name = "id")
	private UserProfile userProfile;
	// @ManyToOne(fetch = FetchType.LAZY, targetEntity = AppRole.class)
	// @MapsId
	// @OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,
	// CascadeType.MERGE })
	// @JoinColumn(updatable = false, name = "id")
	private AppRole appRole;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
