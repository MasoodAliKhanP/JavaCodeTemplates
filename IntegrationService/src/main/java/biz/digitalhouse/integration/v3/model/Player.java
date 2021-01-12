package biz.digitalhouse.integration.v3.model;

import java.io.Serializable;

/**
 * @author Vitalii Babenko
 *         on 28.02.2016.
 */
public class Player implements Serializable {

    private long playerID;
    private String externalPlayerID;
    private long brandID;
    private String nickname;

    public Player() {}

    public Player(long playerID, String externalPlayerID, long brandID, String nickname) {
        this.playerID = playerID;
        this.externalPlayerID = externalPlayerID;
        this.brandID = brandID;
        this.nickname = nickname;
    }

    public long getPlayerID() {
        return playerID;
    }

    public void setPlayerID(long playerID) {
        this.playerID = playerID;
    }

    public String getExternalPlayerID() {
        return externalPlayerID;
    }

    public void setExternalPlayerID(String externalPlayerID) {
        this.externalPlayerID = externalPlayerID;
    }

    public long getBrandID() {
        return brandID;
    }

    public void setBrandID(long brandID) {
        this.brandID = brandID;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerID=" + playerID +
                ", externalPlayerID='" + externalPlayerID + '\'' +
                ", brandID=" + brandID +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
