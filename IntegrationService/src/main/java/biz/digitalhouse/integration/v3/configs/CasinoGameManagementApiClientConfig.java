package biz.digitalhouse.integration.v3.configs;

import biz.digitalhouse.gamemanagement.webservice.fromWSDL.GameManagementAPI;
import biz.digitalhouse.gamemanagement.webservice.fromWSDL.GameManagementAPILocator;
import biz.digitalhouse.gamemanagement.webservice.fromWSDL.GameManagementService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.rpc.ServiceException;
import java.net.URL;

/**
 * @author Vitalii Babenko
 *         on 29.02.2016.
 */
@Configuration
public class CasinoGameManagementApiClientConfig {

    private final Log log = LogFactory.getLog(getClass());

    @Value("${url.gamemanagement.service}")
    private URL url;

    @Bean
    public GameManagementService gameManagementService() throws ServiceException {

        if (log.isInfoEnabled()) {
            log.info("GameManagementService URL[" + url + "]");
        }
        GameManagementAPI locator = new GameManagementAPILocator();
        GameManagementService server = locator.getGameManagementServiceSoap11(url);

//        org.apache.axis.client.Stub stub = (org.apache.axis.client.Stub) server;
//        stub.setTimeout(1000 * generalSettingDAO.getSettingIntValue(GeneralSettingDAO.GeneralSettingKey.SERVICE_SOCKET_TIMEOUT_SECONDS, 0L));
        return server;
    }
}
