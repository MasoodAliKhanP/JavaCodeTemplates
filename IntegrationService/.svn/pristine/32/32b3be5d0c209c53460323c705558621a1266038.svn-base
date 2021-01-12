package biz.digitalhouse.integration.v3.services.externalClient.dto;

/**
 * @author Vitalii Babenko
 *         on 10.04.2016.
 */
public class BuyBingoCardsResult extends BalanceResult {

    private String transactionId;
    private Integer proposedCardsNumber;
    private double bonusPart;
    private int status;

    public BuyBingoCardsResult(int status) {
        this.status = status;
    }

    public BuyBingoCardsResult(String vendorId, double cash, double bonus, String transactionId, double bonusPart) {
        super(vendorId, cash, bonus);
        this.transactionId = transactionId;
        this.status = 0;
        this.bonusPart = bonusPart;
    }

    public BuyBingoCardsResult(String vendorId, double cash, double bonus, Integer proposedCardsNumber, int status) {
        super(vendorId, cash, bonus);
        this.proposedCardsNumber = proposedCardsNumber;
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Integer getProposedCardsNumber() {
        return proposedCardsNumber;
    }
    
    public double getBonusPart() {
		return bonusPart;
	}

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "BuyBingoCardsResult{" +
                "transactionId='" + transactionId + '\'' +
                ", proposedCardsNumber=" + proposedCardsNumber +
                ", bonusPart=" + bonusPart +
                ", status=" + status +
                "} " + super.toString();
    }
}
