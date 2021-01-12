package biz.digitalhouse.integration.v3.services.bingoGame;

import biz.digitalhouse.integration.v3.GeneralSettingKey;
import biz.digitalhouse.integration.v3.ServiceResponseCode;
import biz.digitalhouse.integration.v3.dao.BingoHistoryDAO;
import biz.digitalhouse.integration.v3.dao.BingoTransactionReportDAO;
import biz.digitalhouse.integration.v3.dao.GeneralSettingDAO;
import biz.digitalhouse.integration.v3.dao.I18nDAO;
import biz.digitalhouse.integration.v3.exceptions.CommonServiceException;
import biz.digitalhouse.integration.v3.model.*;
import biz.digitalhouse.integration.v3.services.bingoGame.bingoServerClient.*;
import biz.digitalhouse.integration.v3.services.bingoGame.bingoServerClient.Game;
import biz.digitalhouse.integration.v3.services.bingoGame.dto.*;
import biz.digitalhouse.integration.v3.services.players.PlayerManager;
import biz.digitalhouse.integration.v3.utils.StringUtils;
import biz.digitalhouse.integration.v3.web.ws.http.bingoGameAPI.dto.PlayerHistoryRound;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.util.*;

/**
 * @author Vitalii Babenko
 *         on 01.03.2016.
 */

@Service
public class BingoGameManagerImpl implements BingoGameManager {

    private final Log log = LogFactory.getLog(getClass());
    private PlayerManager playerManager;
    private BingoHistoryDAO bingoHistoryDAO;
    private BingoTransactionReportDAO bingoTransactionReportDAO;
    private GeneralSettingDAO generalSettingDAO;
    private I18nDAO i18nDAO;
    private BingoServerService service;

    @Autowired
    public BingoGameManagerImpl(PlayerManager playerManager, BingoHistoryDAO bingoHistoryDAO, BingoTransactionReportDAO bingoTransactionReportDAO,
                                GeneralSettingDAO generalSettingDAO, I18nDAO i18nDAO, BingoServerService service) {
        this.playerManager = playerManager;
        this.bingoHistoryDAO = bingoHistoryDAO;
        this.bingoTransactionReportDAO = bingoTransactionReportDAO;
        this.generalSettingDAO = generalSettingDAO;
        this.i18nDAO = i18nDAO;
        this.service = service;
    }

    public List<ActiveRoomDTO> bingoRooms(long brandId, String extPlayerID, String language) throws CommonServiceException {
        try {
            Player player = null;
            if (StringUtils.isNotEmpty(extPlayerID)) {
                player = playerManager.getPlayer(brandId, extPlayerID);
            }

            Response<GetRoomListResponse> res = service.getRoomList(new GetRoomListRequest(brandId, language, player != null ? player.getPlayerID() : null)).execute();

            if (!res.isSuccessful()) {
                log.error("Error body: " + res.errorBody().string());
                throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
            }

            GetRoomListResponse response = res.body();
            List<ActiveRoom> activeRooms = response.getActiveRoomList();

            List<ActiveRoomDTO> dtoList = new ArrayList<>();
            for (ActiveRoom activeRoom : activeRooms) {
                dtoList.add(new ActiveRoomDTO(activeRoom.getRoomID(), activeRoom.getRoomName(), activeRoom.getBingoType(), activeRoom.getCardCost(), activeRoom.getGamePrize(),
                        activeRoom.getNextGameStart(), activeRoom.getJackpotAmount(), activeRoom.getPlayersBoughtCards(),
                        activeRoom.getPlayersInRoom(), activeRoom.getRoomStyleID()));
            }

            return dtoList;
        } catch (CommonServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
        }
    }

    public List<BingoRoomDTO> availableGames(long brandId, String extPlayerId, Calendar date, int hour, String language) throws CommonServiceException {

        try {

            Player player = playerManager.getPlayer(brandId, extPlayerId);
            long playerId = player != null ? player.getPlayerID() : 0;

            Response<GetAvailableGamesResponse> res = service.getAvailableGames(new GetAvailableGamesRequest(brandId, playerId, language, date, hour)).execute();

            if (!res.isSuccessful()) {
                log.error("Error body: " + res.errorBody().string());
                throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
            }

            GetAvailableGamesResponse response = res.body();

            if (response.getRoomList() != null && !response.getRoomList().isEmpty()) {
                List<BingoRoomDTO> roomDTOList = new ArrayList<>();
                for (Room room : response.getRoomList()) {
                    BingoRoomDTO roomDTO = new BingoRoomDTO(room.getRoomID(), room.getRoomName(), room.getBingoType());
                    if (room.getGameList() != null) {
                        for (Game game : room.getGameList()) {
                            BingoGameDTO dto = new BingoGameDTO(
                                    game.getGameID(), game.getGameName(), game.getSerialNumber(),
                                    game.getMaxCardsNumber(), game.getCardCost(), game.getPurchaseMode(),
                                    game.getPlayers(), game.getBoughtCardsNumber(), game.getFreeCardsNumber(),
                                    game.getTotalAmount()
                                    );
                            roomDTO.getBingoGames().add(dto);
                        }
                    }
                    roomDTOList.add(roomDTO);
                }
                return roomDTOList;
            }
        } catch (CommonServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
        }
        return null;
    }

    public void preorderCards(long brandId, String extPlayerId, String nickname, String currency, Calendar date, int hour, long roomId, List<PreorderCardDTO> cardDTOList) throws CommonServiceException {

        try {
            Player player = extPlayerId != null && !extPlayerId.trim().isEmpty() ? playerManager.checkAndRegister(brandId, extPlayerId, currency, nickname) : null;

            List<PreorderCard> preorderCards = new ArrayList<>(cardDTOList.size());
            for (PreorderCardDTO dto : cardDTOList) {
                preorderCards.add(new PreorderCard(dto.getGameID(), dto.getSerialNumber(), dto.getCardNumber(), null, null));
            }

            Response<PreorderCardsResponse> res = service.preorderCards(new PreorderCardsRequest(brandId, date, hour, player.getPlayerID(), roomId, preorderCards)).execute();

            if (!res.isSuccessful()) {
                log.error("Error body: " + res.errorBody().string());
                throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
            }

            PreorderCardsResponse response = res.body();
            for (PreorderCardDTO dto : cardDTOList) {
                for (PreorderCard preorderCard : response.getPreorderCardList()) {
                    if (preorderCard.getSerialNumber() == dto.getSerialNumber() && preorderCard.getGameID() == dto.getGameID()) {
                        convertErrorCodePreorderCard(dto, preorderCard.getError());
                        dto.setCardNumber(preorderCard.getCardsNumber());
                    }
                }
                if (dto.getError() == null || dto.getError().isEmpty()) {
                    convertErrorCodePreorderCard(dto, "");
                }
            }
        } catch (CommonServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
        }
    }

    public List<PreorderedDTO> preorderedReport(long brandId, String extPlayerId, Calendar from, Calendar to, String language) throws CommonServiceException {
        try {
            Player player = playerManager.getPlayer(brandId, extPlayerId);
            if (player == null) {
                throw ServiceResponseCode.PLAYER_NOT_FOUND.EXCEPTION();
            }

            Response<GetPreorderReportResponse> res = service.getPreorderReport(new GetPreorderReportRequest(player.getPlayerID(), brandId, from, to, language)).execute();

            if (!res.isSuccessful()) {
                log.error("Error body: " + res.errorBody().string());
                throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
            }


            GetPreorderReportResponse response = res.body();
            if(response.getPreorderedList() == null || response.getPreorderedList().isEmpty()) {
                return null;
            }
            List<PreorderedDTO> dtoList = new ArrayList<>();
            for (Preordered pr : response.getPreorderedList()) {
                dtoList.add(
                        new PreorderedDTO(pr.getPreorderedDate(), pr.getSessionStartDate(), pr.getBingoGameID(), pr.getBingoRoomID(),
                                pr.getRoundID(), pr.getCardCost(), pr.getCardNumbers(), pr.getPurchaseMode(), pr.getBingoGameName(),
                                pr.getBingoRoomName(), pr.getBingoType(), pr.getCurrencySymbol())
                );
            }
            return dtoList;
        } catch (CommonServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
        }
    }

    public void cancelPreorderedCards(long brandId, String extPlayerId, List<CancelPreorderedDTO> dtoList) throws CommonServiceException {
        try {
            Player player = playerManager.getPlayer(brandId, extPlayerId);
            if (player == null) {
                throw ServiceResponseCode.PLAYER_NOT_FOUND.EXCEPTION();
            }

            List<CancelPreorder> cancelPreorders = new ArrayList<>(dtoList.size());
            for (CancelPreorderedDTO dto : dtoList) {
                cancelPreorders.add(new CancelPreorder(dto.getPreorderDate(), dto.getRoundID(), null, null));
            }

            Response<CancelPreorderCardsResponse> res = service.cancelPreorderCards(new CancelPreorderCardsRequest(brandId, player.getPlayerID(), cancelPreorders)).execute();

            if (!res.isSuccessful()) {
                log.error("Error body: " + res.errorBody().string());
                throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
            }

            CancelPreorderCardsResponse response = res.body();
            if (!"0".equals(response.getError())) {
                log.error("Bingo server return error [" + response.getError() + "][" + response.getDescription() + "] response! Please check Bingo Game Server.");
                throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
            }

            for (CancelPreorderedDTO dto : dtoList) {
                for (CancelPreorder cancelPreorder : response.getCancelPreorderList()) {
                    if (cancelPreorder.getPreorderDate().getTimeInMillis() == dto.getPreorderDate().getTimeInMillis() && cancelPreorder.getRoundID() == dto.getRoundID()) {
                        convertErrorCodePreorderCard(dto, cancelPreorder.getError());
                    }
                }
                if (dto.getError() == null || dto.getError().isEmpty()) {
                    convertErrorCodePreorderCard(dto, "");
                }
            }

        } catch (CommonServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
        }

    }

    public List<JackpotDTO> bingoJackpots(long brandId, String language) throws CommonServiceException {
        try {

            Response<GetBingoJackpotsResponse> res = service.getBingoJackpots(new GetBingoJackpotsRequest(brandId, language)).execute();

            if (!res.isSuccessful()) {
                log.error("Error body: " + res.errorBody().string());
                throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
            }
            GetBingoJackpotsResponse response = res.body();

            if (!"0".equals(response.getError())) {
                log.error("Bingo server return error [" + response.getError() + "][" + response.getDescription() + "] response! Please check Bingo Game Server.");
                throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
            }

            if (response.getJackpotList() != null && !response.getJackpotList().isEmpty()) {
                List<JackpotDTO> dtoList = new ArrayList<>();
                for (JackpotDTO jackpot : response.getJackpotList()) {
                    dtoList.add(new JackpotDTO(jackpot.getJackpotName(), jackpot.getJackpotAmount(), jackpot.getBingoType()));
                }
                return dtoList;
            }
        } catch (CommonServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
        }

        return null;
    }

    public BingoGameHistoryDTO bingoHistory(long brandId, long roundId, String language) throws CommonServiceException {
        try {
            Response<GetBingoHistoryResponse> res = service.getBingoHistory(new GetBingoHistoryRequest(brandId, language, roundId)).execute();

            if (!res.isSuccessful()) {
                log.error("Error body: " + res.errorBody().string());
                throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
            }

            GetBingoHistoryResponse response = res.body();

            ServiceResponseCode code = convertErrorCodePreorderCard(response.getError());
            if(!code.equals(ServiceResponseCode.OK)) {
                log.error("Bingo server return error [" + response.getError() + "][" + response.getDescription() + "] response!");
                throw code.EXCEPTION();
            }
            BingoGameHistoryDTO dto = new BingoGameHistoryDTO(response.getGameName(), response.getRoundID(), response.getBingoCalls(), response.getBingoType());
            for(BingoWinner win : response.getBingoWinners()) {
                dto.getBingoWinners().add(new BingoWinnerDTO(win.getPartNumber(), win.getWinners(), win.getPattern(), win.getWinningCallNumber()));
            }
            return dto;
        } catch (CommonServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
        }
    }

    public BingoRoundStatistic roundStatistics(long brandId, long roundId) {
        return bingoHistoryDAO.roundStatistics(brandId, roundId);
    }

    public int transactionReportMaxPortion(long brandID) {
        String result = generalSettingDAO.getValue(GeneralSettingKey.BINGO_TRANSACTION_REPORT_PORTION.key(), brandID);
        if(result == null) {
            return 50;
        } else {
            return Integer.parseInt(result);
        }
    }

    public long transactionReportCount(long brandID, String playerID, Date startDate, Date endDate, String transactionType) {
        return bingoTransactionReportDAO.transactionReportCount(brandID, playerID, startDate, endDate, transactionType);
    }

    public List<BingoTransactionReport> transactionReport(long brandID, String playerID, Date startDate, Date endDate, String transactionType, long fromIndex, int pageOffset) {
        return bingoTransactionReportDAO.transactionReport(brandID, playerID, startDate, endDate, transactionType, fromIndex, pageOffset);
    }

    public List<BingoLeaderBoard> leadersBoard(long brandId, long bingoRoomId, Calendar from, Calendar to) throws CommonServiceException {

        Calendar testDate = Calendar.getInstance();
        testDate.setTimeInMillis(from.getTimeInMillis());
        testDate.add(Calendar.DATE, 1);

        if (testDate.before(to)) {
            if(log.isWarnEnabled()) {
                log.warn("Search with a difference in dates over more than 24 hours is not possible.");
            }
            throw ServiceResponseCode.INVALID_PARAMETER.setMessage("Search with a difference in dates over more than 24 hours is not possible.").EXCEPTION();
        }

        return bingoHistoryDAO.leaderBoards(brandId, bingoRoomId, from, to);
    }

    public List<BingoJackpotWinner> jackpotWinners(long brandId, Calendar date, String language) {
        List<BingoJackpotWinner> result = bingoHistoryDAO.jackpotWinners(brandId, date);
        if(result != null && !result.isEmpty()) {
            for(BingoJackpotWinner bjw : result) {
                String supLanguage = i18nDAO.getRoomSupportedLanguage(brandId, bjw.getRoomId(), language);
                bjw.setGameName(i18nDAO.getI18nValue(bjw.getGameName(), supLanguage));
                bjw.setJackpotName(i18nDAO.getI18nValue(bjw.getJackpotName(), supLanguage));
            }
        }
        return result;
    }

    @Override
    public Integer bingoPlayerHistoryCount(long brandID, String playerID, Date startDate, Date endDate, String language) {
        return bingoTransactionReportDAO.bingoPlayerHistoryCount(brandID, playerID, startDate, endDate, language);
    }

    @Override
    public List<PlayerHistoryRound> bingoPlayerHistory(long brandID, String playerID, Date startDate, Date endDate, long fromIndex, int pageOffset, String language) {
        return bingoTransactionReportDAO.bingoPlayerHistory(brandID, playerID, startDate, endDate, fromIndex, pageOffset, language);
    }

    private <T extends BaseResponseDTO> void convertErrorCodePreorderCard(T dto, String code) {
        ServiceResponseCode res = convertErrorCodePreorderCard(code);
        dto.setError(res.getCode());
        dto.setDescription(res.getDescription());
    }

    private ServiceResponseCode convertErrorCodePreorderCard(String code) {
        if (code == null)
            return ServiceResponseCode.UNKNOWN_ERROR;

        switch (code) {
            case "0":
                return ServiceResponseCode.OK;
            case "101":
                return ServiceResponseCode.PREORDER_CARDS_LIMIT;
            case "102":
                return ServiceResponseCode.PREORDER_CARDS_IS_NOT_ALLOWED;
            case "103":
                return ServiceResponseCode.PREORDER_GAME_NOT_AVAILABLE;
            case "104":
                return ServiceResponseCode.PLAYER_NOT_FOUND;
            case "105":
                return ServiceResponseCode.THE_REQUESTED_DATA_IS_NOT_FOUND;
            case "106":
                return ServiceResponseCode.CANCEL_PREORDER_CARDS_NOT_FOUND;
            case "107":
                return ServiceResponseCode.INCORRECT_NUMBER_OF_CARDS;
            case "108":
                return ServiceResponseCode.INSUFFICIENT_BALANCE;
            default:
                return ServiceResponseCode.UNKNOWN_ERROR;
        }
    }
}
