package biz.digitalhouse.integration.v3.services.bingoGame.bingoServerClient;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by vitaliy.babenko
 * on 31.10.2016.
 */
public class GetPreorderReportRequest implements Serializable {

    private long playerID;
    private long brandID;
    private Calendar dateFrom;
    private Calendar dateTo;
    private String language;

    public GetPreorderReportRequest() {
    }

    public GetPreorderReportRequest(long playerID, long brandID, Calendar dateFrom, Calendar dateTo, String language) {
        this.playerID = playerID;
        this.brandID = brandID;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.language = language;
    }

    public long getPlayerID() {
        return playerID;
    }

    public void setPlayerID(long playerID) {
        this.playerID = playerID;
    }

    public long getBrandID() {
        return brandID;
    }

    public void setBrandID(long brandID) {
        this.brandID = brandID;
    }

    public Calendar getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Calendar dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Calendar getDateTo() {
        return dateTo;
    }

    public void setDateTo(Calendar dateTo) {
        this.dateTo = dateTo;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "GetPreorderReportRequest{" +
                "playerID=" + playerID +
                ", brandID=" + brandID +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", language='" + language + '\'' +
                '}';
    }
}
