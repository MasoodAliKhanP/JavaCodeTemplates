package biz.digitalhouse.integration.v3.services.transactions;

/**
 * @author Vitalii Babenko
 *         on 20.04.2016.
 */
public interface PlayTransactionJobManager {

    /**
     * this method called RefundJob
     */
    void refund();

    /**
     * This method called WinJob
     */
    void win();

    void jackpotWin();
}
