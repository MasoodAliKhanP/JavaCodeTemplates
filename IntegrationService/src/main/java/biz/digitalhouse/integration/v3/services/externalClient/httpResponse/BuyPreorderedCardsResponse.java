package biz.digitalhouse.integration.v3.services.externalClient.httpResponse;

/**
 * Edited by arbuzov on 20/04/2016.
 */
public class BuyPreorderedCardsResponse extends BaseResponse {

    private String transactionID;
    private int boughtCardsNumber;
    private double cash;
    private double bonus;
    private double bonusPartBet;

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public int getBoughtCardsNumber() {
        return boughtCardsNumber;
    }

    public void setBoughtCardsNumber(int boughtCardsNumber) {
        this.boughtCardsNumber = boughtCardsNumber;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }
    
    public double getBonusPartBet() {
		return bonusPartBet;
	}
    
    public void setBonusPartBet(double bonusPartBet) {
		this.bonusPartBet = bonusPartBet;
	}

    @Override
    public String toString() {
        return "BuyPreorderedCardsResponse{" +
                "transactionID='" + transactionID + '\'' +
                ", boughtCardsNumber=" + boughtCardsNumber +
                ", cash=" + cash +
                ", bonus=" + bonus +
                ", bonusPartBet=" + bonusPartBet +
                "} " + super.toString();
    }
}