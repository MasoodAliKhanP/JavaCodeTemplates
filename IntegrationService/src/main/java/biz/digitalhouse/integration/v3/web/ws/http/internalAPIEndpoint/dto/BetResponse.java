package biz.digitalhouse.integration.v3.web.ws.http.internalAPIEndpoint.dto;

public class BetResponse extends BaseResponse {

    private double cash;
    private double bonus;
    private Double usedBonus;
    private String externalTransactionID;

    public double getCash() {
        return cash;
    }

    public void setCash(double value) {
        this.cash = value;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double value) {
        this.bonus = value;
    }

    public Double getUsedBonus() {
        return usedBonus;
    }

    public void setUsedBonus(Double value) {
        this.usedBonus = value;
    }

    public String getExternalTransactionID() {
        return externalTransactionID;
    }

    public void setExternalTransactionID(String value) {
        this.externalTransactionID = value;
    }

}
