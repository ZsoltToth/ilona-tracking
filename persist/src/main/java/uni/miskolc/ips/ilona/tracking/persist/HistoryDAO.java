package uni.miskolc.ips.ilona.tracking.persist;

import uni.miskolc.ips.ilona.tracking.model.UserPosition;
import uni.miskolc.ips.ilona.tracking.persist.exception.UserNotFoundException;

import java.util.List;

public interface HistoryDAO {
    void createHistory(UserPosition userPosition) throws UserNotFoundException;

    List<UserPosition> readHistories();

    void updateHistory(UserPosition userPosition) throws UserNotFoundException;

    void deleteHistory(UserPosition userPosition);

    void deleteHistory(String userId);

    List<UserPosition> readHistoryByUserID(String userID) throws UserNotFoundException;

    List<UserPosition> ReadCurrentPositions();
}
