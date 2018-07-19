package uni.miskolc.ips.ilona.tracking.util.validate;

import java.util.regex.Pattern;

/**
 * 
 * @author Patrik / A5USL0
 *
 */
public class ValidateUserDetails {

	private static String useridPattern = "[a-zA-Z]{1}[a-zA-Z0-9]{0-9}";
	private static String userNamePattern = "[a-zA-Z]{1}[a-zA-Z0-9]{0-9}";
	private static String emailPattrern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
/*
	public static void validateUserDetails(UserDetails userdetails) {

		boolean isUseridValid = Pattern.matches(useridPattern, userdetails.getUserid());

		boolean isUserNameValid = ValidateUserDetails.validateUserName(userdetails.getUsername());

	}

	/**
	 * 
	 * @param userid
	 * @return
	 */
	public static boolean validateUserid(String userid) {
		return Pattern.matches(useridPattern, userid);
	}

	/**
	 * 
	 * @param username
	 * @return
	 */
	public static boolean validateUserName(String username) {
		return Pattern.matches(userNamePattern, username);
	}

	/**
	 * 
	 * @param email
	 * @return
	 */
	public static boolean validateEmail(String email) {
		return Pattern.matches(emailPattrern, email);
	}

}
