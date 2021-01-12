package biz.digitalhouse.integration.v3.web.ws.oldSoap.freeRoundsBonusAPI;

import biz.digitalhouse.integration.v3.ServiceResponseCode;
import biz.digitalhouse.integration.v3.exceptions.CommonServiceException;
import biz.digitalhouse.integration.v3.services.brand.BrandManager;
import biz.digitalhouse.integration.v3.services.freeRoundsBonus.FreeRoundsBonusManager;
import biz.digitalhouse.integration.v3.services.freeRoundsBonus.dto.InfoFRBonus;
import biz.digitalhouse.integration.v3.utils.DateTimeUtil;
import biz.digitalhouse.integration.v3.web.ws.WsAbstractAPI;
import biz.digitalhouse.integration.v3.web.ws.old.dto.freeRoundsBonusAPI.*;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * <p>Class: FreeRoundBonusAPIEndpoint</p>
 * <p>Description: SOAP endpoint</p>
 *
 * @author Sergey Miliaev
 */

@Endpoint
@SuppressWarnings("unused")
public class OldFreeRoundsBonusAPIEndpoint extends WsAbstractAPI {

    private final Log log = LogFactory.getLog(getClass());
    private FreeRoundsBonusManager freeRoundsBonusManager;

    @Autowired
    public OldFreeRoundsBonusAPIEndpoint(BrandManager brandManager, FreeRoundsBonusManager freeRoundsBonusManager) {
        super(brandManager);
        this.freeRoundsBonusManager = freeRoundsBonusManager;
    }

    @SuppressWarnings("unused")
    @PayloadRoot(localPart = "CreateFRBRequest", namespace = "http://gametechclubs.biz/casino/frb/external/schemas")
    @ResponsePayload
    public CreateFRBResponse createFRB(@RequestPayload CreateFRBRequest request) {

        CreateFRBResponse response = new CreateFRBResponse();
        try {
            long brandId = check(request.getSecureLogin(), request.getSecurePassword());

            String bonusId = freeRoundsBonusManager.awardFRB(brandId, request.getPlayerID(), request.getCurrency(),
                    request.getPlayerID(), request.getGameIDList(),
                   request.getRounds(), request.getBonusCode(), request.getCoinValue(), request.getExpirationDate().toGregorianCalendar());

            response.setExternalBonusID(bonusId);
            response.setError(ServiceResponseCode.OK.getCode());
            response.setDescription(ServiceResponseCode.OK.getDescription());
        } catch (CommonServiceException e) {
            response.setError(e.getResponse().getCode());
            response.setDescription(e.getResponse().getDescription());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.setError(ServiceResponseCode.INTERNAL_ERROR.getCode());
            response.setDescription(ServiceResponseCode.INTERNAL_ERROR.getDescription());
        }

        return response;
    }

    @SuppressWarnings("unused")
    @PayloadRoot(localPart = "CheckFRBRequest", namespace = "http://gametechclubs.biz/casino/frb/external/schemas")
    @ResponsePayload
    public CheckFRBResponse checkFRB(@RequestPayload CheckFRBRequest request) {

        CheckFRBResponse response = new CheckFRBResponse();

        try {
            long brandId = check(request.getSecureLogin(), request.getSecurePassword());
            String bonusId = freeRoundsBonusManager.checkFRB(brandId, request.getBonusCode());
            response.setExternalBonusID(bonusId);
            response.setError(ServiceResponseCode.OK.getCode());
            response.setDescription(ServiceResponseCode.OK.getDescription());
        } catch (CommonServiceException e) {
            response.setError(e.getResponse().getCode());
            response.setDescription(e.getResponse().getDescription());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.setError(ServiceResponseCode.INTERNAL_ERROR.getCode());
            response.setDescription(ServiceResponseCode.INTERNAL_ERROR.getDescription());
        }

        return response;
    }

    @SuppressWarnings("unused")
    @PayloadRoot(localPart = "CancelFRBRequest", namespace = "http://gametechclubs.biz/casino/frb/external/schemas")
    @ResponsePayload
    public CancelFRBResponse cancelFRB(@RequestPayload CancelFRBRequest request) {

        CancelFRBResponse response = new CancelFRBResponse();

        try {
            long brandId = check(request.getSecureLogin(), request.getSecurePassword());
            freeRoundsBonusManager.cancelFRB(brandId, request.getExternalBonusID());
            response.setError(ServiceResponseCode.OK.getCode());
            response.setDescription(ServiceResponseCode.OK.getDescription());
        } catch (CommonServiceException e) {
            response.setError(e.getResponse().getCode());
            response.setDescription(e.getResponse().getDescription());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.setError(ServiceResponseCode.INTERNAL_ERROR.getCode());
            response.setDescription(ServiceResponseCode.INTERNAL_ERROR.getDescription());
        }

        return response;
    }

    @SuppressWarnings("unused")
    @PayloadRoot(localPart = "InfoFRBRequest", namespace = "http://gametechclubs.biz/casino/frb/external/schemas")
    @ResponsePayload
    public InfoFRBResponse infoFRB(@RequestPayload InfoFRBRequest request) {

        InfoFRBResponse response = new InfoFRBResponse();
        try {
            long brandId = check(request.getSecureLogin(), request.getSecurePassword());
            List<InfoFRBonus> result = freeRoundsBonusManager.infoFRB(brandId, request.getPlayerID());
            log.debug(result);
            for (InfoFRBonus info : result) {
                FRBonus bonus = new FRBonus();
                bonus.setExternalBonusID(info.getFreeRoundBonusID());
                bonus.setCreateDate(DateTimeUtil.convertDateToXMLCalendar(info.getCreateDate()));
                bonus.setExpireDate(DateTimeUtil.convertDateToXMLCalendar(info.getExpireDate()));
                bonus.setGames(info.getGames());
                bonus.setInitialRounds(info.getInitialRounds());
                bonus.setPlayedRounds(info.getPlayedRounds());
                response.getBonuses().add(bonus);
            }
            response.setError(ServiceResponseCode.OK.getCode());
            response.setDescription(ServiceResponseCode.OK.getDescription());
        } catch (CommonServiceException e) {
            response.setError(e.getResponse().getCode());
            response.setDescription(e.getResponse().getDescription());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.setError(ServiceResponseCode.INTERNAL_ERROR.getCode());
            response.setDescription(ServiceResponseCode.INTERNAL_ERROR.getDescription());
        }

        return response;
    }
}



