package biz.digitalhouse.integration.v3.services.externalClient.dto;

/**
 * Created by vitaliy.babenko
 * on 26.04.2017.
 */
public class BuyReservedCardsResult extends BalanceResult {

    private String transactionID;
    private int boughtCardsNumber;
    private double bonusPart;
    private int status;

    public BuyReservedCardsResult(int status) {
        this.status = status;
    }

    public BuyReservedCardsResult(double cash, double bonus, int status) {
        super(null, cash, bonus);
        this.status = status;
    }

    public BuyReservedCardsResult(double cash, double bonus, double bonusPart, String transactionID, int boughtCardsNumber) {
        super(null, cash, bonus);
        this.transactionID = transactionID;
        this.boughtCardsNumber = boughtCardsNumber;
        this.bonusPart = bonusPart;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public int getBoughtCardsNumber() {
        return boughtCardsNumber;
    }

    public double getBonusPart() {
        return bonusPart;
    }

    public int getStatus() {
        return status;
    }
}
