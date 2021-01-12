package biz.digitalhouse.integration.v3.dao;

import biz.digitalhouse.integration.v3.exceptions.FrozenException;
import biz.digitalhouse.integration.v3.exceptions.GameDisabledException;

/**
 * @author Vitalii Babenko
 *         on 15.04.2016.
 */
public interface PlayTransactionDAO {

    /**
     *
     * @param gameSymbol
     * @param playerID
     * @param vipLevel
     *@param originalRoundID
     * @param bonusCode
     * @param incomingRoundID    @return
     */
    long createPlaySession(String gameSymbol, long playerID, int vipLevel, Long originalRoundID, String bonusCode, String incomingRoundID);


    Long findPlaySessionID(final String playSessionID, final long playerId);
    /**
     *
     * @param playerId
     * @param gameId
     * @param incomingOriginalRoundId
     * @return
     */
    Long getPossibleParentPlaySession(long playerId, long gameId, String incomingOriginalRoundId);

    /**
     *
     * @param transactionID
     * @param playerID
     * @param roundID
     * @param bet
     * @param usedBonus
     * @param cash
     * @param bonus
     * @param gameSymbol
     * @param externalTransactionID
     * @throws FrozenException
     * @throws GameDisabledException
     */
    void transactionBet(String transactionID, long playerID, long roundID, double bet, double usedBonus,
                        double cash, double bonus, String gameSymbol, String externalTransactionID) throws FrozenException, GameDisabledException;

    /**
     *
     * @param transactionID
     * @param playerID
     * @param roundID
     * @param win
     * @param gameSymbol
     * @return
     */
    long transactionWin(String transactionID, long playerID, long roundID, double win, String gameSymbol);

    /**
     *  @param transactionID
     * @param playerID
     * @param roundID
     * @param win
     * @param jackpotID
     * @param tierTypeID TODO
     * @param gameSymbol  @return
     */
    long transactionJackpot(String transactionID, final long playerID, final long roundID, final double win, long jackpotID, String tierTypeID, final String gameSymbol);

    /**
     *
     * @param transactionID
     * @param playerID
     * @param roundID
     * @param win
     * @param usedBonus
     * @param cash
     * @param bonus
     * @param gameSymbol
     * @param externalTransactionID
     * @throws FrozenException
     */
    void gamblingBegin(String transactionID, long playerID, long roundID, double win, double usedBonus,
                       double cash, double bonus, String gameSymbol, String externalTransactionID) throws FrozenException;

    /**
     *
     * @param transactionID
     * @param playerID
     * @param roundID
     * @param win
     * @param gameSymbol
     * @return
     */
    long gamblingEnd(String transactionID, long playerID, long roundID, double win, String gameSymbol);

    /**
     * @param roundID
     * @param internalTransactionID
     * @param playerID
     * @param cash
     * @param bonus
     * @param bonusPart
     * @param externalTransactionID
     * @param transactionID
     */
    void updatePlaySession(final long roundID, final long internalTransactionID, final long playerID,
                           final double cash, final double bonus, double bonusPart, final String externalTransactionID, final String transactionID);

}
