package biz.digitalhouse.integration.v3.services.externalClient.httpResponse;

/**
 * @author Vitalii Babenko
 *         on 03.04.2016.
 */
public class BetResponse extends BalanceResponse {

    String transactionID;
    double bonusPartBet;

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public double getBonusPartBet() {
        return bonusPartBet;
    }

    public void setBonusPartBet(double bonusPartBet) {
        this.bonusPartBet = bonusPartBet;
    }

    @Override
    public String toString() {
        return "BetResponse{" +
                "transactionID='" + transactionID + '\'' +
                ", bonusPartBet=" + bonusPartBet +
                "} " + super.toString();
    }
}
