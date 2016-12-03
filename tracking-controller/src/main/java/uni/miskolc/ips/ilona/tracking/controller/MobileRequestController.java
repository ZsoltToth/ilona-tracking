package uni.miskolc.ips.ilona.tracking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import uni.miskolc.ips.ilona.measurement.model.measurement.Measurement;

@Controller
@RequestMapping(value = "/tracking/mobile")
public class MobileRequestController {

	@RequestMapping(value = "/sendmeasurement")
	public String mobileMeasurementRequestHandler(@RequestBody() Measurement measurement) {
		measurement.toString();
		return "";
	}
}
