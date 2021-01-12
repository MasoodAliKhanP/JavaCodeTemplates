package biz.digitalhouse.integration.v3.services.externalClient.httpResponse;

/**
 * @author Vitalii Babenko
 *         on 10.04.2016.
 */
public class BonusWinResponse extends BalanceResponse {

    private String transactionID;

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    @Override
    public String toString() {
        return "BonusWinResponse{" +
                "transactionID='" + transactionID + '\'' +
                "} " + super.toString();
    }
}
