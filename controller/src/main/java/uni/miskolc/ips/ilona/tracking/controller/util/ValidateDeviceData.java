package uni.miskolc.ips.ilona.tracking.controller.util;

import java.util.regex.Pattern;

import uni.miskolc.ips.ilona.tracking.util.validate.ValidityStatusHolder;

public class ValidateDeviceData {

	private static String deviceidPattern = "^(?=.{5,50}$)^([a-zA-Z0-9]{1}[_:-]{0,1}){1,49}[a-zA-Z0-9]{1}$";

	private static String deviceNamePattern = "^(?=.{5,50}$)^([a-zA-Z0-9]{1}[ ]{0,1}){1,49}[a-zA-Z0-9]{1}$";

	private static String deviceTypePattern = "^[a-zA-Z]{1,20}$";

	private static String deviceTypeNamePattern = "^(?=.{1,30}$)^([a-zA-Z0-9]{1}[_ -]{0,1}){1,29}[a-zA-Z0-9]{1}$";

	public static ValidityStatusHolder validateDeviceid(String deviceid) {
		ValidityStatusHolder errors = new ValidityStatusHolder();

		if (deviceid == null) {
			errors.addValidityError("The deviceid is empty!");
			return errors;
		}

		if (deviceid.equals("")) {
			errors.addValidityError("The deviceid is empty!");
			return errors;
		}

		if (deviceid.length() < 5) {
			errors.addValidityError("The deviceid length is too short!");
		}

		if (deviceid.length() > 50) {
			errors.addValidityError("The deviceid length is too long!");
		}

		if (!Pattern.matches(deviceidPattern, deviceid)) {
			errors.addValidityError("The deviceid format is not valid!");
		}
		return errors;
	}

	public static ValidityStatusHolder validateDeviceName(String deviceName) {
		ValidityStatusHolder errors = new ValidityStatusHolder();

		if (deviceName == null) {
			errors.addValidityError("The device name is empty!");
			return errors;
		}

		if (deviceName.equals("")) {
			errors.addValidityError("The device name is empty!");
			return errors;
		}

		if (deviceName.length() < 5) {
			errors.addValidityError("The device name length is too short!");
		}

		if (deviceName.length() > 50) {
			errors.addValidityError("The device name length is too long!");
		}

		if (!Pattern.matches(deviceNamePattern, deviceName)) {
			errors.addValidityError("The device name format is not valid!");
		}
		return errors;
	}

	public static ValidityStatusHolder validateDeviceType(String deviceType) {
		ValidityStatusHolder errors = new ValidityStatusHolder();

		if (deviceType == null) {
			errors.addValidityError("The device type is empty!");
			return errors;
		}

		if (deviceType.equals("")) {
			errors.addValidityError("The device type is empty!");
			return errors;
		}

		if (deviceType.length() < 1) {
			errors.addValidityError("The device type length is too short!");
		}

		if (deviceType.length() > 20) {
			errors.addValidityError("The device type length is too long!");
		}

		if (!Pattern.matches(deviceTypePattern, deviceType)) {
			errors.addValidityError("The device type format is not valid!");
		}
		return errors;
	}

	public static ValidityStatusHolder validateDeviceTypeName(String deviceTypeName) {
		ValidityStatusHolder errors = new ValidityStatusHolder();

		if (deviceTypeName == null) {
			errors.addValidityError("The device type name is empty!");
			return errors;
		}

		if (deviceTypeName.equals("")) {
			errors.addValidityError("The device type name is empty!");
			return errors;
		}

		if (deviceTypeName.length() < 1) {
			errors.addValidityError("The device type name length is too short!");
		}

		if (deviceTypeName.length() > 30) {
			errors.addValidityError("The device type name length is too long!");
		}

		if (!Pattern.matches(deviceTypeNamePattern, deviceTypeName)) {
			errors.addValidityError("The device type name format is not valid!");
		}
		return errors;
	}
}
