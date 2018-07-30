package uni.miskolc.ips.ilona.tracking.persist;

import uni.miskolc.ips.ilona.tracking.model.UserPosition;
import uni.miskolc.ips.ilona.tracking.persist.exception.UserNotFoundInDatabaseException;

import java.util.List;

public interface HistoryDAO {
    void createHistory(UserPosition userPosition) throws UserNotFoundInDatabaseException;

    List<UserPosition> readHistories();

    void updateHistory(UserPosition userPosition) throws UserNotFoundInDatabaseException;

    void deleteHistory(UserPosition userPosition);

    void deleteHistory(String userId);

    List<UserPosition> readHistoryByUserID(String userID) throws UserNotFoundInDatabaseException;

    List<UserPosition> readCurrentPositions();
}
