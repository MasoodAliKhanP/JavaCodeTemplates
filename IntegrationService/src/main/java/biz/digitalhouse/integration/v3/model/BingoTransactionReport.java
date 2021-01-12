package biz.digitalhouse.integration.v3.model;

import java.util.Date;

/**
 * Created by vitaliy.babenko
 * on 15.06.2017.
 */
public class BingoTransactionReport {

    private String transactionID;
    private String transactionType;
    private Double amount;
    private double cashBalance;
    private double bonusBalance;
    private String currency;
    private Date transactionDate;

    public BingoTransactionReport() {
    }

    public BingoTransactionReport(String transactionID, String transactionType, Double amount, double cashBalance, double bonusBalance, String currency, Date transactionDate) {
        this.transactionID = transactionID;
        this.transactionType = transactionType;
        this.amount = amount;
        this.cashBalance = cashBalance;
        this.bonusBalance = bonusBalance;
        this.currency = currency;
        this.transactionDate = transactionDate;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public Double getAmount() {
        return amount;
    }

    public double getCashBalance() {
        return cashBalance;
    }

    public double getBonusBalance() {
        return bonusBalance;
    }

    public String getCurrency() {
        return currency;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    @Override
    public String toString() {
        return "BingoTransactionReport{" +
                "transactionID='" + transactionID + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", amount=" + amount +
                ", cashBalance=" + cashBalance +
                ", bonusBalance=" + bonusBalance +
                ", currency='" + currency + '\'' +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
