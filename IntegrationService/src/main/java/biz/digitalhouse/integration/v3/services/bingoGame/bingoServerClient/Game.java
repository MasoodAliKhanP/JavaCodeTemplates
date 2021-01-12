package biz.digitalhouse.integration.v3.services.bingoGame.bingoServerClient;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by vitaliy.babenko
 * on 31.10.2016.
 */
public class Game implements Serializable {

    private long gameID;
    private String gameName;
    private int serialNumber;
    private int maxCardsNumber;
    private Map<String, Double> cardCost;
    private String purchaseMode;
    private int players;
    private int boughtCardsNumber;
    private int freeCardsNumber;
    private Map<String, Double> totalAmount;

    public long getGameID() {
        return gameID;
    }

    public void setGameID(long gameID) {
        this.gameID = gameID;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getMaxCardsNumber() {
        return maxCardsNumber;
    }

    public void setMaxCardsNumber(int maxCardsNumber) {
        this.maxCardsNumber = maxCardsNumber;
    }

    public Map<String, Double> getCardCost() {
        return cardCost;
    }

    public void setCardCost(Map<String, Double> cardCost) {
        this.cardCost = cardCost;
    }

    public String getPurchaseMode() {
        return purchaseMode;
    }

    public void setPurchaseMode(String purchaseMode) {
        this.purchaseMode = purchaseMode;
    }

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public int getBoughtCardsNumber() {
        return boughtCardsNumber;
    }

    public void setBoughtCardsNumber(int boughtCardsNumber) {
        this.boughtCardsNumber = boughtCardsNumber;
    }

    public int getFreeCardsNumber() {
        return freeCardsNumber;
    }

    public void setFreeCardsNumber(int freeCardsNumber) {
        this.freeCardsNumber = freeCardsNumber;
    }

    public Map<String, Double> getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Map<String, Double> totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameID=" + gameID +
                ", gameName='" + gameName + '\'' +
                ", serialNumber=" + serialNumber +
                ", maxCardsNumber=" + maxCardsNumber +
                ", cardCost=" + cardCost +
                ", purchaseMode='" + purchaseMode + '\'' +
                ", players=" + players +
                ", boughtCardsNumber=" + boughtCardsNumber +
                ", freeCardsNumber=" + freeCardsNumber +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
