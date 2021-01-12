package biz.digitalhouse.integration.v3.web.ws.soap.freeRoundsBonusAPI;

import biz.digitalhouse.integration.v3.ServiceResponseCode;
import biz.digitalhouse.integration.v3.exceptions.CommonServiceException;
import biz.digitalhouse.integration.v3.services.brand.BrandManager;
import biz.digitalhouse.integration.v3.services.freeRoundsBonus.FreeRoundsBonusManager;
import biz.digitalhouse.integration.v3.services.freeRoundsBonus.dto.InfoFRBonus;
import biz.digitalhouse.integration.v3.utils.DateTimeUtil;
import biz.digitalhouse.integration.v3.web.ws.WsAbstractAPI;
import biz.digitalhouse.integration.v3.web.ws.dto.freeRoundsBonusAPI.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

/**
 * @author Vitalii Babenko
 *         on 26.02.2016.
 */
@Endpoint
public class FreeRoundsBonusAPIEndpoint extends WsAbstractAPI {

    private final static String NAMESPACE_URI = "http://gametechclubs.biz/frb/external/schemas";
    private final Log log = LogFactory.getLog(getClass());
    private FreeRoundsBonusManager freeRoundsBonusManager;

    @Autowired
    public FreeRoundsBonusAPIEndpoint(BrandManager brandManager, FreeRoundsBonusManager freeRoundsBonusManager) {
        super(brandManager);
        this.freeRoundsBonusManager = freeRoundsBonusManager;
    }

    @SuppressWarnings("unused")
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "awardFRBRequest")
    @ResponsePayload
    public AwardFRBResponse awardFRB(@RequestPayload AwardFRBRequest req) {
        AwardFRBResponse res = new AwardFRBResponse();
        try {
            long brandId = check(req.getSecureLogin(), req.getSecurePassword());

            String bonusId = freeRoundsBonusManager.awardFRB(brandId, req.getPlayerID(), req.getCurrency(), req.getNickname(), req.getGameIDList(),
                    req.getRounds(), req.getBonusCode(), req.getCoinValue(), req.getExpirationDate().toGregorianCalendar());

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

    @SuppressWarnings("unused")
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "checkFRBRequest")
    @ResponsePayload
    public CheckFRBResponse checkFRB(@RequestPayload CheckFRBRequest req) {
        CheckFRBResponse res = new CheckFRBResponse();
        try {
            long brandId = check(req.getSecureLogin(), req.getSecurePassword());
            String bonusId = freeRoundsBonusManager.checkFRB(brandId, req.getBonusCode());
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

    @SuppressWarnings("unused")
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "cancelFRBRequest")
    @ResponsePayload
    public CancelFRBResponse cancelFRB(@RequestPayload CancelFRBRequest req) {
        CancelFRBResponse res = new CancelFRBResponse();
        try {
            long brandId = check(req.getSecureLogin(), req.getSecurePassword());
            freeRoundsBonusManager.cancelFRB(brandId, req.getExternalBonusID());
            putMessage(res, ServiceResponseCode.OK);
        } catch (CommonServiceException e) {
            putException(res, e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            putMessage(res, ServiceResponseCode.INTERNAL_ERROR);
        }
        return res;
    }

    @SuppressWarnings("unused")
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "infoFRBRequest")
    @ResponsePayload
    public InfoFRBResponse infoFRB(@RequestPayload InfoFRBRequest req) {
        InfoFRBResponse res = new InfoFRBResponse();
        try {
            long brandId = check(req.getSecureLogin(), req.getSecurePassword());
            List<InfoFRBonus> result = freeRoundsBonusManager.infoFRB(brandId, req.getPlayerID());
            log.debug(result);
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
