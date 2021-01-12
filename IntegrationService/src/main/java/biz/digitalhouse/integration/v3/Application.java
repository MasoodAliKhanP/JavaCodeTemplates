package biz.digitalhouse.integration.v3;

import biz.digitalhouse.integration.v3.configs.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

/**
 * @author Vitalii Babenko
 *         on 26.02.2016.
 *   ===updated===
 *   spring & spring-cloud
 *   @arbuzov on 09.02.2018.
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableCaching
@ServletComponentScan
@EnableAspectJAutoProxy(proxyTargetClass=true)
@EnableAutoConfiguration(exclude = {JacksonAutoConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ServletRegistrationBean httpServlet() {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(ConfigHttpAPIController.class);

        DispatcherServlet servlet = new DispatcherServlet();
        servlet.setApplicationContext(applicationContext);

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(servlet, "/HTTP/*");
        servletRegistrationBean.setName("api-http-servlet");
        return servletRegistrationBean;
    }

    @Bean
    public ServletRegistrationBean freeRoundsBonusAPISoapServlet() {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(ConfigFreeRoundsBonusAPIEndpoint.class);

        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(servlet, "/SOAP/FreeRoundsBonusAPI/*");
        servletRegistrationBean.setName("freeRoundsBonusAPI-soap-servlet");
        return servletRegistrationBean;
    }

    @Bean
    public ServletRegistrationBean casinoGameAPISoapServlet() {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(ConfigCasinoGameAPIEndpoint.class);

        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(servlet, "/SOAP/CasinoGameAPI/*");
        servletRegistrationBean.setName("casinoGameAPI-soap-servlet");
        return servletRegistrationBean;
    }

    @Bean
    public ServletRegistrationBean bingoGameAPISoapServlet() {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(ConfigBingoGameAPIEndpoint.class);

        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(servlet, "/SOAP/BingoGameAPI/*");
        servletRegistrationBean.setName("bingoGameAPI-soap-servlet");
        return servletRegistrationBean;
    }

    @Bean
    public ServletRegistrationBean internalAPIRestServlet() {
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.register(ConfigInternalAPIEndpoint.class);

        DispatcherServlet servlet = new DispatcherServlet();
        servlet.setApplicationContext(applicationContext);

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(servlet, "/HTTP/InternalAPI/*");
        servletRegistrationBean.setName("internalAPI-rest-servlet");
        return servletRegistrationBean;
    }
}
