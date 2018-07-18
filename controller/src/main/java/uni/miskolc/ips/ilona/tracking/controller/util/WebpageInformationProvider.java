package uni.miskolc.ips.ilona.tracking.controller.util;

public class WebpageInformationProvider {

	/*
	 * Titles
	 */

	private static final String useridTitle = "The userid can contain the following characters:";

	private static final String usernameTitle = "The username can contain the following characters:";

	private static final String passwordTitle = "The password can contain the following characters:";

	/*
	 * Restrictions
	 * 
	 * USER:
	 */

	private static final String useridRestrictionMessage = "<b>The userid maximum length is 20 characters!</b><br><br><b>"
			+ "The minimum length is 5 characters!</b><br><br>The userid can contaion letters from the english alphabet,"
			+ "any other letters are restricted like é á ő ű ú ö ü ó<br><br>The userid must <b>start with</b>"
			+ "a letter <b>a-z or A-Z</b> <br><br> after that can contain the following letters: a-z, A-Z, 0-9"
			+ "<br><br> The userid cannot contain any special characters like ? : - ! %";

	private static final String usernameRestrictionMessage = "<b>The username maximum length is 30 characters!"
			+ "</b><br><br><b>The minimum length is 5 characters!</b><br><br>"
			+ "The username can contain any english alphabet letters and these letters:<br><br>  öüóőúéáű and ÖÜÓŐÚŰÁÉ";

	private static final String passwordRestrictionMessage = "<b>The password maximum length is 30 characters!"
			+ "</b><br><br><b>The minimum length is 6 characters!</b>";

	private static final String emailRestrictionMessage = "The email address must be a valid address!";

	private static final String enabledCreationMessage = "If true, then the user will be enabled!";

	private static final String UserRoleCreationMessage = "If true, then the user will be admin!";

	/*
	 * Device:
	 * =========================================================================
	 */

	private static final String deviceidRestrictionMessage = "<b>The deviceid maximum length is 50 characters!</b><br/><br/>"
			+ "<b>The minimum length is 5 characters!</b><br><br>The deviceid can contain the following characters:<br/><br/>"
			+ "a-z A-Z 0-9 : - _  <br><br>" + "Restrictions: <br>"
			+ "a-z A-Z 0-9 These characters can occur multiple times, the id must start and end with one of these characters! <br/><br/>"
			+ ": _ - The following characters can occur multiple times, but the id cannot start and end with these characters and"
			+ "these characters cannot stay side by side!";

	private static final String deviceNameRestrictionMessage = "<b>The device name maximum length is 50 characters!</b><br/><br/>"
			+ "<b>The minimum length is 5 characters!</b><br><br>The device name can contain the following characters:<br>"
			+ "a-z A-Z 0-9 and can contain space, but two spaces cannot stand next to the other!";

	private static final String deviceTypeRestrictionMessage = "<b>The device type maximum length is 20 characters!</b><br/><br/>"
			+ "<b>The minimum length is 1 character!</b><br><br>The device type can contain the following characters:<br>"
			+ "a-z A-Z";

	private static final String deviceTypeNameRestrictionMessage = "<b>The device type name maximum length is 30 characters!</b><br/>"
			+ "<b>The minimum length is 1 character!</b><br><br>The device type name can contain the following characters:<br>"
			+ "a-z A-Z 0-9 - _ and (space) <br><br>" + "Restrictions: <br><br>"
			+ "a-z A-Z 0-9 These characters can occur multiple times, the type name must start and end with one of these characters! <br><br>"
			+ "_- (space) The following characters can occur multiple times, but the id cannot start with these characters and"
			+ "these characters cannot stay side by side!";

	/*
	 * Field patterns!
	 */

	private static final String useridPattern = "^[a-zA-Z][a-zA-Z0-9]{4,19}$";

	private static final String usernamePattern = "^[a-zA-ZöüóőúéáűÖÜÓŐÚŰÁÉ][a-zA-ZöüóőúéáűÖÜÓŐÚŰÁÉ0-9]{4,30}$";

	private static final String passwordPattern = "^.{5,30}$";

	private static final String emailPattern = null; // This value is restrited
														// by the default
														// pattern?!

	private static final String deviceidPattern = "^(?=.{5,50}$)([a-zA-Z0-9]{1}[_:-]{0,1}){1,49}[a-zA-Z0-9]{1}$";

	private static final String deviceNamePattern = "^(?=.{5,50}$)([a-zA-Z0-9]{1}[ ]{0,1}){1,49}[a-zA-Z0-9]{1}$";

	private static final String deviceTypePattern = "^[a-zA-Z]{1,20}$";

	private static final String deviceTypeNamePattern = "^(?=.{1,30}$)([a-zA-Z0-9]{1}[_- ]{0,1}){1,29}[a-zA-Z0-9]{1}$";

	public static String getUseridRestrictionMessage() {
		return useridRestrictionMessage;
	}

	public static String getUsernameRestrictionMessage() {
		return usernameRestrictionMessage;
	}

	public static String getPasswordRestrictionMessage() {
		return passwordRestrictionMessage;
	}

	public static String getEmailRestrictionMessage() {
		return emailRestrictionMessage;
	}

	public static String getEnabledcreationmessage() {
		return enabledCreationMessage;
	}

	public static String getUserrolecreationmessage() {
		return UserRoleCreationMessage;
	}

	public static String getUseridpattern() {
		return useridPattern;
	}

	public static String getUsernamepattern() {
		return usernamePattern;
	}

	public static String getPasswordpattern() {
		return passwordPattern;
	}

	public static String getDeviceidrestrictionmessage() {
		return deviceidRestrictionMessage;
	}

	public static String getDevicenamerestrictionmessage() {
		return deviceNameRestrictionMessage;
	}

	public static String getDevicetyperestrictionmessage() {
		return deviceTypeRestrictionMessage;
	}

	public static String getDevicetypenamerestrictionmessage() {
		return deviceTypeNameRestrictionMessage;
	}

	public static String getDeviceidpattern() {
		return deviceidPattern;
	}

	public static String getDevicenamepattern() {
		return deviceNamePattern;
	}

	public static String getDevicetypepattern() {
		return deviceTypePattern;
	}

	public static String getDevicetypenamepattern() {
		return deviceTypeNamePattern;
	}

}
