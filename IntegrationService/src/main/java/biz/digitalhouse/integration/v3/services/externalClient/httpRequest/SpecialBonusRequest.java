package biz.digitalhouse.integration.v3.services.externalClient.httpRequest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SpecialBonusRequest {
	private String partnerID;
	private String labelID;
	private String ecrExternalId;
	private String bonusId;
	private Long eventTimeStamp;
	private Double offerValue;
	private Integer issueMultiplier;
	private Integer drpAmount;
	private String agentName;
	private String comments;
	private Integer freeSpinCount;
}
