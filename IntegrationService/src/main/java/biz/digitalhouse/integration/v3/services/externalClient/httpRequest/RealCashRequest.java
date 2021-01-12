package biz.digitalhouse.integration.v3.services.externalClient.httpRequest;

import java.util.UUID;

import lombok.Data;

@Data
public class RealCashRequest {
	private String partnerID;
	private String labelID;
	private String productID;
	private String subProductID;
	private String channel;
	private String subChannel;
	private String initiatorReferenceID;
	private String ecrExternalId;
	private Double transactionAmount;
	private String transactionCurrency;
	private Object fundSessionID;
	private Object exchangeSnapshotID;
	private String agentName;
	private String comments;
	private String transactionTypeKey;
	private GameData gameData;
	
	public RealCashRequest(String partnerId, String labelId, String extPlayerId, Double amount, String currency){
		super();
		this.partnerID = partnerId;
		this.labelID = labelId;
		UUID uuid = UUID.randomUUID();
		this.initiatorReferenceID = uuid.toString();
		this.ecrExternalId = extPlayerId;
		this.transactionAmount = amount;
		this.transactionCurrency = currency;
		this.productID = "CASINO";
		this.subProductID = "NONE";
		this.channel = "desktop";
		this.subChannel = "html";
		this.fundSessionID = null;
		this.exchangeSnapshotID = null;
		this.agentName = "SYSTEM";
		this.comments = "";
		this.transactionTypeKey = "REAL_CASH_ADDITION_BY_CAMPAIGN";
		this.gameData = new GameData(0, 0, 0, 2000, 2000, 0);
	}
}
