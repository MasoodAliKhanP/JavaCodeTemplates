package biz.digitalhouse.integration.v3.services.scratchCard;

import java.util.List;

import biz.digitalhouse.integration.v3.web.ws.http.scratchCardAPI.dto.ScratchCardClaimedWinner;
import biz.digitalhouse.integration.v3.web.ws.http.scratchCardAPI.dto.ScratchCardClaimedWinnersRequest;

/**
 * @author masood
 *
 * 06-Jan-2021
 */
public interface ScratchCardManager {

	List<ScratchCardClaimedWinner> winners(ScratchCardClaimedWinnersRequest request);

}

