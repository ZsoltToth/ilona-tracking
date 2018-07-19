package uni.miskolc.ips.ilona.tracking.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/tracking/user")
public class UserMainpageController {

	@RequestMapping(value = "/homepage", method = { RequestMethod.POST })
	public ModelAndView createUserMainpageHandler() {
		ModelAndView mav = new ModelAndView("tracking/user/userMainpage");

		return mav;
	}
}
