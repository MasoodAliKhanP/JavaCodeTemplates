package biz.digitalhouse.integration.v3.web.ws.http.bingoGameAPI.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vitaliy.babenko
 * on 28.04.2017.
 */
public class Room {

    private Long roomID;
    private String roomName;
    private List<Game> gameList;

    public Long getRoomID() {
        return roomID;
    }

    public void setRoomID(Long roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public List<Game> getGameList() {
        if (gameList == null) {
            gameList = new ArrayList<Game>();
        }
        return gameList;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomID=" + roomID +
                ", roomName='" + roomName + '\'' +
                ", gameList=" + gameList +
                '}';
    }
}
