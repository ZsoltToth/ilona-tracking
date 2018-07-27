package uni.miskolc.ips.ilona.tracking.service.impl;

import org.junit.Assert;
import org.junit.Before;
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
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringTestContext.class)
public class HistoryServiceImplTest {

    @Autowired
    HistoryService historyService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void addHistory() throws UserNotFoundException, ServiceGeneralErrorException {
        Position position = positionBuilder();
        historyService.addHistory(position, new Timestamp(System.currentTimeMillis()), "1");
    }

    @Ignore
    public void listHistoryByUser() throws UserNotFoundException {
        List<UserPosition> userPositionList = historyService.listHistoryByUser("1");
        Assert.assertNotNull(userPositionList);
    }

    @Ignore
    public void listCurrentPositions() {
        List<UserPosition> userPositions = historyService.listCurrentPositions();
        Assert.assertNotNull(userPositions);
    }

    @Ignore
    public void listHistoriesByTimeInterval() {
        List<UserPosition> userPositions = historyService.listHistoriesByTimeInterval(new Timestamp(12), new Timestamp(System.currentTimeMillis()));
        Assert.assertNotNull(userPositions);
    }

    @Ignore
    public void listHistoriesByTimeInterval1() throws UserNotFoundException {
        List<UserPosition> userPositions = historyService.listHistoriesByTimeInterval(new Timestamp(12), new Timestamp(System.currentTimeMillis()), "1");
        Assert.assertNotNull(userPositions);
    }

    private Position positionBuilder() {
        Position position = new Position();
        position.setUUID(UUID.randomUUID());
        Zone zone = new Zone();
        zone.setId(UUID.randomUUID());
        zone.setName("asd");
        position.setZone(zone);
        Coordinate coordinate = new Coordinate(1, 2, 3);
        position.setCoordinate(coordinate);
        return position;
    }
}