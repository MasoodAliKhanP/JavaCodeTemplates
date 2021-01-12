package biz.digitalhouse.integration.v3.managers;

import biz.digitalhouse.integration.v3.services.externalClient.AuthClient;
import biz.digitalhouse.integration.v3.services.externalClient.DataClient;

/**
 * Created by vitaliy.babenko
 * on 13.03.2017.
 */
public interface ClientManager {

    AuthClient getAuthClient(Long brandID);

    DataClient getDataClient(Long brandID);
}
