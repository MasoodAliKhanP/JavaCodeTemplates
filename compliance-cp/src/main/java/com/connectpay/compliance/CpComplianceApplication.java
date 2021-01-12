/*
 * 
 */
package com.connectpay.compliance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

// TODO: Auto-generated Javadoc
/**
 * The Class CpComplianceApplication.
 */
@SpringBootApplication(exclude = { SessionAutoConfiguration.class }, scanBasePackages = { "com.connectpay" })
@EnableDiscoveryClient
@EnableFeignClients
public class CpComplianceApplication extends SpringBootServletInitializer {

    /**
     * Configure.
     *
     * @param builder the builder
     * @return the spring application builder
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CpComplianceApplication.class);
    }

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(CpComplianceApplication.class, args);
    }

}
