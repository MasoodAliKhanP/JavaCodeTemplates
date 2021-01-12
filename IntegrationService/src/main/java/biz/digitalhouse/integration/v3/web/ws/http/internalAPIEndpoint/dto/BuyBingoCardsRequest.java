package biz.digitalhouse.integration.v3.web.ws.http.internalAPIEndpoint.dto;

public class BuyBingoCardsRequest {

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

    public void setBrandID(long value) {
        this.brandID = value;
    }

    public long getPlayerID() {
        return playerID;
    }

    public void setPlayerID(long value) {
        this.playerID = value;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String value) {
        this.transactionID = value;
    }

    public long getRoomID() {
        return roomID;
    }

    public void setRoomID(long value) {
        this.roomID = value;
    }

    public long getRoundID() {
        return roundID;
    }

    public void setRoundID(long value) {
        this.roundID = value;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double value) {
        this.amount = value;
    }

    public double getCardCost() {
        return cardCost;
    }

    public void setCardCost(double cardCost) {
        this.cardCost = cardCost;
    }
    
    public double getCardCostUSD() {
		return cardCostUSD;
	}

	public void setCardCostUSD(double cardCostUSD) {
		this.cardCostUSD = cardCostUSD;
	}
	
    public int getCardsNumber() {
        return cardsNumber;
    }

    public void setCardsNumber(int value) {
        this.cardsNumber = value;
    }

    public int getPackSize() {
        return packSize;
    }

    public void setPackSize(int value) {
        this.packSize = value;
    }


    @Override
    public String toString() {
        return "BuyBingoCardsRequest{" +
                "brandID=" + brandID +
                ", playerID=" + playerID +
                ", transactionID='" + transactionID + '\'' +
                ", roomID=" + roomID +
                ", roundID=" + roundID +
                ", amount=" + amount +
                ", cardCost=" + cardCost +
                ", cardCostUSD=" + cardCostUSD +
                ", cardsNumber=" + cardsNumber +
                ", packSize=" + packSize +
                '}';
    }
}
