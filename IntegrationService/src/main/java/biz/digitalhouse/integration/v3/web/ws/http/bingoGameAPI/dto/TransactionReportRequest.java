package biz.digitalhouse.integration.v3.web.ws.http.bingoGameAPI.dto;

import java.util.Date;

/**
 * Created by vitaliy.babenko
 * on 15.06.2017.
 */
public class TransactionReportRequest extends BaseRequest {

    private String playerID;
    private Date startDate;
    private Date endDate;
    private TransactionType transactionType;
    private long fromIndex;
    private int pageOffset;

    public String getPlayerID() {
        return playerID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public long getFromIndex() {
        return fromIndex;
    }

    public int getPageOffset() {
        return pageOffset;
    }

    @Override
    protected String getStringHashBuild() {
        return playerID + startDate + endDate + fromIndex + pageOffset;
    }

    @Override
    public String toString() {
        return "TransactionReportRequest{" +
                "playerID='" + playerID + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", transactionType=" + transactionType +
                ", fromIndex=" + fromIndex +
                ", pageOffset=" + pageOffset +
                "} " + super.toString();
    }
}
