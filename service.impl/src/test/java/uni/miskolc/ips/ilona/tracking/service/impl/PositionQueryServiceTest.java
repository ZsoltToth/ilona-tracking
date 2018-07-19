package uni.miskolc.ips.ilona.tracking.service.impl;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uni.miskolc.ips.ilona.measurement.controller.dto.MeasurementDTO;
import uni.miskolc.ips.ilona.measurement.controller.dto.PositionDTO;
import uni.miskolc.ips.ilona.tracking.service.gateway.PositionQueryService;
import uni.miskolc.ips.ilona.tracking.service.gateway.PositionQueryServiceSIConfig;

import javax.xml.datatype.DatatypeConfigurationException;
import java.io.IOException;
import java.util.UUID;

//-ea -Dpositioning.host=localhost -Dpositioning.port=8089
public class PositionQueryServiceTest {
    private PositionQueryService positionQueryService;
    @Before
    public void setUp(){
        ApplicationContext context = new AnnotationConfigApplicationContext(PositionQueryServiceSIConfig.class);
        positionQueryService = context.getBean("PositionQueryGateway", PositionQueryService.class);
    }

    @Ignore
    public void setAlgorithmTest() {
        String response = positionQueryService.setPositioningAlgorithm("knn");
        //System.out.println(response);
    }

    @Test
    public void getLocation() throws IOException, DatatypeConfigurationException {
        MeasurementDTO measurementDTO = new MeasurementDTO();
        measurementDTO.setId(UUID.randomUUID().toString());
        PositionDTO positionDTO1 = new PositionDTO();
        positionDTO1.setId("1");
        measurementDTO.setPosition(positionDTO1);

        PositionDTO positionDTO = positionQueryService.getLocation(measurementDTO);
        System.out.println(positionDTO.getId());
    }


}
