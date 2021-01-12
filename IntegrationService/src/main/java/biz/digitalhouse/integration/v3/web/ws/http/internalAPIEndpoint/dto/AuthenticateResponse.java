package biz.digitalhouse.integration.v3.web.ws.http.internalAPIEndpoint.dto;

import java.util.Map;

public class AuthenticateResponse extends BaseResponse {

    private String currency;
    private long playerID;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String value) {
        this.currency = value;
    }

    public long getPlayerID() {
        return playerID;
    }

    public void setPlayerID(long value) {
        this.playerID = value;
    }


    @Override
    public String toString() {
        return "AuthenticateResponse{" +
                "currency='" + currency + '\'' +
                ", playerID=" + playerID +
                "} " + super.toString();
    }
}
