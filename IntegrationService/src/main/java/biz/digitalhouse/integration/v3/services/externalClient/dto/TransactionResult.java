package biz.digitalhouse.integration.v3.services.externalClient.dto;

import biz.digitalhouse.integration.v3.InternalServiceResponseStatus;

/**
 * @author Vitalii Babenko
 *         on 03.04.2016.
 */
public class TransactionResult extends BalanceResult {

    private String transactionId;
    private double usedBonus;
    private int status;


    public TransactionResult() {
    }

    public TransactionResult(int status) {
        this.status = status;
    }

    public TransactionResult(String vendorId, double cash, double bonus, int status) {
        super(vendorId, cash, bonus);
        this.status = status;
    }

    public TransactionResult(String vendorId, double cash, double bonus, Double usedBonus, String transactionId) {
        super(vendorId, cash, bonus);
        this.status = InternalServiceResponseStatus.OK;
        this.usedBonus = usedBonus != null ? usedBonus : 0D;
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public double getUsedBonus() {
        return usedBonus;
    }

    public int getStatus() {
        return status;
    }

	@Override
	public String toString() {
		return "TransactionResult [transactionId=" + transactionId + ", usedBonus=" + usedBonus + ", status=" + status
				+ "] " + super.toString();
	}
}
