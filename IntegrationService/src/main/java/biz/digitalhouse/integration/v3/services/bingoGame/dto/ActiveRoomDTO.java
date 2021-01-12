package biz.digitalhouse.integration.v3.services.bingoGame.dto;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Vitalii Babenko
 *         on 01.03.2016.
 */
public class ActiveRoomDTO {

    private long roomID;
    private String roomName;
    private int bingoType;
    private Map<String, Double> cardCost;
    private Map<String, Double> gamePrize;
    private Calendar nextGameStart;
    private Map<String, Double> jackpotAmount;
    private int playersBoughtCards;
    private int playersInRoom;
    private String roomStyleID;



    public ActiveRoomDTO(long roomID, String roomName, int bingoType, Map<String, Double> cardCost, Map<String, Double> gamePrize, Calendar nextGameStart,
                         Map<String, Double> jackpotAmount, int playersBoughtCards, int playersInRoom, String roomStyleID) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.bingoType = bingoType;
        this.cardCost = cardCost;
        this.gamePrize = gamePrize;
        this.nextGameStart = nextGameStart;
        this.jackpotAmount = jackpotAmount;
        this.playersBoughtCards = playersBoughtCards;
        this.playersInRoom = playersInRoom;
        this.roomStyleID = roomStyleID;
    }

    public long getRoomID() {
        return roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getBingoType() {
        return bingoType;
    }

    public int getPlayersBoughtCards() {
        return playersBoughtCards;
    }

    public int getPlayersInRoom() {
        return playersInRoom;
    }

    public String getRoomStyleID() {
        return roomStyleID;
    }

    public Map<String, Double> getCardCost() {
        return cardCost;
    }

    public void setCardCost(Map<String, Double> cardCost) {
        this.cardCost = cardCost;
    }

    public Map<String, Double> getGamePrize() {
        return gamePrize;
    }

    public void setGamePrize(Map<String, Double> gamePrize) {
        this.gamePrize = gamePrize;
    }

    public Map<String, Double> getJackpotAmount() {
        return jackpotAmount;
    }

    public void setJackpotAmount(Map<String, Double> jackpotAmount) {
        this.jackpotAmount = jackpotAmount;
    }

    public Calendar getNextGameStart() {
        return nextGameStart;
    }

    public void setNextGameStart(Calendar nextGameStart) {
        this.nextGameStart = nextGameStart;
    }

    @Override
    public String toString() {
        return "ActiveRoomDTO{" +
                "roomID=" + roomID +
                ", roomName='" + roomName + '\'' +
                ", bingoType=" + bingoType +
                ", cardCost=" + cardCost +
                ", gamePrize=" + gamePrize +
                ", nextGameStart=" + nextGameStart +
                ", jackpotAmount=" + jackpotAmount +
                ", playersBoughtCards=" + playersBoughtCards +
                ", playersInRoom=" + playersInRoom +
                ", roomStyleID='" + roomStyleID + '\'' +
                '}';
    }
}
