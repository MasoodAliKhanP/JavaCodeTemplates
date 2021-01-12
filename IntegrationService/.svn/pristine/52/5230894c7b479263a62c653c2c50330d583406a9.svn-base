package biz.digitalhouse.integration.v3.services.balances;

import biz.digitalhouse.integration.v3.dao.redis.CustomRedisCache;
import biz.digitalhouse.integration.v3.model.Balance;
import biz.digitalhouse.integration.v3.model.Player;
import biz.digitalhouse.integration.v3.services.externalClient.ExternalClient;
import biz.digitalhouse.integration.v3.services.externalClient.dto.BalanceResult;
import biz.digitalhouse.integration.v3.services.players.PlayerManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Vitalii Babenko
 *         on 01.04.2016.
 */
@Service
public class BalanceManagerImpl implements BalanceManager {

    private static final Log log = LogFactory.getLog(BalanceManager.class);
    private ExternalClient externalClient;
    private PlayerManager playerManager;
    private CustomRedisCache customRedisCache;

    @Autowired
    public BalanceManagerImpl(ExternalClient externalClient, PlayerManager playerManager, CustomRedisCache customRedisCache) {
        this.externalClient = externalClient;
        this.playerManager = playerManager;
        this.customRedisCache = customRedisCache;
    }

    @Override
    public Balance putBalance(Balance balance) {
        customRedisCache.set("integrations:service:" + balance.getPlayerId() + balance.getVendorId(), balance, 10);
        return balance;
    }

    @Override
    public Balance getBalance(long brandId, long playerId, String vendorId) {
        Balance bal = customRedisCache.get("integrations:service:" + brandId + vendorId, Balance.class);
        if (bal != null){
            return bal;
        }

        try {
            Player player = playerManager.getOnlinePlayer(playerId);
            BalanceResult dto = externalClient.balance(brandId, vendorId, player.getExternalPlayerID());
            if (dto != null) {
                return putBalance(new Balance(playerId, vendorId, dto.getCash(), dto.getBonus()));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

}
