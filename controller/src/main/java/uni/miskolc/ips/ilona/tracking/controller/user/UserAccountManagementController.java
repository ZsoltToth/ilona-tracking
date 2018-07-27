package uni.miskolc.ips.ilona.tracking.controller.user;

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
import uni.miskolc.ips.ilona.tracking.controller.exception.UserChangeDetailsExecutionException;
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
@RequestMapping(value = "/tracking/user")
public class UserAccountManagementController {

    private static Logger logger = LogManager.getLogger(UserAccountManagementController.class);

    @Autowired
    private UserAndDeviceService userAndDeviceService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TrackingModuleCentralManager trackingModuleCentralManager;

    @RequestMapping(value = "/accountmanagement", method = {RequestMethod.POST})
    public ModelAndView createAccountManagementHandler() {
        ModelAndView mav = new ModelAndView("tracking/user/accountManagement");
        fillModelAndViewPageDetails(mav);
        return mav;
    }

    /**
     * DOC! 100: OK 200: parameter 300: validation error 400: service error
     *
     * @param newUserDetails
     * @return
     * @throws UserChangeDetailsExecutionException
     */
    @RequestMapping(value = "/accountmanagement/changeuserdetails", method = {RequestMethod.POST})
    @ResponseBody
    public ExecutionResultDTO changerUserDetailsHandler(@ModelAttribute() UserBaseDetailsDTO newUserDetails) {

        ExecutionResultDTO result = new ExecutionResultDTO(100, new ArrayList<String>());
        ValidityStatusHolder errors = new ValidityStatusHolder();

        /*
         * Request null check provided by the spring with default constructor?!
         */
        if (newUserDetails == null) {
            result.addMessage("Invalid parameter!");
            result.setResponseState(200);
            return result;
        }

        try {
            errors.appendValidityStatusHolder(ValidateUserData.validateUsername(newUserDetails.getUsername()));
            errors.appendValidityStatusHolder(ValidateUserData.validateEmail(newUserDetails.getEmail()));

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

        /*
         * Logged in user details
         */
        UserSecurityDetails userDetails = (UserSecurityDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        /*
         * Validation
         */

        /*
         * Update User data
         */
        try {
            UserData updatedUserDetails = userAndDeviceService.getUser(userDetails.getUserid());
            updatedUserDetails.setUsername(newUserDetails.getUsername());
            updatedUserDetails.setEmail(newUserDetails.getEmail());
            userAndDeviceService.updateUser(updatedUserDetails);

        } catch (Exception e) {
            logger.error("Service error! Cause: " + e.getMessage());
            result.addMessage("Service error!");
            result.setResponseState(400);
            return result;
        }

        /*
         * Update principal
         */
        userDetails.setEmail(newUserDetails.getEmail());
        userDetails.setUsername(newUserDetails.getUsername());

        /*
         * Return with the updated data.
         */
        result.addMessage("The account has been modified!");
        return result;

    }

    /**
     * DOC! 100: OK 200: parameter / authority error 300: validity error 400:
     * service error
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/accountmanagement/changepassword", method = {RequestMethod.POST})
    @ResponseBody
    public ExecutionResultDTO changeUserPasswordHandler(@ModelAttribute() UserBaseDetailsDTO user) {

        ExecutionResultDTO result = new ExecutionResultDTO(100, new ArrayList<String>());

        if (user == null) {
            result.addMessage("Invalid parameter!");
            result.setResponseState(200);
            return result;
        }
        try {
            ValidityStatusHolder valErrors = new ValidityStatusHolder();
            valErrors.appendValidityStatusHolder(ValidateUserData.validateRawPassword(user.getPassword()));
            if (!valErrors.isValid()) {
                result.setMessages(valErrors.getErrors());
                result.setResponseState(300);
                return result;
            }
        } catch (Exception e) {
            logger.error("Service error! Cause: " + e.getMessage());
            result.addMessage("Service error!");
            result.setResponseState(400);
            return result;
        }

        UserSecurityDetails userDetails = (UserSecurityDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        String hashedPassword = null;
        Date credentialsValidity = null;
        try {
            UserData updatedUserDetails = userAndDeviceService.getUser(userDetails.getUserid());
            hashedPassword = passwordEncoder.encode(user.getPassword());
            updatedUserDetails.setPassword(hashedPassword);
            credentialsValidity = new Date(new Date().getTime() + this.trackingModuleCentralManager.getCredentialsValidityPeriod());
            updatedUserDetails.setCredentialNonExpiredUntil(credentialsValidity);
            userAndDeviceService.updateUser(updatedUserDetails);
        } catch (Exception e) {
            logger.error("Service error! Cause: " + e.getMessage());
            result.addMessage("Service error!");
            result.setResponseState(400);
            return result;
        }

        /*
         * Update principal
         */
        userDetails.setPassword(hashedPassword);
        userDetails.setCredentialNonExpiredUntil(credentialsValidity);
        /*
         * Account management page with updated details
         */
        result.addMessage("The update was successful!");
        return result;
    }

    /**
     * @param mav
     */
    private void fillModelAndViewPageDetails(ModelAndView mav) {

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

    }

    public void setUserAndDeviceService(UserAndDeviceService userAndDeviceService) {
        this.userAndDeviceService = userAndDeviceService;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void setTrackingModuleCentralManager(TrackingModuleCentralManager trackingModuleCentralManager) {
        this.trackingModuleCentralManager = trackingModuleCentralManager;
    }

}
