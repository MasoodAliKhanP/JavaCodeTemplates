package biz.digitalhouse.integration.v3.services.freeRoundsBonus;

import biz.digitalhouse.integration.v3.ServiceResponseCode;
import biz.digitalhouse.integration.v3.exceptions.CommonServiceException;
import biz.digitalhouse.integration.v3.exceptions.FRBServiceException;
import biz.digitalhouse.integration.v3.model.Player;
import biz.digitalhouse.integration.v3.services.freeRoundsBonus.dto.*;
import biz.digitalhouse.integration.v3.services.players.PlayerManager;
import biz.digitalhouse.integration.v3.utils.StringUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * @author Vitalii Babenko
 *         on 28.02.2016.
 */
@Service
public class FreeRoundsBonusManagerImpl implements FreeRoundsBonusManager {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private final Log log = LogFactory.getLog(getClass());
    private FRBServiceClient frbServiceClient;
    private PlayerManager playerManager;

    @Autowired
    public FreeRoundsBonusManagerImpl(FRBServiceClient frbServiceClient, PlayerManager playerManager) {
        this.frbServiceClient = frbServiceClient;
        this.playerManager = playerManager;
    }
    
    @Override
    public List<String[]> getFreeRoundsApplicableGames(long brandId,String technology) throws CommonServiceException {



        ResultFreeRoundsGames response = frbServiceClient.getFreeRoundsApplicableGames(brandId,technology);
        if(response == null) {
            throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
        }
        if(response.getStatus() > 0) {
            throw new FRBServiceException(response.getStatus(), response.getDescription());
        }
        return response.getGames();
    }
    
    @Override
    public String awardFRB(long brandId, String externalPlayerId, String currency, String nickname, String gamesList,
                           int rounds, String bonusCode, double coinValue, Calendar expirationDate) throws Exception {


        Player player  = playerManager.checkAndRegister(brandId, externalPlayerId, currency, nickname);

        String date = expirationDate != null ? DATE_FORMAT.format(expirationDate.getTime()) : null;
        ResultAwardFRBonus response = frbServiceClient.createFRB(brandId, player.getPlayerID(), bonusCode, rounds, gamesList,coinValue, date);
        if(response == null) {
            log.error("Couldn't create FRB by code " + bonusCode + ", brand = " + brandId + ". Response from FRBService is null");
            throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
        }
        if(response.getStatus() > 0) {
            if(log.isWarnEnabled()) {
                log.warn("Award FRB by bonusCode[" + bonusCode + "] and brand[" + brandId + "] returned incorrect status. Response:" + response);
            }
            throw new FRBServiceException(response.getStatus(), response.getDescription());
        }
        return response.getFreeRoundBonusID();
    }

    @Override
    public String checkFRB(long brandId, String bonusCode) throws CommonServiceException {

        ResultCheckFRBonus response = frbServiceClient.checkFRB(brandId, bonusCode);
        if (response == null) {
            log.error("Couldn't check FRB by bonusCode[" + bonusCode + "], brand[" + brandId + ". Response from FRBService is null");
            throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
        }
        if (response.getStatus() > 0) {
            if(log.isWarnEnabled()) {
                log.warn("Check FRB by bonusCode[" + bonusCode + "] and brand[" + brandId + "] returned incorrect status. Response:" + response);
            }
            throw new FRBServiceException(response.getStatus(), response.getDescription());
        }
        if(!StringUtils.isBlank(response.getExternalBonusId())){
        	return response.getExternalBonusId();
        }else{
        	return String.valueOf(response.getFreeRoundBonusID());
        }
        
    }

    @Override
    public void cancelFRB(long brandId, String bonusId) throws CommonServiceException {

        ResultCancelFRBonus response = frbServiceClient.cancelFRB(brandId, bonusId);
        if (response == null) {
            log.error("Couldn't cancel FRB by bonus[" + bonusId + "], brand[" + brandId + "]. Response from FRBService is null");
            throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
        }

        if (response.getStatus() > 0) {
            if (log.isWarnEnabled()) {
                log.warn("Cancel FRB by bonus[" + bonusId + "] and brand[" + brandId + "] returned incorrect status. Response:" + response);
            }
            throw new FRBServiceException(response.getStatus(), response.getDescription());
        }
    }

    @Override
    public List<InfoFRBonus> infoFRB(long brandId, String externalPlayerId) throws Exception {

        Player player = playerManager.getPlayer(brandId, externalPlayerId);
        if(player == null) {
            throw ServiceResponseCode.PLAYER_NOT_FOUND.EXCEPTION();
        }
        long playerId  = player.getPlayerID();

        ResultInfoFRBonus response = frbServiceClient.infoFRB(brandId, playerId);
        if (response == null) {
            log.error("Couldn't info FRB by player[" + playerId + "] and brand[" + brandId + "]. Response from FRBService is null");
            throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
        }

        if (response.getStatus() > 0) {
            if (log.isWarnEnabled()) {
                log.warn("Info FRB by player[" + player + "] and brand[" + brandId + "] returned incorrect status. Response:" + response);
            }
            throw new FRBServiceException(response.getStatus(), response.getDescription());
        }
        return response.getBonuses();
    }
}
