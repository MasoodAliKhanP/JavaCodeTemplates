package biz.digitalhouse.integration.v3.services.bingoGame.bingoServerClient;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * Created by vitaliy.babenko
 * on 31.10.2016.
 */
public class PreorderCardsRequest implements Serializable {

    private long brandID;
    private Calendar date;
    private int hour;
    private long playerID;
    private long roomID;
    private List<PreorderCard> preorderCardList;

    public PreorderCardsRequest() {
    }

    public PreorderCardsRequest(long brandID, Calendar date, int hour, long playerID, long roomID, List<PreorderCard> preorderCardList) {
        this.brandID = brandID;
        this.date = date;
        this.hour = hour;
        this.playerID = playerID;
        this.roomID = roomID;
        this.preorderCardList = preorderCardList;
    }

    public long getBrandID() {
        return brandID;
    }

    public void setBrandID(long brandID) {
        this.brandID = brandID;
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

    public long getPlayerID() {
        return playerID;
    }

    public void setPlayerID(long playerID) {
        this.playerID = playerID;
    }

    public long getRoomID() {
        return roomID;
    }

    public void setRoomID(long roomID) {
        this.roomID = roomID;
    }

    public List<PreorderCard> getPreorderCardList() {
        return preorderCardList;
    }

    public void setPreorderCardList(List<PreorderCard> preorderCardList) {
        this.preorderCardList = preorderCardList;
    }

    @Override
    public String toString() {
        return "PreorderCardsRequest{" +
                "brandID=" + brandID +
                ", date=" + date +
                ", hour=" + hour +
                ", playerID=" + playerID +
                ", roomID=" + roomID +
                ", preorderCardList=" + preorderCardList +
                '}';
    }
}
