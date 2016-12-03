package uni.miskolc.ips.ilona.tracking.controller.util;

import uni.miskolc.ips.ilona.tracking.util.validate.ValidityStatusHolder;

public class ValidateExecutionParameters {

	private static long accountExpirationTimeMin = 2678400000L; // one month
	private static long accountExpirationTimeMax = 3 * 31536000000L; // 3 years

	private static long credentialsValidityPeriodMin = 2678400000L; // one month
	private static long credentialsValidityPeriodMax = 3 * 31536000000L; // 3
																			// years

	private static int badLoginsUpperBoundMin = 3;
	private static int badLoginsUpperBoundMax = 20;

	private static long lockTimeAfterBadLoginsMin = 60000L; // one minute
	private static long lockTimeAfterBadLoginsMax = 2678400000L; // one month

	private static long passwordRevoceryTokenValidityMin = 10 * 60000; // 10
																		// minutes
	private static long passwordRevoceryTokenValidityMax = 7 * 86400000; // one
																			// week

	public static ValidityStatusHolder validateAccountExpiration(long time) {
		ValidityStatusHolder errors = new ValidityStatusHolder();
		if (time < accountExpirationTimeMin) {
			errors.addValidityError("Account expiration time is less than expected!");
		}

		if (time > accountExpirationTimeMax) {
			errors.addValidityError("Account expiration time is more than expected!");
		}

		return errors;
	}

	public static ValidityStatusHolder validateCredentialsValidityPeriod(long time) {
		ValidityStatusHolder errors = new ValidityStatusHolder();
		if (time < credentialsValidityPeriodMin) {
			errors.addValidityError("Credentials validity period is less than expected!");
		}

		if (time > credentialsValidityPeriodMax) {
			errors.addValidityError("Credentials validity period is more than expected!");
		}

		return errors;
	}

	public static ValidityStatusHolder validateBadLoginsUpperBound(int max) {
		ValidityStatusHolder errors = new ValidityStatusHolder();
		if (max < badLoginsUpperBoundMin) {
			errors.addValidityError("Bad logins upper bound is less than expected!");
		}

		if (max > badLoginsUpperBoundMax) {
			errors.addValidityError("Bad logins upper bound is more than expected!");
		}

		return errors;
	}

	public static ValidityStatusHolder validateLockTimeAfterBadLogins(long time) {
		ValidityStatusHolder errors = new ValidityStatusHolder();
		if (time < lockTimeAfterBadLoginsMin) {
			errors.addValidityError("Lock time after bad logins is less than expected!");
		}

		if (time > lockTimeAfterBadLoginsMax) {
			errors.addValidityError("Lock time after bad logins is more than expected!");
		}

		return errors;
	}
	
	public static ValidityStatusHolder validatePasswordRecoveryTokenValidity(long time) {
		ValidityStatusHolder errors = new ValidityStatusHolder();
		if (time < passwordRevoceryTokenValidityMin) {
			errors.addValidityError("Password recovery token time is less than expected!");
		}

		if (time > passwordRevoceryTokenValidityMax) {
			errors.addValidityError("Password recovery token time is more than expected!");
		}

		return errors;
	}
	
}
