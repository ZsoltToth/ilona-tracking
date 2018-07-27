package uni.miskolc.ips.ilona.tracking.controller.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uni.miskolc.ips.ilona.tracking.controller.exception.InvalidParameterException;
import uni.miskolc.ips.ilona.tracking.controller.exception.TrackingServiceErrorException;
import uni.miskolc.ips.ilona.tracking.controller.model.ExecutionResultDTO;
import uni.miskolc.ips.ilona.tracking.controller.model.UserBaseDetailsDTO;
import uni.miskolc.ips.ilona.tracking.controller.passwordrecovery.PasswordGenerator;
import uni.miskolc.ips.ilona.tracking.controller.passwordrecovery.PasswordTokenSender;
import uni.miskolc.ips.ilona.tracking.controller.util.ValidateUserData;
import uni.miskolc.ips.ilona.tracking.model.UserData;
import uni.miskolc.ips.ilona.tracking.service.UserAndDeviceService;
import uni.miskolc.ips.ilona.tracking.util.TrackingModuleCentralManager;
import uni.miskolc.ips.ilona.tracking.util.validate.ValidityStatusHolder;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Controller
@RequestMapping(value = "/tracking/admin")
public class AdminUserModificationController {

    private static Logger logger = LogManager.getLogger(AdminUserModificationController.class);

    @Autowired
    private UserAndDeviceService userAndDeviceService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TrackingModuleCentralManager trackingModuleCentralManager;

    @Autowired
    private PasswordTokenSender passwordSender;

    @Autowired
    private PasswordGenerator passwordGenerator;

    @RequestMapping(value = "/usermodresetaccountexpiration", method = {RequestMethod.POST})
    @ResponseBody
    public long accountExpirationResetHandler(@RequestParam(value = "userid", required = false) String userid)
            throws AccessDeniedException, InvalidParameterException {
        if (userid == null) {
            throw new InvalidParameterException("Userid is null!");
        }
        try {
            UserData user = userAndDeviceService.getUser(userid);
            Date newDate = new Date();
            user.setLastLoginDate(newDate);
            userAndDeviceService.updateUser(user);
            return newDate.getTime();

        } catch (Exception e) {
            logger.error("Service error! Cause: " + e.getMessage());
            throw new AccessDeniedException("dasdasda");
        }
    }

    @RequestMapping(value = "/usermodmanualpasswordchange", method = {RequestMethod.POST})
    @ResponseBody
    public long accountManualPasswordResetHandler(@ModelAttribute() UserBaseDetailsDTO user)
            throws TrackingServiceErrorException, InvalidParameterException {

        try {
            ValidityStatusHolder errors = new ValidityStatusHolder();
            errors.appendValidityStatusHolder(ValidateUserData.validateRawPassword(user.getPassword()));
            errors.appendValidityStatusHolder(ValidateUserData.validateUserid(user.getUserid()));

            if (!errors.isValid()) {
                logger.info("Validity error!");
                throw new InvalidParameterException();
            }
        } catch (Exception e) {
            if (e instanceof InvalidParameterException) {
                throw new InvalidParameterException("Invalid password!");
            }
            throw new TrackingServiceErrorException("Service error!");
        }

        try {
            UserData newUser = userAndDeviceService.getUser(user.getUserid());
            String hashedPassword = passwordEncoder.encode(user.getPassword());
            newUser.setPassword(hashedPassword);
            Date credentialsValidity = new Date(new Date().getTime() + trackingModuleCentralManager.getCredentialsValidityPeriod());
            newUser.setCredentialNonExpiredUntil(credentialsValidity);
            userAndDeviceService.updateUser(newUser);
            return credentialsValidity.getTime();

        } catch (Exception e) {
            logger.error("Service error! Cause: " + e.getMessage());
            throw new TrackingServiceErrorException("Service error!");
        }
    }

    @RequestMapping(value = "/usermodchangeaccountdetails", method = {RequestMethod.POST})
    @ResponseBody
    public ExecutionResultDTO accountManagementChangeAccountDetails(@ModelAttribute() UserBaseDetailsDTO user) {

        ExecutionResultDTO result = new ExecutionResultDTO(100, new ArrayList<String>());

        try {
            ValidityStatusHolder errors = new ValidityStatusHolder();
            errors.appendValidityStatusHolder(ValidateUserData.validateUserid(user.getUserid()));
            errors.appendValidityStatusHolder(ValidateUserData.validateUsername(user.getUsername()));
            errors.appendValidityStatusHolder(ValidateUserData.validateEmail(user.getEmail()));

            if (!errors.isValid()) {
                logger.info("Invalid userdata!");
                result.setResponseState(300);
                result.addMessage("Invalid data validity!");
                return result;
            }
        } catch (Exception e) {
            logger.error("Service error! Cause: " + e.getMessage());
            result.addMessage("Service error!");
            result.setResponseState(400);
            return result;
        }

        try {
            UserData updatedUser = userAndDeviceService.getUser(user.getUserid());
            updatedUser.setUsername(user.getUsername());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setEnabled(user.isEnabled());
            Collection<String> roles = new ArrayList<>();
            roles.add("ROLE_USER");
            if (user.isAdminRole() == true) {
                roles.add("ROLE_ADMIN");
            }
            updatedUser.setRoles(roles);
            userAndDeviceService.updateUser(updatedUser);
        } catch (Exception e) {
            logger.error("Service error! Cause: " + e.getMessage());
            result.addMessage("Service error!");
            result.setResponseState(400);
            return result;
        }
        result.addMessage("User updated!");
        return result;
    }

    @RequestMapping(value = "/usermodresetpasswordexpiration", method = {RequestMethod.POST})
    @ResponseBody
    public Date accountManagementResetUserPasswordExpirationHandler(
            @RequestParam(value = "userid", required = false) String userid)
            throws InvalidParameterException, TrackingServiceErrorException {

        if (userid == null) {
            throw new InvalidParameterException();
        }

        try {
            UserData updatedUser = userAndDeviceService.getUser(userid);
            Date newPasswordExpiration = new Date(new Date().getTime() + trackingModuleCentralManager.getCredentialsValidityPeriod());
            updatedUser.setCredentialNonExpiredUntil(newPasswordExpiration);
            userAndDeviceService.updateUser(updatedUser);
            return newPasswordExpiration;

        } catch (Exception e) {
            logger.error("Service error! Cause" + e.getMessage());
            throw new TrackingServiceErrorException("Service error!");
        }

    }

    @RequestMapping(value = "/usermodupdateloginattempts", method = {RequestMethod.POST})
    @ResponseBody
    public String accountManagementUpdateLoginAttemptsHandler(@RequestParam("userid") String userid,
                                                              @RequestParam(value = "attempts[]", required = false) String[] loginAttempts) throws AccessDeniedException {
        // integrity check!
        // validation!
        Collection<Date> logins = new ArrayList<>();
        for (int i = 0; i < loginAttempts.length; i++) {
            logins.add(new Date(Long.valueOf(loginAttempts[i])));
        }

        for (Date d : logins) {
            System.out.println(d.toString());
        }
        try {
            UserData updatedUser = userAndDeviceService.getUser(userid);

            return "";

        } catch (Exception e) {
            throw new AccessDeniedException("");
        }

    }

    @RequestMapping(value = "/automatedpasswordreset", method = {RequestMethod.POST})
    @ResponseBody
    public long userPasswordAutoGenerationHandler(@RequestParam(value = "userid", required = false) String userid)
            throws InvalidParameterException, TrackingServiceErrorException {

        if (userid == null) {
            throw new InvalidParameterException("Invalid parameter!");
        }

        try {
            UserData user = userAndDeviceService.getUser(userid);
            String oldPassword = user.getPassword();
            Date oldValidity = user.getCredentialNonExpiredUntil();
            String newPassword = passwordGenerator.generatePassword(10);
            String hashedPassword = passwordEncoder.encode(newPassword);
            Date credentialValidity = new Date(new Date().getTime() + trackingModuleCentralManager.getCredentialsValidityPeriod());

            user.setPassword(hashedPassword);
            user.setCredentialNonExpiredUntil(credentialValidity);

            userAndDeviceService.updateUser(user);
            try {
                passwordSender.sendNewPassword(userid, newPassword, user.getEmail());
            } catch (Exception e) {
                logger.error("Mail sending error! Cause: " + e.getMessage());
                user.setPassword(oldPassword);
                user.setCredentialNonExpiredUntil(oldValidity);
                userAndDeviceService.updateUser(user);
                throw new TrackingServiceErrorException();
            }
            return credentialValidity.getTime();
        } catch (Exception e) {
            logger.error("Service error! Cause: " + e.getMessage());
            throw new TrackingServiceErrorException("Service error!");
        }
    }

    @ExceptionHandler(InvalidParameterException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleInvalidParameterException(InvalidParameterException e) {
        return "Invalid parameter!";
    }

    @ExceptionHandler(TrackingServiceErrorException.class)
    @ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public String handleServiceErrorException(TrackingServiceErrorException e) {
        return "Service error!";
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

    public void setPasswordGenerator(PasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
    }

}
