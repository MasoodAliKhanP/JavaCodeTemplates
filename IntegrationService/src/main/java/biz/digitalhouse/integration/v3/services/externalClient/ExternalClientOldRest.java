package biz.digitalhouse.integration.v3.services.externalClient;

import biz.digitalhouse.integration.v3.GeneralSettingKey;
import biz.digitalhouse.integration.v3.InternalServiceResponseStatus;
import biz.digitalhouse.integration.v3.Vendors;
import biz.digitalhouse.integration.v3.dao.GeneralSettingDAO;
import biz.digitalhouse.integration.v3.exceptions.ExternalServiceException;
import biz.digitalhouse.integration.v3.model.Player;
import biz.digitalhouse.integration.v3.model.ProcessBingoBucks;
import biz.digitalhouse.integration.v3.services.externalClient.dto.*;
import biz.digitalhouse.integration.v3.services.externalClient.oldHttpResponse.*;
import biz.digitalhouse.integration.v3.services.players.PlayerManager;
import biz.digitalhouse.integration.v3.utils.ConfUtils;
import biz.digitalhouse.integration.v3.utils.EncodeUtil;
import biz.digitalhouse.integration.v3.utils.StringUtils;
import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @author Vitalii Babenko
 *         on 30.06.2016.
 */
@Service("ExternalClientOldRest")
public class ExternalClientOldRest implements ExternalClient {

    private final Log log = LogFactory.getLog(getClass());
    private GeneralSettingDAO generalSettingDAO;
    private PlayerManager playerManager;
    private Gson gson = new Gson();

    @Autowired
    public ExternalClientOldRest(GeneralSettingDAO generalSettingDAO, PlayerManager playerManager) {
        this.generalSettingDAO = generalSettingDAO;
        this.playerManager = playerManager;
    }

    @Override
    public AuthenticateResult authenticate(long brandID, String token) {

        AttributeHolder requestArgs = createOrderedMapWithProviderIdIfNeeded(brandID);
        requestArgs.put("token", token);
        requestArgs.put("vendorId", Vendors.TOP_GAME);

        AuthorizeResponse res = send(brandID, ExternalServiceEndpoint.AUTHENTICATE, requestArgs, AuthorizeResponse.class);

        checkError(res);
        return new AuthenticateResult(res.getUserId(), res.getNickname(), res.getCurrency());
    }

    @Override
    public BalanceResult balance(long brandID, String vendorID, String extPlayerID) {

        AttributeHolder requestArgs = createOrderedMapWithProviderIdIfNeeded(brandID);
        requestArgs.put("userId", extPlayerID);
        requestArgs.put("vendorId", vendorID);

        BalanceResponse response = send(brandID, ExternalServiceEndpoint.BALANCE, requestArgs, BalanceResponse.class);
        checkError(response);

        return new BalanceResult(vendorID, response.getCash(), response.getBonus());
    }

    @Override
    public TransactionResult bet(long brandID, String vendorID, String extPlayerID, String gameID, long roundID, Long originalRoundID, double amount, String transactionID, String roundDetails, String bonusCode) {

        AttributeHolder requestArgs = createOrderedMapWithProviderIdIfNeeded(brandID);
        requestArgs.put("userId", extPlayerID);
        requestArgs.put("vendorId", vendorID);
        requestArgs.put("gameId", gameID);
        requestArgs.put("roundId", roundID);
        requestArgs.put("amount", Double.toString(amount));
        requestArgs.put("reference", transactionID);
        requestArgs.put("timestamp", Calendar.getInstance().getTimeInMillis());
        requestArgs.put("roundDetails", roundDetails);
        requestArgs.put("originalRoundId", originalRoundID);
        requestArgs.put("bonusCode", bonusCode);

        BetResponse resp = send(brandID, ExternalServiceEndpoint.BET, requestArgs, BetResponse.class);
        checkError(resp);

        if (resp.getError() == null || resp.getError().equals(ExternalServiceCode.SUCCESS)) {

            if (StringUtils.isBlank(resp.getTransactionId())) {
                if (log.isWarnEnabled()) {
                    log.warn("External service returned an empty transactionID with success status code! roundId[" + roundID
                            + "], brandID[" + brandID + "], userId[" + extPlayerID + "], gameId[" + gameID + "]");
                }
                return new TransactionResult(InternalServiceResponseStatus.ERROR);
            } else {

                return new TransactionResult(vendorID, resp.getCash(), resp.getBonus(), resp.getUsedBonus(), resp.getTransactionId());
            }
        } else if (resp.getError().equals(ExternalServiceCode.INSUFFICIENT_BALANCE)) {
            if (log.isDebugEnabled()) {
                log.debug("External userId(" + extPlayerID + ") balance (cash: " + resp.getCash() + ", bonus: " + resp.getBonus() + ") is not enough to make a bet " + amount);
            }
            //not enough balance
            return new TransactionResult(vendorID, resp.getCash(), resp.getBonus(), InternalServiceResponseStatus.INSUFFICIENT_FUNDS_ERROR);

        } else if (resp.getError().equals(ExternalServiceCode.BET_NOT_ALLOWED)) {
            if (log.isDebugEnabled()) {
                log.debug("External userId[" + extPlayerID + "], balance {cash[" + resp.getCash() + "], bonus[" + resp.getBonus() + "]} is not allowed to make a bet " + amount
                        + " in game " + gameID);
            }
            //not allowed to play this game with user balance (maybe because of bonus)
            return new TransactionResult(vendorID, resp.getCash(), resp.getBonus(), InternalServiceResponseStatus.GAME_DISABLED_FOR_BONUS);

        } else {
            //Some other problems
            if (log.isWarnEnabled()) {
                log.warn("BetResponse has an error. " + resp + ". BrandId[" + brandID + "], memberID[" + extPlayerID + "], " +
                        "gameID[" + gameID + "], roundId[" + roundID + "]");
            }
            return new TransactionResult(InternalServiceResponseStatus.ERROR);
        }
    }

    @Override
    public String refund(long brandID, String vendorID, String extPlayerID, String transactionID) {

        AttributeHolder requestArgs = createOrderedMapWithProviderIdIfNeeded(brandID);
        requestArgs.put("userId", extPlayerID);
        requestArgs.put("reference", transactionID);

        RefundResponse resp = send(brandID, ExternalServiceEndpoint.REFUND, requestArgs, RefundResponse.class);

        checkError(resp);

        return resp.getTransactionId();
    }

    @Override
    public TransactionResult win(long brandID, String vendorID, String extPlayerID, String gameID, long roundID, Long originalRoundID, double amount, String transactionID, String roundDetails, String bonusCode, Boolean endRound, Boolean isJob) {

        AttributeHolder requestArgs = createOrderedMapWithProviderIdIfNeeded(brandID);
        requestArgs.put("userId", extPlayerID);
        requestArgs.put("vendorId", vendorID);
        requestArgs.put("gameId", gameID);
        requestArgs.put("roundId", roundID);
        requestArgs.put("amount", amount);
        requestArgs.put("reference", transactionID);
        requestArgs.put("timestamp", Calendar.getInstance().getTimeInMillis());
        requestArgs.put("roundDetails", roundDetails);
        requestArgs.put("originalRoundId", originalRoundID);
        requestArgs.put("bonusCode", bonusCode);

        BetResultResponse resp = send(brandID, ExternalServiceEndpoint.BET_RESULT, requestArgs, BetResultResponse.class);

        checkError(resp);

        if (StringUtils.isBlank(resp.getTransactionId())) {
            if (log.isWarnEnabled()) {
                log.warn("External service returned an empty transactionID with success status code! roundId[" + roundID
                        + "], brandID[" + brandID + "], userId[" + extPlayerID + "], gameId[" + gameID + "]");
            }

            return new TransactionResult(InternalServiceResponseStatus.ERROR);
        } else {

            return new TransactionResult(vendorID, resp.getCash(), resp.getBonus(), null, resp.getTransactionId());
        }
    }

    @Override
    public TransactionResult jackpotWin(long brandID, String vendorID, String extPlayerID, String gameID, long roundID,Long originalRoundID, 
    		double amount, Long jackpotID, String transactionID, String roundDetails, Boolean endRound) {

        AttributeHolder requestArgs = createOrderedMapWithProviderIdIfNeeded(brandID)
                .put("userId", extPlayerID)
                .put("vendorId", vendorID)
                .put("gameId", gameID)
                .put("roundId", roundID)
                .put("amount", amount)
                .put("reference", transactionID)
                .put("timestamp", Calendar.getInstance().getTimeInMillis())
                .put("jackpotId", jackpotID)
                .put("originalRoundId", originalRoundID);

        BetResultResponse resp = send(brandID, ExternalServiceEndpoint.JACKPOT_WIN, requestArgs, BetResultResponse.class);

        checkError(resp);

        if (StringUtils.isBlank(resp.getTransactionId())) {
            if (log.isWarnEnabled()) {
                log.warn("External service returned an empty transactionID with success status code! roundId[" + roundID
                        + "], brandID[" + brandID + "], userId[" + extPlayerID + "], gameId[" + gameID + "]");
            }

            return new TransactionResult(InternalServiceResponseStatus.ERROR);
        } else {

            return new TransactionResult(vendorID, resp.getCash(), resp.getBonus(), null, resp.getTransactionId());
        }
    }

    @Override
    public void endRound(long brandID, String vendorID, String extPlayerID, String gameID, long roundID) {

        AttributeHolder requestArgs = createOrderedMapWithProviderIdIfNeeded(brandID);

        requestArgs.put("userId", extPlayerID);
        requestArgs.put("gameId", gameID);
        requestArgs.put("roundId", roundID);

        EndRoundResponse response = send(brandID, ExternalServiceEndpoint.END_ROUND, requestArgs, EndRoundResponse.class);
        checkError(response);
    }

    @Override
    public TransactionResult bonusWin(long brandID, String vendorID, String extPlayerID, double amount, String bonusCode) {

        if(log.isWarnEnabled()) {
            log.warn("Please! Not use this method! Attribute reference (transactionID) not by this method created!");
        }

        AttributeHolder requestArgs = createOrderedMapWithProviderIdIfNeeded(brandID);
        requestArgs.put("userId", extPlayerID);
        requestArgs.put("vendorId", vendorID);
        requestArgs.put("amount", amount);
        requestArgs.put("reference", null);
        requestArgs.put("timestamp", Calendar.getInstance().getTimeInMillis());
        requestArgs.put("bonusCode", bonusCode);


        BonusWinResponse res = send(brandID, ExternalServiceEndpoint.BONUS_WIN, requestArgs, BonusWinResponse.class);

        checkError(res);

        if (StringUtils.isBlank(res.getTransactionId())) {
            if (log.isWarnEnabled()) {
                log.warn("External service returned an empty transactionID with success status code! brandID[" + brandID + "], userId[" + extPlayerID + "]");
            }
            return new TransactionResult(InternalServiceResponseStatus.ERROR);
        } else {
            return new TransactionResult(vendorID, res.getCash(), res.getBonus(), null, res.getTransactionId());
        }
    }

    @Override
    public BuyBingoCardsResult buyBingoCards(long brandID, String vendorID, String extPlayerID, double amount, double cardCost, double cardCostUSD, long roomID, long roundID, int cardsNumber, int packSize, String transactionID) {

        AttributeHolder requestArgs = createOrderedMapWithProviderIdIfNeeded(brandID);
        requestArgs.put("userId", extPlayerID);
        requestArgs.put("roomId", roomID);
        requestArgs.put("roundId", roundID);
        requestArgs.put("amount", amount);
        requestArgs.put("cardsNumber", cardsNumber);
        requestArgs.put("packSize", packSize);
        requestArgs.put("reference", transactionID);
        requestArgs.put("vendorId", vendorID);

        BuyBingoCardsResponse res = send(brandID, ExternalServiceEndpoint.BUY_BINGO_CARDS, requestArgs, BuyBingoCardsResponse.class);

        if (res.getError() == null || res.getError().equals(ExternalServiceCode.SUCCESS)) {
            if (StringUtils.isBlank(res.getTransactionId())) {
                if (log.isWarnEnabled()) {
                    log.warn("External service returned an empty transactionID with success status code!");
                }
                return new BuyBingoCardsResult(InternalServiceResponseStatus.ERROR);
            }
            return new BuyBingoCardsResult(vendorID, res.getCash(), res.getBonus(), res.getTransactionId(), 0);
        } else if (res.getError().equals(ExternalServiceCode.INSUFFICIENT_BALANCE)) {
            if (log.isDebugEnabled()) {
                log.debug("BuyBingoCards receive an error code " + res.getError() + " with description '" + res.getDescription() + "'"
                        + ". brandID[" + brandID + "], extPlayerID[" + extPlayerID + "], roomID[" + roomID + "], roundID["
                        + roundID + "], amount[" + amount + "], cardsNumber[" + cardsNumber + "]");
            }
            return new BuyBingoCardsResult(vendorID, res.getCash(), res.getBonus(), res.getProposedCardsNumber(), InternalServiceResponseStatus.INSUFFICIENT_FUNDS_ERROR);
        } else {
            //Some other problems
            if (log.isWarnEnabled()) {
                log.warn("BuyBingoCards receive an error code " + res.getError() + " with description '" + res.getDescription() + "'"
                        + ". brandID[" + brandID + "], extPlayerID[" + extPlayerID + "], roomID[" + roomID + "], roundID["
                        + roundID + "], amount[" + amount + "], cardsNumber[" + cardsNumber + "]");
            }
            return new BuyBingoCardsResult(InternalServiceResponseStatus.ERROR);
        }
    }

    @Override
    public String bingoRefund(long brandID, String vendorID, String extPlayerID, String transactionID) {

        AttributeHolder requestArgs = createOrderedMapWithProviderIdIfNeeded(brandID);
        requestArgs.put("userId", extPlayerID);
        requestArgs.put("reference", transactionID);

        RefundResponse resp = send(brandID, ExternalServiceEndpoint.BINGO_REFUND, requestArgs, RefundResponse.class);

        checkError(resp);

        return resp.getTransactionId();
    }

    @Override
    public void bingoEndRound(long brandID, String externalPlayerID, long bingoRoomID, long bingoRoundID) {
//        log.debug("bingoEndRound is not realized!");
    }

    @Override
    public BingoWinResult bingoWin(Long brandID, String playerID, String transctionID, Double amount, Long roundID, Long roomID) {

        if (log.isDebugEnabled()) {
            log.debug("Start query BingoResult by transctionID:" + transctionID);
        }

        AttributeHolder requestArgs = createOrderedMapWithProviderIdIfNeeded(brandID);

        requestArgs.put("userId", playerID);
        requestArgs.put("reference", transctionID);
        requestArgs.put("amount", Double.toString(amount));
        requestArgs.put("resultType", "complete");
        requestArgs.put("roundId", roundID.toString());
        requestArgs.put("roomId", roomID.toString());

        BingoResultResponse resp = send(brandID, ExternalServiceEndpoint.BINGO_RESULT, requestArgs, BingoResultResponse.class);
        checkError(resp);

        if (log.isDebugEnabled()) {
            log.debug("External member (" + playerID + ") after process bingo results is " + resp);
        }
        return new BingoWinResult(resp.getCash(), resp.getBonus(), resp.getTransactionId(), 0);
    }


    @Override
    public BuyReservedCardsResult buyReservedCards(long brandID, String extPlayerID, double amount, long roomID, long roundID, double cardCost,double cardCostUSD, int cardsNumber, int packSize, String transactionID) {
        AttributeHolder requestArgs = createOrderedMapWithProviderIdIfNeeded(brandID);
        requestArgs.put("userId", extPlayerID);
        requestArgs.put("reference", transactionID);
        requestArgs.put("amount", amount);
        requestArgs.put("roundId", roundID);
        requestArgs.put("roomId", roomID);
        requestArgs.put("cardCost", cardCost);
        requestArgs.put("cardCostUSD",cardCostUSD);
        requestArgs.put("cardsNumber", cardsNumber);
        requestArgs.put("packSize", packSize);

        BuyPreorderedCardsResponse res = send(brandID, ExternalServiceEndpoint.BUY_PREORDERED_CARDS, requestArgs, BuyPreorderedCardsResponse.class);

        if (res.getError() == null || res.getError().equals(ExternalServiceCode.SUCCESS)) {
            if (StringUtils.isBlank(res.getTransactionId())) {
                if (log.isWarnEnabled()) {
                    log.warn("External service returned an empty transactionID with success status code!");
                }
                return new BuyReservedCardsResult(InternalServiceResponseStatus.ERROR);
            }
            return new BuyReservedCardsResult(res.getCash(), res.getBonus(), 0, res.getTransactionId(), res.getBoughtCardsNumber());
        } else if (res.getError().equals(ExternalServiceCode.INSUFFICIENT_BALANCE)) {
            if(log.isDebugEnabled()) {
                log.debug("BuyBingoCards receive an error code " + res.getError() + " with description '" + res.getDescription() + "'"
                        + ". brandID[" + brandID + "], extPlayerID[" + extPlayerID + "], roomID[" + roomID + "], roundID["
                        + roundID + "], amount[" + amount + "], cardsNumber[" + cardsNumber + "]");
            }
            return new BuyReservedCardsResult(res.getCash(), res.getBonus(), InternalServiceResponseStatus.INSUFFICIENT_FUNDS_ERROR);
        } else {
            //Some other problems
            if (log.isWarnEnabled()) {
                log.warn("BuyBingoCards receive an error code " + res.getError() + " with description '" + res.getDescription() + "'"
                        + ". brandID[" + brandID + "], extPlayerID[" + extPlayerID + "], roomID[" + roomID + "], roundID["
                        + roundID + "], amount[" + amount + "], cardsNumber[" + cardsNumber + "]");
            }
            return new BuyReservedCardsResult(InternalServiceResponseStatus.ERROR);
        }
    }

    @Override
    public ProcessBingoBucks processBingoBucks(long brandID, String extPlayerID, double amount, long roomID, long chatRoundID, int isUnlimited) {
        throw new RuntimeException("Method 'processBingoBucks' is not supported.");
    }

    private <T> T send(long brandID, ExternalServiceEndpoint endpoint, final AttributeHolder requestArgs, Class<T> resultClass) {

        if (requestArgs == null || requestArgs.isEmpty()) {
            throw new IllegalArgumentException("Request arguments is null or empty!");
        }

        String url = generateUrl(brandID, endpoint.getEndpoint());

        String secretWord = getProviderPassword(brandID);
        if (StringUtils.isNotEmpty(secretWord)) {
            //Does not calculate hash and add it to request if secret word is blank
            String sourceHash = requestArgs.getString() + secretWord;

            if (log.isDebugEnabled()) {
                log.debug("Hash source: " + sourceHash);
            }

            requestArgs.put("hash", EncodeUtil.md5(sourceHash));
        }

        List<NameValuePair> nvpList = new ArrayList<NameValuePair>(requestArgs.size());
        for (Map.Entry<String, Object> entry : requestArgs.getEntryList()) {
            nvpList.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
        }

        String responseBody = null;
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(1000 * ConfUtils.getIntValue(brandID, 1, GeneralSettingKey.SERVICE_SOCKET_TIMEOUT_SECONDS, generalSettingDAO))
                .setConnectionRequestTimeout(1000 * ConfUtils.getIntValue(brandID, 1, GeneralSettingKey.SERVICE_SOCKET_TIMEOUT_SECONDS, generalSettingDAO))
                .setSocketTimeout(1000 * ConfUtils.getIntValue(brandID, 1, GeneralSettingKey.SERVICE_SOCKET_TIMEOUT_SECONDS, generalSettingDAO))
                .build();

        CloseableHttpClient httpClient = HttpClientBuilder
                .create()
                .setDefaultRequestConfig(config)
                .build();
        try {
            final HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(nvpList));

            final ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                public String handleResponse(final HttpResponse response) throws IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        if (status!= 200){
                            log.info("Handled responce status not equals 200. Status received: " + Integer.toString(status) + "; Body is: " + entity);
                        }
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }
            };

            responseBody = httpClient.execute(httpPost, responseHandler);
        } catch (Exception e) {
            log.error("Couldn't connect to " + url + ". Error: " + e.getMessage(), e);
            throw new ExternalServiceException(e.getMessage());
        } finally {
            try {
                httpClient.close();
            } catch (Exception e) {
                log.error("Could not close http client", e);
            }
            if (log.isDebugEnabled()) {

                StringBuilder sb = new StringBuilder(String.format("Result of call external service. Url: %s, Secret: '%s'", url, secretWord));
                sb.append("\nArguments: ");
                for (Map.Entry entry : requestArgs.getEntryList()) {
                    sb.append("\n\t").append(entry.getKey()).append("=").append(entry.getValue());
                }
                sb.append("\nExternal service response: ").append(responseBody);
                log.debug(sb.toString());
            }
        }

        try {
            return gson.fromJson(responseBody, resultClass);
        } catch (Exception e) {
            log.error("Couldn't parse json response. Response: " + responseBody, e);
            throw new ExternalServiceException();
        }
    }

    private String getUrl(final long brandID) {
        String url;
        if ((url = generalSettingDAO.getValue(GeneralSettingKey.OPERATOR_URL.key() + ".WWG", brandID)) == null){
            url = generalSettingDAO.getValue(GeneralSettingKey.OPERATOR_URL.key(), brandID);
        }
        return url;
    }

    private String getLogin(final long brandID) {
        String url;
        if ((url = generalSettingDAO.getValue(GeneralSettingKey.PROVIDER_LOGIN.key() + ".WWG", brandID)) == null){
            url = generalSettingDAO.getValue(GeneralSettingKey.PROVIDER_LOGIN.key(), brandID);
        }
        return url;
    }

    private String generateUrl(final long brandID, final String urlEndPart) {
        String url = getUrl(brandID);
        return url.endsWith("/") ? url + urlEndPart : url + "/" + urlEndPart;
    }

    private AttributeHolder createOrderedMapWithProviderIdIfNeeded(long brandID) {
        AttributeHolder attributeHolder = new AttributeHolder();
        String providerID = getLogin(brandID);
        if (!StringUtils.isBlank(providerID)) {
            attributeHolder.put("providerId", providerID);
        }
        return attributeHolder;
    }

    private String getProviderPassword(long brandID) {
        String url;
        if ((url = generalSettingDAO.getValue(GeneralSettingKey.PROVIDER_PASSWORD.key() + ".WWG", brandID)) == null){
            url = generalSettingDAO.getValue(GeneralSettingKey.PROVIDER_PASSWORD.key(), brandID);
        }
        return url;
    }

    private enum ExternalServiceEndpoint {
        BET("bet.html"),
        BET_RESULT("result.html"),
        JACKPOT_WIN("jackpotWin.html"),
        REFUND("refund.html"),
        BALANCE("balance.html"),
        AUTHENTICATE("authenticate.html"),
        VERIFICATION("verification.html"),
        BONUS_WIN("bonusWin.html"),
        END_ROUND("endRound.html"),
        BUY_BINGO_CARDS("buyBingoCards.html"),
        BINGO_RESULT("processBingoResults.html"),
        BUY_PREORDERED_CARDS("buyPreorderedCards.html"),
        BINGO_REFUND("bingoRefund.html");

        String endpoint;

        ExternalServiceEndpoint(java.lang.String endpoint) {
            this.endpoint = endpoint;
        }

        public String getEndpoint() {
            return endpoint;
        }
    }

    private class AttributeHolder {

        List<Map.Entry<String, Object>> entryList = new ArrayList<Map.Entry<String, Object>>();

        AttributeHolder put(String key, Object val) {
            if (val != null)
                entryList.add(new AbstractMap.SimpleEntry<>(key, val));
            return this;
        }

        public String getString() {

            List<String> sortList = new ArrayList<String>();

            for (Map.Entry entry : entryList) {
                sortList.add(entry.toString());
            }

            Collections.sort(sortList);

            StringBuilder builder = new StringBuilder();
            for (String data : sortList) {
                builder.append(data).append("&");
            }

            return builder.substring(0, builder.length() - 1);
        }

        List<Map.Entry<String, Object>> getEntryList() {
            return entryList;
        }

        public boolean isEmpty() {
            return entryList.isEmpty();
        }

        public int size() {
            return entryList.size();
        }
    }

    private <T extends BaseResponse> void checkError(T res) {
        if (res == null) {
            if (log.isErrorEnabled()) {
                log.error("External Service returned null.");
            }
            throw new ExternalServiceException("External Service returned null.");
        }
        if (!"0".equals(res.getError())) {
            if (log.isWarnEnabled()) {
                log.warn("External Service returned error. " + res);
            }
            throw new ExternalServiceException("External Service returned error. " + res);
        }
    }

	@Override
	public ScratchCardWinResult scratchCardWin(long brandID, String vendorID, Player player, long promotionId, String prizeTypeId,
			double offerValue, String currency) {
		// TODO Auto-generated method stub
		return null;
	}

}
