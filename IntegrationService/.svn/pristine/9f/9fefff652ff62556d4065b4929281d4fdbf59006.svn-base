package biz.digitalhouse.integration.v3.services.bingoGame.bingoServerClient;

import java.io.Serializable;

/**
 * Created by vitaliy.babenko
 * on 31.10.2016.
 */
public class PreorderCard implements Serializable {

    private long gameID;
    private int serialNumber;
    private int cardsNumber;
    private String error;
    private String description;

    public PreorderCard() {
    }

    public PreorderCard(long gameID, int serialNumber, int cardsNumber, String error, String description) {
        this.gameID = gameID;
        this.serialNumber = serialNumber;
        this.cardsNumber = cardsNumber;
        this.error = error;
        this.description = description;
    }

    public long getGameID() {
        return gameID;
    }

    public void setGameID(long gameID) {
        this.gameID = gameID;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getCardsNumber() {
        return cardsNumber;
    }

    public void setCardsNumber(int cardsNumber) {
        this.cardsNumber = cardsNumber;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "PreorderCard{" +
                "gameID=" + gameID +
                ", serialNumber=" + serialNumber +
                ", cardsNumber=" + cardsNumber +
                ", error='" + error + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
