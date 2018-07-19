package uni.miskolc.ips.ilona.tracking.controller;

import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AuthenticationController {

	@ExceptionHandler(value = { AuthenticationException.class })
	@ResponseBody
	public String handleAuthenticationErrors() {
		return null;
	}
}
