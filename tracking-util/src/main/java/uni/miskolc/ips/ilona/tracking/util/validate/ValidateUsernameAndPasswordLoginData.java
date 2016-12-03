package uni.miskolc.ips.ilona.tracking.util.validate;

import java.util.regex.Pattern;



/**
 * This class is responsible for validating the UsernameAndPasswordLoginData.
 * 
 * @author Patrik / A5USL0
 *
 * @see UsernameAndPasswordLoginData
 *      {@link uni.miskolc.ips.ilona.tracking.model.ValidateUsernameAndPasswordLoginData}
 *      dsd
 */
public class ValidateUsernameAndPasswordLoginData {

	private static String useridPattern = "^[a-zA-Z]{1}[a-zA-Z0-9]{4,19}";

	private static String passwordPattern = "[a-z-A-Z\\.?!-_]{6,30}";

	public ValidateUsernameAndPasswordLoginData() {

	}
/* 
	public static ValidityStatusHolder ValidateLoginData(UsernameAndPasswordLoginData logindata) {
		ValidityStatusHolder holder = new ValidityStatusHolder();
		if (logindata == null) {
			holder.addValidityError("the password is null");
			return holder;
		}
		
		holder.appendValidityStatusHolder(ValidateUserid(logindata.getUserid()));
		
		holder.appendValidityStatusHolder(ValidatePassword(logindata.getPassword()));

		return holder;
	}
*/
	public static ValidityStatusHolder ValidateUserid(String userid) {
		ValidityStatusHolder holder = new ValidityStatusHolder();
		if (userid == null) {
			holder.addValidityError("The userid is null!");
			return holder;
		}
		
		if(userid.equals("")) {
			holder.addValidityError("The userid is empty!");
			return holder;
		}
		
		if(userid.length() < 5) {
			holder.addValidityError("The userid is less than 5 characters!");
		}
		
		if (userid.length() > 20) {
			holder.addValidityError("The userid is too long! ");
		}
		if (Pattern.matches(useridPattern, userid) == false) {
			holder.addValidityError("The userid format is invalid!");
		}
		return holder;
	}

	public static ValidityStatusHolder ValidatePassword(String password) {
		ValidityStatusHolder holder = new ValidityStatusHolder();
		if (password == null) {
			holder.addValidityError("The password cannot be null!");
			return holder;
		}
		
		if(password.equals("")) {
			holder.addValidityError("The password is empty!");
			return holder;
		}

		if (password.length() < 6) {
			holder.addValidityError("The password length is less  than 6 characters!");
		}
		
		if (password.length() > 30) {
			holder.addValidityError("The password length is too long!");
		}

		if (Pattern.matches(passwordPattern, password) == false) {
			holder.addValidityError("The password format is invalid!");
		}
		return holder;
	}
}
