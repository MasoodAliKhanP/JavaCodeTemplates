package biz.digitalhouse.integration.v3.services.bingoGame.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitalii Babenko
 *         on 01.03.2016.
 */
public class BingoRoomDTO {

    private long roomId;
    private String roomName;
    private int bingoType;
    private List<BingoGameDTO> bingoGames;

    public BingoRoomDTO(long roomId, String roomName, int bingoType) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.bingoType = bingoType;
    }

    public long getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getBingoType() {
        return bingoType;
    }

    public List<BingoGameDTO> getBingoGames() {
        if(bingoGames == null) {
            bingoGames = new ArrayList<>();
        }
        return bingoGames;
    }

    @Override
    public String toString() {
        return "BingoRoomDTO{" +
                "roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", bingoGames=" + bingoGames +
                '}';
    }
}
