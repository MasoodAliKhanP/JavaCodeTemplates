package biz.digitalhouse.integration.v3.services.bingoGame.bingoServerClient;

import java.io.Serializable;
import java.util.List;

/**
 * Created by vitaliy.babenko
 * on 31.10.2016.
 */
public class GetAvailableGamesResponse implements Serializable {

    private List<Room> roomList;

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    @Override
    public String toString() {
        return "GetAvailableGamesResponse{" +
                "roomList=" + roomList +
                '}';
    }
}
