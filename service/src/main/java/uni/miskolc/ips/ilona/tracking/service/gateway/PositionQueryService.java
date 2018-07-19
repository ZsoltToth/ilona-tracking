package uni.miskolc.ips.ilona.tracking.service.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.GatewayHeader;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import uni.miskolc.ips.ilona.measurement.controller.dto.MeasurementDTO;
import uni.miskolc.ips.ilona.measurement.controller.dto.PositionDTO;

@MessagingGateway(name = "PositionQueryGateway", defaultRequestChannel = "positionQueryRequestChannel")
public interface PositionQueryService {


    @Gateway(headers = {@GatewayHeader(name = "METHOD_NAME", value = "getLocation")}, replyChannel = "getLocationReplyChannel")
    PositionDTO getLocation(@Payload MeasurementDTO measurementDTO);

    @Gateway(headers = {@GatewayHeader(name = "METHOD_NAME", value = "setAlgorithm")}, replyChannel = "setAlgorithmReplyChannel")
    @Payload(("new java.util.Date()"))
    String setPositioningAlgorithm(@Header(name = "algorithm") final String algorithm);
}
