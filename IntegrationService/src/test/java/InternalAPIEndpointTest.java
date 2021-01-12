import biz.digitalhouse.integration.v3.services.balances.BalanceManager;
import biz.digitalhouse.integration.v3.services.currency.CurrencyManager;
import biz.digitalhouse.integration.v3.services.externalClient.ExternalClient;
import biz.digitalhouse.integration.v3.services.externalClient.ExternalSiteServiceClient;
import biz.digitalhouse.integration.v3.services.players.PlayerManager;
import biz.digitalhouse.integration.v3.services.transactions.PlayTransactionManager;
import biz.digitalhouse.integration.v3.web.ws.http.internalAPIEndpoint.InternalAPIEndpoint;
import junit.framework.TestCase;
import org.mockito.Mockito;

public class InternalAPIEndpointTest extends TestCase {

    private ExternalClient externalClient = Mockito.mock(ExternalClient.class);;
    private PlayerManager playerManager = Mockito.mock(PlayerManager.class);;
    private BalanceManager balanceManager = Mockito.mock(BalanceManager.class);;
    private PlayTransactionManager playTransactionManager = Mockito.mock(PlayTransactionManager.class);;
    private ExternalSiteServiceClient externalSiteServiceClient = Mockito.mock(ExternalSiteServiceClient.class);;

    private InternalAPIEndpoint internalAPIEndpoint = null;

    public InternalAPIEndpointTest(){
        internalAPIEndpoint = new InternalAPIEndpoint(externalClient, playerManager, balanceManager, playTransactionManager, externalSiteServiceClient);
    }


    public void test(){

    }


}
