package biz.digitalhouse.integration.v3.services.bingoGame.dto;

import java.util.Map;

/**
 * @author Vitalii Babenko
 *         on 01.03.2016.
 */
public class BingoGameDTO {

    private long bingoGameId;
    private String gameName;
    private int serialNumber;
    private int maxCardNumber;
    private Map<String, Double> cardCost;
    private String purchaseMode;
    private int players;
    private int boughtCardsNumber;
    private int freeCardsNumber;
    private Map<String, Double> totalAmount;

    public BingoGameDTO(long bingoGameId, String gameName, int serialNumber, int maxCardNumber, Map<String, Double> cardCost, String purchaseMode,
                        int players, int boughtCardsNumber, int freeCardsNumber, Map<String, Double> totalAmount) {

        this.bingoGameId = bingoGameId;
        this.gameName = gameName;
        this.serialNumber = serialNumber;
        this.maxCardNumber = maxCardNumber;
        this.cardCost = cardCost;
        this.purchaseMode = purchaseMode;
        this.players = players;
        this.boughtCardsNumber = boughtCardsNumber;
        this.freeCardsNumber = freeCardsNumber;
        this.totalAmount = totalAmount;
    }

    public long getBingoGameId() {
        return bingoGameId;
    }

    public String getGameName() {
        return gameName;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public int getMaxCardNumber() {
        return maxCardNumber;
    }

    public Map<String, Double> getCardCost() {
        return cardCost;
    }

    public String getPurchaseMode() {
        return purchaseMode;
    }

    public int getPlayers() {
        return players;
    }

    public int getBoughtCardsNumber() {
        return boughtCardsNumber;
    }

    public int getFreeCardsNumber() {
        return freeCardsNumber;
    }

    public Map<String, Double> getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return "BingoGameDTO{" +
                "bingoGameId=" + bingoGameId +
                ", gameName='" + gameName + '\'' +
                ", serialNumber=" + serialNumber +
                ", maxCardNumber=" + maxCardNumber +
                ", cardCost=" + cardCost +
                ", purchaseMode='" + purchaseMode + '\'' +
                ", players=" + players +
                ", boughtCardsNumber=" + boughtCardsNumber +
                ", freeCardsNumber=" + freeCardsNumber +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
