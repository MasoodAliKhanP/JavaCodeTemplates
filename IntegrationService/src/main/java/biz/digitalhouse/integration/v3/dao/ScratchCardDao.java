package biz.digitalhouse.integration.v3.dao;

import java.util.List;

import biz.digitalhouse.integration.v3.model.BonusDetails;
import biz.digitalhouse.integration.v3.web.ws.http.scratchCardAPI.dto.ScratchCardClaimedWinner;
import biz.digitalhouse.integration.v3.web.ws.http.scratchCardAPI.dto.ScratchCardClaimedWinnersRequest;

public interface ScratchCardDao {
	BonusDetails getBonusDetails(String bonusType);
	
	void updateScratchCardWinClaim(String externalReference, long memberId, long promotionId, String prizeTypeId, double amount);
	
	List<ScratchCardClaimedWinner>  winners(ScratchCardClaimedWinnersRequest request);
}
