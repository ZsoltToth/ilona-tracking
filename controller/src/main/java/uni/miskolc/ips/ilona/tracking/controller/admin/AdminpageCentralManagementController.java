package uni.miskolc.ips.ilona.tracking.controller.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import uni.miskolc.ips.ilona.tracking.controller.model.ExecutionResultDTO;
import uni.miskolc.ips.ilona.tracking.controller.util.ValidateExecutionParameters;
import uni.miskolc.ips.ilona.tracking.util.TrackingModuleCentralManager;
import uni.miskolc.ips.ilona.tracking.util.validate.ValidityStatusHolder;

import java.util.ArrayList;

@Controller
@RequestMapping(value = "/tracking/admin")
public class AdminpageCentralManagementController {

    private static Logger logger = LogManager.getLogger(AdminpageCentralManagementController.class);

    @Autowired
    private TrackingModuleCentralManager centralManager;

    @RequestMapping(value = "/centralmanagement", method = {RequestMethod.POST})
    public ModelAndView generateAdminCentralManagementpage() {
        ModelAndView mav = new ModelAndView("tracking/admin/centralManagement");
        mav.addObject("enabledCheck", centralManager.isEnabledCheckEnabled() == true ? true : false);
        mav.addObject("accExpCheck", centralManager.isAccountExpirationCheckEnabled() == true ? true : false);
        mav.addObject("accExpTime", centralManager.getAccountExpirationTime());
        mav.addObject("credValPeriod", centralManager.getCredentialsValidityPeriod());
        mav.addObject("badLoginUpperBound", centralManager.getBadLoginsUpperBound());
        mav.addObject("lockedCheck", centralManager.isLockedCheckEnabled() == true ? true : false);
        mav.addObject("lockedTimeAfterBadLogin", centralManager.getLockedTimeAfterBadLogins());
        mav.addObject("passwordRecoveryTokenTime", centralManager.getPasswordRecoveryTokenValidityTime());
        return mav;
    }

    @RequestMapping(value = "/centman/updateenabledcheck")
    @ResponseBody
    public ExecutionResultDTO updateEnabledHandler(@RequestParam("data") String stateText) {
        ExecutionResultDTO result = new ExecutionResultDTO(100, new ArrayList<String>());
        try {
            boolean newState = false;
            if (stateText.trim().equalsIgnoreCase("ON")) {
                newState = true;
            }
            centralManager.setEnabledCheckEnabled(newState);
        } catch (Exception e) {
            logger.error("Service error! Cause: " + e.getMessage());
            result.addMessage("Service error!");
            result.setResponseState(400);
        }
        result.addMessage("Updated!");
        return result;
    }

    @RequestMapping(value = "/centman/updateaccexpcheck")
    @ResponseBody
    public ExecutionResultDTO updateExpirationCheckHandler(@RequestParam("data") String stateText) {
        ExecutionResultDTO result = new ExecutionResultDTO(100, new ArrayList<String>());
        try {
            boolean newState = false;
            if (stateText.trim().equalsIgnoreCase("ON")) {
                newState = true;
            }
            centralManager.setAccountExpirationCheckEnabled(newState);
        } catch (Exception e) {
            logger.error("Service error! Cause: " + e.getMessage());
            result.addMessage("Service error!");
            result.setResponseState(400);
        }
        result.addMessage("Updated!");
        return result;
    }

    @RequestMapping(value = "/centman/updateaccexptime")
    @ResponseBody
    public ExecutionResultDTO updateAccountExpirationTimeHandler(@RequestParam("data") long newValue) {
        ExecutionResultDTO result = new ExecutionResultDTO(100, new ArrayList<String>());

        try {
            ValidityStatusHolder errors = new ValidityStatusHolder();
            errors.appendValidityStatusHolder(ValidateExecutionParameters.validateAccountExpiration(newValue));
            if (!errors.isValid()) {
                result.addMessage("Invalid parameter!");
                result.setResponseState(300);
            }
        } catch (Exception e) {
            logger.error("Service error! Cause: " + e.getMessage());
            result.addMessage("Service error!");
            result.setResponseState(400);
            return result;
        }

        try {
            centralManager.setAccountExpirationTime(newValue);
        } catch (Exception e) {
            logger.error("Service error! Cause: " + e.getMessage());
            result.addMessage("Service error!");
            result.setResponseState(400);
        }
        result.addMessage("Updated!");
        return result;
    }

    @RequestMapping(value = "/centman/updatecredvalidtime")
    @ResponseBody
    public ExecutionResultDTO updateAccountCredentialsValidTimeHandler(@RequestParam("data") long newValue) {
        ExecutionResultDTO result = new ExecutionResultDTO(100, new ArrayList<String>());

        try {
            ValidityStatusHolder errors = new ValidityStatusHolder();
            errors.appendValidityStatusHolder(ValidateExecutionParameters.validateCredentialsValidityPeriod(newValue));
            if (!errors.isValid()) {
                result.addMessage("Invalid parameter!");
                result.setResponseState(300);
            }
        } catch (Exception e) {
            logger.error("Service error! Cause: " + e.getMessage());
            result.addMessage("Service error!");
            result.setResponseState(400);
            return result;
        }

        try {
            centralManager.setCredentialsValidityPeriod(newValue);
        } catch (Exception e) {
            logger.error("Service error! Cause: " + e.getMessage());
            result.addMessage("Service error!");
            result.setResponseState(400);
        }
        result.addMessage("Updated!");
        return result;
    }

    @RequestMapping(value = "/centman/updatebadloginuppervalue")
    @ResponseBody
    public ExecutionResultDTO updateAccountBadLoginUpperBoundHandler(@RequestParam("data") int newValue) {
        ExecutionResultDTO result = new ExecutionResultDTO(100, new ArrayList<String>());

        try {
            ValidityStatusHolder errors = new ValidityStatusHolder();
            errors.appendValidityStatusHolder(ValidateExecutionParameters.validateBadLoginsUpperBound(newValue));
            if (!errors.isValid()) {
                result.addMessage("Invalid parameter!");
                result.setResponseState(300);
            }
        } catch (Exception e) {
            logger.error("Service error! Cause: " + e.getMessage());
            result.addMessage("Service error!");
            result.setResponseState(400);
            return result;
        }

        try {
            centralManager.setBadLoginsUpperBound(newValue);
        } catch (Exception e) {
            logger.error("Service error! Cause: " + e.getMessage());
            result.addMessage("Service error!");
            result.setResponseState(400);
        }
        result.addMessage("Updated!");
        return result;
    }

    @RequestMapping(value = "/centman/updatelockcheckenabled")
    @ResponseBody
    public ExecutionResultDTO updateAccountLockedEnabledHandler(@RequestParam("data") String stateText) {
        ExecutionResultDTO result = new ExecutionResultDTO(100, new ArrayList<String>());
        try {
            boolean newState = false;
            if (stateText.trim().equalsIgnoreCase("ON")) {
                newState = true;
            }
            centralManager.setLockedCheckEnabled(newState);
        } catch (Exception e) {
            logger.error("Service error! Cause: " + e.getMessage());
            result.addMessage("Service error!");
            result.setResponseState(400);
        }
        result.addMessage("Updated!");
        return result;
    }

    @RequestMapping(value = "/centman/updatebantimeafterbadlogins")
    @ResponseBody
    public ExecutionResultDTO updateLockTimeAfterBadLoginsHandler(@RequestParam("data") long newValue) {
        ExecutionResultDTO result = new ExecutionResultDTO(100, new ArrayList<String>());

        try {
            ValidityStatusHolder errors = new ValidityStatusHolder();
            errors.appendValidityStatusHolder(ValidateExecutionParameters.validateLockTimeAfterBadLogins(newValue));
            if (!errors.isValid()) {
                result.addMessage("Invalid parameter!");
                result.setResponseState(300);
            }
        } catch (Exception e) {
            logger.error("Service error! Cause: " + e.getMessage());
            result.addMessage("Service error!");
            result.setResponseState(400);
            return result;
        }

        try {
            centralManager.setLockedTimeAfterBadLogins(newValue);
        } catch (Exception e) {
            logger.error("Service error! Cause: " + e.getMessage());
            result.addMessage("Service error!");
            result.setResponseState(400);
        }
        result.addMessage("Updated!");
        return result;
    }

    @RequestMapping(value = "/centman/updatepasstokvaliditytime")
    @ResponseBody
    public ExecutionResultDTO updatePasswordTokenValidityTimeHandler(@RequestParam("data") long newValue) {
        ExecutionResultDTO result = new ExecutionResultDTO(100, new ArrayList<String>());

        try {
            ValidityStatusHolder errors = new ValidityStatusHolder();
            errors.appendValidityStatusHolder(
                    ValidateExecutionParameters.validatePasswordRecoveryTokenValidity(newValue));
            if (!errors.isValid()) {
                result.addMessage("Invalid parameter!");
                result.setResponseState(300);
            }
        } catch (Exception e) {
            logger.error("Service error! Cause: " + e.getMessage());
            result.addMessage("Service error!");
            result.setResponseState(400);
            return result;
        }

        try {
            centralManager.setPasswordRecoveryTokenValidityTime(newValue);
        } catch (Exception e) {
            logger.error("Service error! Cause: " + e.getMessage());
            result.addMessage("Service error!");
            result.setResponseState(400);
        }
        result.addMessage("Updated!");
        return result;
    }

    public void setCentralManager(TrackingModuleCentralManager centralManager) {
        this.centralManager = centralManager;
    }

}
