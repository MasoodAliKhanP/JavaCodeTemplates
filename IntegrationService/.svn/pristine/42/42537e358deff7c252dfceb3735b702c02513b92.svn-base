package biz.digitalhouse.integration.v3.web.ws.http.casinoGameAPI;

import biz.digitalhouse.integration.v3.ServiceResponseCode;
import biz.digitalhouse.integration.v3.exceptions.CommonServiceException;
import biz.digitalhouse.integration.v3.services.brand.BrandManager;
import biz.digitalhouse.integration.v3.services.casinoGames.CasinoGameManager;
import biz.digitalhouse.integration.v3.services.casinoGames.dto.CasinoGameInfoDTO;
import biz.digitalhouse.integration.v3.services.casinoGames.dto.JackpotInfoDTO;
import biz.digitalhouse.integration.v3.services.casinoGames.dto.PlaySessionDTO;
import biz.digitalhouse.integration.v3.web.ws.WsAbstractAPI;
import biz.digitalhouse.integration.v3.web.ws.dto.casinoGameAPI.*;
import biz.digitalhouse.integration.v3.web.ws.dto.casinoGameAPI.BaseResponse;
import biz.digitalhouse.integration.v3.web.ws.http.casinoGameAPI.dto.*;
import biz.digitalhouse.integration.v3.web.ws.http.casinoGameAPI.dto.CasinoJackpotResponse;
import biz.digitalhouse.integration.v3.model.CasinoJackpot;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitalii Babenko
 *         on 28.02.2016.
 */
@Controller
@RequestMapping("/CasinoGameAPI")
public class CasinoGameAPIController extends WsAbstractAPI {

    private final Log log = LogFactory.getLog(getClass());
    private CasinoGameManager casinoGameManager;

    @Autowired
    public CasinoGameAPIController(BrandManager brandManager, CasinoGameManager casinoGameManager) {
        super(brandManager);
        this.casinoGameManager = casinoGameManager;
    }

    @RequestMapping(value = "/casinoGames", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public CasinoGamesResponse casinoGames(@RequestParam("identifier") String identifier,
                                           @RequestParam("hash") String hash) {

        CasinoGamesResponse res = new CasinoGamesResponse();
        try {
            long brandId = check(identifier, hash);
            List<CasinoGameInfoDTO> result = casinoGameManager.getCasinoGames(brandId);
            for(CasinoGameInfoDTO dto : result) {
                CasinoGame game = new CasinoGame();
                game.setGameID(dto.getGameSymbol());
                game.setGameName(dto.getGameName());
                game.setTechnology(dto.getGameTechnology());
                res.getGameList().add(game);
            }
            putMessage(res, ServiceResponseCode.OK);
        } catch (CommonServiceException e) {
            putException(res, e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            putMessage(res, ServiceResponseCode.INTERNAL_ERROR);
        }
        return res;
    }
    
    @RequestMapping(value = "/casinoGamesByType", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public CasinoGamesResponse casinoGamesByType(@RequestParam("identifier") String identifier,
                                           @RequestParam("hash") String hash,
                                           @RequestParam("type") String type) {

        CasinoGamesResponse res = new CasinoGamesResponse();
        try {
            long brandId = check(identifier, hash);
            List<CasinoGameInfoDTO> result = casinoGameManager.getCasinoGamesByType(brandId,type);
            for(CasinoGameInfoDTO dto : result) {
                CasinoGame game = new CasinoGame();
                game.setGameID(dto.getGameSymbol());
                game.setGameName(dto.getGameName());
                game.setTechnology(dto.getGameTechnology());
                res.getGameList().add(game);
            }
            putMessage(res, ServiceResponseCode.OK);
        } catch (CommonServiceException e) {
            putException(res, e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            putMessage(res, ServiceResponseCode.INTERNAL_ERROR);
        }
        return res;
    }

    @RequestMapping(value = "/playSessions", method = {RequestMethod.POST})
    @ResponseBody
    public PlaySessionsResponse playSessions(@RequestBody PlaySessionsRequest req) {
        PlaySessionsResponse res = new PlaySessionsResponse();
        try {
            long brandId = check(req.getIdentifier(), req.getHash());
            for(PlaySessionDTO dto : casinoGameManager.getPlaySessions(brandId, req.getExtPlayerID(), req.getGameSymbol(), req.getDate())) {
                res.getPlaySessionList().add(new PlaySessionsResponse.PlaySession(dto.getPlaySessionID(), dto.getExtPlayerID(), dto.getGameSymbol(), dto.getCurrency(), dto.getBetAmount(), dto.getWinAmount(), dto.getCreated()));
            }
            putMessage(res, ServiceResponseCode.OK);
        } catch (CommonServiceException e) {
            putException(res, e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            putMessage(res, ServiceResponseCode.INTERNAL_ERROR);
        }
        return res;
    }

    @RequestMapping(value = "/casinoJackpots", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public CasinoJackpotResponse casinoJackpots(@RequestParam("identifier") String identifier,
    											@RequestParam("currency_symbol") String currencySymbol){
    	CasinoJackpotResponse res = new CasinoJackpotResponse();
    	try {
    		long brandId = check(identifier);
            List<JackpotInfoDTO> result = casinoGameManager.getAllJackpotsForCasino(brandId);
            double exchangeRate = casinoGameManager.getExchangeRate(currencySymbol);
            ArrayList<CasinoJackpot> jackpotList = new ArrayList<CasinoJackpot>();
            
            log.info("exchange rate" + String.valueOf(exchangeRate));
            for(JackpotInfoDTO dto : result) {
                CasinoJackpot casinoJackpot = new CasinoJackpot();
                casinoJackpot.setGameSymbol(dto.getGameSymbol());
                casinoJackpot.setJackpotName(dto.getJackpotName());
                casinoJackpot.setJackpotID(dto.getJackpotID());
                casinoJackpot.setJackpotRealAmount(dto.getJackpotRealAmount() / exchangeRate);
                casinoJackpot.setTier(dto.getTier());
                casinoJackpot.setCasinoID(dto.getCasinoID());
                jackpotList.add(casinoJackpot);	
            }
            res.setCasinoJackpotList(jackpotList);
            putMessage(res, ServiceResponseCode.OK);
        } catch (CommonServiceException e) {
            putException(res, e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            putMessage(res, ServiceResponseCode.INTERNAL_ERROR);
        }
        return res;
    }
    
    private void putMessage(biz.digitalhouse.integration.v3.web.ws.http.casinoGameAPI.dto.BaseResponse res, ServiceResponseCode en) {
        res.setError(en.getCode());
        res.setDescription(en.getDescription());
    }

    private void putException(biz.digitalhouse.integration.v3.web.ws.http.casinoGameAPI.dto.BaseResponse res, CommonServiceException e) {
        res.setError(e.getResponse().getCode());
        res.setDescription(e.getResponse().getDescription());
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

