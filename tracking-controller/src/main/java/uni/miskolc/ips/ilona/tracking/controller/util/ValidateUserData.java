package uni.miskolc.ips.ilona.tracking.controller.util;

import java.util.regex.Pattern;

import uni.miskolc.ips.ilona.tracking.util.validate.ValidityStatusHolder;

public class ValidateUserData {

	private static String useridPattern = "^[a-zA-Z]{1}[a-zA-Z0-9]{4,19}$";
	private static String userNamePattern = "^[a-zA-ZöüóőúéáűÖÜÓŐÚŰÁÉ][a-zA-ZöüóőúéáűÖÜÓŐÚŰÁÉ0-9]{4,30}$";
	private static String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

	public static ValidityStatusHolder validateUserid(String userid) {
		ValidityStatusHolder errors = new ValidityStatusHolder();

		if (userid == null) {
			errors.addValidityError("The current userid is empty!");
			return errors;
		}

		if (userid.equals("")) {
			errors.addValidityError("The userid is empty!");
			return errors;
		}

		if (userid.length() < 5) {
			errors.addValidityError("The userid is too short!");
		}

		if (userid.length() > 20) {
			errors.addValidityError("The userid is too long!");
		}

		if (!Pattern.matches(useridPattern, userid)) {
			errors.addValidityError("The userid format is not valid!");

		}
		return errors;
	}

	public static ValidityStatusHolder validateUsername(String username) {
		ValidityStatusHolder errors = new ValidityStatusHolder();

		if (username == null) {
			errors.addValidityError("The username is empty!");
			return errors;
		}

		if (username.equals("")) {
			errors.addValidityError("The username is empty!");
			return errors;
		}

		if (username.length() < 5) {
			errors.addValidityError("The username is too short!");
		}

		if (username.length() > 30) {
			errors.addValidityError("The username is too long!");
		}

		if (!Pattern.matches(userNamePattern, username)) {
			errors.addValidityError("The username format is not valid!");
		}
		return errors;
	}

	public static ValidityStatusHolder validateRawPassword(String password) {
		ValidityStatusHolder errors = new ValidityStatusHolder();
		if(password == null) {
			errors.addValidityError("The password is empty!");
			return errors;
		}
		if(!Pattern.matches(WebpageInformationProvider.getPasswordpattern(), password)) {
			errors.addValidityError("Invalid password!");
		}
		return errors;
	}

	public static ValidityStatusHolder validateEmail(String email) {

		ValidityStatusHolder errors = new ValidityStatusHolder();

		if (email == null) {
			errors.addValidityError("The email is empty!");
			return errors;
		}

		if (email.equals("")) {
			errors.addValidityError("The email is empty!");
			return errors;
		}

		if (!Pattern.matches(emailPattern, email)) {
			errors.addValidityError("The email format is not valid!");
		}
		return errors;
	}
}