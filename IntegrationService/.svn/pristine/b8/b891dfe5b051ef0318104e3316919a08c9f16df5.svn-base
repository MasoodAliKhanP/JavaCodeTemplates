package biz.digitalhouse.integration.v3.services.bingoGame.bingoServerClient;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by vitaliy.babenko
 * on 31.10.2016.
 */
public class GetAvailableGamesRequest implements Serializable {

    private long brandID;
    private long playerID;
    private String language;
    private Calendar date;
    private int hour;

    public GetAvailableGamesRequest(long brandID, long playerID, String language, Calendar date, int hour) {
        this.brandID = brandID;
        this.playerID = playerID;
        this.language = language;
        this.date = date;
        this.hour = hour;
    }

    public long getBrandID() {
        return brandID;
    }

    public void setBrandID(long brandID) {
        this.brandID = brandID;
    }

    public long getPlayerID() {
        return playerID;
    }

    public void setPlayerID(long playerID) {
        this.playerID = playerID;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    @Override
    public String toString() {
        return "GetAvailableGamesRequest{" +
                "brandID=" + brandID +
                ", playerID=" + playerID +
                ", language='" + language + '\'' +
                ", date=" + date +
                ", hour=" + hour +
                '}';
    }
}
