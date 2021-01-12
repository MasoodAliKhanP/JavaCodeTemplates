package biz.digitalhouse.integration.v3.services.externalClient;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.google.gson.Gson;

import biz.digitalhouse.integration.v3.GeneralSettingKey;
import biz.digitalhouse.integration.v3.InternalServiceResponseStatus;
import biz.digitalhouse.integration.v3.Vendors;
import biz.digitalhouse.integration.v3.dao.GeneralSettingDAO;
import biz.digitalhouse.integration.v3.dao.jdbc.ScratchCardDaoJdbc;
import biz.digitalhouse.integration.v3.exceptions.ExternalServiceException;
import biz.digitalhouse.integration.v3.exceptions.InvalidTokenException;
import biz.digitalhouse.integration.v3.managers.AuthTokenManager;
import biz.digitalhouse.integration.v3.managers.ClientManager;
import biz.digitalhouse.integration.v3.model.BonusDetails;
import biz.digitalhouse.integration.v3.model.Player;
import biz.digitalhouse.integration.v3.model.ProcessBingoBucks;
import biz.digitalhouse.integration.v3.services.externalClient.dto.AuthenticateResult;
import biz.digitalhouse.integration.v3.services.externalClient.dto.BalanceResult;
import biz.digitalhouse.integration.v3.services.externalClient.dto.BingoWinResult;
import biz.digitalhouse.integration.v3.services.externalClient.dto.BuyBingoCardsResult;
import biz.digitalhouse.integration.v3.services.externalClient.dto.BuyReservedCardsResult;
import biz.digitalhouse.integration.v3.services.externalClient.dto.RetryInfo;
import biz.digitalhouse.integration.v3.services.externalClient.dto.ScratchCardWinResult;
import biz.digitalhouse.integration.v3.services.externalClient.dto.TransactionResult;
import biz.digitalhouse.integration.v3.services.externalClient.httpRequest.RealCashRequest;
import biz.digitalhouse.integration.v3.services.externalClient.httpRequest.SpecialBonusRequest;
import biz.digitalhouse.integration.v3.services.externalClient.httpResponse.AuthenticateResponse;
import biz.digitalhouse.integration.v3.services.externalClient.httpResponse.BalanceResponse;
import biz.digitalhouse.integration.v3.services.externalClient.httpResponse.BaseResponse;
import biz.digitalhouse.integration.v3.services.externalClient.httpResponse.BetResponse;
import biz.digitalhouse.integration.v3.services.externalClient.httpResponse.BingoEndRoundResponce;
import biz.digitalhouse.integration.v3.services.externalClient.httpResponse.BingoResultResponse;
import biz.digitalhouse.integration.v3.services.externalClient.httpResponse.BonusWinResponse;
import biz.digitalhouse.integration.v3.services.externalClient.httpResponse.BuyBingoCardsResponse;
import biz.digitalhouse.integration.v3.services.externalClient.httpResponse.BuyPreorderedCardsResponse;
import biz.digitalhouse.integration.v3.services.externalClient.httpResponse.JackpotWinResponse;
import biz.digitalhouse.integration.v3.services.externalClient.httpResponse.ProcessBingoBucksResponse;
import biz.digitalhouse.integration.v3.services.externalClient.httpResponse.RealCashResponse;
import biz.digitalhouse.integration.v3.services.externalClient.httpResponse.RefundResponse;
import biz.digitalhouse.integration.v3.services.externalClient.httpResponse.SpecialBonusResponse;
import biz.digitalhouse.integration.v3.services.externalClient.httpResponse.WinResponse;
import biz.digitalhouse.integration.v3.utils.ConfUtils;
import biz.digitalhouse.integration.v3.utils.EncodeUtil;
import biz.digitalhouse.integration.v3.utils.StringUtils;
import retrofit2.Response;

/**
 * @author Vitalii Babenko on 05.03.2016.
 */
@Service("ExternalClientRest")
public class ExternalClientRest implements ExternalClient {

	private final Log log = LogFactory.getLog(getClass());
	private GeneralSettingDAO generalSettingDAO;
	private ScratchCardDaoJdbc scratchCardDaoJdbc;
	private AuthTokenManager authTokenManager;
	private ClientManager clientManager;
	private Gson gson = new Gson();

	@Autowired
	public ExternalClientRest(GeneralSettingDAO generalSettingDAO, AuthTokenManager authTokenManager,
			ClientManager clientManager, ScratchCardDaoJdbc scratchCardDaoJdbc) {
		this.generalSettingDAO = generalSettingDAO;
		this.authTokenManager = authTokenManager;
		this.clientManager = clientManager;
		this.scratchCardDaoJdbc = scratchCardDaoJdbc;
	}

	@Override
	public AuthenticateResult authenticate(long brandID, String token) {

		AttributeHolder requestArgs = createOrderedMapWithProviderIdIfNeeded(brandID);

		requestArgs.put(Parameter.TOKEN, token);

		AuthenticateResponse res = send(brandID, ExternalServiceEndpoint.AUTHENTICATE, requestArgs,
				AuthenticateResponse.class);

		checkError(res);

		if (res.getUserID() == null) {
			if (log.isErrorEnabled()) {
				log.error("External Service return userID is null.");
			}
			throw new ExternalServiceException();
		}

		return new AuthenticateResult(res.getUserID(), res.getNickname(), res.getCurrency());
	}

	@Override
	public BalanceResult balance(long brandID, String vendorId, String extPlayerId) {

		AttributeHolder requestArgs = createOrderedMapWithProviderIdIfNeeded(brandID);

		requestArgs.put(Parameter.PLAYER_ID, extPlayerId);
		requestArgs.put(Parameter.VENDOR_ID, vendorId);

		BalanceResponse res = send(brandID, ExternalServiceEndpoint.BALANCE, requestArgs, BalanceResponse.class);

		checkError(res);
		return new BalanceResult(vendorId, res.getCash(), res.getBonus());
	}

	@Override
	public ScratchCardWinResult scratchCardWin(long brandID, String vendorID, Player player, long promotionId, String prizeTypeId,
			double offerValue, String currency) {
		
		try {
			String token = authTokenManager.getToken(brandID);
            String partnerId = generalSettingDAO.getGeneralSettings(GeneralSettingDAO.KEY_AUTH_PARTNER_ID, brandID);

			if (prizeTypeId.equals(FREE_SPIN_PRIZEID) || prizeTypeId.equals(BONUS_PRIZEID)) {
				return processSpecialBonus(brandID, partnerId, player, promotionId,prizeTypeId, offerValue, token);
			} else if (prizeTypeId.equals(CASHBACK_PRIZEID)) {
				return processRealCashBack(brandID, partnerId, player, promotionId,prizeTypeId, offerValue, currency, token);
			}
		} catch (InvalidTokenException e) {
			if (log.isInfoEnabled()) {
				log.info("Remove invalid token by brand [" + brandID + "];");
			}
			authTokenManager.removeToken(brandID);
		}
		return new ScratchCardWinResult();
	}

	private ScratchCardWinResult processSpecialBonus(long brandID, String partnerId, Player player, long promotionId, String prizeTypeId,
			double offerValue, String token) {
		DataClient dataClient = clientManager.getDataClient(brandID);
		try {
			Response<SpecialBonusResponse> bonusResp = null;
			
			if (prizeTypeId.equals(FREE_SPIN_PRIZEID)) {
				BonusDetails bd = scratchCardDaoJdbc.getBonusDetails(BonusDetails.FREE_SPIN);
				String bonusId = bd.getBonusId(); 
				int issueMultiplier = bd.getIssueMultiplier();
				
				SpecialBonusRequest request = new SpecialBonusRequest(partnerId, partnerId,
						player.getExternalPlayerID(), bonusId, new Date().getTime(), (double) 0, issueMultiplier, 0, "SYSTEM", "",
						(int) offerValue);
				bonusResp = dataClient.offerSpecialBonus(token, request).execute();
			} else if (prizeTypeId.equals(BONUS_PRIZEID)) {
				BonusDetails bd = scratchCardDaoJdbc.getBonusDetails(BonusDetails.BONUS);
				String bonusId = bd.getBonusId(); 
				int issueMultiplier = bd.getIssueMultiplier();
				double offerValueinCents = offerValue * 100.0;
				SpecialBonusRequest request = new SpecialBonusRequest(partnerId, partnerId,
						player.getExternalPlayerID(), bonusId, new Date().getTime(), offerValueinCents, issueMultiplier, 0,
						"SYSTEM", "", 0);
				bonusResp = dataClient.offerSpecialBonus(token, request).execute();
			}
			if (!bonusResp.isSuccessful()) {
				log.warn(bonusResp.errorBody().string());
				return new ScratchCardWinResult();
			}

			SpecialBonusResponse result = bonusResp.body();
			if (log.isDebugEnabled()) {
				log.debug(result);
			}
			if (!result.checkStatus()) {
				return new ScratchCardWinResult();
			}

			scratchCardDaoJdbc.updateScratchCardWinClaim(result.getBonusId(), player.getPlayerID(), promotionId, prizeTypeId, offerValue);
			
			ScratchCardWinResult res = new ScratchCardWinResult();
			res.setErrorCode(result.getErrorCode());
			res.setErrorDescription(result.getErrorDescription());
			res.setErrorId(result.getErrorId());
			res.setStatus(res.getStatus());
			return res;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	private ScratchCardWinResult processRealCashBack(long brandID, String partnerId, Player player, long promotionId, String prizeTypeId,
			double offerValue, String currency, String token) {
		DataClient dataClient = clientManager.getDataClient(brandID);
		log.debug("Data Client: " + dataClient);
		try {
			double offerValueinCents = offerValue * 100.0;
 			RealCashRequest request = new RealCashRequest(partnerId, partnerId,
					player.getExternalPlayerID(), offerValueinCents, currency);
 			log.debug("Real Cash reqeust: " + request);
			Response<RealCashResponse> resp = dataClient.realCash(token, request).execute();

			if (!resp.isSuccessful()) {
				log.warn(resp.errorBody().string());
				return new ScratchCardWinResult();
			}

			RealCashResponse result = resp.body();
			if (log.isDebugEnabled()) {
				log.debug(result);
			}
			if (!result.checkStatus()) {
				return new ScratchCardWinResult();
			}

			scratchCardDaoJdbc.updateScratchCardWinClaim(String.valueOf(result.getFundTransactionID()), player.getPlayerID(), promotionId, prizeTypeId, offerValue);

			ScratchCardWinResult res = new ScratchCardWinResult();
			res.setErrorCode(result.getErrorCode());
			res.setErrorDescription(result.getErrorDescription());
			res.setErrorId(result.getErrorId());
			res.setStatus(res.getStatus());
			return res;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	public TransactionResult bet(long brandID, String vendorId, String extPlayerId, String gameId, long roundId,
			Long originalRoundId, double amount, String transactionId, String roundDetails, String bonusCode) {

		AttributeHolder requestArgs = createOrderedMapWithProviderIdIfNeeded(brandID);

		requestArgs.put(Parameter.PLAYER_ID, extPlayerId);
		requestArgs.put(Parameter.VENDOR_ID, vendorId);

		requestArgs.put(Parameter.GAME_ID, gameId);
		requestArgs.put(Parameter.ROUND_ID, roundId);
		requestArgs.put(Parameter.ORIGINAL_ROUND_ID, originalRoundId);
		requestArgs.put(Parameter.AMOUNT, amount);
		requestArgs.put(Parameter.REFERENCE, transactionId);
		requestArgs.put(Parameter.ROUND_DETAILS, roundDetails);
		requestArgs.put(Parameter.BONUS_CODE, bonusCode);
		BetResponse res;

		if (Vendors.TOP_GAME.equals(vendorId) || Vendors.TOP_GAME_BINGO.equals(vendorId)) {
			res = sendWithRetry(brandID, ExternalServiceEndpoint.BET, requestArgs, BetResponse.class);
		} else {
			res = send(brandID, ExternalServiceEndpoint.BET, requestArgs, BetResponse.class);
		}

		if (res == null) {
			log.error("Response is null!");
			throw new ExternalServiceException();
		}

		if (res.getError() == null || res.getError().equals(ExternalServiceCode.SUCCESS)) {

			if (StringUtils.isBlank(res.getTransactionID())) {
				if (log.isWarnEnabled()) {
					log.warn("External service returned an empty transactionID with success status code! roundId["
							+ roundId + "], brandID[" + brandID + "], userId[" + extPlayerId + "], gameId[" + gameId
							+ "]");
				}
				return new TransactionResult(InternalServiceResponseStatus.ERROR);
			} else {

				return new TransactionResult(vendorId, res.getCash(), res.getBonus(), res.getBonusPartBet(),
						res.getTransactionID());
			}
		} else if (res.getError().equals(ExternalServiceCode.INSUFFICIENT_BALANCE)) {
			if (log.isDebugEnabled()) {
				log.debug("External userId(" + extPlayerId + ") balance (cash: " + res.getCash() + ", bonus: "
						+ res.getBonus() + ") is not enough to make a bet " + amount);
			}
			// not enough balance
			return new TransactionResult(vendorId, res.getCash(), res.getBonus(),
					InternalServiceResponseStatus.INSUFFICIENT_FUNDS_ERROR);

		}
//        else if (res.getError().equals(ExternalServiceCode.BET_NOT_ALLOWED)) {
//            if (log.isDebugEnabled()) {
//                log.debug("External userId[" + extPlayerId + "], balance {cash[" + res.getCash() + "], bonus[" + res.getBonus() + "]} is not allowed to make a bet " + amount
//                        + " in game " + gameId);
//            }
//            //not allowed to play this game with user balance (maybe because of bonus)
//            return new TransactionResult(vendorId, res.getCash(), res.getBonus(), InternalServiceResponseStatus.GAME_DISABLED_FOR_BONUS);
//
//        }
		else {
			// Some other problems
			if (log.isWarnEnabled()) {
				log.warn("BetResponse has an error. " + res + ". BrandId[" + brandID + "], memberID[" + extPlayerId
						+ "], " + "gameID[" + gameId + "], roundId[" + roundId + "]");
			}
			return new TransactionResult(InternalServiceResponseStatus.ERROR);
		}
	}

	@Override
	public String refund(long brandID, String vendorId, String extPlayerId, String transactionId) {

		AttributeHolder requestArgs = createOrderedMapWithProviderIdIfNeeded(brandID);

		requestArgs.put(Parameter.PLAYER_ID, extPlayerId);
		requestArgs.put(Parameter.REFERENCE, transactionId);

		RefundResponse res = send(brandID, ExternalServiceEndpoint.REFUND, requestArgs, RefundResponse.class);

		checkError(res);

		return res.getTransactionID();
	}

	@Override
	public TransactionResult win(long brandID, String vendorId, String extPlayerId, String gameId, long roundId,
			Long originalRoundId, double amount, String transactionId, String roundDetails, String bonusCode,
			Boolean endRound, Boolean isJob) {

		AttributeHolder requestArgs = createOrderedMapWithProviderIdIfNeeded(brandID);

		requestArgs.put(Parameter.PLAYER_ID, extPlayerId);
		requestArgs.put(Parameter.VENDOR_ID, vendorId);

		requestArgs.put(Parameter.GAME_ID, gameId);
		requestArgs.put(Parameter.ROUND_ID, roundId);
		requestArgs.put(Parameter.ORIGINAL_ROUND_ID, originalRoundId);
		requestArgs.put(Parameter.AMOUNT, amount);
		requestArgs.put(Parameter.REFERENCE, transactionId);
		requestArgs.put(Parameter.ROUND_DETAILS, roundDetails);
		requestArgs.put(Parameter.BONUS_CODE, bonusCode);
		requestArgs.put(Parameter.END_ROUND, endRound != null && endRound);
		WinResponse res;

		if ((Vendors.TOP_GAME.equals(vendorId) || Vendors.TOP_GAME_BINGO.equals(vendorId)) && !isJob) {
			res = sendWithRetry(brandID, ExternalServiceEndpoint.BET_RESULT, requestArgs, WinResponse.class);
		} else {
			res = send(brandID, ExternalServiceEndpoint.BET_RESULT, requestArgs, WinResponse.class);
		}

		checkError(res);
		// Check result Transaction ID if not end round
		if (transactionId != null && StringUtils.isBlank(res.getTransactionID())) {
			if (log.isWarnEnabled()) {
				log.warn("External service returned an empty transactionID with success status code! roundId[" + roundId
						+ "], brandID[" + brandID + "], userId[" + extPlayerId + "], gameId[" + gameId + "]");
			}

			return new TransactionResult(InternalServiceResponseStatus.ERROR);
		} else {
			return new TransactionResult(vendorId, res.getCash(), res.getBonus(), res.getBonusPartWin(),
					res.getTransactionID());
		}
	}

	@Override
	public TransactionResult jackpotWin(long brandID, String vendorID, String extPlayerID, String gameID, long roundID,
			Long originalRoundID, double amount, Long jackpotID, String transactionID, String roundDetails,
			Boolean endRound) {

		AttributeHolder requestArgs = createOrderedMapWithProviderIdIfNeeded(brandID);

		requestArgs.put(Parameter.PLAYER_ID, extPlayerID);
		requestArgs.put(Parameter.VENDOR_ID, vendorID);

		requestArgs.put(Parameter.GAME_ID, gameID);
		requestArgs.put(Parameter.ROUND_ID, roundID);
		requestArgs.put(Parameter.ORIGINAL_ROUND_ID, originalRoundID);
		requestArgs.put(Parameter.AMOUNT, amount);
		requestArgs.put(Parameter.JACKPOT_ID, jackpotID);
		requestArgs.put(Parameter.REFERENCE, transactionID);
		requestArgs.put(Parameter.ROUND_DETAILS, roundDetails);
		requestArgs.put(Parameter.END_ROUND, endRound != null && endRound);
		JackpotWinResponse res;

//        if ((Vendors.TOP_GAME.equals(vendorID) || Vendors.TOP_GAME_BINGO.equals(vendorID)) && !isJob) {
//            res = sendWithRetry(brandID, ExternalServiceEndpoint.JACKPOT_WIN, requestArgs, WinResponse.class);
//        } else {
		res = send(brandID, ExternalServiceEndpoint.JACKPOT_WIN, requestArgs, JackpotWinResponse.class);
//        }

		checkError(res);
		// Check result Transaction ID if not end round
		if (transactionID != null && StringUtils.isBlank(res.getTransactionID())) {
			if (log.isWarnEnabled()) {
				log.warn("External service returned an empty transactionID with success status code! roundId[" + roundID
						+ "], brandID[" + brandID + "], userId[" + extPlayerID + "], gameId[" + gameID + "]");
			}

			return new TransactionResult(InternalServiceResponseStatus.ERROR);
		} else {
			return new TransactionResult(vendorID, res.getCash(), res.getBonus(), res.getBonusPartWin(),
					res.getTransactionID());
		}
	}

	@Override
	public void endRound(long brandID, String vendorId, String extPlayerId, String gameId, long roundId) {

		AttributeHolder requestArgs = createOrderedMapWithProviderIdIfNeeded(brandID);

		requestArgs.put(Parameter.PLAYER_ID, extPlayerId);
		requestArgs.put(Parameter.GAME_ID, gameId);
		requestArgs.put(Parameter.ROUND_ID, roundId);

		BaseResponse res = send(brandID, ExternalServiceEndpoint.END_ROUND, requestArgs, BaseResponse.class);
		checkError(res);
	}

	@Override
	public TransactionResult bonusWin(long brandID, String vendorId, String extPlayerId, double amount,
			String bonusCode) {

		AttributeHolder requestArgs = createOrderedMapWithProviderIdIfNeeded(brandID);

		requestArgs.put(Parameter.PLAYER_ID, extPlayerId);
		requestArgs.put(Parameter.VENDOR_ID, vendorId);

		requestArgs.put(Parameter.AMOUNT, amount);
		requestArgs.put(Parameter.BONUS_CODE, bonusCode);

		BonusWinResponse res = send(brandID, ExternalServiceEndpoint.BONUS_WIN, requestArgs, BonusWinResponse.class);

		checkError(res);

		if (StringUtils.isBlank(res.getTransactionID())) {
			if (log.isWarnEnabled()) {
				log.warn("External service returned an empty transactionID with success status code! brandID[" + brandID
						+ "], userId[" + extPlayerId + "]");
			}
			return new TransactionResult(InternalServiceResponseStatus.ERROR);
		} else {
			return new TransactionResult(vendorId, res.getCash(), res.getBonus(), null, res.getTransactionID());
		}
	}

	@Override
	public BuyBingoCardsResult buyBingoCards(long brandID, String vendorID, String extPlayerID, double amount,
			double cardCost, double cardCostUSD, long roomID, long roundID, int cardsNumber, int packSize,
			String transactionID) {

		AttributeHolder requestArgs = createOrderedMapWithProviderIdIfNeeded(brandID);

		requestArgs.put(Parameter.PLAYER_ID, extPlayerID);
		requestArgs.put(Parameter.VENDOR_ID, vendorID);
		requestArgs.put(Parameter.AMOUNT, amount);
		requestArgs.put(Parameter.ROOM_ID, roomID);
		requestArgs.put(Parameter.ROUND_ID, roundID);
		requestArgs.put(Parameter.CARDS_NUMBER, cardsNumber);
		requestArgs.put(Parameter.PACK_SIZE, packSize);
		requestArgs.put(Parameter.REFERENCE, transactionID);
		requestArgs.put(Parameter.CARD_COST, cardCost);
		requestArgs.put(Parameter.CARD_COST_USD, cardCostUSD);

		BuyBingoCardsResponse res = send(brandID, ExternalServiceEndpoint.BUY_BINGO_CARDS, requestArgs,
				BuyBingoCardsResponse.class);

		if (res.getError() == null || res.getError().equals(ExternalServiceCode.SUCCESS)) {
			if (StringUtils.isBlank(res.getTransactionID())) {
				if (log.isWarnEnabled()) {
					log.warn("External service returned an empty transactionID with success status code!");
				}
				return new BuyBingoCardsResult(InternalServiceResponseStatus.ERROR);
			}
			return new BuyBingoCardsResult(vendorID, res.getCash(), res.getBonus(), res.getTransactionID(),
					res.getBonusPartBet());
		} else if (res.getError().equals(ExternalServiceCode.INSUFFICIENT_BALANCE)) {
			if (log.isDebugEnabled()) {
				log.debug("BuyBingoCards receive an error code " + res.getError() + " with description '"
						+ res.getDescription() + "'" + ". brandID[" + brandID + "], extPlayerID[" + extPlayerID
						+ "], roomID[" + roomID + "], roundID[" + roundID + "], amount[" + amount + "], cardsNumber["
						+ cardsNumber + "]");
			}
			return new BuyBingoCardsResult(vendorID, res.getCash(), res.getBonus(), res.getProposedCardsNumber(),
					InternalServiceResponseStatus.INSUFFICIENT_FUNDS_ERROR);
		} else {
			// Some other problems
			if (log.isWarnEnabled()) {
				log.warn("BuyBingoCards receive an error code " + res.getError() + " with description '"
						+ res.getDescription() + "'" + ". brandID[" + brandID + "], extPlayerID[" + extPlayerID
						+ "], roomID[" + roomID + "], roundID[" + roundID + "], amount[" + amount + "], cardsNumber["
						+ cardsNumber + "]");
			}
			return new BuyBingoCardsResult(InternalServiceResponseStatus.ERROR);
		}
	}

	@Override
	public String bingoRefund(long brandID, String vendorID, String extPlayerID, String transactionID) {
		AttributeHolder requestArgs = createOrderedMapWithProviderIdIfNeeded(brandID);

		requestArgs.put(Parameter.PLAYER_ID, extPlayerID);
		requestArgs.put(Parameter.REFERENCE, transactionID);

		RefundResponse res = send(brandID, ExternalServiceEndpoint.BINGO_REFUND, requestArgs, RefundResponse.class);

		checkError(res);

		return res.getTransactionID();
	}

	/* */

	private AttributeHolder createOrderedMapWithProviderIdIfNeeded(long brandID) {
		AttributeHolder attributeHolder = new AttributeHolder();
		String identifier = generalSettingDAO.getValue(GeneralSettingKey.PROVIDER_LOGIN.key(), brandID);
		if (identifier != null && !identifier.trim().isEmpty()) {
			attributeHolder.put(Parameter.PROVIDER_ID, identifier);
		}
		return attributeHolder;
	}

	private <T> T send(long brandID, String urlEndPart, final AttributeHolder requestArgs, Class<T> resultClass) {
		if (requestArgs == null || requestArgs.isEmpty()) {
			throw new IllegalArgumentException("Request arguments is null!");
		}
		String password = generalSettingDAO.getValue(GeneralSettingKey.PROVIDER_PASSWORD.key(), brandID);
		if (password != null && !password.trim().isEmpty()) {
			// Does not calculate hash and add it to request if secret word is blank
			String sourceHash = requestArgs.getString() + password;
			if (log.isDebugEnabled()) {
				log.debug("Hash source: " + sourceHash);
			}
			if (!requestArgs.isKeyPresent(Parameter.HASH))
				requestArgs.put(Parameter.HASH, EncodeUtil.md5(sourceHash));
		}

		List<NameValuePair> nvpList = new ArrayList<>(requestArgs.size());
		for (Map.Entry<String, Object> entry : requestArgs.getEntryList()) {
			nvpList.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
		}

		String responseBody = null;
		RequestConfig config = RequestConfig.custom()
				.setConnectTimeout(1000 * ConfUtils.getIntValue(brandID, 1,
						GeneralSettingKey.SERVICE_SOCKET_TIMEOUT_SECONDS, generalSettingDAO))
				.setConnectionRequestTimeout(1000 * ConfUtils.getIntValue(brandID, 1,
						GeneralSettingKey.SERVICE_SOCKET_TIMEOUT_SECONDS, generalSettingDAO))
				.setSocketTimeout(1000 * ConfUtils.getIntValue(brandID, 1,
						GeneralSettingKey.SERVICE_SOCKET_TIMEOUT_SECONDS, generalSettingDAO))
				.build();

		CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();

		String url = generalSettingDAO.getValue(GeneralSettingKey.OPERATOR_URL.key(), brandID);
		url = url.endsWith("/") ? url + urlEndPart : url + "/" + urlEndPart;

		long startTime = System.currentTimeMillis();
		try {
			final HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(nvpList));

			final ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				public String handleResponse(final HttpResponse response) throws IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}
			};

			responseBody = httpClient.execute(httpPost, responseHandler);
		} catch (Exception e) {
			log.error("Couldn't connect to " + url + ". Error: " + e.getMessage());
			throw new ExternalServiceException("Couldn't connect to " + url + ". Error: " + e.getMessage());
		} finally {
			if (log.isInfoEnabled()) {
				log.info("Request took " + (System.currentTimeMillis() - startTime)
						+ " milliseconds. Currently active threads: " + Thread.activeCount());
			}
			try {
				httpClient.close();
			} catch (Exception e) {
				log.error("Could not close http client", e);
			}
			if (log.isDebugEnabled()) {
				StringBuilder sb = new StringBuilder(
						String.format("Result of call to external service. Url: %s, Secret: '%s'", url, password));
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
			log.error("Couldn't parse json response. Response: " + responseBody);
			throw new ExternalServiceException();
		}
	}

	private <T> T sendWithRetry(long brandID, String urlEndPart, final AttributeHolder requestArgs,
			Class<T> resultClass) {
		T resp = null;
		int index = 0;
		final RetryInfo retryInfo = new RetryInfo(generalSettingDAO, brandID);

		do {
			try {
				resp = send(brandID, urlEndPart, requestArgs, resultClass);
				if (resp == null) {
					String msg = "Service return an empty response! BrandID[" + brandID + "], request["
							+ requestArgs.getString() + "]";
					throw new ExternalServiceException(msg);
				}
				break;
			} catch (ExternalServiceException e) {
				if (!retryInfo.isRetryConfigured()) {
					log.error(e.getMessage());
					break;
				}

				index++;
				if (log.isWarnEnabled()) {
					log.warn("Attempt to call external service was failed. Error: " + e.getMessage()
							+ ". Current attempt is " + index);
				}

				if (index >= retryInfo.getCount()) {
					if (log.isWarnEnabled()) {
						log.warn("All " + retryInfo.getCount() + " attempts to call external service were failed.");
					}
					break;
				}

				if (log.isWarnEnabled()) {
					log.warn("Will retry after " + retryInfo.getDelay() + " seconds.");
				}

				try {
					Thread.sleep(retryInfo.getDelay());
				} catch (InterruptedException e1) {
					log.error("Exception was on delay. Error: " + e1.getMessage());
					break;
				}
			}
		} while (true);

		return resp;
	}

	private void checkError(BaseResponse res) {
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
			throw new ExternalServiceException(res.getDescription());
		}
	}

	private class AttributeHolder {

		List<Map.Entry<String, Object>> entryList = new ArrayList<>();

		void put(String key, Object val) {
			if (val != null)
				entryList.add(new AbstractMap.SimpleEntry<>(key, val));
		}

		public String getString() {

			List<String> sortList = new ArrayList<>();

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

		public boolean isKeyPresent(String key) {
			for (Map.Entry<String, Object> entry : entryList) {
				if (key.equals(entry.getKey())) {
					return true;
				}
			}
			return false;
		}
	}

	@Override
	public void bingoEndRound(long brandID, String externalPlayerID, long bingoRoomID, long bingoRoundID) {
		if (log.isDebugEnabled()) {
			log.debug("Start BingoEndRound extPlayer:" + externalPlayerID + ", bingoRoundID: " + bingoRoundID);
		}

		AttributeHolder requestArgs = createOrderedMapWithProviderIdIfNeeded(brandID);
		requestArgs.put(Parameter.PLAYER_ID, externalPlayerID);
		requestArgs.put(Parameter.ROOM_ID, bingoRoomID);
		requestArgs.put(Parameter.ROUND_ID, bingoRoundID);
		requestArgs.put(Parameter.VENDOR_ID, Vendors.TOP_GAME_BINGO);

		BingoEndRoundResponce resp = send(brandID, ExternalServiceEndpoint.BINGO_END_ROUND, requestArgs,
				BingoEndRoundResponce.class);

		checkError(resp);
	}

	public BingoWinResult bingoWin(Long brandID, String playerID, String transactionID, Double amount, Long roundID,
			Long roomID) {

		if (log.isDebugEnabled()) {
			log.debug("Start query BingoResult by transactionID:" + transactionID);
		}

		AttributeHolder requestArgs = createOrderedMapWithProviderIdIfNeeded(brandID);

		requestArgs.put(Parameter.PLAYER_ID, playerID);
		requestArgs.put(Parameter.VENDOR_ID, Vendors.TOP_GAME_BINGO);
		requestArgs.put(Parameter.REFERENCE, transactionID);
		// FIXME - pass calculated amount here
		requestArgs.put(Parameter.AMOUNT, Double.toString(amount));
		requestArgs.put(Parameter.RESULT_TYPE, "complete");
		requestArgs.put(Parameter.ROUND_ID, roundID.toString());
		requestArgs.put(Parameter.ROOM_ID, roomID.toString());

		BingoResultResponse resp = send(brandID, ExternalServiceEndpoint.BINGO_RESULT, requestArgs,
				BingoResultResponse.class);
		checkError(resp);
		return new BingoWinResult(resp.getCash(), resp.getBonus(), resp.getTransactionID(), resp.getBonusPartWin());
	}

	public BuyReservedCardsResult buyReservedCards(long brandID, String extPlayerID, double amount, long roomID,
			long roundID, double cardCost, double cardCostUSD, int cardsNumber, int packSize, String transactionID) {

		AttributeHolder requestArgs = createOrderedMapWithProviderIdIfNeeded(brandID);
		requestArgs.put(Parameter.PLAYER_ID, extPlayerID);
		requestArgs.put(Parameter.VENDOR_ID, Vendors.TOP_GAME_BINGO);
		requestArgs.put(Parameter.REFERENCE, transactionID);
		requestArgs.put(Parameter.AMOUNT, Double.toString(amount));
		requestArgs.put(Parameter.ROUND_ID, Long.toString(roundID));
		requestArgs.put(Parameter.ROOM_ID, Long.toString(roomID));
		requestArgs.put(Parameter.CARD_COST, String.valueOf(cardCost));
		requestArgs.put(Parameter.CARD_COST_USD, String.valueOf(cardCostUSD));
		requestArgs.put(Parameter.CARDS_NUMBER, String.valueOf(cardsNumber));
		requestArgs.put(Parameter.PACK_SIZE, String.valueOf(packSize));

		BuyPreorderedCardsResponse res = send(brandID, ExternalServiceEndpoint.BUY_PREORDERED_CARDS, requestArgs,
				BuyPreorderedCardsResponse.class);

		if (res.getError() == null || res.getError().equals(ExternalServiceCode.SUCCESS)) {
			if (StringUtils.isBlank(res.getTransactionID())) {
				if (log.isWarnEnabled()) {
					log.warn("External service returned an empty transactionID with success status code!");
				}
				return new BuyReservedCardsResult(InternalServiceResponseStatus.ERROR);
			}
			return new BuyReservedCardsResult(res.getCash(), res.getBonus(), res.getBonusPartBet(),
					res.getTransactionID(), res.getBoughtCardsNumber());
		} else if (res.getError().equals(ExternalServiceCode.INSUFFICIENT_BALANCE)) {
			if (log.isDebugEnabled()) {
				log.debug("BuyBingoCards receive an error code " + res.getError() + " with description '"
						+ res.getDescription() + "'" + ". brandID[" + brandID + "], extPlayerID[" + extPlayerID
						+ "], roomID[" + roomID + "], roundID[" + roundID + "], amount[" + amount + "], cardsNumber["
						+ cardsNumber + "]");
			}
			return new BuyReservedCardsResult(res.getCash(), res.getBonus(),
					InternalServiceResponseStatus.INSUFFICIENT_FUNDS_ERROR);
		} else {
			// Some other problems
			if (log.isWarnEnabled()) {
				log.warn("BuyBingoCards receive an error code " + res.getError() + " with description '"
						+ res.getDescription() + "'" + ". brandID[" + brandID + "], extPlayerID[" + extPlayerID
						+ "], roomID[" + roomID + "], roundID[" + roundID + "], amount[" + amount + "], cardsNumber["
						+ cardsNumber + "]");
			}
			return new BuyReservedCardsResult(InternalServiceResponseStatus.ERROR);
		}

	}

	@Override
	public ProcessBingoBucks processBingoBucks(long brandID, String extPlayerID, double amount, long roomID,
			long chatRoundID, int isUnlimited) {

		AttributeHolder requestArgs = createOrderedMapWithProviderIdIfNeeded(brandID);
		requestArgs.put(Parameter.PLAYER_ID, extPlayerID);
//        requestArgs.put(Parameter.REFERENCE, transactionID);
		requestArgs.put(Parameter.AMOUNT, Double.toString(amount));
		requestArgs.put(Parameter.CHAT_ROUND_ID, Long.toString(chatRoundID));
		requestArgs.put(Parameter.ROOM_ID, Long.toString(roomID));
		requestArgs.put(Parameter.IS_UNLIMITED, isUnlimited == 1 ? true : false);

		ProcessBingoBucksResponse res = send(brandID, ExternalServiceEndpoint.PROCESS_BINGO_BUCKS, requestArgs,
				ProcessBingoBucksResponse.class);

		return new ProcessBingoBucks(res.getError(), res.getDescription(), res.getAmount(), res.getTransactionID());
	}

	interface Parameter {
		String HASH = "hash";
		String TOKEN = "token";
		String PLAYER_ID = "userID";
		String VENDOR_ID = "vendorID";
		String AMOUNT = "amount";
		String GAME_ID = "gameID";
		String PROVIDER_ID = "providerID";
		String REFERENCE = "reference";
		String ROUND_ID = "roundID";
		String CHAT_ROUND_ID = "chatRoundID";
		String ORIGINAL_ROUND_ID = "originalRoundID";
		String BONUS_CODE = "bonusCode";
		String ROUND_DETAILS = "roundDetails";
		String ROOM_ID = "roomID";
		String CARDS_NUMBER = "cardsNumber";
		String PACK_SIZE = "packSize";
		String RESULT_TYPE = "resultType";
		String CARD_COST = "cardCost";
		String CARD_COST_USD = "cardCostUSD";
		String END_ROUND = "endRound";
		String DOMAIN = "domain";
		String JACKPOT_ID = "jackpotID";
		String IS_UNLIMITED = "isUnlimited";
		String PRIZE_NAME = "prizeName";
		String PRIZE_TYPE_ID = "prizeTypeID";
		String OFFER_VALUE = "offerValue";
	}

	interface ExternalServiceEndpoint {
		String AUTHENTICATE = "authenticate";
		String VERIFICATION = "verification";
		String BET = "bet";
		String BET_RESULT = "result";
		String JACKPOT_WIN = "jackpotWin";
		String REFUND = "refund";
		String BALANCE = "balance";
		String BONUS_WIN = "bonusWin";
		String END_ROUND = "endRound";
		String BUY_BINGO_CARDS = "buyBingoCards";
		String BINGO_RESULT = "processBingoResults";
		String BUY_PREORDERED_CARDS = "buyPreorderedCards";
		String BINGO_REFUND = "bingoRefund";
		String BINGO_END_ROUND = "bingoEndRound";
		String PROCESS_BINGO_BUCKS = "processBingoBucks";
		String SCRATCH_CARD_WIN = "scratchCardWin";
	}

}
