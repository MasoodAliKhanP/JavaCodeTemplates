package biz.digitalhouse.integration.v3.web.ws.http.internalAPIEndpoint.dto;

public class BingoRefundResponse extends BaseResponse {

    private String externalTransactionID;

    public String getExternalTransactionID() {
        return externalTransactionID;
    }

    public void setExternalTransactionID(String value) {
        this.externalTransactionID = value;
    }

}
