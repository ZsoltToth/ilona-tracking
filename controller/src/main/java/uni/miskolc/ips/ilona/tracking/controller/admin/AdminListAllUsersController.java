package uni.miskolc.ips.ilona.tracking.controller.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uni.miskolc.ips.ilona.tracking.controller.exception.TrackingServiceErrorException;
import uni.miskolc.ips.ilona.tracking.controller.model.ExecutionResultDTO;
import uni.miskolc.ips.ilona.tracking.controller.model.LoginAttemptFormStorage;
import uni.miskolc.ips.ilona.tracking.controller.model.UserBaseDetailsDTO;
import uni.miskolc.ips.ilona.tracking.controller.model.UserSecurityDetails;
import uni.miskolc.ips.ilona.tracking.controller.util.WebpageInformationProvider;
import uni.miskolc.ips.ilona.tracking.model.DeviceData;
import uni.miskolc.ips.ilona.tracking.model.UserData;
import uni.miskolc.ips.ilona.tracking.service.UserAndDeviceService;
import uni.miskolc.ips.ilona.tracking.service.exceptions.UserNotFoundException;
import uni.miskolc.ips.ilona.tracking.util.TrackingModuleCentralManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Controller
@RequestMapping(value = "/tracking/admin")
public class AdminListAllUsersController {

    private static Logger logger = LogManager.getLogger(AdminListAllUsersController.class);

    @Autowired
    private UserAndDeviceService userAndDeviceService;

    @Autowired
    private TrackingModuleCentralManager centralManager;

    @RequestMapping(value = "/listallusers")
    public ModelAndView createAdminpageListAlluserspage() {
        ModelAndView userlistPage = new ModelAndView("tracking/admin/listAllUsers");
        Collection<UserData> users = new ArrayList<UserData>();
        Collection<UserBaseDetailsDTO> filteredUsers = new ArrayList<UserBaseDetailsDTO>();
        try {
            users = userAndDeviceService.getAllUsers();

            for (UserData user : users) {
                UserBaseDetailsDTO newUser = new UserBaseDetailsDTO();
                newUser.setUserid(user.getUserid());
                newUser.setUsername(user.getUsername());
                newUser.setEmail(user.getEmail());
                newUser.setEnabled(user.isEnabled());

                boolean isAdmin = false;
                for (String role : user.getRoles()) {
                    if (role.equals("ROLE_ADMIN")) {
                        isAdmin = true;
                    }
                }
                newUser.setAdminRole(isAdmin);
                filteredUsers.add(newUser);
            }
        } catch (Exception e) {
            logger.error("Service error! Cause: " + e.getMessage());
            userlistPage.addObject("serviceError", "Service error!");
        }
        userlistPage.addObject("users", filteredUsers);
        return userlistPage;
    }

    @RequestMapping(value = "/listusers/getusersdevices", method = {RequestMethod.POST})
    public ModelAndView createListUserDevicespage(@RequestParam(name = "userid", required = false) String userid)
            throws TrackingServiceErrorException {
        ModelAndView mav = new ModelAndView("tracking/admin/userDevices");

        if (userid == null) {
            logger.info("Userid null!");
            throw new TrackingServiceErrorException("Error: The userid is null!");
        }

        Collection<DeviceData> devices = new ArrayList<>();
        try {
            UserData user = userAndDeviceService.getUser(userid);
            devices = userAndDeviceService.readUserDevices(user);
            mav.addObject("devices", devices);
            mav.addObject("deviceOwner", userid);
        } catch (Exception e) {
            logger.error("Service error! Cause: " + e.getMessage());
            throw new TrackingServiceErrorException("Error: Service error!");
        }
        return mav;
    }

    @RequestMapping(value = "/listusers/deleteuser", method = {RequestMethod.POST})
    @ResponseBody
    public ExecutionResultDTO deleteUser(@RequestParam(value = "userid", required = false) String userid) {
        ExecutionResultDTO result = new ExecutionResultDTO(100, new ArrayList<String>());

        if (userid == null) {
            result.addMessage("Userid is not valid!");
            result.setResponseState(200);
            return result;
        }

        UserSecurityDetails userDetails = (UserSecurityDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        if (userDetails.getUserid().equals(userid)) {
            result.addMessage("Own account deletion is forbidden!");
            result.setResponseState(700);
            return result;
        }
        try {
            UserData user = userAndDeviceService.getUser(userid);
            userAndDeviceService.deleteUser(user);
        } catch (UserNotFoundException e) {
            logger.error("User not found: " + e.getMessage());
            result.addMessage("User not found with id: " + userid);
            result.setResponseState(600);
            return result;
        } catch (Exception e) {
            logger.error("Service error! Cause: " + e.getMessage());
            result.addMessage("Service error!");
            result.setResponseState(400);
            return result;
        }
        result.addMessage("User deleted!");
        return result;
    }

    @RequestMapping(value = "/listusers/modifyuser")
    public ModelAndView createAdminUserModificationpageHandler(
            @RequestParam(name = "userid", required = false) String userid) throws TrackingServiceErrorException {
        ModelAndView mav = new ModelAndView("tracking/admin/userModification");

        if (userid == null) {
            throw new TrackingServiceErrorException("Invalid userid!");
        }

        try {
            UserData user = userAndDeviceService.getUser(userid);
            mav.addObject("Userid", user.getUserid());
            mav.addObject("Username", user.getUsername());
            mav.addObject("Email", user.getEmail());
            /*
             * User enabled
             */
            if (user.isEnabled() == true) {
                mav.addObject("Enabled", "checked='checked'");
            } else {
                mav.addObject("Enabled", "");
            }
            /*
             * is admin check
             */
            Collection<String> roles = user.getRoles();
            boolean isAdmin = false;
            for (String role : roles) {
                if (role.equals("ROLE_ADMIN")) {
                    isAdmin = true;
                }
            }
            if (isAdmin == true) {
                mav.addObject("IsAdmin", "checked=\"checked\"");
            } else {
                mav.addObject("IsAdmin", "");
            }
            Date lastLogin = user.getLastLoginDate();
            if ((new Date().getTime() - centralManager.getAccountExpirationTime()) > lastLogin.getTime()) {
                mav.addObject("isAccountNonExpired", false);
            } else {
                mav.addObject("isAccountNonExpired", true);
            }
            mav.addObject("lastLoginDate", user.getLastLoginDate().getTime());

            if (user.getCredentialNonExpiredUntil().getTime() < new Date().getTime()) {
                mav.addObject("isPasswordNotExpired", false);
            } else {
                mav.addObject("isPasswordNotExpired", true);
            }
            mav.addObject("passwordValidUntil", user.getCredentialNonExpiredUntil().getTime());

            Collection<LoginAttemptFormStorage> attempts = new ArrayList<>();
            Collection<Date> loginAttempts = user.getBadLogins();
            for (Date date : loginAttempts) {
                LoginAttemptFormStorage storage = new LoginAttemptFormStorage();
                storage.setFormatDate(date.toString());
                storage.setFormatMilliseconds(date.getTime());
                attempts.add(storage);
            }
            mav.addObject("loginAttempts", attempts);
        } catch (Exception e) {
            throw new TrackingServiceErrorException("Service error!");
        }

        mav.addObject("useridRestriction", WebpageInformationProvider.getUseridRestrictionMessage());
        mav.addObject("usernameRestriction", WebpageInformationProvider.getUsernameRestrictionMessage());
        mav.addObject("emailRestriction", WebpageInformationProvider.getEmailRestrictionMessage());
        mav.addObject("passwordRestriction", WebpageInformationProvider.getPasswordRestrictionMessage());
        mav.addObject("enabledRestriction", WebpageInformationProvider.getEnabledcreationmessage());
        mav.addObject("adminRestriction", WebpageInformationProvider.getUserrolecreationmessage());
        return mav;
    }

    @ExceptionHandler(TrackingServiceErrorException.class)
    @ResponseBody
    public ExecutionResultDTO handleTrackingServiceErrorException(TrackingServiceErrorException exception) {
        ExecutionResultDTO result = new ExecutionResultDTO(400, new ArrayList<String>());
        result.addMessage("Service error!");
        return result;
    }

    public void setUserAndDeviceService(UserAndDeviceService userAndDeviceService) {
        this.userAndDeviceService = userAndDeviceService;
    }

    public void setCentralManager(TrackingModuleCentralManager centralManager) {
        this.centralManager = centralManager;
    }

}
