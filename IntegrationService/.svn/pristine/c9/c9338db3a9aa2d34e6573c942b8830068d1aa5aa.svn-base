package biz.digitalhouse.integration.v3.services.externalClient;

import biz.digitalhouse.integration.v3.model.Player;
import biz.digitalhouse.integration.v3.model.ProcessBingoBucks;
import biz.digitalhouse.integration.v3.services.externalClient.dto.*;

/**
 * @author Vitalii Babenko
 *         on 05.03.2016.
 */
public interface ExternalClient {
	public static final String BETTER_LUCK_PRIZEID = "1";
	public static final String FREE_SPIN_PRIZEID = "2";
	public static final String BONUS_PRIZEID = "3";
	public static final String CASHBACK_PRIZEID = "4";
	
    AuthenticateResult authenticate(long brandID, String token);

    BalanceResult balance(long brandID, String vendorID, String extPlayerID);

    TransactionResult bet(long brandID, String vendorID, String extPlayerID,
                          String gameID, long roundID, Long originalRoundID, double amount, String transactionID, String roundDetails,
                          String bonusCode);

    String refund(long brandID, String vendorID, String extPlayerID, String transactionID);

    TransactionResult win(long brandID, String vendorID, String extPlayerID,
                          String gameID, long roundID, Long originalRoundID, double amount, String transactionID, String roundDetails,
                          String bonusCode, Boolean endRound, Boolean isJob);

    TransactionResult jackpotWin(long brandID, String vendorID, String extPlayerID, String gameID, long roundID, Long originalRoundID,
                                 double amount, Long jackpotID, String transactionID, String roundDetails, Boolean endRound);

    void endRound(long brandID, String vendorID, String extPlayerID, String gameID, long roundID);

    TransactionResult bonusWin(long brandID, String vendorID, String extPlayerID, double amount, String bonusCode);

    BuyBingoCardsResult buyBingoCards(long brandID, String vendorID, String extPlayerID, double amount,
                                      double cardCost,double cardCostUSD, long roomID, long roundID,
                                      int cardsNumber, int packSize, String transactionID);

    String bingoRefund(long brandID, String vendorID, String extPlayerID, String transactionID);

    void bingoEndRound(long brandID, String externalPlayerID, long bingoRoomID, long bingoRoundID);

    BingoWinResult bingoWin(Long brandID, String playerID, String transctionID, Double amount, Long roundID, Long roomID);


    BuyReservedCardsResult buyReservedCards(long brandID, String extPlayerID, double amount, long roomID, long roundID, double cardCost,double cardCostUSD, int cardsNumber, int packSize, String transactionID);

    ProcessBingoBucks processBingoBucks(long brandID, String extPlayerID, double amount, long roomID, long chatRoundID, int isUnlimited);

    ScratchCardWinResult scratchCardWin(long brandID, String vendorID, Player player, long promotionId,
			String prizeTypeId, double offerValue, String currency);
    
    interface ExternalServiceCode {

        String SUCCESS = "0";
        String INSUFFICIENT_BALANCE = "1";
        String PLAYER_NOT_FOUND = "2";
        String BET_NOT_ALLOWED = "3";
        String INVALID_TOKEN = "4";
        String INVALID_HASH_CODE = "5";
        String USER_FROZEN = "6";
        String BAD_REQUEST = "7";
        String GAME_NOT_FOUND = "8";
        String ITERNAL_SERVER_ERROR = "100";
    }
}
