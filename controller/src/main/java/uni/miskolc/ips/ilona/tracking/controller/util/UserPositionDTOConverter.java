package uni.miskolc.ips.ilona.tracking.controller.util;

import uni.miskolc.ips.ilona.measurement.model.position.Coordinate;
import uni.miskolc.ips.ilona.measurement.model.position.Position;
import uni.miskolc.ips.ilona.measurement.model.position.Zone;
import uni.miskolc.ips.ilona.tracking.controller.model.CoordinateDTO;
import uni.miskolc.ips.ilona.tracking.controller.model.PositionDTO;
import uni.miskolc.ips.ilona.tracking.controller.model.UserPositionDTO;
import uni.miskolc.ips.ilona.tracking.controller.model.ZoneDTO;
import uni.miskolc.ips.ilona.tracking.model.UserData;
import uni.miskolc.ips.ilona.tracking.model.UserPosition;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

public class UserPositionDTOConverter {

    public static UserPosition convertToUserPosition(UserPositionDTO userPositionDTO) {
        UserPosition userPosition = new UserPosition();
        UserData userData = new UserData();
        userData.setUserid(userPositionDTO.getUserId());
        userPosition.setUser(userData);
        userPosition.setPosition(convertToPosition(userPositionDTO.getPosition()));
        userPosition.setTime(new Timestamp(userPositionDTO.getTime().toGregorianCalendar().getTimeInMillis()));
        return userPosition;
    }

    private static Position convertToPosition(PositionDTO positionDTO) {
        Position position = new Position();
        position.setUUID(UUID.fromString(positionDTO.getId()));
        Coordinate coordinate = new Coordinate();
        coordinate.setX(position.getCoordinate().getX());
        coordinate.setY(position.getCoordinate().getY());
        coordinate.setZ(position.getCoordinate().getZ());
        position.setCoordinate(coordinate);
        Zone zone = new Zone();
        zone.setId(UUID.fromString(positionDTO.getZone().getId()));
        zone.setName(positionDTO.getZone().getName());
        position.setZone(zone);
        return position;
    }

    public static UserPositionDTO convertToUserPositionDTO(UserPosition userPosition) throws DatatypeConfigurationException {
        UserPositionDTO userPositionDTO = new UserPositionDTO();
        userPositionDTO.setPosition(convertToPositionDTO(userPosition.getPosition()));
        userPositionDTO.setTime(convertToXMLXmlGregorianCalendar(userPosition.getTime()));
        userPositionDTO.setUserId(userPosition.getUser().getUserid());
        return userPositionDTO;
    }

    public static List<UserPositionDTO> convertToUserPositionDTOList(List<UserPosition> userPositions) throws DatatypeConfigurationException {
        List<UserPositionDTO> userPositionDTOS = new ArrayList<>();
        for (UserPosition userPosition : userPositions) {
            userPositionDTOS.add(convertToUserPositionDTO(userPosition));
        }
        return userPositionDTOS;
    }

    private static PositionDTO convertToPositionDTO(Position position) {
        PositionDTO positionDTO = new PositionDTO();
        positionDTO.setId(position.getUUID().toString());
        ZoneDTO zoneDTO = new ZoneDTO();
        zoneDTO.setId(position.getZone().getId().toString());
        zoneDTO.setName(position.getZone().getName());
        positionDTO.setZone(zoneDTO);
        CoordinateDTO coordinateDTO = new CoordinateDTO();
        coordinateDTO.setX(position.getCoordinate().getX());
        coordinateDTO.setY(position.getCoordinate().getY());
        coordinateDTO.setZ(position.getCoordinate().getZ());
        positionDTO.setCoordinate(coordinateDTO);
        return positionDTO;
    }

    private static XMLGregorianCalendar convertToXMLXmlGregorianCalendar(Timestamp timestamp) throws DatatypeConfigurationException {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeInMillis(timestamp.getTime());
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
    }
}
