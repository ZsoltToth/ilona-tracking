package uni.miskolc.ips.ilona.tracking.controller.history;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uni.miskolc.ips.ilona.tracking.controller.model.UserPositionDTO;
import uni.miskolc.ips.ilona.tracking.controller.util.UserPositionDTOConverter;
import uni.miskolc.ips.ilona.tracking.model.UserPosition;
import uni.miskolc.ips.ilona.tracking.service.HistoryService;
import uni.miskolc.ips.ilona.tracking.service.exceptions.ServiceGeneralErrorException;
import uni.miskolc.ips.ilona.tracking.service.exceptions.UserNotFoundException;

import javax.xml.datatype.DatatypeConfigurationException;
import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping(value = "history")
public class HistoryController {

    private final Logger LOG = LogManager.getLogger(HistoryController.class);

    private HistoryService historyService;

    @Autowired
    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @RequestMapping(value = "add", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addHistory(@RequestBody UserPositionDTO userPositionDTO) throws ServiceGeneralErrorException, UserNotFoundException {
        UserPosition userPosition = UserPositionDTOConverter.convertToUserPosition(userPositionDTO);
        try {
            historyService.addHistory(userPosition.getPosition(), userPosition.getTime(), userPosition.getUser().getUserid());
        } catch (UserNotFoundException e) {
            LOG.error("No user found with id: " + userPosition.getUser().getUserid());
            throw e;
        }
        LOG.info("History added to user: " + userPosition.getUser().getUserid());
    }

    @RequestMapping(value = "listHistoryByUserId/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<UserPositionDTO> listHistoryByUser(@PathVariable("userId") String userId) throws DatatypeConfigurationException, UserNotFoundException {
        List<UserPositionDTO> userPositionDTOS;
        try {
            userPositionDTOS = UserPositionDTOConverter.convertToUserPositionDTOList(historyService.listHistoryByUser(userId));
        } catch (UserNotFoundException e) {
            LOG.error("No user found with id: " + userId);
            throw e;
        }
        LOG.info("Path history of user " + userId + "accessed");
        return userPositionDTOS;
    }

    @RequestMapping(value = "getCurrentPositions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<UserPositionDTO> listCurrentPositions() throws DatatypeConfigurationException {
        List<UserPositionDTO> userPositionDTOS;
        userPositionDTOS = UserPositionDTOConverter.convertToUserPositionDTOList(historyService.listCurrentPositions());
        LOG.info("Current position of everybody accessed");
        return userPositionDTOS;
    }

    @RequestMapping(value = "getHistoryByTime/{start}/{end}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<UserPositionDTO> listHistoriesByTimeInterval(@PathVariable("start") Timestamp start, @PathVariable("end") Timestamp end) throws DatatypeConfigurationException {
        List<UserPositionDTO> userPositionDTOS;
        userPositionDTOS = UserPositionDTOConverter.convertToUserPositionDTOList(historyService.listHistoriesByTimeInterval(start, end));
        LOG.info("Positions between " + start + " and " + end + " accessed");
        return userPositionDTOS;
    }

    @RequestMapping(value = "getHistoryByTime/{user}/{start}/{end}")
    public List<UserPositionDTO> listHistoriesByTimeInterval(@PathVariable("start") Timestamp start, @PathVariable("end") Timestamp end, @PathVariable("user") String userId) throws UserNotFoundException, DatatypeConfigurationException {
        List<UserPositionDTO> userPositionDTOS;
        try {
            userPositionDTOS = UserPositionDTOConverter.convertToUserPositionDTOList(historyService.listHistoriesByTimeInterval(start, end, userId));
        } catch (UserNotFoundException e) {
            LOG.error("No user found with id: " + userId);
            throw e;
        } catch (DatatypeConfigurationException e) {
            LOG.error("Timestamp conversion error");
            throw e;
        }
        LOG.info(userId + " positions between " + start + " and " + end + " accessed");
        return userPositionDTOS;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No user found with that id")
    @ExceptionHandler(UserNotFoundException.class)
    public String userNotFoundExceptionHandler() {
        return "No user found with that id";
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid Timestamp")
    @ExceptionHandler(DatatypeConfigurationException.class)
    public String invalidTimestampExceptionHandler() {
        return "Invalid Timestamp";
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not found")
    @ExceptionHandler(NullPointerException.class)
    public String nullPointerExceptionHandler() {
        return "Nothing found in the database";
    }
}
