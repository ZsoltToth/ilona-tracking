package uni.miskolc.ips.ilona.tracking.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/tracking/admin")
public class AdminStatisticsController {

	@RequestMapping(value = "/statistics", method = { RequestMethod.POST })
	public ModelAndView createAdminStatisticspageHandler() {
		ModelAndView mav = new ModelAndView("tracking/admin/statistics");
		
		return mav;
	}
}
