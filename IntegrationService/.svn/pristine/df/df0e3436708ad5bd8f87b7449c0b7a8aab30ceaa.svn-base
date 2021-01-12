package biz.digitalhouse.integration.v3.web.ws.http.bingoGameAPI.dto;

import java.util.Date;

/**
 * Created by vitaliy.babenko
 * on 15.06.2017.
 */
public class Transaction {

    private String transactionID;
    private TransactionType transactionType;
    private Double amount;
    private Balance balance;
    private String currency;
    private Date transactionDate;

    public Transaction() {
    }

    public Transaction(String transactionID, TransactionType transactionType, Double amount, Balance balance, String currency, Date transactionDate) {
        this.transactionID = transactionID;
        this.transactionType = transactionType;
        this.amount = amount;
        this.balance = balance;
        this.currency = currency;
        this.transactionDate = transactionDate;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Double getAmount() {
        return amount;
    }

    public Balance getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionID='" + transactionID + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", amount=" + amount +
                ", balance=" + balance +
                ", currency='" + currency + '\'' +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
