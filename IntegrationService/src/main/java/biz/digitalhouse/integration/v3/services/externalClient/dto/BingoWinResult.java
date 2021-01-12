package biz.digitalhouse.integration.v3.services.externalClient.dto;

/**
 * @author vitalii.babenko
 * created: 27.03.2018 12:52
 */
public class BingoWinResult extends BalanceResult {

    private String transactionID;
    private double bonusPart;


    public BingoWinResult(double cash, double bonus, String transactionID, double bonusPart) {
        super(null, cash, bonus);
        this.transactionID = transactionID;
        this.bonusPart = bonusPart;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public double getBonusPart() {
        return bonusPart;
    }


    @Override
    public String toString() {
        return "BingoWinResult{" +
                "transactionID='" + transactionID + '\'' +
                ", bonusPart=" + bonusPart +
                ", vendorId='" + vendorId + '\'' +
                ", cash=" + cash +
                ", bonus=" + bonus +
                '}';
    }
}
