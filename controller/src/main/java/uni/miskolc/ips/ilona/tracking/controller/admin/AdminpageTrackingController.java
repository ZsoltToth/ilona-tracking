package uni.miskolc.ips.ilona.tracking.controller.admin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import uni.miskolc.ips.ilona.measurement.model.position.Coordinate;
import uni.miskolc.ips.ilona.measurement.model.position.Position;
import uni.miskolc.ips.ilona.measurement.model.position.Zone;
import uni.miskolc.ips.ilona.tracking.controller.exception.TrackingServiceErrorException;
import uni.miskolc.ips.ilona.tracking.controller.model.ExecutionResultDTO;
import uni.miskolc.ips.ilona.tracking.controller.model.PositionTrackDTO;
import uni.miskolc.ips.ilona.tracking.controller.track.GraphFunctions;
import uni.miskolc.ips.ilona.tracking.model.DeviceData;
import uni.miskolc.ips.ilona.tracking.model.UserData;
import uni.miskolc.ips.ilona.tracking.service.UserAndDeviceService;

@Controller
@RequestMapping(value = "/tracking/admin")
public class AdminpageTrackingController {

	private static Logger logger = LogManager.getLogger(AdminpageTrackingController.class);

	@Resource(name = "UserAndDeviceService")
	private UserAndDeviceService userAndDeviceService;

	@Resource(name = "graphFunctions")
	private GraphFunctions graphFunctions;

	@RequestMapping(value = "/trackingmainpage", method = { RequestMethod.POST })
	public ModelAndView createTrackingMainpage() {
		ModelAndView mav = new ModelAndView("tracking/admin/tracking");
		try {
			Collection<UserData> users = userAndDeviceService.getAllUsers();
			Collection<String> userids = new ArrayList<String>(users.size());
			for (UserData user : users) {
				userids.add(user.getUserid());
			}
			mav.addObject("userids", userids);
		} catch (Exception e) {

		}
		return mav;
	}

	@RequestMapping(value = "/tracking/getdevicelist", method = { RequestMethod.POST })
	@ResponseBody
	public Collection<String> getDevicesforUserHandler(@RequestParam(value = "userid") String userid)
			throws TrackingServiceErrorException {
		try {
			UserData user = userAndDeviceService.getUser(userid);
			Collection<DeviceData> devices = userAndDeviceService.readUserDevices(user);
			Collection<String> deviceList = new ArrayList<>(devices.size());
			for (DeviceData device : devices) {
				deviceList.add(device.getDeviceid());
			}
			return deviceList;
		} catch (Exception e) {
			logger.error("Service error! Cause: " + e.getMessage());
			throw new TrackingServiceErrorException();
		}
	}

	@RequestMapping(value = "/tracking/getpositions", method = { RequestMethod.POST })
	@ResponseBody
	public Collection<PositionTrackDTO> getPositionsForDeviceHandler() throws TrackingServiceErrorException {
		try {
			int count = 8;
			Collection<PositionTrackDTO> positions = new ArrayList<PositionTrackDTO>();
			for (int i = 0; i < count; i++) {
				Coordinate coord = new Coordinate();
				coord.setX(Math.random() * 50);
				coord.setY(Math.random() * 28);
				//coord.setZ(Math.random() * 10);
				coord.setZ(Math.random() + 3);
				Position pos = new Position(coord);
				Zone zone = new Zone("Zone-" + Math.random());
				pos.setZone(zone);
				PositionTrackDTO dto = new PositionTrackDTO();
				dto.setPosition(pos);
				long randomTime = new Date().getTime() - (long) Math.round(Math.random() * 10_000_000D);
				dto.setDate(new Date(randomTime));
				positions.add(dto);
			}
			return positions;
		} catch (Exception e) {
			logger.error("Service error! Cause: " + e.getMessage());
			throw new TrackingServiceErrorException();
		}
	}

	@RequestMapping(value = "/tracking/calculatepath", method = { RequestMethod.POST })
	@ResponseBody
	public Collection<String> calculatePath(@RequestParam(value = "start") String start,
			@RequestParam(value = "end") String end) {
		return graphFunctions.generateShortestPath(start, end);
		//return null;
	}

	@ExceptionHandler(TrackingServiceErrorException.class)
	@ResponseBody
	public ExecutionResultDTO serviceErrorHandler(TrackingServiceErrorException error) {
		ExecutionResultDTO result = new ExecutionResultDTO(400, new ArrayList<String>());
		result.addMessage("Service error!");
		return result;
	}

	public void setGraphFunctions(GraphFunctions graphFunctions) {
		this.graphFunctions = graphFunctions;
	}

	public void setUserAndDeviceService(UserAndDeviceService userAndDeviceService) {
		this.userAndDeviceService = userAndDeviceService;
	}

}
