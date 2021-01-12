package biz.digitalhouse.integration.v3.services.externalClient.connectionManager;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.stereotype.Component;

/**
 * Created by kirill.arbuzov on 24/10/2016.
 */
@Component
public class ConnectionPoolFactory {

    private PoolingHttpClientConnectionManager connectionManager;

    public ConnectionPoolFactory() {
        this.connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(5000);
        connectionManager.setValidateAfterInactivity(10000);
    }

    public PoolingHttpClientConnectionManager getConnectionManager(){
        return connectionManager;
    }
}
