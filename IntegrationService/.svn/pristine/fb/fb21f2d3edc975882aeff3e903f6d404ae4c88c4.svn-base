package biz.digitalhouse.integration.v3.dao;

import biz.digitalhouse.integration.v3.model.Transaction;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Vitalii Babenko
 *         on 10.04.2016.
 */
public interface TransactionDAO {

    /**
     *
     * @param vendorID
     * @param incomingTransactionID
     * @param incomingRoundID
     * @param incomingOriginalRoundID
     * @param brandID
     * @param amount
     * @param roundID
     * @param originalRoundID
     * @param gameID
     * @param gameSymbol
     * @param gambling
     * @param bonusCode
     *@param type  @return Transaction
     */
    Transaction generateTransaction(String vendorID,
                                    String incomingTransactionID,
                                    String incomingRoundID,
                                    String incomingOriginalRoundID,
                                    long brandID,
                                    double amount,
                                    long roundID,
                                    Long originalRoundID,
                                    long gameID, String gameSymbol, Boolean gambling,
                                    String bonusCode, Transaction.TransactionType type,
                                    long playerID,
                                    String externalPlayerID);

    /**
     *
     * @param transaction
     */
    void updateTransaction(Transaction transaction);

    /**
     *
     * @param transactionID
     * @param operationDate
     * @return
     */
    Transaction getTransactionForProcessing(String transactionID, Calendar operationDate);

    /**
     *
     * @param vendorID
     * @param incomingTransactionID
     * @param type
     * @param incomingRoundID
     * @return
     */
    Transaction findTransactionByIncomingTransactionID(String vendorID, String incomingTransactionID, Transaction.TransactionType type, String incomingRoundID);

    /**
     *
     * @param vendorID
     * @param incomingRoundID
     * @param type
     * @return
     */
    Transaction findTransactionByIncomingRoundID(String vendorID, String incomingRoundID, Transaction.TransactionType type);

    List<Transaction> findTransactionsForRetry(Long brandID, Date createDate, Date lastProcessingDate, Transaction.TransactionType type);

    Transaction lockTransactionForRetry(String transactionID);
}
