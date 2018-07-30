package uni.miskolc.ips.ilona.tracking.service.impl;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uni.miskolc.ips.ilona.measurement.model.position.Coordinate;
import uni.miskolc.ips.ilona.measurement.model.position.Position;
import uni.miskolc.ips.ilona.measurement.model.position.Zone;
import uni.miskolc.ips.ilona.tracking.model.UserPosition;
import uni.miskolc.ips.ilona.tracking.service.HistoryService;
import uni.miskolc.ips.ilona.tracking.service.exceptions.ServiceGeneralErrorException;
import uni.miskolc.ips.ilona.tracking.service.exceptions.UserNotFoundException;

import java.sql.Timestamp;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringTestContext.class)
public class HistoryServiceImplTest {

    @Autowired
    HistoryService historyService;


    @Test
    public void addHistory() throws UserNotFoundException, ServiceGeneralErrorException {
        Position position = positionBuilder();
        historyService.addHistory(position, new Timestamp(System.currentTimeMillis()), "1");
    }

    @Ignore
    public void listHistoryByUser() throws UserNotFoundException {
        List<UserPosition> userPositionList = historyService.listHistoryByUser("1");
        Assert.assertTrue(userPositionList.size() > 0);
    }

    @Ignore
    public void listCurrentPositions() {
        List<UserPosition> userPositions = historyService.listCurrentPositions();
        Assert.assertTrue(userPositions.size() > 0);
    }

    @Ignore
    public void listHistoriesByTimeInterval() {
        List<UserPosition> userPositions = historyService.listHistoriesByTimeInterval(new Timestamp(12), new Timestamp(System.currentTimeMillis()));
        Assert.assertTrue(userPositions.size() > 0);
    }

    @Ignore
    public void listHistoriesByTimeInterval1() throws UserNotFoundException {
        List<UserPosition> userPositions = historyService.listHistoriesByTimeInterval(new Timestamp(12), new Timestamp(System.currentTimeMillis()), "1");
        Assert.assertTrue(userPositions.size() > 0);
    }

    private Position positionBuilder() {
        Zone zone = new Zone("DummyZone");
        Coordinate coordinate = new Coordinate(1, 2, 3);
        Position position = new Position(coordinate, zone);
        return position;
    }
}