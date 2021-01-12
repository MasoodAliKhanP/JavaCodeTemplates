package biz.digitalhouse.integration.v3.web.ws.http.scratchCardAPI.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ScratchCardClaimedWinnersRequest extends BaseRequest {
	@NotNull(message = "PromotionId is mandatory")
	private Integer promotionId;
	@NotNull(message = "External PlayerId is mandatory")
	private String externalPlayerId;
}
