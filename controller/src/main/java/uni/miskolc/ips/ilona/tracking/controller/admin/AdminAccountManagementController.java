package uni.miskolc.ips.ilona.tracking.controller.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import uni.miskolc.ips.ilona.tracking.controller.model.ExecutionResultDTO;
import uni.miskolc.ips.ilona.tracking.controller.model.UserBaseDetailsDTO;
import uni.miskolc.ips.ilona.tracking.controller.model.UserSecurityDetails;
import uni.miskolc.ips.ilona.tracking.controller.util.ValidateUserData;
import uni.miskolc.ips.ilona.tracking.controller.util.WebpageInformationProvider;
import uni.miskolc.ips.ilona.tracking.model.UserData;
import uni.miskolc.ips.ilona.tracking.service.UserAndDeviceService;
import uni.miskolc.ips.ilona.tracking.util.TrackingModuleCentralManager;
import uni.miskolc.ips.ilona.tracking.util.validate.ValidityStatusHolder;

import java.util.ArrayList;
import java.util.Date;

@Controller
@RequestMapping(value = "/tracking/admin")
public class AdminAccountManagementController {

    private static Logger logger = LogManager.getLogger(AdminAccountManagementController.class);

    @Autowired
    private UserAndDeviceService userAndDeviceService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TrackingModuleCentralManager centralManager;

    @RequestMapping(value = "/accountmanagement", method = {RequestMethod.POST})
    public ModelAndView createAdminAccountManagementpage() {
        ModelAndView mav = new ModelAndView("tracking/admin/accountManagement");

        mav.addObject("useridRestriction", WebpageInformationProvider.getUseridRestrictionMessage());
        mav.addObject("usernameRestriction", WebpageInformationProvider.getUsernameRestrictionMessage());
        mav.addObject("passwordRestriction", WebpageInformationProvider.getPasswordRestrictionMessage());
        mav.addObject("emailRestriction", WebpageInformationProvider.getEmailRestrictionMessage());
        mav.addObject("enabledRestriction", WebpageInformationProvider.getEnabledcreationmessage());
        mav.addObject("userRoleRestriction", WebpageInformationProvider.getUserrolecreationmessage());

        mav.addObject("useridPattern", WebpageInformationProvider.getUseridpattern());
        mav.addObject("usernamePattern", WebpageInformationProvider.getUsernamepattern());
        mav.addObject("passwordPattern", WebpageInformationProvider.getPasswordpattern());

        UserSecurityDetails userDetails = (UserSecurityDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        mav.addObject("useridValue", userDetails.getUserid());
        mav.addObject("usernameValue", userDetails.getUsername());
        mav.addObject("emailValue", userDetails.getEmail());

        return mav;
    }

    /**
     * DOC! 100: OK 200: parameter error 300: validity error 400: service error
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/accountchangepassword", method = {RequestMethod.POST})
    @ResponseBody
    public ExecutionResultDTO changeAccountPasswordHandler(@ModelAttribute() UserBaseDetailsDTO user) {
        ExecutionResultDTO result = new ExecutionResultDTO(100, new ArrayList<String>());
        ValidityStatusHolder errors = new ValidityStatusHolder();

        if (user == null) {
            logger.info("Invalid parameter!");
            result.addMessage("User is null!");
            result.setResponseState(200);
            return result;
        }
        try {
            errors.appendValidityStatusHolder(ValidateUserData.validateRawPassword(user.getPassword()));
            errors.appendValidityStatusHolder(ValidateUserData.validateUserid(user.getUserid()));

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
        String hashedPassword = null;
        Date credentialsValidity = null;
        try {
            UserData userData = userAndDeviceService.getUser(userDetails.getUserid());
            hashedPassword = passwordEncoder.encode(user.getPassword());
            userData.setPassword(hashedPassword);
            credentialsValidity = new Date(new Date().getTime() + this.centralManager.getCredentialsValidityPeriod());
            userData.setCredentialNonExpiredUntil(credentialsValidity);
            userAndDeviceService.updateUser(userData);
        } catch (Exception e) {
            logger.error("Service error! Cause: " + e.getMessage());
            result.addMessage("Service error!");
            result.setResponseState(400);
            return result;
        }
        userDetails.setPassword(hashedPassword);
        userDetails.setCredentialNonExpiredUntil(credentialsValidity);
        result.addMessage("Password modification is complete!");
        return result;
    }

    @RequestMapping(value = "/accountchangedetails", method = {RequestMethod.POST})
    @ResponseBody
    public ExecutionResultDTO changeAccountDetailsHandler(@ModelAttribute() UserBaseDetailsDTO user) {

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
            errors.appendValidityStatusHolder(ValidateUserData.validateEmail(user.getEmail()));

            if (!errors.isValid()) {
                result.setMessages(errors.getErrors());
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
            UserData userData = userAndDeviceService.getUser(userDetails.getUserid());
            userData.setEmail(user.getEmail());
            userData.setUsername(user.getUsername());
            userAndDeviceService.updateUser(userData);
        } catch (Exception e) {
            logger.error("Service error! Cause: " + e.getMessage());
            result.addMessage("Service error!");
            result.setResponseState(400);
            return result;
        }

        userDetails.setEmail(user.getEmail());
        userDetails.setUsername(user.getUsername());

        result.addMessage("Account has been updated!");
        return result;

    }

    public void setUserDeviceService(UserAndDeviceService userDeviceService) {
        this.userAndDeviceService = userDeviceService;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void setCentralManager(TrackingModuleCentralManager centralManager) {
        this.centralManager = centralManager;
    }

}
