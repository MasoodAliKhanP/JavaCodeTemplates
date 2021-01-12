package biz.digitalhouse.integration.v3.web.ws.http.bingoGameAPI.dto;

import java.util.List;

/**
 * Created by vitaliy.babenko
 * on 15.06.2017.
 */
public class TransactionReportResponse extends BaseResponse {

    private List<Transaction> transactions;
    private Long count;

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "TransactionReportResponse{" +
                "transactions=" + transactions +
                ", count=" + count +
                "} " + super.toString();
    }
}
