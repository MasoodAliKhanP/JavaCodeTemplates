package biz.digitalhouse.integration.v3.services.transactions;

import biz.digitalhouse.integration.v3.model.Player;
import biz.digitalhouse.integration.v3.services.externalClient.dto.ScratchCardWinResult;
import biz.digitalhouse.integration.v3.services.externalClient.dto.TransactionResult;

/**
 * @author Vitalii Babenko on 15.04.2016.
 */
public interface PlayTransactionManager {

	long createPlaySession(String vendorID, long brandID, Player player, int vipLevel, String gameSymbol,
			String incomingRoundID, Long incomingOriginalRoundID, String bonusCode);

	TransactionResult bet(long brandID, String vendorID, Player player, String gameSymbol, double amount,
			String incomingTransactionID, String incomingRoundID, String incomingOriginalRoundID, boolean gamble,
			String bonusCode);

	String refund(long brandID, String vendorID, Player player, String incomingTransactionID);

	TransactionResult win(long brandID, String vendorID, Player player, String gameSymbol, double amount,
			String incomingTransactionID, String incomingRoundID, String incomingOriginalRoundID, boolean gamble,
			String bonusCode, Boolean endRound);

	TransactionResult jackpotWin(long brandID, String vendorID, Player player, String gameSymbol, double amount,
			long jackpotID, String tierTypeID, String incomingTransactionID, String incomingRoundID,
			String incomingOriginalRoundID, boolean endRound);

	ScratchCardWinResult scratchCardWin(long brandID, String vendorID, Player player, long promotionId, String prizeTypeId,
			double offerValue, String string);

	void endRound(long brandID, String vendorID, Player player, long gameID, String incomingRoundID);
}
