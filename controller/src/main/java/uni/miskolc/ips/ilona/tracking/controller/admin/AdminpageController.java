package uni.miskolc.ips.ilona.tracking.controller.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uni.miskolc.ips.ilona.tracking.controller.model.ExecutionResultDTO;
import uni.miskolc.ips.ilona.tracking.controller.model.UserBaseDetailsDTO;
import uni.miskolc.ips.ilona.tracking.controller.util.ValidateUserData;
import uni.miskolc.ips.ilona.tracking.controller.util.WebpageInformationProvider;
import uni.miskolc.ips.ilona.tracking.model.DeviceData;
import uni.miskolc.ips.ilona.tracking.model.UserData;
import uni.miskolc.ips.ilona.tracking.service.UserAndDeviceService;
import uni.miskolc.ips.ilona.tracking.service.exceptions.DuplicatedUserException;
import uni.miskolc.ips.ilona.tracking.util.TrackingModuleCentralManager;
import uni.miskolc.ips.ilona.tracking.util.validate.ValidityStatusHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Patrik / A5USL0
 */
@Controller
@RequestMapping(value = "/tracking/admin")
public class AdminpageController {

    private static Logger logger = LogManager.getLogger(AdminpageController.class);

    @Autowired
    private UserAndDeviceService userDeviceService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TrackingModuleCentralManager trackingModuleCentralManager;

    @RequestMapping(value = "/homepage", method = {RequestMethod.POST})
    public ModelAndView createAdminHomepage() {
        ModelAndView mav = new ModelAndView("tracking/admin/mainpage");

        return mav;
    }

    @RequestMapping(value = "/createuser", method = {RequestMethod.POST})
    public ModelAndView createAdminCreateAccountpage() {
        ModelAndView mav = new ModelAndView("tracking/admin/createuser");

        mav.addObject("useridRestriction", WebpageInformationProvider.getUseridRestrictionMessage());
        mav.addObject("usernameRestriction", WebpageInformationProvider.getUsernameRestrictionMessage());
        mav.addObject("passwordRestriction", WebpageInformationProvider.getPasswordRestrictionMessage());
        mav.addObject("emailRestriction", WebpageInformationProvider.getEmailRestrictionMessage());
        mav.addObject("enabledRestriction", WebpageInformationProvider.getEnabledcreationmessage());
        mav.addObject("userRoleRestriction", WebpageInformationProvider.getUserrolecreationmessage());

        mav.addObject("useridPattern", WebpageInformationProvider.getUseridpattern());
        mav.addObject("usernamePattern", WebpageInformationProvider.getUsernamepattern());
        mav.addObject("passwordPattern", WebpageInformationProvider.getPasswordpattern());
        return mav;
    }

    @RequestMapping(value = "registeruser", method = {RequestMethod.POST})
    @ResponseBody
    public ExecutionResultDTO registerUserByAdmin(@ModelAttribute() UserBaseDetailsDTO user) {
        ExecutionResultDTO result = new ExecutionResultDTO(100, new ArrayList<String>());

        if (user == null) {
            result.addMessage("User is null!");
            result.setResponseState(200);
            return result;
        }
        try {
            ValidityStatusHolder errors = new ValidityStatusHolder();
            errors.appendValidityStatusHolder(ValidateUserData.validateUserid(user.getUserid()));
            errors.appendValidityStatusHolder(ValidateUserData.validateUsername(user.getUsername()));
            errors.appendValidityStatusHolder(ValidateUserData.validateRawPassword(user.getPassword()));
            errors.appendValidityStatusHolder(ValidateUserData.validateEmail(user.getEmail()));

            if (!errors.isValid()) {
                result.setMessages(errors.getErrors());
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
            List<String> userRoles = new ArrayList<String>();
            userRoles.add("ROLE_USER");
            if (user.isAdminRole() == true) {
                userRoles.add("ROLE_ADMIN");
            }
            List<Date> badLogins = new ArrayList<Date>();
            List<DeviceData> devices = new ArrayList<DeviceData>();
            Date credentialsValidUntil = new Date(new Date().getTime() + trackingModuleCentralManager.getCredentialsValidityPeriod());
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            UserData userFull = new UserData(user.getUserid(), user.getUsername(), user.getEmail(), hashedPassword,
                    user.isEnabled(), userRoles, new Date(), credentialsValidUntil, new Date(), true, badLogins,
                    devices);
            userDeviceService.createUser(userFull);
        } catch (DuplicatedUserException e) {
            result.addMessage("This userid is already reserved! ID: " + user.getUserid());
            result.setResponseState(600);
            return result;
        } catch (Exception e) {
            result.addMessage("Service error!");
            result.setResponseState(400);
            return result;
        }
        result.addMessage("The account has been created!");
        return result;
    }

    /*
     *
     */
    @RequestMapping(value = "/createusercreationpage", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView createAdminpageCreateUserpage() {
        ModelAndView createUserPage = new ModelAndView("tracking/adminCreateuser");
        return createUserPage;
    }

    @RequestMapping(value = "/adminhomepage", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getAdminHomepage() {
        return new ModelAndView("tracking/homepage");
    }

    @RequestMapping(value = "/adminsettings", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getAdminSettingspage() {
        return new ModelAndView("tracking/adminSettings");
    }

    @RequestMapping(value = "/deleteuserbyid", method = {RequestMethod.GET, RequestMethod.POST})
    public String deleteUserById(@RequestParam(value = "userid", required = false) String userid) {
        if (userid != null) {
            // hiba visszadobás

        }
        System.out.println(userid);
        return "redirect:/tracking/listallusers";
    }

    public ModelAndView mavModellSetup(ModelAndView mav) {
        mav.addObject("useridRestriction", WebpageInformationProvider.getUseridRestrictionMessage());
        mav.addObject("usernameRestriction", WebpageInformationProvider.getUsernameRestrictionMessage());
        mav.addObject("passwordRestriction", WebpageInformationProvider.getPasswordRestrictionMessage());
        mav.addObject("emailRestriction", WebpageInformationProvider.getEmailRestrictionMessage());
        mav.addObject("enabledRestriction", WebpageInformationProvider.getEnabledcreationmessage());
        mav.addObject("userRoleRestriction", WebpageInformationProvider.getUserrolecreationmessage());

        mav.addObject("useridPattern", WebpageInformationProvider.getUseridpattern());
        mav.addObject("usernamePattern", WebpageInformationProvider.getUsernamepattern());
        mav.addObject("passwordPattern", WebpageInformationProvider.getPasswordpattern());

        return mav;
    }

    public void setUserDeviceService(UserAndDeviceService userDeviceService) {
        this.userDeviceService = userDeviceService;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void setTrackingModuleCentralManager(TrackingModuleCentralManager trackingModuleCentralManager) {
        this.trackingModuleCentralManager = trackingModuleCentralManager;
    }

}
