package biz.digitalhouse.integration.v3.web.ws.http.scratchCardAPI.dto;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author masood
 *
 * 07-Jan-2021
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ScratchCardClaimedWinnerResponse extends BaseResponse {
	List<ScratchCardClaimedWinner> winners;
}
