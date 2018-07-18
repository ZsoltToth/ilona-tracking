package uni.miskolc.ips.ilona.tracking.controller.admin;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import uni.miskolc.ips.ilona.tracking.controller.model.ExecutionResultDTO;
import uni.miskolc.ips.ilona.tracking.controller.model.UserDeviceDataDTO;
import uni.miskolc.ips.ilona.tracking.controller.util.ValidateDeviceData;
import uni.miskolc.ips.ilona.tracking.controller.util.ValidateUserData;
import uni.miskolc.ips.ilona.tracking.controller.util.WebpageInformationProvider;
import uni.miskolc.ips.ilona.tracking.model.DeviceData;
import uni.miskolc.ips.ilona.tracking.model.UserData;
import uni.miskolc.ips.ilona.tracking.service.UserAndDeviceService;
import uni.miskolc.ips.ilona.tracking.service.exceptions.DeviceNotFoundException;
import uni.miskolc.ips.ilona.tracking.service.exceptions.DuplicatedDeviceException;
import uni.miskolc.ips.ilona.tracking.service.exceptions.UserNotFoundException;
import uni.miskolc.ips.ilona.tracking.util.validate.ValidityStatusHolder;

@Controller
@RequestMapping(value = "/tracking/admin")
public class AdminManageDevicesController {

	private static Logger logger = LogManager.getLogger(AdminManageDevicesController.class);

	@Resource(name = "UserAndDeviceService")
	private UserAndDeviceService userAndDeviceService;

	@RequestMapping(value = "/updatedevicedetails", method = { RequestMethod.POST })
	@ResponseBody
	public ExecutionResultDTO updateUserDeviceDetails(@ModelAttribute() UserDeviceDataDTO device) {
		ExecutionResultDTO result = new ExecutionResultDTO(100, new ArrayList<String>());
		System.out.println(device.toString());
		try {
			ValidityStatusHolder errors = new ValidityStatusHolder();
			errors.appendValidityStatusHolder(ValidateUserData.validateUserid(device.getUserid()));
			errors.appendValidityStatusHolder(ValidateDeviceData.validateDeviceid(device.getDeviceid()));
			errors.appendValidityStatusHolder(ValidateDeviceData.validateDeviceName(device.getDeviceName()));
			errors.appendValidityStatusHolder(ValidateDeviceData.validateDeviceType(device.getDeviceType()));
			errors.appendValidityStatusHolder(ValidateDeviceData.validateDeviceTypeName(device.getDeviceTypeName()));

			if (!errors.isValid()) {
				result.addMessage("Invalid device property format!");
				result.setResponseState(300);
				return result;
			}
		} catch (Exception e) {
			logger.error("Service error! Cause: " + e.getMessage());
			result.addMessage("Service error!");
			result.setResponseState(400);
			return result;
		}

		try {
			UserData user = userAndDeviceService.getUser(device.getUserid());
			DeviceData newDevice = new DeviceData();
			newDevice.setDeviceid(device.getDeviceid());
			newDevice.setDeviceName(device.getDeviceName());
			newDevice.setDeviceType(device.getDeviceType());
			newDevice.setDeviceTypeName(device.getDeviceTypeName());
			userAndDeviceService.updateDevice(newDevice, user);

		} catch (DeviceNotFoundException e) {
			logger.error("Device not found! Cause: " + e.getMessage());
			result.addMessage("Device not found!");
			result.setResponseState(600);
			return result;
		} catch (Exception e) {
			logger.error("Service error! Cause: " + e.getMessage());
			result.addMessage("Service error!");
			result.setResponseState(400);
			return result;
		}
		result.addMessage("Device updated!");
		return result;
	}

	@RequestMapping(value = "/deleteuserdevice", method = { RequestMethod.POST })
	@ResponseBody
	public ExecutionResultDTO deleteUserDevice(@ModelAttribute() UserDeviceDataDTO device) {
		ExecutionResultDTO result = new ExecutionResultDTO(100, new ArrayList<String>());

		try {
			ValidityStatusHolder errors = new ValidityStatusHolder();
			errors.appendValidityStatusHolder(ValidateUserData.validateUserid(device.getUserid()));
			errors.appendValidityStatusHolder(ValidateDeviceData.validateDeviceid(device.getDeviceid()));

			if (!errors.isValid()) {
				result.addMessage("Invalid parameter!");
				result.setResponseState(200);
				return result;
			}
		} catch (Exception e) {
			logger.error("Service error! Cause: " + e.getMessage());
			result.addMessage("Service error!");
			result.setResponseState(400);
			return result;
		}

		try {
			UserData user = userAndDeviceService.getUser(device.getUserid());
			DeviceData deletableDevice = new DeviceData();
			deletableDevice.setDeviceid(device.getDeviceid());
			deletableDevice.setDeviceName(device.getDeviceName());
			deletableDevice.setDeviceType(device.getDeviceType());
			deletableDevice.setDeviceTypeName(device.getDeviceTypeName());
			userAndDeviceService.deleteDevice(deletableDevice, user);

		} catch (UserNotFoundException e) {
			logger.error("User not found! Cause: " + e.getMessage());
			result.addMessage("User not found!");
			result.setResponseState(600);
			return result;
		} catch (DeviceNotFoundException e) {
			logger.error("Device not found! Cause: " + e.getMessage());
			result.addMessage("Device not found!");
			result.setResponseState(700);
			return result;
		} catch (Exception e) {
			logger.error("Service error! Cause: " + e.getMessage());
			result.addMessage("Service error!");
			result.setResponseState(400);
			return result;
		}
		result.addMessage("Device deleted!");
		return result;
	}

	@RequestMapping(value = "/createnewdeviceforuser", method = { RequestMethod.POST })
	@ResponseBody
	public ExecutionResultDTO createDeviceForUserCreateDeviceRequestHandler(
			@ModelAttribute() UserDeviceDataDTO newDevice) {

		ExecutionResultDTO result = new ExecutionResultDTO(100, new ArrayList<String>());
		if (newDevice == null) {
			result.addMessage("User is null!");
			result.setResponseState(200);
			return result;
		}
		try {
			ValidityStatusHolder errors = new ValidityStatusHolder();
			errors.appendValidityStatusHolder(ValidateDeviceData.validateDeviceid(newDevice.getDeviceid()));
			errors.appendValidityStatusHolder(ValidateDeviceData.validateDeviceName(newDevice.getDeviceName()));
			errors.appendValidityStatusHolder(ValidateDeviceData.validateDeviceType(newDevice.getDeviceType()));
			errors.appendValidityStatusHolder(ValidateDeviceData.validateDeviceTypeName(newDevice.getDeviceTypeName()));

			if (!errors.isValid()) {
				result.setMessages(errors.getErrors());
				result.setResponseState(300);
				return result;
			}
		} catch (Exception e) {
			logger.error("Serice error! Cause: " + e.getMessage());
			result.addMessage("Service error!");
			result.setResponseState(400);
			return result;
		}

		try {
			UserData user = userAndDeviceService.getUser(newDevice.getUserid());
			DeviceData device = new DeviceData();
			device.setDeviceid(newDevice.getDeviceid());
			device.setDeviceName(newDevice.getDeviceName());
			device.setDeviceType(newDevice.getDeviceType());
			device.setDeviceTypeName(newDevice.getDeviceTypeName());
			userAndDeviceService.storeDevice(device, user);
		} catch (UserNotFoundException e) {
			result.addMessage("User not found with ID: " + newDevice.getUserid());
			result.setResponseState(600);
			return result;
		} catch (DuplicatedDeviceException e) {
			result.addMessage("A device is already exists with ID: " + newDevice.getDeviceid());
			result.setResponseState(700);
			return result;
		} catch (Exception e) {
			logger.error("Service error! Cause: " + e.getMessage());
			result.addMessage("Service error!");
			result.setResponseState(400);
			return result;
		}

		result.addMessage("The device has been created!");
		return result;
	}

	@RequestMapping(value = "/userdevcreatenewdevicepagerequest", method = { RequestMethod.POST })
	public ModelAndView createDeviceForUserRequestHandler(@RequestParam("userid") String userid) {
		ModelAndView mav = new ModelAndView("tracking/admin/createNewDevice");
		mav.addObject("deviceOwnerid", userid);
		fillModelAndViewWithCreateDeviceData(mav);
		return mav;
	}

	private ModelAndView fillModelAndViewWithCreateDeviceData(ModelAndView mav) {
		mav.addObject("deviceidRestriction", WebpageInformationProvider.getDeviceidrestrictionmessage());
		mav.addObject("deviceNameRestriction", WebpageInformationProvider.getDevicenamerestrictionmessage());
		mav.addObject("deviceTypeRestriction", WebpageInformationProvider.getDevicetyperestrictionmessage());
		mav.addObject("deviceTypeNameRestriction", WebpageInformationProvider.getDevicetypenamerestrictionmessage());

		mav.addObject("deviceidPattern", WebpageInformationProvider.getDeviceidpattern());
		mav.addObject("deviceNamePattern", WebpageInformationProvider.getDevicenamepattern());
		mav.addObject("deviceTypePattern", WebpageInformationProvider.getDevicetypepattern());
		mav.addObject("deviceTypeNamePattern", WebpageInformationProvider.getDevicetypenamepattern());

		return mav;
	}

	public void setUserAndDeviceService(UserAndDeviceService userAndDeviceService) {
		this.userAndDeviceService = userAndDeviceService;
	}

}
