package biz.digitalhouse.integration.v3.services.balances;

import biz.digitalhouse.integration.v3.dao.redis.CustomRedisCache;
import biz.digitalhouse.integration.v3.model.Balance;
import biz.digitalhouse.integration.v3.model.Player;
import biz.digitalhouse.integration.v3.services.externalClient.ExternalClient;
import biz.digitalhouse.integration.v3.services.externalClient.dto.BalanceResult;
import biz.digitalhouse.integration.v3.services.players.PlayerManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by vitaliy.babenko
 * on 31.08.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BalanceManagerImplTest {

    @Mock
    private ExternalClient externalClient;

    @Mock
    private PlayerManager playerManager;

    @Autowired
    private CustomRedisCache customRedisCache;

    private BalanceManagerImpl balanceManager = null;

    @Before
    public void setUp() throws Exception {
        Mockito.reset(externalClient);
        balanceManager = new BalanceManagerImpl(externalClient, playerManager, customRedisCache);
    }

    @Test
    public void getBalanceSerial() throws Exception {
        Mockito.when(externalClient.balance(1, "TB", "2")).thenReturn(new BalanceResult("TB", 0.1, 0.1));
        Mockito.when(playerManager.getOnlinePlayer(1)).thenReturn(
                new Player(2L, "2", 1L, "Vasya"));

        balanceManager.getBalance(1, 1, "TB");
        balanceManager.getBalance(1, 1, "TB");
        balanceManager.getBalance(1, 1, "TB");
        Mockito.verify(externalClient, Mockito.times(1)).balance(1, "TB", "2");
    }

    @Test
    public void getBalancePut() throws Exception {
        long playerID = 41111;
        Balance balance = new Balance(playerID, "TB", 3, 2);
        balanceManager.putBalance(balance);

        balanceManager.getBalance(3, playerID, "TB");
        balanceManager.getBalance(3, playerID, "TB");
        Mockito.verify(externalClient, Mockito.times(0)).balance(1, "TB", "2");
    }

    @Test
    public void getBalance() throws Exception {

        long playerID = 31;
        Mockito.when(playerManager.getOnlinePlayer(playerID)).thenReturn(new Player(playerID, "2", 1,  "Vasya"));
        Mockito.when(externalClient.balance(1, "TB", "2")).thenReturn(new BalanceResult("TB", 0.1, 0.1));

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                balanceManager.getBalance(1, playerID, "TB");
            }
        });
        t1.start();

        Thread t2 =  new Thread(new Runnable() {
            @Override
            public void run() {
                balanceManager.getBalance(1, playerID, "TB");
            }
        });
        t2.start();

        balanceManager.getBalance(1, playerID, "TB");

        t1.join();
        t2.join();

        Mockito.verify(externalClient, Mockito.atLeast(0)).balance(1, "TB", "2");
    }

    @Test
    public void getBalanceException() throws Exception {
        long playerID = 2;

        Answer<BalanceResult> answer = new Answer<BalanceResult>() {
            @Override
            public BalanceResult answer(InvocationOnMock invocationOnMock) throws Throwable {
                Thread.sleep(300);
                throw new Exception("Test Exception");
            }
        };

        Mockito.when(playerManager.getOnlinePlayer(playerID)).thenReturn(new Player(playerID, "2", 1,  "Vasya"));
        Mockito.when(externalClient.balance(1, "TB", "2")).thenAnswer(answer);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                balanceManager.getBalance(1, playerID, "TB");
            }
        });
        t1.start();

        Thread t2 =  new Thread(new Runnable() {
            @Override
            public void run() {
                balanceManager.getBalance(1, playerID, "TB");
            }
        });
        t2.start();

        balanceManager.getBalance(1, playerID, "TB");

        t1.join();
        t2.join();

        Mockito.verify(externalClient, Mockito.times(0)).balance(1, "TB", "2");
    }
}