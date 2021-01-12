package biz.digitalhouse.integration.v3.web.ws.soap.bingoGameAPI;

import biz.digitalhouse.integration.v3.DefaultValues;
import biz.digitalhouse.integration.v3.ServiceResponseCode;
import biz.digitalhouse.integration.v3.exceptions.CommonServiceException;
import biz.digitalhouse.integration.v3.model.*;
import biz.digitalhouse.integration.v3.services.bingoGame.BingoGameManager;
import biz.digitalhouse.integration.v3.services.bingoGame.dto.*;
import biz.digitalhouse.integration.v3.services.brand.BrandManager;
import biz.digitalhouse.integration.v3.services.players.PlayerManager;
import biz.digitalhouse.integration.v3.utils.DateTimeUtil;
import biz.digitalhouse.integration.v3.web.ws.WsAbstractAPI;
import biz.digitalhouse.integration.v3.web.ws.dto.bingoGameAPI.*;
import biz.digitalhouse.integration.v3.web.ws.dto.bingoGameAPI.Balance;
import biz.digitalhouse.integration.v3.web.ws.dto.bingoGameAPI.Game;
import biz.digitalhouse.integration.v3.web.ws.dto.bingoGameAPI.PlayerCards;
import biz.digitalhouse.integration.v3.web.ws.dto.bingoGameAPI.Transaction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author Vitalii Babenko
 *         on 01.03.2016.
 */
@Endpoint
@EnableAspectJAutoProxy
public class BingoGameAPIEndpoint extends WsAbstractAPI {

    private final static String NAMESPACE_URI = "http://gametechclubs.biz/bingo/external/schemas";
    private final Log log = LogFactory.getLog(getClass());
    private BingoGameManager bingoGameManager;
    private PlayerManager playerManager;

    @Autowired
    public BingoGameAPIEndpoint(BrandManager brandManager,
                                BingoGameManager bingoGameManager,
                                PlayerManager playerManager) {
        super(brandManager);
        this.bingoGameManager = bingoGameManager;
        this.playerManager = playerManager;
    }

    @SuppressWarnings("unused")
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "roomListRequest")
    @ResponsePayload
    public RoomListResponse roomList(@RequestPayload RoomListRequest req) {
        RoomListResponse res = new RoomListResponse();
        try {
            long brandId = check(req.getSecureLogin(), req.getSecurePassword());
            List<ActiveRoomDTO> activeRooms = bingoGameManager.bingoRooms(brandId, req.getPlayerID(), req.getLanguage());
            for (ActiveRoomDTO dto : activeRooms) {
                ActiveRoom room = new ActiveRoom();
                room.setRoomID(dto.getRoomID());
                room.setRoomName(dto.getRoomName());
                room.setBingoType(dto.getBingoType());

                populateCurrencyMap(dto.getCardCost(), room.getCardCost());
                populateCurrencyMap(dto.getGamePrize(), room.getGamePrize());

                room.setNextGameStart(DateTimeUtil.convertCalendarToXMLCalendar(dto.getNextGameStart()));
                populateCurrencyMap(dto.getJackpotAmount(), room.getJackpotAmount());
                room.setPlayersBoughtCards(dto.getPlayersBoughtCards());
                room.setPlayersInRoom(dto.getPlayersInRoom());
                res.getActiveRoomList().add(room);
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

    @SuppressWarnings("unused")
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "availableGamesRequest")
    @ResponsePayload
    public AvailableGamesResponse availableGames(@RequestPayload AvailableGamesRequest req) {
        AvailableGamesResponse res = new AvailableGamesResponse();
        try {
            Calendar date = DateTimeUtil.convertXMLCalendarToCalendar(req.getDate());
            log.debug(date.getTime());
            long brandId = check(req.getSecureLogin(), req.getSecurePassword());
            List<BingoRoomDTO> bingoRooms = bingoGameManager.availableGames(brandId, req.getPlayerID(), date, req.getHour(), req.getLanguage());
            if (bingoRooms != null) {
                for (BingoRoomDTO rd : bingoRooms) {
                    Room room = new Room();
                    room.setRoomID(rd.getRoomId());
                    room.setRoomName(rd.getRoomName());
                    room.setBingoType(rd.getBingoType());
                    if (rd.getBingoGames() != null) {
                        for (BingoGameDTO gd : rd.getBingoGames()) {
                            Game game = new Game();
                            game.setGameID(gd.getBingoGameId());
                            game.setGameName(gd.getGameName());
                            game.setSerialNumber(gd.getSerialNumber());
                            game.setMaxCardsNumber(gd.getMaxCardNumber());
                            populateCurrencyMap(gd.getCardCost(), game.getCardCost());
                            game.setPurchaseMode(gd.getPurchaseMode());
                            game.setPlayers(gd.getPlayers());
                            game.setBoughtCardsNumber(gd.getBoughtCardsNumber());
                            game.setFreeCardsNumber(gd.getFreeCardsNumber());
                            populateCurrencyMap(gd.getTotalAmount(), game.getTotalAmount());

                            room.getGameList().add(game);
                        }
                    }
                    res.getRoomList().add(room);
                }
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

    @SuppressWarnings("unused")
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "preorderCardsRequest")
    @ResponsePayload
    public PreorderCardsResponse preorderCards(@RequestPayload PreorderCardsRequest req) {
        PreorderCardsResponse res = new PreorderCardsResponse();
        try {
            Calendar date = DateTimeUtil.convertXMLCalendarToCalendar(req.getDate());
            long brandId = check(req.getSecureLogin(), req.getSecurePassword());

            if (req.getPreorderCardList() == null || req.getPreorderCardList().isEmpty()) {
                throw ServiceResponseCode.EMPTY_MANDATORY_FIELD.setMessage("preorderCardList").EXCEPTION();
            }

            List<PreorderCardDTO> cardDTOList = new ArrayList<>(req.getPreorderCardList().size());
            for (PreorderCard card : req.getPreorderCardList()) {
                if (card.getGameID() < 1) {
                    throw ServiceResponseCode.WRONG_FIELD.setMessage("gameId").EXCEPTION();
                }
                if (card.getSerialNumber() < 1) {
                    throw ServiceResponseCode.WRONG_FIELD.setMessage("serialNumber").EXCEPTION();
                }
                if (card.getCardNumber() < 1) {
                    throw ServiceResponseCode.WRONG_FIELD.setMessage("cardNumber").EXCEPTION();
                }
                cardDTOList.add(new PreorderCardDTO(card.getGameID(), card.getSerialNumber(), card.getCardNumber()));
            }

            bingoGameManager.preorderCards(brandId, req.getPlayerID(), req.getNickname(), req.getCurrency(), date, req.getHour(), req.getRoomID(), cardDTOList);

            for (PreorderCardDTO dto : cardDTOList) {
                PreorderCard card = new PreorderCard();
                card.setGameID(dto.getGameID());
                card.setSerialNumber(dto.getSerialNumber());
                card.setCardNumber(dto.getCardNumber());
                card.setDescription(dto.getDescription());
                card.setError(dto.getError());
                res.getPreorderCardList().add(card);
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

    @SuppressWarnings("unused")
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "preorderedReportRequest")
    @ResponsePayload
    public PreorderedReportResponse preorderedReport(@RequestPayload PreorderedReportRequest req) {
        PreorderedReportResponse res = new PreorderedReportResponse();
        try {
            long brandId = check(req.getSecureLogin(), req.getSecurePassword());
            List<PreorderedDTO> dtoList = bingoGameManager.preorderedReport(brandId, req.getPlayerID(), DateTimeUtil.convertXMLCalendarToCalendar(req.getDateFrom()), DateTimeUtil.convertXMLCalendarToCalendar(req.getDateTo()), req.getLanguage());
            if(dtoList != null && !dtoList.isEmpty()) {
                for (PreorderedDTO dto : dtoList) {
                    Preordered pr = new Preordered();
                    pr.setPreorderedDate(DateTimeUtil.convertCalendarToXMLCalendar(dto.getPreorderedDate()));
                    pr.setSessionStartDate(DateTimeUtil.convertCalendarToXMLCalendar(dto.getSessionStartDate()));
                    pr.setGameID(dto.getBingoGameID());
                    pr.setGameName(dto.getBingoGameName());
                    pr.setRoomID(dto.getBingoRoomID());
                    pr.setRoomName(dto.getBingoRoomName());
                    pr.setCardNumbers(dto.getCardNumbers());
                    pr.setCardCost(dto.getCardCost());
                    pr.setPurchaseMode(dto.getPurchaseMode());
                    pr.setRoundID(dto.getRoundID());
                    pr.setBingoType(dto.getBingoType());
                    pr.setCurrency(dto.getCurrencySymbol());
                    res.getPreorderedList().add(pr);
                }
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

    @SuppressWarnings("unused")
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "cancelPreorderedCardsRequest")
    @ResponsePayload
    public CancelPreorderedCardsResponse cancelPreorderedCards(@RequestPayload CancelPreorderedCardsRequest req) {
        CancelPreorderedCardsResponse res = new CancelPreorderedCardsResponse();
        try {
            long brandId = check(req.getSecureLogin(), req.getSecurePassword());
            if (req.getCancelPreorderedList().isEmpty()) {
                throw ServiceResponseCode.EMPTY_MANDATORY_FIELD.setMessage("cancelPreorderedList").EXCEPTION();
            }
            List<CancelPreorderedDTO> dtoList = new ArrayList<>();
            for (CancelPreordered cp : req.getCancelPreorderedList()) {
                dtoList.add(new CancelPreorderedDTO(DateTimeUtil.convertXMLCalendarToCalendar(cp.getPreorderDate()), cp.getRoundID()));
            }
            bingoGameManager.cancelPreorderedCards(brandId, req.getPlayerID(), dtoList);
            for (CancelPreorderedDTO dto : dtoList) {
                CancelPreordered cp = new CancelPreordered();
                cp.setPreorderDate(DateTimeUtil.convertCalendarToXMLCalendar(dto.getPreorderDate()));
                cp.setRoundID(dto.getRoundID());
                cp.setError(dto.getError());
                cp.setDescription(dto.getDescription());
                res.getCancelPreorderedList().add(cp);
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

    @SuppressWarnings("unused")
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "bingoJackpotsRequest")
    @ResponsePayload
    public BingoJackpotsResponse bingoJackpots(@RequestPayload BingoJackpotsRequest req) {
        BingoJackpotsResponse res = new BingoJackpotsResponse();
        try {
            long brandId = check(req.getSecureLogin(), req.getSecurePassword());
            List<JackpotDTO> dtoList = bingoGameManager.bingoJackpots(brandId, req.getLanguage());
            if (dtoList != null) {
                for (JackpotDTO dto : dtoList) {
                    Jackpot jackpot = new Jackpot();
                    populateCurrencyMap(dto.getJackpotAmount(), jackpot.getJackpotAmount());
                    jackpot.setJackpotName(dto.getJackpotName());
                    jackpot.setBingoType(dto.getBingoType());
                    res.getJackpotList().add(jackpot);
                }
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

    @SuppressWarnings("unused")
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "bingoHistoryRequest")
    @ResponsePayload
    public BingoHistoryResponse bingoHistory(@RequestPayload BingoHistoryRequest req) {
        BingoHistoryResponse res = new BingoHistoryResponse();
        try {
            if (req.getRoundID() < 1) {
                throw ServiceResponseCode.WRONG_FIELD.setMessage("roundId").EXCEPTION();
            }
            long brandId = check(req.getSecureLogin(), req.getSecurePassword());
            BingoGameHistoryDTO dto = bingoGameManager.bingoHistory(brandId, req.getRoundID(), req.getLanguage());
            res.setRoundID(dto.getRoundID());
            res.setGameName(dto.getGameName());
            res.setBingoCalls(dto.getBingoCalls());
            res.setBingoType(dto.getBingoType());
            for (BingoWinnerDTO dtoWin : dto.getBingoWinners()) {
                BingoWinner win = new BingoWinner();
                win.setPartNumber(dtoWin.getPartNumber());
                win.setPattern(dtoWin.getPattern());
                win.setWinningCallNumber(dtoWin.getWinningCallNumber());
                win.setWinners(dtoWin.getWinners());
                res.getBingoWinners().add(win);
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

    @SuppressWarnings("unused")
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "roundStatisticsRequest")
    @ResponsePayload
    public RoundStatisticsResponse roundStatistics(@RequestPayload RoundStatisticsRequest req) {
        RoundStatisticsResponse res = new RoundStatisticsResponse();
        try {
            if (req.getRoundID() < 1) {
                throw ServiceResponseCode.WRONG_FIELD.setMessage("roundId").EXCEPTION();
            }
            long brandId = check(req.getSecureLogin(), req.getSecurePassword());
            BingoRoundStatistic result = bingoGameManager.roundStatistics(brandId, req.getRoundID());
            if (result != null) {
                res.setRoundID(result.getRoundId());

                for (biz.digitalhouse.integration.v3.model.PlayerCards pc : result.getPlayersCards()) {
                    PlayerCards cards = new PlayerCards();
                    cards.setPlayerID(pc.getExtPlayerId());
                    cards.setBoughtCardsNumber(pc.getBoughtCardsNumber());
                    cards.setGivenCardsNumber(pc.getGivenCardsNumber());
                    cards.setCardCost(pc.getCardCost());
                    cards.setCurrency(pc.getCurrencySymbol());
                    res.getBoughtCards().add(cards);
                }
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


    @SuppressWarnings("unused")
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "transactionReportRequest")
    @ResponsePayload
    public TransactionReportResponse transactionReport(@RequestPayload TransactionReportRequest req) {

        TransactionReportResponse res = new TransactionReportResponse();
        try {

            long brandID = check(req.getSecureLogin(), req.getSecurePassword());

            int maxPortion = bingoGameManager.transactionReportMaxPortion(brandID);
            if(req.getPageOffset() > maxPortion) {
                ServiceResponseCode code = ServiceResponseCode.INVALID_PARAMETER.setMessage("Parameter 'size' can not be more than " + maxPortion);
                res.setError(code.getCode());
                res.setDescription(code.getDescription());
                return res;
            }

            long countReport = bingoGameManager.transactionReportCount(brandID, req.getPlayerID(),
                    DateTimeUtil.convertXMLCalendarToDate(req.getStartDate()), DateTimeUtil.convertXMLCalendarToDate(req.getEndDate()),
                    req.getTransactionType() != null ? req.getTransactionType().name() : null);

            if(countReport > 0 && countReport > req.getFromIndex()) {

                List<BingoTransactionReport> result = bingoGameManager.transactionReport(brandID, req.getPlayerID(),
                        DateTimeUtil.convertXMLCalendarToDate(req.getStartDate()),
                        DateTimeUtil.convertXMLCalendarToDate(req.getEndDate()),
                        req.getTransactionType() != null ? req.getTransactionType().name() : null,
                        req.getFromIndex(), req.getPageOffset());

                if (result != null) {
                    for (BingoTransactionReport rep : result) {
                        Transaction tr = new Transaction();
                        tr.setTransactionID(rep.getTransactionID());
                        tr.setTransactionDate(DateTimeUtil.convertDateToXMLCalendar(rep.getTransactionDate()));
                        if(rep.getTransactionType() != null) {
                            try {
                                tr.setTransactionType(TransactionType.fromValue(rep.getTransactionType()));
                            } catch (Exception e) {
                                log.error("DB return Transaction Type " + rep.getTransactionType() + ". This type not covert to enum.\n" + e.getMessage(), e);
                            }
                        }
                        tr.setAmount(rep.getAmount());
                        Balance bal = new Balance();
                        bal.setCash(rep.getCashBalance());
                        bal.setBonus(rep.getBonusBalance());
                        tr.setBalance(bal);
                        tr.setCurrency(rep.getCurrency());
                        res.getTransactions().add(tr);
                    }
                }
            }
            res.setCount(countReport);
            res.setError(ServiceResponseCode.OK.getCode());
            res.setDescription(ServiceResponseCode.OK.getDescription());

        } catch (CommonServiceException e) {
            res.setError(e.getResponse().getCode());
            res.setDescription(e.getResponse().getDescription());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res.setError(ServiceResponseCode.INTERNAL_ERROR.getCode());
            res.setDescription(ServiceResponseCode.INTERNAL_ERROR.getDescription());
        }
        return res;
    }

    @SuppressWarnings("unused")
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "leadersBoardRequest")
    @ResponsePayload
    public LeadersBoardResponse leadersBoard(@RequestPayload LeadersBoardRequest req) {
        LeadersBoardResponse res = new LeadersBoardResponse();
        try {
            if (req.getRoomID() < 0) {
                throw ServiceResponseCode.INVALID_PARAMETER.setMessage("roomId").EXCEPTION();
            }
            if (req.getFromDate() == null) {
                throw ServiceResponseCode.INVALID_PARAMETER.setMessage("fromDate").EXCEPTION();
            }
            if (req.getToDate() == null) {
                throw ServiceResponseCode.INVALID_PARAMETER.setMessage("toDate").EXCEPTION();
            }

            long brandId = check(req.getSecureLogin(), req.getSecurePassword());

            List<BingoLeaderBoard> result = bingoGameManager.leadersBoard(brandId, req.getRoomID(), req.getFromDate().toGregorianCalendar(), req.getToDate().toGregorianCalendar());
            if (result != null) {
                for (BingoLeaderBoard b : result) {
                    LeaderBoard lb = new LeaderBoard();
                    lb.setPlayerID(b.getExtPlayerId());
                    lb.setNickname(b.getNickname());
                    lb.setNumberOfWonCards(b.getNumberOfWonCards());
                    lb.setTotalAmount(b.getTotalAmount());
                    lb.setCurrency(b.getCurrencySymbol());
                    res.getLeaderBoards().add(lb);
                }
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

    @SuppressWarnings("unused")
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "jackpotWinnersRequest")
    @ResponsePayload
    public JackpotWinnersResponse jackpotWinners(@RequestPayload JackpotWinnersRequest req) {
        JackpotWinnersResponse res = new JackpotWinnersResponse();
        try {
            long brandId = check(req.getSecureLogin(), req.getSecurePassword());
            List<BingoJackpotWinner> result = bingoGameManager.jackpotWinners(brandId, req.getDate().toGregorianCalendar(), req.getLanguage());
            if (result != null) {
                for (BingoJackpotWinner win : result) {
                    JackpotWinner winner = new JackpotWinner();
                    winner.setPlayerID(win.getNickname());
                    winner.setPlayerID(win.getExtPlayerId());
                    winner.setAmount(win.getAmount());
                    winner.setDateTime(DateTimeUtil.convertCalendarToXMLCalendar(win.getDateTime()));
                    winner.setJackpotName(win.getJackpotName());
                    winner.setGameName(win.getGameName());
                    winner.setRoomID(win.getRoomId());
                    winner.setRoundID(win.getRoundId());
                    winner.setBingoType(win.getBingoType());
                    winner.setCurrency(win.getCurrencySymbol());
                    res.getJackpotWinners().add(winner);
                }
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

    @SuppressWarnings("unused")
    @PayloadRoot(localPart = "bingoChatBanRequest", namespace = NAMESPACE_URI)
    @ResponsePayload
    public BingoChatBanResponse bingoChatBan(@RequestPayload BingoChatBanRequest req) {

        BingoChatBanResponse res = new BingoChatBanResponse();
        try {
            long brandID = check(req.getSecureLogin(), req.getSecurePassword());
            res.setBan(playerManager.banPlayer(brandID, req.getPlayerID(), req.isBan()));
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
    @PayloadRoot(localPart = "changeNicknameRequest", namespace = NAMESPACE_URI)
    @ResponsePayload
    public ChangeNicknameResponse changeNickname(@RequestPayload ChangeNicknameRequest req) {

        ChangeNicknameResponse res = new ChangeNicknameResponse();
        try {
            long brandID = check(req.getSecureLogin(), req.getSecurePassword());
            playerManager.changeNickname(brandID, req.getPlayerID(), req.getNickname());
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

    private void populateCurrencyMap(Map<String, Double> currencyMap, List<CurrencyEntry> currencyList){
        for(Map.Entry<String, Double> currency: currencyMap.entrySet()){
            CurrencyEntry c = new CurrencyEntry();
            c.setSymbol(currency.getKey());
            c.setValue(currency.getValue());
            currencyList.add(c);
        }
    }

}
