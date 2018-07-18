package uni.miskolc.ips.ilona.tracking.persist.mysql;

import java.util.List;

import uni.miskolc.ips.ilona.tracking.model.UserDetails;

public interface TrackingUserMapper {

	public void createUser(UserDetails user);

	public UserDetails getUser(String userID);

	public void updateUser(UserDetails user);

	public void deleteUser(String userID);

	public void insertUserRoles(UserDetails user);

	public List<UserDetails> getAllUsers();
}
