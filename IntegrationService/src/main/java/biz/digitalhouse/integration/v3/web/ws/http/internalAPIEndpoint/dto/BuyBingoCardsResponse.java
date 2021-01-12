package biz.digitalhouse.integration.v3.web.ws.http.internalAPIEndpoint.dto;

public class BuyBingoCardsResponse extends BaseResponse {

    private String externalTransactionID;
    private double cash;
    private double bonus;
    private double bonusPart;
    private Integer proposedCardsNumber;

    public String getExternalTransactionID() {
        return externalTransactionID;
    }

    public void setExternalTransactionID(String value) {
        this.externalTransactionID = value;
    }

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

    public double getBonusPart() {
        return bonusPart;
    }

    public void setBonusPart(double value) {
        this.bonusPart = value;
    }

    public Integer getProposedCardsNumber() {
        return proposedCardsNumber;
    }

    public void setProposedCardsNumber(Integer value) {
        this.proposedCardsNumber = value;
    }

}
