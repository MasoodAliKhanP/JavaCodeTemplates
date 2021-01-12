package biz.digitalhouse.integration.v3.services.bingoGame.bingoServerClient;

import java.io.Serializable;
import java.util.List;

/**
 * Created by vitaliy.babenko
 * on 31.10.2016.
 */
public class CancelPreorderCardsRequest implements Serializable {

    private long brandID;
    private long playerID;
    private List<CancelPreorder> cancelPreorderList;

    public CancelPreorderCardsRequest() {
    }

    public CancelPreorderCardsRequest(long brandID, long playerID, List<CancelPreorder> cancelPreorderList) {
        this.brandID = brandID;
        this.playerID = playerID;
        this.cancelPreorderList = cancelPreorderList;
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

    public List<CancelPreorder> getCancelPreorderList() {
        return cancelPreorderList;
    }

    public void setCancelPreorderList(List<CancelPreorder> cancelPreorderList) {
        this.cancelPreorderList = cancelPreorderList;
    }

    @Override
    public String toString() {
        return "CancelPreorderCardsRequest{" +
                "brandID=" + brandID +
                ", playerID=" + playerID +
                ", cancelPreorderList=" + cancelPreorderList +
                '}';
    }
}
