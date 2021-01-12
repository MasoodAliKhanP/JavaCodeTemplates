package biz.digitalhouse.integration.v3.services.externalClient.httpResponse;

/**
 * @author Vitalii Babenko
 *         on 10.04.2016.
 */
public class WinResponse extends BalanceResponse {

    private String transactionID;
    private double bonusPartWin;

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }
    
    public double getBonusPartWin() {
		return bonusPartWin;
	}
    
    public void setBonusPartWin(double bonusPartWin) {
		this.bonusPartWin = bonusPartWin;
	}

    @Override
    public String toString() {
        return "WinResponse{" +
                "transactionID='" + transactionID + '\'' +
                ", bonusPartWin='" + bonusPartWin + '\'' +
                "} " + super.toString();
    }
}
