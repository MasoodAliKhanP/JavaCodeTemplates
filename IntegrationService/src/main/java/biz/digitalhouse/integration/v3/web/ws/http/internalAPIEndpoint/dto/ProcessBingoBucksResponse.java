package biz.digitalhouse.integration.v3.web.ws.http.internalAPIEndpoint.dto;

/**
 * @author Vitalii
 * on 30.07.2018.
 */
public class ProcessBingoBucksResponse {

    private String error;
    private String description;
    private Double amount;
    private String transactionID;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }
}
