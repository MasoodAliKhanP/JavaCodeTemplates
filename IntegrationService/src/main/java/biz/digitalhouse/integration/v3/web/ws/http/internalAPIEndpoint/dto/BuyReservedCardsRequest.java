package biz.digitalhouse.integration.v3.web.ws.http.internalAPIEndpoint.dto;

/**
 * Created by vitaliy.babenko
 * on 26.04.2017.
 */
public class BuyReservedCardsRequest {

    private long brandID;
    private long playerID;
    private String transactionID;
    private long roomID;
    private long roundID;
    private double amount;
    private double cardCost;
    private double cardCostUSD;
    private int cardsNumber;
    private int packSize;

    public long getBrandID() {
        return brandID;
    }

    public long getPlayerID() {
        return playerID;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public long getRoomID() {
        return roomID;
    }

    public long getRoundID() {
        return roundID;
    }

    public double getAmount() {
        return amount;
    }

    public double getCardCost() {
        return cardCost;
    }
    
    public double getCardCostUSD() {
		return cardCostUSD;
	}

    public int getCardsNumber() {
        return cardsNumber;
    }

    public int getPackSize() {
        return packSize;
    }

    @Override
    public String toString() {
        return "BuyReservedCardsRequest{" +
                "brandID=" + brandID +
                ", playerID=" + playerID +
                ", transactionID='" + transactionID + '\'' +
                ", roomID=" + roomID +
                ", roundID=" + roundID +
                ", amount=" + amount +
                ", cardCost=" + cardCost +
                ", cardCostUSD="+cardCostUSD+
                ", cardsNumber=" + cardsNumber +
                ", packSize=" + packSize +
                '}';
    }
}
