package uni.miskolc.ips.ilona.tracking.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import uni.miskolc.ips.ilona.measurement.model.position.Position;
import uni.miskolc.ips.ilona.tracking.model.UserPosition;
import uni.miskolc.ips.ilona.tracking.persist.HistoryDAO;
import uni.miskolc.ips.ilona.tracking.persist.exception.UserNotFoundException;
import uni.miskolc.ips.ilona.tracking.service.HistoryService;
import uni.miskolc.ips.ilona.tracking.service.UserAndDeviceService;
import uni.miskolc.ips.ilona.tracking.service.exceptions.ServiceGeneralErrorException;

import java.sql.Timestamp;
import java.util.List;

public class HistoryServiceImpl implements HistoryService {

    private static final Logger LOG = LogManager.getLogger(HistoryServiceImpl.class);

    private HistoryDAO historyDAO;
    private UserAndDeviceService userAndDeviceService;

    @Autowired
    public HistoryServiceImpl(HistoryDAO historyDAO, UserAndDeviceService userAndDeviceService) {
        this.historyDAO = historyDAO;
        this.userAndDeviceService = userAndDeviceService;
    }


    @Override
    public void addHistory(Position position, Timestamp timestamp, String userId) throws uni.miskolc.ips.ilona.tracking.service.exceptions.UserNotFoundException, ServiceGeneralErrorException {
        UserPosition userPosition = new UserPosition();
        userPosition.setTime(timestamp);
        userPosition.setPosition(position);
        userPosition.setUser(userAndDeviceService.getUser(userId));

        try {
            historyDAO.createHistory(userPosition);
        } catch (UserNotFoundException e) {
            LOG.error("This user cannot be found:" + userId);
            throw new uni.miskolc.ips.ilona.tracking.service.exceptions.UserNotFoundException("This user cannot be found:" + userId);
        }
        LOG.info("History Added to user: " + userPosition.getUser().getUserid());
    }

    @Override
    public List<UserPosition> listHistoryByUser(String userId) throws uni.miskolc.ips.ilona.tracking.service.exceptions.UserNotFoundException {
        List<UserPosition> userPositions = null;
        try {
            userPositions = historyDAO.readHistoryByUserID(userId);
        } catch (UserNotFoundException e) {
            throw new uni.miskolc.ips.ilona.tracking.service.exceptions.UserNotFoundException("This user cannot be found:" + userId);
        }
        return userPositions;
    }

    @Override
    public List<UserPosition> listCurrentPositions() {
        return historyDAO.ReadCurrentPositions();
    }

    //TODO dao methods not yet created
    @Override
    public List<UserPosition> listHistoriesByTimeInterval(Timestamp start, Timestamp end) {
        return null;
    }

    @Override
    public List<UserPosition> listHistoriesByTimeInterval(Timestamp start, Timestamp end, String userId) {
        return null;
    }
}
