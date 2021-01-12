package biz.digitalhouse.integration.v3.web.ws.http.internalAPIEndpoint.dto;

/**
 * @author vitalii.babenko
 * created: 27.03.2018 12:05
 */
public class BingoResultResponse extends BalanceResponse {

    private String transactionID;
    private double bonusPart;
    private String description;


    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public double getBonusPart() {
        return bonusPart;
    }

    public void setBonusPart(double bonusPart) {
        this.bonusPart = bonusPart;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "BingoResultResponse{" +
                "transactionID='" + transactionID + '\'' +
                ", bonusPart=" + bonusPart +
                ", description='" + description + '\'' +
                "} " + super.toString();
    }
}
