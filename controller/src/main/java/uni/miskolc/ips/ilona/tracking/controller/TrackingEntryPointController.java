package uni.miskolc.ips.ilona.tracking.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import uni.miskolc.ips.ilona.tracking.controller.exception.PasswordRecoveryTokenValidityErrorException;
import uni.miskolc.ips.ilona.tracking.controller.exception.TrackingServiceErrorException;
import uni.miskolc.ips.ilona.tracking.controller.model.ExecutionResultDTO;
import uni.miskolc.ips.ilona.tracking.controller.model.UserCreationDTO;
import uni.miskolc.ips.ilona.tracking.controller.passwordrecovery.PasswordRecoveryManager;
import uni.miskolc.ips.ilona.tracking.controller.util.ValidateUserData;
import uni.miskolc.ips.ilona.tracking.controller.util.WebpageInformationProvider;
import uni.miskolc.ips.ilona.tracking.model.DeviceData;
import uni.miskolc.ips.ilona.tracking.model.UserData;
import uni.miskolc.ips.ilona.tracking.service.UserAndDeviceService;
import uni.miskolc.ips.ilona.tracking.service.exceptions.DuplicatedUserException;
import uni.miskolc.ips.ilona.tracking.service.exceptions.ServiceGeneralErrorException;
import uni.miskolc.ips.ilona.tracking.service.exceptions.UserNotFoundException;
import uni.miskolc.ips.ilona.tracking.util.TrackingModuleCentralManager;
import uni.miskolc.ips.ilona.tracking.util.validate.ValidityStatusHolder;

/*
* Content:
* 
* 0) dependencies
* 1) Entrypoint mapper
* 2) mainpage mappers
* 3) exception handlers
* 4) setters/getters
*/

/**
 * This controller is responsible for the main page requests in the ILONA
 * System. This class can:<br>
 * - load the main page navigation bar<br>
 * - load the main page home page<br>
 * - load the main page login page<br>
 * - load the main page registration page
 * 
 * @author Patrik / A5USL0
 *
 */
@Controller(value = "trackingEntryPointController")
@RequestMapping(value = "/tracking")
public class TrackingEntryPointController {

	private static Logger logger = LogManager.getLogger(TrackingEntryPointController.class);

	@Resource(name = "UserAndDeviceService")
	private UserAndDeviceService userDeviceService;

	@Resource(name = "trackingPasswordEncoder")
	private PasswordEncoder passwordEncoder;

	@Resource(name = "trackingCentralManager")
	private TrackingModuleCentralManager centralManager;

	@Resource(name = "passwordRecoveryManager")
	private PasswordRecoveryManager passwordRecoveryManager;

	/**
	 * This method sends back the actual tracking content.<br>
	 * <dl>
	 * <dt>If the user is not authenticated:</dt>
	 * <dd>- Sends back the start page of the tracking modul.</dd>
	 * <dd>- This page contains the login and the sign up functions.</dd>
	 * <dt>If the user is authenticated</dt>
	 * <dd>- Sends back the logged in user main page</dd>
	 * <dd>- If the user has the authority admin, the return page is the admin
	 * main page</dd>
	 * <dd>- If the user has the authority user, the return page is the user
	 * main page</dd>
	 * </dl>
	 * 
	 * @return The actual tracking content depends on the actual login status
	 *         and/or the actual authority of the current user.
	 */
	@RequestMapping(value = "/maincontentdecision", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView createTrackingContentpage(Authentication authentication) throws InterruptedException {
		/*
		 * 
		 * RETURN PAGE DEPENDS ON THE ACTUAL AUTHENTICATION! anonymousUser
		 */

		/*
		 * Get the actual authenticaton.
		 */
		authentication = SecurityContextHolder.getContext().getAuthentication();

		/*
		 * Check if the current user is anonymous user? Theoretically the
		 * authentication cannot be null.
		 */

		if (authentication == null) {
			logger.info("Anonymus authentication request!");
			return new ModelAndView("tracking/mainpageHome");
		}

		System.out.println(authentication.getClass().getName());

		/*
		 * If the current user is not autthenticated (anonymus) the returned
		 * page will be the tracking login page.
		 */
		if (authentication != null) {
			logger.info("Anonymus authentication request!");

			for (GrantedAuthority role : authentication.getAuthorities()) {
				if (role.getAuthority().equals("ROLE_ANONYMOUS")) {
					return new ModelAndView("tracking/mainpageHome");
				}
			}
		}

		/**
		 * Chech for admin rights
		 */
		for (GrantedAuthority auth : authentication.getAuthorities()) {
			if (auth.getAuthority().equals("ROLE_ADMIN")) {
				logger.info("Admin page authentication request!" + authentication.getName());
				return new ModelAndView("tracking/admin/mainpage");
			}
		}

		/**
		 * Check for user rights
		 */
		for (GrantedAuthority auth : authentication.getAuthorities()) {
			if (auth.getAuthority().equals("ROLE_USER")) {
				logger.info("User page authentication request!" + authentication.getName());
				return new ModelAndView("tracking/user/userMainpage");
			}
		}

		/**
		 * Other role?
		 */
		return new ModelAndView("tracking/mainpageHome");
	}

	/**
	 * This method sends back the login page for the tracking main page.
	 * 
	 * @return
	 * 
	 * @RequestMapping(value = "/login", method = { RequestMethod.GET,
	 *                       RequestMethod.POST }) public ModelAndView
	 *                       loadTrackingLoginpage() { ModelAndView loginpage =
	 *                       new ModelAndView("tracking/loginpage"); return
	 *                       loginpage; }
	 */

	@RequestMapping(value = "/getmainpagehome", method = { RequestMethod.POST })
	public ModelAndView generateMainpageHome() {
		return new ModelAndView("tracking/mainpageHome");
	}

	@RequestMapping(value = "/getmainpagesignup", method = { RequestMethod.POST })
	public ModelAndView generateMainpageSignup() throws InterruptedException {
		ModelAndView mav = new ModelAndView("tracking/mainpageSignup");
		mav.addObject("useridRestriction", WebpageInformationProvider.getUseridRestrictionMessage());
		mav.addObject("usernameRestriction", WebpageInformationProvider.getUsernameRestrictionMessage());
		mav.addObject("passwordRestriction", WebpageInformationProvider.getPasswordRestrictionMessage());
		mav.addObject("emailRestriction", WebpageInformationProvider.getEmailRestrictionMessage());

		return mav;
	}

	@RequestMapping(value = "/getloginpage", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView generateLoginpage(@RequestParam(value = "error", required = false) String error) {
		ModelAndView mav = new ModelAndView("tracking/loginpage");
		mav.addObject("error", error);
		return mav;
	}

	/**
	 * 
	 * @param user
	 * @return {@link ExecutionResultDTO}<br>
	 *         <ul>
	 *         <li>100: OK</li>
	 *         <li>200: Parameter error</li>
	 *         <li>300: Validity error</li>
	 *         <li>400: Service error</li>
	 *         <li>500: Server error(timeout)</li>
	 *         <li>600: Duplicated user error</li>
	 *         </ul>
	 */
	@RequestMapping(value = "/registeruser", method = { RequestMethod.POST })
	@ResponseBody
	public ExecutionResultDTO registerUser(@ModelAttribute(name = "user") UserCreationDTO user) {

		ExecutionResultDTO result = new ExecutionResultDTO(100, new ArrayList<String>());
		try {
			ValidityStatusHolder errors = new ValidityStatusHolder();
			errors.appendValidityStatusHolder(ValidateUserData.validateUserid(user.getUserid()));
			errors.appendValidityStatusHolder(ValidateUserData.validateUsername(user.getUsername()));
			errors.appendValidityStatusHolder(ValidateUserData.validateEmail(user.getEmail()));
			errors.appendValidityStatusHolder(ValidateUserData.validateRawPassword(user.getPassword()));

			if (!errors.isValid()) {
				result.setResponseState(300);
				result.setMessages(errors.getErrors());
				return result;
			}
		} catch (Exception e) {
			result.setResponseState(400);
			result.addMessage(e.getMessage());
			return result;
		}
		try {

			/*
			 * Password encoding.
			 */
			String encodedPassword = passwordEncoder.encode(user.getPassword());
			/*
			 * ROLES setup.
			 */
			ArrayList<String> roles = new ArrayList<String>();
			roles.add("ROLE_USER");

			/*
			 * Credentials validity setup.
			 */
			Date passwordExpired = new Date(new Date().getTime() + centralManager.getCredentialsValidityPeriod());

			/*
			 * Setup other still null fields.
			 */
			Collection<Date> badLogins = new ArrayList<Date>();
			Collection<DeviceData> devices = new ArrayList<DeviceData>();
			/*
			 * Create user with the given details. The account will be enabled
			 * and non locked and the last login date is the account creation
			 * time.
			 */
			UserData userDB = new UserData(user.getUserid(), user.getUsername(), user.getEmail(), encodedPassword, true,
					roles, new Date(), passwordExpired, new Date(), true, badLogins, devices);
			/*
			 * 
			 */
			this.userDeviceService.createUser(userDB);
		} catch (DuplicatedUserException e) {
			logger.error("Duplicated userid: " + user.getUserid());
			result.addMessage("Duplicated user with this userid: " + user.getUserid());
			result.setResponseState(600);
			return result;
		} catch (ServiceGeneralErrorException e) {
			logger.error("Error: " + e.getMessage());
			result.addMessage("There has been an error with the service, the account is not created!");
			result.setResponseState(400);
			return result;
		} catch (Exception e) {
			logger.error("Error: " + e.getMessage());
			result.addMessage("There has been an error with the service, the account is not created!");
			result.setResponseState(400);
			return result;
		}

		result.addMessage("Account has been created!");
		return result;
	}

	/**
	 * DOC! 100: OK 200: Invalid Parameter 400: service error 600: User not
	 * found
	 * 
	 * @param userid
	 * @return
	 */
	@RequestMapping(value = "/resetpassword", method = { RequestMethod.POST })
	@ResponseBody
	public ExecutionResultDTO trackingResetPasswordHandler(
			@RequestParam(value = "userid", required = false) String userid) {
		ExecutionResultDTO result = new ExecutionResultDTO(100, new ArrayList<String>());
		if (userid == null) {
			result.setResponseState(200);
			result.addMessage("Invalid parameter!");
			return result;
		}
		try {
			UserData user = userDeviceService.getUser(userid);
			passwordRecoveryManager.handlePasswordRecoveryRequest(user);
		} catch (UserNotFoundException e) {
			logger.info("Password recovery has failed, user not found! userid: " + userid);
			result.addMessage("User not found with id: " + userid);
			result.setResponseState(600);
			return result;
		} catch (Exception e) {
			logger.info("Password recovery has failed! userid: " + userid);
			result.setResponseState(400);
			result.addMessage("Password recovery has failed!");
			return result;
		}

		result.addMessage("Password recovery has been sent!");
		return result;
	}

	/**
	 * DOC! 100: OK 200: parameter error 300: token validity error 400: service
	 * 600: user not found
	 * 
	 * @param userid
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/passwordrequestwithtoken", method = { RequestMethod.POST })
	@ResponseBody
	public ExecutionResultDTO resetPasswordWithTokenHandler(
			@RequestParam(value = "userid", required = false) String userid,
			@RequestParam(value = "token", required = false) String token) {
		ExecutionResultDTO result = new ExecutionResultDTO(100, new ArrayList<String>());

		if (userid == null || token == null) {
			result.addMessage("Invalid paramter: userid: " + userid + " Token: " + token);
			result.setResponseState(200);
			return result;
		}
		try {
			UserData user = userDeviceService.getUser(userid);
			passwordRecoveryManager.handlePasswordRestore(user, token);
		} catch (UserNotFoundException e) {
			logger.info("user not found with id: " + userid);
			result.addMessage("user not found with id: " + userid);
			result.setResponseState(600);
			return result;
		} catch (PasswordRecoveryTokenValidityErrorException e) {
			logger.info("Password recovery token validity error! userid: " + userid + " token: " + token);
			result.addMessage("Password token validity error!");
			result.setResponseState(300);
			return result;
		} catch (Exception e) {
			logger.info("Password recovery error! userid: " + userid + " token: " + token);
			result.addMessage("Service error!");
			result.setResponseState(400);
			return result;
		}
		result.addMessage("The new password has been sent!");
		return result;
	}

	@ExceptionHandler(TrackingServiceErrorException.class)
	@ResponseBody
	@ResponseStatus(code = HttpStatus.CREATED)
	public String dadsa() {
		return "";
	}

	public void setUserDeviceService(UserAndDeviceService userDeviceService) {
		this.userDeviceService = userDeviceService;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public void setCentralManager(TrackingModuleCentralManager centralManager) {
		this.centralManager = centralManager;
	}

	public void setPasswordRecoveryManager(PasswordRecoveryManager passwordRecoveryManager) {
		this.passwordRecoveryManager = passwordRecoveryManager;
	}

}
