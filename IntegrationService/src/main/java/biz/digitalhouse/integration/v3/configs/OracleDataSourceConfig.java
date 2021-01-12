package biz.digitalhouse.integration.v3.configs;

import oracle.jdbc.pool.OracleDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Vitalii Babenko
 *         on 28.02.2016.
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
    private String MinLimit;

    @Value("${oracle.datasource.connectionCacheProperties.MaxLimit}")
    private String MaxLimit;

    @Value("${oracle.datasource.connectionCacheProperties.InitialLimit}")
    private String InitialLimit;

    @Value("${oracle.datasource.connectionCacheProperties.ConnectionWaitTimeout}")
    private String ConnectionWaitTimeout;

    @Value("${oracle.datasource.connectionCacheProperties.InactivityTimeout}")
    private String InactivityTimeout;

    @Value("${oracle.datasource.connectionCacheProperties.ValidateConnection}")
    private String ValidateConnection;


    public void OracleDataSourceConfig()
    {
        System.out.println("#########################################################");
        System.out.println("oracle.datasource.URL: " + URL);
        System.out.println("#########################################################");

    }

    @Primary
    @Bean
    @ConfigurationProperties(prefix="datasource.primary")
    public DataSource dataSource() throws SQLException {
        OracleDataSource dataSource = new OracleDataSource();

        dataSource.setConnectionCachingEnabled(connectionCachingEnabled);
        dataSource.setURL(URL);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        Properties properties = new Properties();
        properties.put("MinLimit", MinLimit);
        properties.put("MaxLimit", MaxLimit);
        properties.put("InitialLimit", InitialLimit);
        properties.put("ConnectionWaitTimeout", ConnectionWaitTimeout);
        properties.put("InactivityTimeout", InactivityTimeout);
        properties.put("ValidateConnection", ValidateConnection);
        dataSource.setConnectionCacheProperties(properties);

        if(log.isInfoEnabled()) {
            log.info("OracleDataSource was created successfully.");
        }

        return dataSource;
    }
}
