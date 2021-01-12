package biz.digitalhouse.integration.v3.services.freeRoundsBonus;


import biz.digitalhouse.integration.v3.ServiceResponseCode;
import biz.digitalhouse.integration.v3.exceptions.CommonServiceException;
import biz.digitalhouse.integration.v3.services.freeRoundsBonus.dto.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Class: FRBServiceClientImpl</p>
 * <p>Description: </p>
 *
 * @author Sergey Miliaev
 */

@Service
public class FRBServiceClientImpl implements FRBServiceClient {

    private final Log log = LogFactory.getLog(getClass());
    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
    private HttpClient httpClient = new HttpClient();

    @Value("${url.bonus.service}")
    private String BONUS_SERVICE_URL;

    enum BonusServiceMethods {
        createFreeRounds,
        cancelFreeRounds,
        checkFreeRoundBonus,
        getFreeRoundBonuses,
        gamesApplicable
    }

    enum PostParams {
        bonusCode,
        casinoID,
        expDate,
        freeRoundsNumber,
        gameSymbols,
        coinValue,
        memberID,
        freeRoundBonusID,
        technology
    }
    
    @Override
    public ResultFreeRoundsGames getFreeRoundsApplicableGames(long brandId,String technology) throws CommonServiceException {

        List<NameValuePair> map = new ArrayList<NameValuePair>();
        map.add(new NameValuePair(PostParams.casinoID.name(), Long.toString(brandId)));
        map.add(new NameValuePair(PostParams.technology.name(), technology));

        Response<List<String[]>> data = httpPost(getBonusServiceURL(BonusServiceMethods.gamesApplicable),
                map.toArray(new NameValuePair[map.size()]),
                new TypeToken<Response<List<String[]>>>() {
                }.getType());

        ResultFreeRoundsGames result = new ResultFreeRoundsGames();
        result.setGames(data.getValue(new ArrayList<String[]>()));
        result.setStatus(data.getStatus());
        result.setDescription(data.getDescription());

        return result;

    }

    @Override
    public ResultAwardFRBonus createFRB(long brandId, long playerId, String bonusCode, int freeRoundsNumber,
                                        String gameSymbols, double coinValue, String expDate) throws CommonServiceException {

        List<NameValuePair> map = new ArrayList<NameValuePair>();
        map.add(new NameValuePair(PostParams.bonusCode.name(), bonusCode));
        map.add(new NameValuePair(PostParams.casinoID.name(), Long.toString(brandId)));
        map.add(new NameValuePair(PostParams.expDate.name(), expDate));
        map.add(new NameValuePair(PostParams.freeRoundsNumber.name(), Integer.toString(freeRoundsNumber)));
        map.add(new NameValuePair(PostParams.gameSymbols.name(), gameSymbols));
        map.add(new NameValuePair(PostParams.coinValue.name(), Double.toString(coinValue)));
        map.add(new NameValuePair(PostParams.memberID.name(), Long.toString(playerId)));

        Response<ResultAwardFRBonus> data = httpPost(getBonusServiceURL(BonusServiceMethods.createFreeRounds),
                map.toArray(new NameValuePair[map.size()]),
                new TypeToken<Response<ResultAwardFRBonus>>() {
                }.getType());

        ResultAwardFRBonus result = data.getValue(new ResultAwardFRBonus());
        result.setStatus(data.getStatus());
        result.setDescription(data.getDescription());

        return result;

    }

    @Override
    public ResultCheckFRBonus checkFRB(long brandId, String bonusCode) throws CommonServiceException {

        List<NameValuePair> map = new ArrayList<NameValuePair>();
        map.add(new NameValuePair(PostParams.casinoID.name(), String.valueOf(brandId)));
        map.add(new NameValuePair(PostParams.bonusCode.name(), bonusCode));

        Response<ResultCheckFRBonus> data = httpPost(getBonusServiceURL(BonusServiceMethods.checkFreeRoundBonus),
                map.toArray(new NameValuePair[map.size()]),
                new TypeToken<Response<ResultCheckFRBonus>>() {
                }.getType());

        ResultCheckFRBonus result = data.getValue(new ResultCheckFRBonus());
        result.setStatus(data.getStatus());
        result.setDescription(data.getDescription());

        return result;

    }

    @Override
    public ResultCancelFRBonus cancelFRB(long brandId, String bonusId) throws CommonServiceException {

        List<NameValuePair> map = new ArrayList<NameValuePair>();
        map.add(new NameValuePair(PostParams.freeRoundBonusID.name(), bonusId));
        map.add(new NameValuePair(PostParams.casinoID.name(), Long.toString(brandId)));

        Response<ResultCancelFRBonus> data = httpPost(getBonusServiceURL(BonusServiceMethods.cancelFreeRounds),
                map.toArray(new NameValuePair[map.size()]),
                new TypeToken<Response<ResultCancelFRBonus>>() {
                }.getType());

        ResultCancelFRBonus result = data.getValue(new ResultCancelFRBonus());
        result.setStatus(data.getStatus());
        result.setDescription(data.getDescription());

        return result;

    }

    @Override
    public ResultInfoFRBonus infoFRB(long brandId, long playerId) throws CommonServiceException {

        List<NameValuePair> map = new ArrayList<NameValuePair>();
        map.add(new NameValuePair(PostParams.memberID.name(), Long.toString(playerId)));

        ArrayResponse<InfoFRBonus> data = httpPost(getBonusServiceURL(BonusServiceMethods.getFreeRoundBonuses),
                map.toArray(new NameValuePair[map.size()]),
                new TypeToken<ArrayResponse<InfoFRBonus>>() {
                }.getType());

        ResultInfoFRBonus result = new ResultInfoFRBonus();
        result.setBonuses(data.getValue());
        result.setStatus(data.getStatus());
        result.setDescription(data.getDescription());

        return result;

    }

    private String getBonusServiceURL(BonusServiceMethods method) throws CommonServiceException {

        if (BONUS_SERVICE_URL == null || BONUS_SERVICE_URL.isEmpty()) {
            log.error("System property 'url.bonus.service' is not set");
            throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
        }
        if (!BONUS_SERVICE_URL.endsWith("/")) {
            BONUS_SERVICE_URL += "/";
        }

        return BONUS_SERVICE_URL + method;
    }

    private <T extends BaseResponse> T httpPost(String url, NameValuePair[] fields, Type type) throws CommonServiceException {

        if (log.isDebugEnabled()) {
            log.debug("Connect to external service: " + url);
        }

        PostMethod httpMethod = null;
        try {
            httpMethod = new PostMethod(url);
            httpMethod.addRequestHeader("Cache-Control", "no-cache");
            httpMethod.setRequestBody(fields);
            int httpStatus = httpClient.executeMethod(httpMethod);
            if(log.isDebugEnabled()) {
                log.debug("Http response: httpStatus = " + httpStatus + ";  ResponseBody: " + httpMethod.getResponseBodyAsString());
            }
            if (httpStatus != HttpStatus.SC_OK) {
                log.error("Http error: httpStatus = " + httpStatus + ";  ResponseBody: " + httpMethod.getResponseBodyAsString());
                throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
            }
            String response = httpMethod.getResponseBodyAsString();
            return gson.fromJson(response, type);
        } catch (CommonServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
        } finally {
            if (httpMethod != null) {
                httpMethod.releaseConnection();
            }
        }
    }

}
