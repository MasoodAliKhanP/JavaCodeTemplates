package biz.digitalhouse.integration.v3.services.bingoGame.bingoServerClient;

import java.io.Serializable;
import java.util.List;

/**
 * Created by vitaliy.babenko
 * on 31.10.2016.
 */
public class Room implements Serializable {

    private Long roomID;
    private String roomName;
    private int bingoType;
    private List<Game> gameList;

    public Long getRoomID() {
        return roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getBingoType() {
        return bingoType;
    }

    public List<Game> getGameList() {
        return gameList;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomID=" + roomID +
                ", roomName='" + roomName + '\'' +
                ", bingoType=" + bingoType +
                ", gameList=" + gameList +
                '}';
    }
}
