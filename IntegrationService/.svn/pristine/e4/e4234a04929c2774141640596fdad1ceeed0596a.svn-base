package biz.digitalhouse.integration.v3.services.players;

import biz.digitalhouse.integration.v3.DefaultValues;
import biz.digitalhouse.integration.v3.ServiceResponseCode;
import biz.digitalhouse.integration.v3.dao.redis.CustomRedisCache;
import biz.digitalhouse.integration.v3.model.Player;
import biz.digitalhouse.integration.v3.services.userservice.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Vitalii Babenko
 *         on 28.02.2016.
 */
@Service
public class PlayerManagerImpl implements PlayerManager {

    private final Log log = LogFactory.getLog(getClass());
    private CustomRedisCache customRedisCache;
    private UserService userService;

    @Autowired
    public PlayerManagerImpl(CustomRedisCache customRedisCache, UserService userService) {
        this.customRedisCache = customRedisCache;
        this.userService = userService;
    }

    public Player getPlayer(long brandId, String externalPlayerId) throws Exception {

        GetExternalPlayerResponse player = userService.getExternalPlayerByExtID(brandId, externalPlayerId);
        return new Player(player.getMemberID(), externalPlayerId, brandId, player.getNickname());
    }

    @Override
    public Player checkAndRegister(long brandId, String externalPlayerId, String currency, String nickname) throws Exception {

        GetExternalPlayerResponse player = userService.getExternalPlayerByExtID(brandId, externalPlayerId);

        if (UserService.CODE.MemberIsNotFound.equalCode(player)) {
            if (log.isDebugEnabled()) {
                log.debug("Player externalPlayerID[" + externalPlayerId + "] not found for brand[" + brandId + "]. Player will be created.");
            }

            if(nickname == null || nickname.trim().isEmpty()) {
                throw ServiceResponseCode.WRONG_FIELD.setMessage("nickname").EXCEPTION();
            }

            CreateExternalPlayerResponse createPlayer = userService.createExternalPlayer(brandId, externalPlayerId,
                    nickname, externalPlayerId + "@example.com", nickname, nickname, currency, DefaultValues.language);

            if (UserService.CODE.CurrencyCodeIsIncorrectOrUnsupported.equalCode(createPlayer)) {
                log.error("Currency[" + currency + "] is not supported in brand[" + brandId + "]");
                throw ServiceResponseCode.CURRENCY_NOT_ALLOWED.EXCEPTION();
            }

            if (!UserService.CODE.OK.equalCode(createPlayer)) {
                log.error("Registration error: " + createPlayer.getError() + ":" + createPlayer.getDescription());
                throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
            }

            return new Player(createPlayer.getMemberID(), externalPlayerId, brandId, nickname);

        } else if(nickname != null && !nickname.trim().isEmpty() && !player.getNickname().equals(nickname)){

            UpdateExternalPlayerResponse updatePlayer = userService.updateExternalPlayer(player.getMemberID(), nickname, player.getEmail(),
                    player.getFirstName(), player.getLastName(), player.getLanguage());

            if (!UserService.CODE.OK.equalCode(updatePlayer)) {
                log.error("Change nickname error: " + updatePlayer.getError() + ":" + updatePlayer.getDescription());
                throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
            }
        }

        return new Player(player.getMemberID(), externalPlayerId, brandId, player.getNickname());
    }

    public void putOnlinePlayer(Player player) {
        if(player == null) {
            throw new IllegalArgumentException("Player is null");
        }

        customRedisCache.set("integrations:service:onlinePlayerCache:" + player.getPlayerID(), player,300);
//        cacheManager.getCache("onlinePlayerCache").put(player.getPlayerID(), player);
    }

    public Player getOnlinePlayer(long playerID) throws Exception {
//        Player player = cacheManager.getCache("onlinePlayerCache").get(playerId, Player.class);
        Player player = customRedisCache.get("integrations:service:onlinePlayerCache:" + playerID, Player.class);

        if(player == null) {
            if(log.isDebugEnabled()) {
                log.debug("Player not found in online cache. Load player from DB");
            }
            GetExternalPlayerResponse pl = userService.getExternalPlayer(playerID);
            player = new Player(pl.getMemberID(), pl.getExternalPlayerID(), pl.getCasinoID(), pl.getNickname());
            putOnlinePlayer(player);
        }
        return player;
    }

    public boolean banPlayer(long brandID, String externalPlayerID, Boolean ban) throws Exception {

        BanPlayerResponse banPlayer = userService.banPlayerByExtID(brandID, externalPlayerID, ban);
        if(UserService.CODE.OK.equalCode(banPlayer)) {
            return banPlayer.getBan();
        } else if (UserService.CODE.MemberIsNotFound.equalCode(banPlayer)) {
            throw ServiceResponseCode.PLAYER_NOT_FOUND.EXCEPTION();
        } else {
            log.error("Ban player error: " + banPlayer.getError() + ":" + banPlayer.getDescription());
            throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
        }
    }

    @Override
    public void changeNickname(long brandID, String externalPlayerID, String nickname) throws Exception {

        GetExternalPlayerResponse player = userService.getExternalPlayerByExtID(brandID, externalPlayerID);

        if (UserService.CODE.MemberIsNotFound.equalCode(player)) {
            throw ServiceResponseCode.PLAYER_NOT_FOUND.EXCEPTION();
        } else if (!UserService.CODE.OK.equalCode(player)) {
            log.error("Change nickname error: " + player.getError() + ":" + player.getDescription());
            throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
        }

        UpdateExternalPlayerResponse updatePlayer = userService.updateExternalPlayer(player.getMemberID(), nickname, player.getEmail(),
                player.getFirstName(), player.getLastName(), player.getLanguage());

        if (!UserService.CODE.OK.equalCode(updatePlayer)) {
            log.error("Change nickname error: " + updatePlayer.getError() + ":" + updatePlayer.getDescription());
            throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
        }
    }

}
