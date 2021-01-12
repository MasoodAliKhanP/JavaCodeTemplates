package biz.digitalhouse.integration.v3.model;

/**
 * @author Vitalii Babenko
 *         on 04.03.2016.
 */
public class PlayerCards {

    private String extPlayerId;
    private int boughtCardsNumber;
    private int givenCardsNumber;
    private double cardCost;
    private String currencySymbol;

    public PlayerCards(String extPlayerId, int boughtCardsNumber, int givenCardsNumber, double cardCost, String currencySymbol) {
        this.extPlayerId = extPlayerId;
        this.boughtCardsNumber = boughtCardsNumber;
        this.givenCardsNumber = givenCardsNumber;
        this.cardCost = cardCost;
        this.currencySymbol = currencySymbol;
    }

    public String getExtPlayerId() {
        return extPlayerId;
    }

    public int getBoughtCardsNumber() {
        return boughtCardsNumber;
    }

    public int getGivenCardsNumber() {
        return givenCardsNumber;
    }

    public double getCardCost() {
        return cardCost;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    @Override
    public String toString() {
        return "PlayerCards{" +
                "extPlayerId='" + extPlayerId + '\'' +
                ", boughtCardsNumber=" + boughtCardsNumber +
                ", givenCardsNumber=" + givenCardsNumber +
                '}';
    }
}
