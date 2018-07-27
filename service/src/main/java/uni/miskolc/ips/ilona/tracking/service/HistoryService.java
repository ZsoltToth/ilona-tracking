package uni.miskolc.ips.ilona.tracking.service;

import uni.miskolc.ips.ilona.measurement.model.measurement.Measurement;
import uni.miskolc.ips.ilona.tracking.model.UserPosition;
import uni.miskolc.ips.ilona.tracking.service.exceptions.UserNotFoundException;

import java.sql.Timestamp;
import java.util.List;

public interface HistoryService {

    void addHistory(Measurement measurement, String userId);

    List<UserPosition> listHistoryByUser(String userId) throws UserNotFoundException;

    List<UserPosition> listCurrentPositions();

    List<UserPosition> listHistoriesByTimeInterval(Timestamp start, Timestamp end);

    List<UserPosition> listHistoriesByTimeInterval(Timestamp start, Timestamp end, String userId) throws UserNotFoundException;
}
