package biz.digitalhouse.integration.v3.services.bingoGame.bingoServerClient;

import java.io.Serializable;

/**
 * Created by vitaliy.babenko
 * on 31.10.2016.
 */
public class GetBingoHistoryRequest implements Serializable {

    private long brandID;
    private String locale;
    private long roundID;

    public GetBingoHistoryRequest() {
    }

    public GetBingoHistoryRequest(long brandID, String locale, long roundID) {
        this.brandID = brandID;
        this.locale = locale;
        this.roundID = roundID;
    }

    public long getBrandID() {
        return brandID;
    }

    public void setBrandID(long brandID) {
        this.brandID = brandID;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public long getRoundID() {
        return roundID;
    }

    public void setRoundID(long roundID) {
        this.roundID = roundID;
    }

    @Override
    public String toString() {
        return "GetBingoHistoryRequest{" +
                "brandID=" + brandID +
                ", locale='" + locale + '\'' +
                ", roundID=" + roundID +
                '}';
    }
}
