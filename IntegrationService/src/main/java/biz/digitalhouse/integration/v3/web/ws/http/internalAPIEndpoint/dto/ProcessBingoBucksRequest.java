package biz.digitalhouse.integration.v3.web.ws.http.internalAPIEndpoint.dto;

/**
 * @author Vitalii
 * on 30.07.2018.
 */
public class ProcessBingoBucksRequest {

    private long playerID;
    private long roomID;
    private long chatRoundID;
    private String transactionID;
    private double amount;
    private int isUnlimited;

	public long getPlayerID() {
        return playerID;
    }

    public void setPlayerID(long playerID) {
        this.playerID = playerID;
    }

    public long getRoomID() {
        return roomID;
    }

    public void setRoomID(long roomID) {
        this.roomID = roomID;
    }

    public long getChatRoundID() {
        return chatRoundID;
    }

    public void setChatRoundID(long chatRoundID) {
        this.chatRoundID = chatRoundID;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public int getIsUnlimited() {
		return isUnlimited;
	}

	public void setIsUnlimited(int isUnlimited) {
		this.isUnlimited = isUnlimited;
	}

    @Override
    public String toString() {
        return "ProcessBingoBucksRequest{" +
                "playerID=" + playerID +
                ", roomID=" + roomID +
                ", chatRoundID=" + chatRoundID +
                ", transactionID='" + transactionID + '\'' +
                ", amount=" + amount +
                ",isUnlimited="+isUnlimited+
                '}';
    }
}
