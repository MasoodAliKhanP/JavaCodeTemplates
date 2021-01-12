package biz.digitalhouse.integration.v3.services.scratchCard;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biz.digitalhouse.integration.v3.dao.ScratchCardDao;
import biz.digitalhouse.integration.v3.web.ws.http.scratchCardAPI.dto.ScratchCardClaimedWinner;
import biz.digitalhouse.integration.v3.web.ws.http.scratchCardAPI.dto.ScratchCardClaimedWinnersRequest;

/**
 * @author masood
 *
 *         07-Jan-2021
 */
@Service
public class ScratchCardManagerImpl implements ScratchCardManager {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private ScratchCardDao scratchCardDao;

	@Override
	public List<ScratchCardClaimedWinner> winners(ScratchCardClaimedWinnersRequest request){
			return scratchCardDao.winners(request);
	}

}
