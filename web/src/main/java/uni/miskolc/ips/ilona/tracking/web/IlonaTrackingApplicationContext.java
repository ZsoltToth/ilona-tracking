package uni.miskolc.ips.ilona.tracking.web;


import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import uni.miskolc.ips.ilona.tracking.controller.passwordrecovery.*;
import uni.miskolc.ips.ilona.tracking.controller.track.GraphFunctions;
import uni.miskolc.ips.ilona.tracking.controller.track.GraphFunctionsImp;
import uni.miskolc.ips.ilona.tracking.persist.UserAndDeviceDAO;
import uni.miskolc.ips.ilona.tracking.persist.mysql.MySqlAndMybatisUserAndDeviceDAOImplementation;
import uni.miskolc.ips.ilona.tracking.service.UserAndDeviceService;
import uni.miskolc.ips.ilona.tracking.service.impl.UserAndDeviceServiceImpl;
import uni.miskolc.ips.ilona.tracking.util.TrackingModuleCentralManager;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "uni.miskolc.ips.ilona.tracking.controller")
public class IlonaTrackingApplicationContext extends WebMvcConfigurerAdapter {

    @Bean(autowire = Autowire.BY_NAME)
    public UserAndDeviceService userAndDeviceService() {
        return new UserAndDeviceServiceImpl();
    }

    @Bean(autowire = Autowire.BY_NAME)
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
    }

    @Bean(autowire = Autowire.BY_NAME)
    public TrackingModuleCentralManager trackingModuleCentralManager() {
        return new TrackingModuleCentralManager();
    }

    @Bean(autowire = Autowire.BY_NAME)
    public PasswordRecoveryManager passwordRecoveryManager() {
        return new SimplePasswordRecoveryManager();
    }

    @Bean(autowire = Autowire.BY_NAME)
    public GraphFunctions graphFunctions() {
        return new GraphFunctionsImp();
    }

    @Bean(autowire = Autowire.BY_NAME)
    public PasswordTokenSender passwordTokenSender() {
        return new EmailBasedTokenSenderImpl(mailSender());
    }

    @Bean(autowire = Autowire.BY_NAME)
    public JavaMailSender mailSender() {
        return new JavaMailSenderImpl();
    }

    @Bean(autowire = Autowire.BY_NAME)
    public PasswordGenerator passwordGenerator() {
        return new SimpleUUIDBasedPasswordGenerator();
    }

    @Bean
    public InternalResourceViewResolver jspViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setPrefix("/WEB-INF/views/");
        bean.setSuffix(".jsp");
        return bean;
    }

    @Bean
    public UserAndDeviceDAO userAndDeviceDAO() {
        return new MySqlAndMybatisUserAndDeviceDAOImplementation();
    }


    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/img/**").addResourceLocations("/img/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("/fonts/");

    }
}
