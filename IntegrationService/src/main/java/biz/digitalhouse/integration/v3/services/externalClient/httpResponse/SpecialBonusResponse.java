package biz.digitalhouse.integration.v3.services.externalClient.httpResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SpecialBonusResponse extends BaseAPIResponse{
	private String bonusId;

}
