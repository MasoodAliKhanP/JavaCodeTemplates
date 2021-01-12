package com.octopusgaming.backoffice.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import oracle.jdbc.pool.OracleDataSource;

/**
 * Created by Pathan Masood Ali Khan on 01-Jul-20.
 */
@Configuration
public class OracleDataSourceConfig {
    private static final Log log = LogFactory.getLog(OracleDataSourceConfig.class);

    @Value("${oracle.datasource.URL}")
    private String URL;
    @Value("${oracle.datasource.user}")
    private String user;
    @Value("${oracle.datasource.password}")
    private String password;
    @Value("${oracle.datasource.connectionCachingEnabled}")
    private boolean connectionCachingEnabled;
    @Value("${oracle.datasource.connectionCacheProperties.MinLimit}")
    private String minLimit;
    @Value("${oracle.datasource.connectionCacheProperties.MaxLimit}")
    private String maxLimit;
    @Value("${oracle.datasource.connectionCacheProperties.InitialLimit}")
    private String initialLimit;
    @Value("${oracle.datasource.connectionCacheProperties.ConnectionWaitTimeout}")
    private String connectionWaitTimeout;
    @Value("${oracle.datasource.connectionCacheProperties.InactivityTimeout}")
    private String inactivityTimeout;
    @Value("${oracle.datasource.connectionCacheProperties.ValidateConnection}")
    private String validateConnection;


    public OracleDataSourceConfig()
    {
        System.out.println("#########################################################");
        System.out.println("oracle.datasource.URL: " + URL);
        System.out.println("#########################################################");

    }
    
    @Bean(destroyMethod = "close")
    @Primary
    public DataSource dataSource() throws SQLException {
        OracleDataSource dataSource = new OracleDataSource();
        
        log.debug("oracle url: " + URL + " user: " +  user);
        dataSource.setConnectionCachingEnabled(connectionCachingEnabled);
        dataSource.setURL(URL);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        Properties properties = new Properties();
        properties.put("MinLimit", minLimit);
        properties.put("MaxLimit", maxLimit);
        properties.put("InitialLimit", initialLimit);
        properties.put("ConnectionWaitTimeout", connectionWaitTimeout);
        properties.put("InactivityTimeout", inactivityTimeout);
        properties.put("ValidateConnection", validateConnection);
        dataSource.setConnectionCacheProperties(properties);

        if(log.isInfoEnabled()) {
            log.info("OracleDataSource was created successfully.");
        }

        return dataSource;
    }
}
