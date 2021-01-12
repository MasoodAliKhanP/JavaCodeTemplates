package biz.digitalhouse.integration.v3.services.externalClient.httpResponse;

/**
 * @author Vitalii Babenko
 *         on 10.04.2016.
 */
public class BuyBingoCardsResponse extends BalanceResponse {

    private String transactionID;
    private Integer proposedCardsNumber;
    private double bonusPartBet;

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public Integer getProposedCardsNumber() {
        return proposedCardsNumber;
    }

    public void setProposedCardsNumber(Integer proposedCardsNumber) {
        this.proposedCardsNumber = proposedCardsNumber;
    }
    
    public double getBonusPartBet() {
		return bonusPartBet;
	}
    
    public void setBonusPartBet(double bonusPartBet) {
		this.bonusPartBet = bonusPartBet;
	}

    @Override
    public String toString() {
        return "BuyBingoCardsResponse{" +
                "transactionID='" + transactionID + '\'' +
                ", proposedCardsNumber=" + proposedCardsNumber +
                ", bonusPartBet=" + bonusPartBet +
                "} " + super.toString();
    }
}
