package biz.digitalhouse.integration.v3.services.externalClient.httpResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RealCashResponse extends BaseAPIResponse{	
	private String ecrExternalId;
	private String ecrCurrency;
	private long fundTransactionID;
	private long fundSessionID;
	private long exchangeSnapshotID;
	private String initiatorReferenceID;
	private String transactionTypeKey;
	private String requestedCurrency;
	private long realCashTransactionID;
	
	private int previousBalanceInECRCurrency;
	private int currentBalanceInECRCurrency;
	private int transactionAmountInHouseCurrency;
	private int transactionAmountInECRCurrency;
	private int transactionAmountInRequestedCurrency;
	private long bonusTransactionId;
	private int rrpPreviousBalanceInECRCurrency;
	private int rrpCurrentBalanceInECRCurrency;
	private int crpPreviousBalanceInECRCurrency;
	private int crpCurrentBalanceInECRCurrency;
	private int wrpPreviousBalanceInECRCurrency;
	private int wrpCurrentBalanceInECRCurrency;
	private int drpPreviousBalanceInECRCurrency;
	private int drpCurrentBalanceInECRCurrency;
}
