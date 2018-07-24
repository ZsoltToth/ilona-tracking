package uni.miskolc.ips.ilona.tracking.service.gateway;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.http.HttpMethod;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.http.outbound.HttpRequestExecutingMessageHandler;
import org.springframework.integration.stream.CharacterStreamWritingMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;
import uni.miskolc.ips.ilona.measurement.controller.dto.ZoneDTO;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan
@EnableIntegration
@IntegrationComponentScan("uni.miskolc.ips.ilona.tracking.service.gateway")
@MessageEndpoint
public class ZoneQueryServiceSIConfig {
    private static final Logger LOG = LogManager.getLogger(ZoneQueryServiceSIConfig.class);
    @Autowired
    private Environment env;

    @Router(inputChannel = "zoneQueryRequestChannel")
    public String route(@Header(value = "METHOD_NAME") String methodname) {
        if (methodname.equals("listZones"))
            return "listZonesQueryChannel";
        else if (methodname.equals("getZone")) {
            return "getZoneQueryChannel";
        }
        return "stdErrChannel";
    }

    @Bean
    public MessageChannel zoneQueryRequestChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel zoneQueryChannel() {
        return new DirectChannel();
    }


    @Bean
    public MessageChannel listZonesQueryChannel() {
        return new DirectChannel();
    }


    @Bean
    public MessageChannel getZoneQueryChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel stdErrChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel listZonesReplyChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel getZoneReplyChannel() {
        return new DirectChannel();
    }


    /**
     * TODO std error output
     *
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "stdErrChannel", autoStartup = "true")
    public CharacterStreamWritingMessageHandler logwriter00() {
        LOG.error("Invalid gateway name in PositionQueryRequestChannel");
        return new CharacterStreamWritingMessageHandler(new BufferedWriter(new OutputStreamWriter(System.err)));
    }


    @Bean
    @ServiceActivator(inputChannel = "getZoneQueryChannel")
    public HttpRequestExecutingMessageHandler httpGateway() {

        SpelExpressionParser expressionParser = new SpelExpressionParser();
        Map<String, Expression> uriVariableExpressions = new HashMap<>(1);
        uriVariableExpressions.put("zoneID", expressionParser.parseExpression("headers['zoneID']"));
        HttpRequestExecutingMessageHandler gateway = new HttpRequestExecutingMessageHandler("http://" + System.getProperty("measurement.host") + ":" + System.getProperty("measurement.port") + "/zones/{zoneID}");
        gateway.setUriVariableExpressions(uriVariableExpressions);
        gateway.setHttpMethod(HttpMethod.GET);
        gateway.setExpectedResponseType(ZoneDTO.class);
        gateway.setOutputChannel(getZoneReplyChannel());
        LOG.info("get zone by id query was requested from measurement server with Spring Integration");
        return gateway;
    }

    @Bean
    @ServiceActivator(inputChannel = "listZonesQueryChannel")
    public HttpRequestExecutingMessageHandler listGateway() {


        HttpRequestExecutingMessageHandler gateway = new HttpRequestExecutingMessageHandler("http://" + System.getProperty("measurement.host") + ":" + System.getProperty("measurement.port") + "/listZones");
        gateway.setHttpMethod(HttpMethod.GET);
        gateway.setExpectedResponseType(Collection.class);
        gateway.setOutputChannel(listZonesReplyChannel());
        LOG.info("list Zones query was requested from measurement server with Spring Integration");
        return gateway;
    }
}
