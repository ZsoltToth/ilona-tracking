package uni.miskolc.ips.ilona.tracking.service.impl;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uni.miskolc.ips.ilona.tracking.persist.HistoryDAO;
import uni.miskolc.ips.ilona.tracking.service.HistoryService;
import uni.miskolc.ips.ilona.tracking.service.UserAndDeviceService;

@Configuration
public class SpringTestContext {

    @Bean(autowire = Autowire.BY_NAME)
    public UserAndDeviceService userAndDeviceService() {
        return new UserAndDeviceServiceImpl();
    }

    //TODO
    @Bean(autowire = Autowire.BY_NAME)
    public HistoryDAO historyDAO() {
        return null;
    }

    @Bean(autowire = Autowire.BY_NAME)
    public HistoryService historyService() {
        return new HistoryServiceImpl(historyDAO(), userAndDeviceService());
    }

}
