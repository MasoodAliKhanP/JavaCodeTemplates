package biz.digitalhouse.integration.v3.configs;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author Vitalii Babenko
 *         on 19.04.2016.
 */
@Configuration
public class MySQLConfig {

    private static final Log log = LogFactory.getLog(MySQLConfig.class);

    @Value("${mysql.datasource.URL}")
    private String URL;

    @Value("${mysql.datasource.user}")
    private String user;

    @Value("${mysql.datasource.password}")
    private String password;

    @Bean(name = "mysqlDataSource")
    public DataSource dataSource()  {

        MysqlDataSource dataSource = new MysqlConnectionPoolDataSource();

        dataSource.setURL(URL);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        if(log.isInfoEnabled()) {
            log.info("MySQLDataSource was created successfully.");
        }

        return dataSource;
    }
}
