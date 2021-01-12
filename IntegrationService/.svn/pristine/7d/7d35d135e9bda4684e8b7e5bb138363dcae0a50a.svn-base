package biz.digitalhouse.integration.v3.services.bingoGame.bingoServerClient;

import java.io.Serializable;

/**
 * Created by vitaliy.babenko
 * on 31.10.2016.
 */
public class GetRoomListRequest implements Serializable {

    private long brandID;
    private String language;
    private Long playerID;

    public GetRoomListRequest(long brandID, String language, Long playerID) {
        this.brandID = brandID;
        this.language = language;
        this.playerID = playerID;
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

    public Long getPlayerID() {
        return playerID;
    }

    public void setPlayerID(Long playerID) {
        this.playerID = playerID;
    }

    @Override
    public String toString() {
        return "GetRoomListRequest{" +
                "brandID=" + brandID +
                ", language='" + language + '\'' +
                ", playerID=" + playerID +
                '}';
    }
}
