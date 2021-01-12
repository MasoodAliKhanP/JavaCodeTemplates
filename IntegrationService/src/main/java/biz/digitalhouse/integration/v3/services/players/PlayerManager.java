package biz.digitalhouse.integration.v3.services.players;

import biz.digitalhouse.integration.v3.model.Player;

/**
 * @author Vitalii Babenko
 *         on 28.02.2016.
 */
public interface PlayerManager {

    String SYSTEM_NOT_FOUND = "SYSTEM_NOT_FOUND";
    String DUPLICATE_ACCOUNT = "DUPLICATE_ACCOUNT";
    String MEMBER_INVALID = "MEMBERID_INVALID";
    String NICKNAME_DUPLICATE = "NICKNAME_DUPLICATE";
    String EMAIL_DUPLICATE = "EMAIL_DUPLICATE";
    String MEMBER_DUPLICATE = "MEMBER_DUPLICATE";

    Player getPlayer(long brandId, String externalPlayerId) throws Exception;

    Player checkAndRegister(long brandId, String externalPlayerId, String currency, String nickname) throws Exception;

    void putOnlinePlayer(Player player);

    Player getOnlinePlayer(long playerID) throws Exception;

    boolean banPlayer(long brandID, String externalPlayerID, Boolean ban) throws Exception;

    void changeNickname(long brandID, String externalPlayerID, String newNickname) throws Exception;
}
