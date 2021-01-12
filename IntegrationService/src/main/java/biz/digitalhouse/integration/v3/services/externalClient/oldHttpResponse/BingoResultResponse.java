package biz.digitalhouse.integration.v3.services.externalClient.oldHttpResponse;

/**
 * Created by vitaliy.babenko
 * on 29.09.2015.
 */
public class BingoResultResponse extends BaseResponse {

    private String reference;
    private String transactionId;
    private Double cash;
    private Double bonus;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Double getCash() {
        return cash;
    }

    public void setCash(Double cash) {
        this.cash = cash;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public String toString() {
        return "BingoResultResponse{" +
                "reference='" + reference + '\'' +
                "transactionId='" + transactionId + '\'' +
                ", cash=" + cash +
                ", bonus=" + bonus +
                "} " + super.toString();
    }
}
