package biz.digitalhouse.integration.v3.services.externalClient;

import biz.digitalhouse.integration.v3.GeneralSettingKey;
import biz.digitalhouse.integration.v3.dao.GeneralSettingDAO;
import biz.digitalhouse.integration.v3.model.Player;
import biz.digitalhouse.integration.v3.model.ProcessBingoBucks;
import biz.digitalhouse.integration.v3.services.externalClient.dto.*;
import biz.digitalhouse.integration.v3.utils.ConfUtils;
import biz.digitalhouse.integration.v3.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author Vitalii Babenko
 *         on 05.03.2016.
 */
@Service("externalClient")
public class ExternalClientImpl implements ExternalClient {

    private final Log log = LogFactory.getLog(getClass());

    @Autowired
    private GeneralSettingDAO generalSettingDAO;

    @Autowired
    @Qualifier("ExternalClientRest")
    private ExternalClient rest;
    @Autowired
    @Qualifier("ExternalClientOldRest")
    private ExternalClient oldRest;


    @Override
    public AuthenticateResult authenticate(long brandID, String token) {
        return selectClient(brandID).authenticate(brandID, token);
    }

    @Override
    public BalanceResult balance(long brandID, String vendorID, String extPlayerID) {
        return selectClient(brandID).balance(brandID, vendorID, extPlayerID);
    }

    public TransactionResult bet(long brandID, String vendorID, String extPlayerID, String gameID, long roundID, Long originalRoundID,
                                 double amount, String transactionID, String roundDetails, String bonusCode) {
        if(StringUtils.isNotEmpty(bonusCode) && !ConfUtils.isTransferBonusCode(brandID, generalSettingDAO)) {
            if(log.isWarnEnabled()) {
                log.warn("Sending bonus code in makeBet for this operator is not allowed by configuration. Please check ["
                        + GeneralSettingKey.OPERATOR_CONFIGURATIONS + "] setting." +
                        " brandID[" + brandID + "], extPlayerID[" + extPlayerID +
                        "], roundID[" + roundID + "], gameID[" + gameID +
                        "], bonusCode[" + bonusCode + "]");
            }
            bonusCode = null;
        }
        return selectClient(brandID).bet(brandID, vendorID, extPlayerID, gameID, roundID, originalRoundID, amount, transactionID, roundDetails, bonusCode);
    }

    @Override
    public String refund(long brandID, String vendorID, String extPlayerID, String transactionID) {
        return selectClient(brandID).refund(brandID, vendorID, extPlayerID, transactionID);
    }

    @Override
    public TransactionResult win(long brandID, String vendorID, String extPlayerID, String gameID, long roundID, Long originalRoundID, double amount, String transactionID, String roundDetails, String bonusCode, Boolean endRound, Boolean isJob) {

        if(StringUtils.isNotEmpty(bonusCode) && !ConfUtils.isTransferBonusCode(brandID, generalSettingDAO)) {
            if(log.isWarnEnabled()) {
                log.warn("Sending bonus code in win for this operator is not allowed by configuration. Please check ["
                        + GeneralSettingKey.OPERATOR_CONFIGURATIONS + "] setting." +
                        " brandID[" + brandID + "], extPlayerID[" + extPlayerID +
                        "], roundID[" + roundID + "], gameID[" + gameID +
                        "], bonusCode[" + bonusCode + "]");
            }
            bonusCode = null;
        }

        return selectClient(brandID).win(brandID, vendorID, extPlayerID, gameID, roundID, originalRoundID, amount, transactionID, roundDetails, bonusCode, endRound, isJob);
    }

    @Override
    public TransactionResult jackpotWin(long brandID, String vendorID, String extPlayerID, String gameID, long roundID, 
    		Long originalRoundID, double amount, Long jackpotID, String transactionID, String roundDetails, Boolean endRound) {
        return selectClient(brandID).jackpotWin(brandID, vendorID, extPlayerID, gameID, roundID, originalRoundID, amount, jackpotID, transactionID, 
        		roundDetails, endRound);
    }

    @Override
    public void endRound(long brandID, String vendorID, String extPlayerID, String gameID, long roundID) {

        if(ConfUtils.isSendEndRound(brandID, generalSettingDAO)) {
            selectClient(brandID).endRound(brandID, vendorID, extPlayerID, gameID, roundID);
        }
    }

    @Override
    public TransactionResult bonusWin(long brandID, String vendorID, String extPlayerID, double amount, String bonusCode) {

        return selectClient(brandID).bonusWin(brandID, vendorID, extPlayerID, amount, bonusCode);
    }

    @Override
    public BuyBingoCardsResult buyBingoCards(long brandID, String vendorID, String extPlayerID, double amount, double cardCost,double cardCostUSD, long roomID, long roundID, int cardsNumber, int packSize, String transactionID) {

        return selectClient(brandID).buyBingoCards(brandID, vendorID, extPlayerID, amount, cardCost,cardCostUSD, roomID, roundID, cardsNumber, packSize, transactionID);
    }

    @Override
    public String bingoRefund(long brandID, String vendorID, String extPlayerID, String transactionID) {
        return selectClient(brandID).bingoRefund(brandID, vendorID, extPlayerID, transactionID);
    }

    @Override
    public void bingoEndRound(long brandID, String externalPlayerID, long bingoRoomID, long bingoRoundID) {
        selectClient(brandID).bingoEndRound(brandID, externalPlayerID, bingoRoomID, bingoRoundID);
    }

    @Override
    public BingoWinResult bingoWin(Long brandID, String playerID, String transctionID, Double amount, Long roundID, Long roomID) {
    	return selectClient(brandID).bingoWin(brandID, playerID, transctionID, amount, roundID, roomID);
    }

    @Override
    public BuyReservedCardsResult buyReservedCards(long brandID, String extPlayerID, double amount, long roomID, long roundID, double cardCost,double cardCostUSD, int cardsNumber, int packSize, String transactionID) {
        return selectClient(brandID).buyReservedCards(brandID, extPlayerID, amount, roomID, roundID, cardCost,cardCostUSD, cardsNumber, packSize, transactionID);
    }

    @Override
    public ProcessBingoBucks processBingoBucks(long brandID, String extPlayerID, double amount, long roomID, long chatRoundID, int isUnlimited) {
        return selectClient(brandID).processBingoBucks(brandID, extPlayerID, amount, roomID, chatRoundID, isUnlimited);
    }

	@Override
	public ScratchCardWinResult scratchCardWin(long brandID, String vendorID, Player player, long promotionId,
			String prizeTypeId, double offerValue, String currency) {
		return selectClient(brandID).scratchCardWin(brandID, vendorID, player, promotionId, prizeTypeId, offerValue, currency);
	}
	
    public ExternalClient selectClient(long brandID) {
        String settingsKey = GeneralSettingKey.PROTOCOL.key();

        String protocol = generalSettingDAO.getValue(settingsKey, brandID);
        // that
        if (log.isDebugEnabled()) {
            log.debug("Check for WWG redirect ==> brandID: " + brandID + ", protocol: " + protocol);
        }

        if(protocol == null) {
            log.error("Configuration PROTOCOL by brand[ " + brandID +  " ] not found.");
            throw new IllegalArgumentException("Configuration by brand[ " + brandID +  " ] not found.");
        }
        if ("HTTP".equalsIgnoreCase(protocol)) {
            return rest;
        } else if ("HTTP-O".equalsIgnoreCase(protocol)) {
            return oldRest;
        } else {
            String message = String.format("Unknown external service protocol[%s] by brand[%d].", protocol, brandID);
            log.error(message);
            throw new RuntimeException(message);
        }
    }

}
