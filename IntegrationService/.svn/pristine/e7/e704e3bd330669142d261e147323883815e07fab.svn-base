package biz.digitalhouse.integration.v3.web.ws.http.scratchCardAPI;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import biz.digitalhouse.integration.v3.ServiceResponseCode;
import biz.digitalhouse.integration.v3.exceptions.CommonServiceException;
import biz.digitalhouse.integration.v3.services.brand.BrandManager;
import biz.digitalhouse.integration.v3.services.scratchCard.ScratchCardManager;
import biz.digitalhouse.integration.v3.web.ws.WsAbstractAPI;
import biz.digitalhouse.integration.v3.web.ws.http.scratchCardAPI.dto.BaseResponse;
import biz.digitalhouse.integration.v3.web.ws.http.scratchCardAPI.dto.ScratchCardClaimedWinner;
import biz.digitalhouse.integration.v3.web.ws.http.scratchCardAPI.dto.ScratchCardClaimedWinnerResponse;
import biz.digitalhouse.integration.v3.web.ws.http.scratchCardAPI.dto.ScratchCardClaimedWinnersRequest;


/**
 * @author masood
 *
 * 07-Jan-2021
 */
@Controller
@RequestMapping("/scratchCardAPI")
public class ScratchCardAPIController extends WsAbstractAPI {

    private final Log log = LogFactory.getLog(getClass());
    private ScratchCardManager scratchCardManager;
    
    @Autowired
    public ScratchCardAPIController(BrandManager brandManager, ScratchCardManager scratchCardManager) {
        super(brandManager);
        this.scratchCardManager = scratchCardManager;
    }

    @RequestMapping(value = "/scratchCardWinners", method = {RequestMethod.POST})
    @ResponseBody
    public ScratchCardClaimedWinnerResponse scratchCardWinners(@Valid @RequestBody ScratchCardClaimedWinnersRequest request) {

    	ScratchCardClaimedWinnerResponse res = new ScratchCardClaimedWinnerResponse();
        try {
            check(request.getIdentifier(), request.getPassword());
            List<ScratchCardClaimedWinner> winnersList = scratchCardManager.winners(request);
            res.setWinners(winnersList);
            putMessage(res, ServiceResponseCode.OK);
        } catch (CommonServiceException e) {
            putException(res, e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            putMessage(res, ServiceResponseCode.INTERNAL_ERROR);
        }
        return res;
    }
    
    private void putMessage(BaseResponse res, ServiceResponseCode en) {
        res.setError(en.getCode());
        res.setDescription(en.getDescription());
    }

    private void putException(BaseResponse res, CommonServiceException e) {
        res.setError(e.getResponse().getCode());
        res.setDescription(e.getResponse().getDescription());
    }
}

