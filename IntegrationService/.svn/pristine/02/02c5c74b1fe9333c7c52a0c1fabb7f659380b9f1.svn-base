package biz.digitalhouse.integration.v3.web.ws.http.internalAPIEndpoint.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScratchCardWinRequest extends BaseRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private long playerID;
	private long promotionId;
	private String prizeTypeId;
	private double offerValue;
	private String currency;

	public ScratchCardWinRequest() {
		super();
	}

	public ScratchCardWinRequest(String vendorID, long brandID, long promotionId, long playerID, String prizeTypeId, double offerValue,
			String currency) {
		super(vendorID, brandID);
		this.promotionId = promotionId;
		this.playerID = playerID;
		this.prizeTypeId = prizeTypeId;
		this.offerValue = offerValue;
		this.currency = currency;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
