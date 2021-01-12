package biz.digitalhouse.integration.v3.web.ws.http.freeRoundsBonusAPI;

import biz.digitalhouse.integration.v3.ServiceResponseCode;
import biz.digitalhouse.integration.v3.exceptions.CommonServiceException;
import biz.digitalhouse.integration.v3.services.brand.BrandManager;
import biz.digitalhouse.integration.v3.services.freeRoundsBonus.FreeRoundsBonusManager;
import biz.digitalhouse.integration.v3.services.freeRoundsBonus.dto.InfoFRBonus;
import biz.digitalhouse.integration.v3.services.freeRoundsBonus.dto.ResultFreeRoundsGames;
import biz.digitalhouse.integration.v3.utils.DateTimeUtil;
import biz.digitalhouse.integration.v3.web.ws.WsAbstractAPI;
import biz.digitalhouse.integration.v3.web.ws.dto.freeRoundsBonusAPI.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Vitalii Babenko
 *         on 28.02.2016.
 */
@Controller
@RequestMapping("/FreeRoundsBonusAPI")
public class FreeRoundsBonusAPIController extends WsAbstractAPI {

    private final Log log = LogFactory.getLog(getClass());
    private FreeRoundsBonusManager freeRoundsBonusManager;

    @Autowired
    public FreeRoundsBonusAPIController(BrandManager brandManager, FreeRoundsBonusManager freeRoundsBonusManager) {
        super(brandManager);
        this.freeRoundsBonusManager = freeRoundsBonusManager;
    }
    @RequestMapping(value = "/gamesApplicable", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public List<String[]> getFreeRoundsApplicableGames(@RequestParam("identifier") String identifier,
                                     @RequestParam("hash") String hash,@RequestParam("technology") String technology
                                    ) {
        try {
            long brandId = check(identifier, hash);
            List<String[]> games = freeRoundsBonusManager.getFreeRoundsApplicableGames(brandId,technology);
            return games;
        } catch (CommonServiceException e) {
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }
    
    
    @RequestMapping(value = "/awardFRB", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public AwardFRBResponse awardFRB(@RequestParam("identifier") String identifier,
                                     @RequestParam("hash") String hash,
                                     @RequestParam("playerId") String playerId,
                                     @RequestParam("currency") String currency,
                                     @RequestParam("nickname") String nickname,
                                     @RequestParam("gameIDList") String gameIDList,
                                     @RequestParam("rounds") int rounds,
                                     @RequestParam("bonusCode") String bonusCode,
                                     @RequestParam("coinValue") double coinValue,
                                     @RequestParam("expirationDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Calendar expirationDate) {
        AwardFRBResponse res = new AwardFRBResponse();
        try {
            long brandId = check(identifier, hash);
            String bonusId = freeRoundsBonusManager.awardFRB(brandId, playerId, currency, nickname, gameIDList, rounds, bonusCode, coinValue, expirationDate);
            res.setExternalBonusID(bonusId);
            putMessage(res, ServiceResponseCode.OK);
        } catch (CommonServiceException e) {
            putException(res, e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            putMessage(res, ServiceResponseCode.INTERNAL_ERROR);
        }
        return res;
    }

    @RequestMapping(value = "/checkFRB", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public CheckFRBResponse checkFRB(@RequestParam("identifier") String identifier,
                                     @RequestParam("hash") String hash,
                                     @RequestParam("bonusCode") String bonusCode) {
        CheckFRBResponse res = new CheckFRBResponse();
        try {
            long brandId = check(identifier, hash);
            String bonusId = freeRoundsBonusManager.checkFRB(brandId, bonusCode);
            res.setExternalBonusID(bonusId);
            putMessage(res, ServiceResponseCode.OK);
        } catch (CommonServiceException e) {
            putException(res, e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            putMessage(res, ServiceResponseCode.INTERNAL_ERROR);
        }
        return res;
    }

    @RequestMapping(value = "/cancelFRB", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public CancelFRBResponse cancelFRB(@RequestParam("identifier") String identifier,
                                       @RequestParam("hash") String hash,
                                       @RequestParam("externalBonusId") String externalBonusId) {
        CancelFRBResponse res = new CancelFRBResponse();
        try {
            long brandId = check(identifier, hash);
            freeRoundsBonusManager.cancelFRB(brandId, externalBonusId);
            putMessage(res, ServiceResponseCode.OK);
        } catch (CommonServiceException e) {
            putException(res, e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            putMessage(res, ServiceResponseCode.INTERNAL_ERROR);
        }
        return res;
    }

    @RequestMapping(value = "/infoFRB", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public InfoFRBResponse infoFRB(@RequestParam("identifier") String identifier,
                                   @RequestParam("hash") String hash,
                                   @RequestParam("playerId") String playerId) {
        InfoFRBResponse res = new InfoFRBResponse();
        try {
            long brandId = check(identifier, hash);
            List<InfoFRBonus> result = freeRoundsBonusManager.infoFRB(brandId, playerId);
            for (InfoFRBonus info : result) {
                FRBonus bonus = new FRBonus();
                bonus.setExternalBonusID(info.getFreeRoundBonusID());
                bonus.setCreateDate(DateTimeUtil.convertDateToXMLCalendar(info.getCreateDate()));
                bonus.setExpireDate(DateTimeUtil.convertDateToXMLCalendar(info.getExpireDate()));
                bonus.setGames(info.getGames());
                bonus.setInitialRounds(info.getInitialRounds());
                bonus.setPlayedRounds(info.getPlayedRounds());
                res.getBonuses().add(bonus);
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

    private void putMessage(BaseResponse res, ServiceResponseCode en) {
        res.setError(en.getCode());
        res.setDescription(en.getDescription());
    }

    private void putException(BaseResponse res, CommonServiceException e) {
        res.setError(e.getResponse().getCode());
        res.setDescription(e.getResponse().getDescription());
    }
}
