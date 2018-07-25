package uni.miskolc.ips.ilona.tracking.controller.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import uni.miskolc.ips.ilona.tracking.controller.model.ExecutionResultDTO;
import uni.miskolc.ips.ilona.tracking.controller.model.UserDeviceDataDTO;
import uni.miskolc.ips.ilona.tracking.controller.model.UserSecurityDetails;
import uni.miskolc.ips.ilona.tracking.controller.util.ValidateDeviceData;
import uni.miskolc.ips.ilona.tracking.controller.util.WebpageInformationProvider;
import uni.miskolc.ips.ilona.tracking.model.DeviceData;
import uni.miskolc.ips.ilona.tracking.model.UserData;
import uni.miskolc.ips.ilona.tracking.service.UserAndDeviceService;
import uni.miskolc.ips.ilona.tracking.service.exceptions.DeviceNotFoundException;
import uni.miskolc.ips.ilona.tracking.service.exceptions.DuplicatedDeviceException;
import uni.miskolc.ips.ilona.tracking.util.validate.ValidityStatusHolder;

import java.util.ArrayList;
import java.util.Collection;

@Controller
@RequestMapping(value = "/tracking/user")
public class UserManageDevicesController {

    private static Logger logger = LogManager.getLogger(UserManageDevicesController.class);

    @Autowired
    private UserAndDeviceService userAndDeviceService;

    @RequestMapping(value = "/managedevices")
    public ModelAndView createDeviceManagementpageHandler() {
        ModelAndView mav = new ModelAndView("tracking/user/manageDevices");
        Collection<DeviceData> devices = new ArrayList<>();
        try {
            UserSecurityDetails userDetails = (UserSecurityDetails) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            UserData user = userAndDeviceService.getUser(userDetails.getUserid());
            devices = userAndDeviceService.readUserDevices(user);
            fillModelAndViewWithCreateDeviceData(mav);
            mav.addObject("devices", devices);
            mav.addObject("deviceOwnerid", userDetails.getUserid());
            mav.addObject("deviceOwnerName", userDetails.getUsername());
            return mav;

        } catch (Exception e) {
            logger.error("Service error: " + e.getMessage());
            mav.addObject("executionError", "Service error!");
            return mav;
        }
    }

    /**
     * DOC! 100: OK 200: parameter error 400: service error 600: device not
     * found
     *
     * @param deletableDevice
     * @return
     */
    @RequestMapping(value = "/mandevdeletedevice", method = {RequestMethod.POST})
    @ResponseBody
    public ExecutionResultDTO processDeleteDeviceRequestHandler(@ModelAttribute() UserDeviceDataDTO deletableDevice) {

        ExecutionResultDTO result = new ExecutionResultDTO(100, new ArrayList<String>());

        try {
            ValidityStatusHolder errors = new ValidityStatusHolder();
            errors.appendValidityStatusHolder(ValidateDeviceData.validateDeviceid(deletableDevice.getDeviceid()));
        } catch (Exception e) {
            logger.info("Invalid deviceid " + deletableDevice.getDeviceid());
            result.addMessage("Invalid deviceid!");
            result.setResponseState(200);
            return result;
        }

        UserSecurityDetails userDetails = (UserSecurityDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        try {
            UserData owner = userAndDeviceService.getUser(userDetails.getUserid());
            DeviceData dev = new DeviceData();
            dev.setDeviceid(deletableDevice.getDeviceid());
            dev.setDeviceName(deletableDevice.getDeviceName());
            dev.setDeviceType(deletableDevice.getDeviceType());
            dev.setDeviceTypeName(deletableDevice.getDeviceTypeName());
            userAndDeviceService.deleteDevice(dev, owner);
        } catch (DeviceNotFoundException e) {
            result.addMessage("Device not found!");
            result.setResponseState(600);
            return result;
        } catch (Exception e) {
            result.addMessage("Service error!");
            result.setResponseState(400);
            return result;
        }
        result.addMessage("Device has been deleted!");
        return result;
    }

    /**
     * DOC! 100: OK 200: parameter error 300: validation error 400: service
     * error 600: not found
     *
     * @param device
     * @return
     */
    @RequestMapping(value = "/mandevupdatedevicedetails", method = {RequestMethod.POST})
    @ResponseBody
    public ExecutionResultDTO processUpdateUserDeviceDetailsRequestHandler(@ModelAttribute() UserDeviceDataDTO device) {

        ExecutionResultDTO result = new ExecutionResultDTO(100, new ArrayList<String>());
        try {
            ValidityStatusHolder errors = new ValidityStatusHolder();
            errors.appendValidityStatusHolder(ValidateDeviceData.validateDeviceid(device.getDeviceid()));
            errors.appendValidityStatusHolder(ValidateDeviceData.validateDeviceName(device.getDeviceName()));
            errors.appendValidityStatusHolder(ValidateDeviceData.validateDeviceType(device.getDeviceType()));
            errors.appendValidityStatusHolder(ValidateDeviceData.validateDeviceTypeName(device.getDeviceTypeName()));

            if (!errors.isValid()) {
                result.setMessages(errors.getErrors());
                result.setResponseState(300);
                return result;
            }
        } catch (Exception e) {
            result.addMessage("Service error!");
            result.setResponseState(400);
            return result;
        }

        UserSecurityDetails userDetails = (UserSecurityDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        try {
            UserData user = userAndDeviceService.getUser(userDetails.getUserid());
            DeviceData dev = new DeviceData();
            dev.setDeviceid(device.getDeviceid());
            dev.setDeviceName(device.getDeviceName());
            dev.setDeviceType(device.getDeviceType());
            dev.setDeviceTypeName(device.getDeviceTypeName());
            userAndDeviceService.updateDevice(dev, user);

        } catch (DeviceNotFoundException e) {
            result.addMessage("Device not found!");
            result.setResponseState(600);
            return result;
        } catch (Exception e) {
            result.addMessage("Service error!");
            result.setResponseState(400);
            return result;
        }
        result.addMessage("Device modification was successfull!");
        return result;
    }

    @RequestMapping(value = "/createdevicepage", method = {RequestMethod.POST})
    public ModelAndView createUserCreateDevicepageHandler() {
        ModelAndView mav = new ModelAndView("tracking/user/createDevice");
        UserSecurityDetails userDetails = (UserSecurityDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        fillModelAndViewWithCreateDeviceData(mav);
        mav.addObject("deviceOwnerid", userDetails.getUserid());

        return mav;
    }

    /**
     * DOC! 100: OK 200: parameter / authorization error 300: validity error
     * 400: service error 600: duplicated device
     *
     * @param newDevice
     * @return
     */
    @RequestMapping(value = "/createnewdevice", method = {RequestMethod.POST})
    @ResponseBody
    public ExecutionResultDTO createUserDeviceHandler(@ModelAttribute UserDeviceDataDTO newDevice) {
        ExecutionResultDTO result = new ExecutionResultDTO(100, new ArrayList<String>());

        if (newDevice == null) {
            logger.error("Invalid parameter: input is null!");
            result.addMessage("Invalid parameter!");
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
            logger.error("Service error! Error: " + e.getMessage());
            result.addMessage("Service error!");
            result.setResponseState(400);
            return result;
        }

        UserSecurityDetails userDetails = (UserSecurityDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        try {
            UserData user = userAndDeviceService.getUser(userDetails.getUserid());
            DeviceData dev = new DeviceData();
            dev.setDeviceid(newDevice.getDeviceid());
            dev.setDeviceName(newDevice.getDeviceName());
            dev.setDeviceType(newDevice.getDeviceType());
            dev.setDeviceTypeName(newDevice.getDeviceTypeName());
            userAndDeviceService.storeDevice(dev, user);
        } catch (DuplicatedDeviceException e) {
            result.addMessage("Device already exists with id: " + newDevice.getDeviceid());
            result.setResponseState(600);
            return result;
        } catch (Exception e) {
            result.addMessage("Service error, device storage has failed!");
            return result;
        }

        result.addMessage("Device has been created successfully!");
        return result;
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
