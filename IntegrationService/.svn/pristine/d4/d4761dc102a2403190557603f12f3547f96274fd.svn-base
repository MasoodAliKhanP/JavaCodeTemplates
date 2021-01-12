package biz.digitalhouse.integration.v3.services.bingoGame.bingoServerClient;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by vitaliy.babenko
 * on 31.10.2016.
 */
public class ActiveRoom {

    private Long roomID;
    private String roomName;
    private int bingoType;
    private HashMap<String, Double> cardCost = new HashMap<>();
    private HashMap<String, Double> gamePrize = new HashMap<>();
    private Calendar nextGameStart;
    private HashMap<String, Double> jackpotAmount = new HashMap<>();
    private Integer playersBoughtCards;
    private Integer playersInRoom;
    private String roomStyleID;

    public ActiveRoom() {}

    public ActiveRoom(Long roomID, String roomName, int bingoType,
                      long nextGameStart, Integer playersBoughtCards, Integer playersInRoom, String roomStyleID) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.bingoType = bingoType;
        this.nextGameStart = Calendar.getInstance();
        this.nextGameStart.setTimeInMillis(nextGameStart);
        this.playersBoughtCards = playersBoughtCards;
        this.playersInRoom = playersInRoom;
        this.roomStyleID = roomStyleID;
    }

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

    public int getBingoType() {
        return bingoType;
    }

    public void setBingoType(int bingoType) {
        this.bingoType = bingoType;
    }

    public Calendar getNextGameStart() {
        return nextGameStart;
    }

    public void setNextGameStart(Calendar nextGameStart) {
        this.nextGameStart = nextGameStart;
    }

    public Integer getPlayersBoughtCards() {
        return playersBoughtCards;
    }

    public void setPlayersBoughtCards(Integer playersBoughtCards) {
        this.playersBoughtCards = playersBoughtCards;
    }

    public Integer getPlayersInRoom() {
        return playersInRoom;
    }

    public void setPlayersInRoom(Integer playersInRoom) {
        this.playersInRoom = playersInRoom;
    }

    public String getRoomStyleID() {
        return roomStyleID;
    }

    public void setRoomStyleID(String roomStyleID) {
        this.roomStyleID = roomStyleID;
    }

    public HashMap<String, Double> getCardCost() {
        return cardCost;
    }

    public void setCardCost(HashMap<String, Double> cardCost) {
        this.cardCost = cardCost;
    }

    public HashMap<String, Double> getGamePrize() {
        return gamePrize;
    }

    public void setGamePrize(HashMap<String, Double> gamePrize) {
        this.gamePrize = gamePrize;
    }

    public HashMap<String, Double> getJackpotAmount() {
        return jackpotAmount;
    }

    public void setJackpotAmount(HashMap<String, Double> jackpotAmount) {
        this.jackpotAmount = jackpotAmount;
    }
}
