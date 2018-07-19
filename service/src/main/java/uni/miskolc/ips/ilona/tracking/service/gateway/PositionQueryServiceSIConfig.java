package uni.miskolc.ips.ilona.tracking.service.gateway;

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
import uni.miskolc.ips.ilona.measurement.controller.dto.PositionDTO;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan
@EnableIntegration
@IntegrationComponentScan("uni.miskolc.ips.ilona.tracking.service.gateway")
@MessageEndpoint
public class PositionQueryServiceSIConfig {
    @Autowired
    private Environment env;

    @Router(inputChannel = "positionQueryRequestChannel")
    public String route(@Header(value = "METHOD_NAME") String methodname) {
        if (methodname.equals("getLocation"))
            return "getLocationQueryChannel";
        else if (methodname.equals("setAlgorithm")) {
            return "setAlgorithmQueryChannel";
        }
        return "stdErrChannel";
    }

    @Bean
    public MessageChannel positionQueryRequestChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel setAlgorithmQueryChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel getLocationQueryChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel stdErrChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel getLocationReplyChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel setAlgorithmReplyChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "stdErrChannel", autoStartup = "true")
    public CharacterStreamWritingMessageHandler logwriter00() {
        return new CharacterStreamWritingMessageHandler(new BufferedWriter(new OutputStreamWriter(System.err)));
    }

    @Bean
    @ServiceActivator(inputChannel = "setAlgorithmQueryChannel")
    public HttpRequestExecutingMessageHandler httpGateway() {
        SpelExpressionParser expressionParser = new SpelExpressionParser();
        Map<String, Expression> uriVariableExpressions = new HashMap<>(1);
        uriVariableExpressions.put("algorithm", expressionParser.parseExpression("headers['algorithm']"));
        HttpRequestExecutingMessageHandler gateway = new HttpRequestExecutingMessageHandler("http://" + System.getProperty("positioning.host") + ":" + System.getProperty("positioning.port") + "/positioningSetup/{algorithm}");
        gateway.setUriVariableExpressions(uriVariableExpressions);
        gateway.setHttpMethod(HttpMethod.GET);
        gateway.setExpectedResponseType(String.class);
        gateway.setOutputChannel(setAlgorithmReplyChannel());
        return gateway;
    }

    @Bean
    @ServiceActivator(inputChannel = "getLocationQueryChannel")
    public HttpRequestExecutingMessageHandler locationGateway() {
        //System.out.println("fsaz");

        //SpelExpressionParser expressionParser = new SpelExpressionParser();

        //System.out.println(expressionParser.parseExpression("payload").getValue());


        HttpRequestExecutingMessageHandler gateway = new HttpRequestExecutingMessageHandler("http://" + System.getProperty("positioning.host") + ":" + System.getProperty("positioning.port") + "/getLocation");
        gateway.setHttpMethod(HttpMethod.GET);
        gateway.setExpectedResponseType(PositionDTO.class);
        gateway.setOutputChannel(getLocationReplyChannel());
        return gateway;
    }
}
