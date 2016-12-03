package uni.miskolc.ips.ilona.tracking.controller.model;

public class UserBaseDetailsDTO {

	private String userid;

	private String username;

	private String password;

	private String email;

	private boolean enabled;

	private boolean adminRole;

	public UserBaseDetailsDTO() {

	}

	public UserBaseDetailsDTO(String userid, String username, String password, String email, boolean enabled,
			boolean adminRole) {
		super();
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.email = email;
		this.enabled = enabled;
		this.adminRole = adminRole;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAdminRole() {
		return adminRole;
	}

	public void setAdminRole(boolean adminRole) {
		this.adminRole = adminRole;
	}

	@Override
	public String toString() {
		return "UserRegistrationDTO [userid=" + userid + ", username=" + username + ", password=" + password
				+ ", email=" + email + ", enabled=" + enabled + ", adminRole=" + adminRole + "]";
	}

}
