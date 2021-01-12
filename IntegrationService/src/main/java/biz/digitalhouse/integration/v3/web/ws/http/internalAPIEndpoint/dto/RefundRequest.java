package biz.digitalhouse.integration.v3.web.ws.http.internalAPIEndpoint.dto;

public class RefundRequest extends BaseRequest {

    private long playerID;
    private String transactionID;

    public long getPlayerID() {
        return playerID;
    }

    public void setPlayerID(long value) {
        this.playerID = value;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String value) {
        this.transactionID = value;
    }

}
