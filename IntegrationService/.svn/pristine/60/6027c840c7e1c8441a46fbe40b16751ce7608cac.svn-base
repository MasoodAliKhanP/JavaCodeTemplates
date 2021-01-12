package biz.digitalhouse.integration.v3.services.bingoGame.bingoServerClient;

import java.io.Serializable;

/**
 * Created by vitaliy.babenko
 * on 31.10.2016.
 */
public class GetBingoJackpotsRequest implements Serializable {

    private long brandID;
    private String language;

    public GetBingoJackpotsRequest() {
    }

    public GetBingoJackpotsRequest(long brandID, String language) {
        this.brandID = brandID;
        this.language = language;
    }

    public long getBrandID() {
        return brandID;
    }

    public void setBrandID(long brandID) {
        this.brandID = brandID;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "GetBingoJackpotsRequest{" +
                "brandID=" + brandID +
                ", language='" + language + '\'' +
                '}';
    }
}
