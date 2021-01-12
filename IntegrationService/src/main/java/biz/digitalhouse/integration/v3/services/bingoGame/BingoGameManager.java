package biz.digitalhouse.integration.v3.services.bingoGame;

import biz.digitalhouse.integration.v3.exceptions.CommonServiceException;
import biz.digitalhouse.integration.v3.model.BingoJackpotWinner;
import biz.digitalhouse.integration.v3.model.BingoLeaderBoard;
import biz.digitalhouse.integration.v3.model.BingoRoundStatistic;
import biz.digitalhouse.integration.v3.model.BingoTransactionReport;
import biz.digitalhouse.integration.v3.services.bingoGame.dto.*;
import biz.digitalhouse.integration.v3.web.ws.http.bingoGameAPI.dto.PlayerHistoryRound;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Vitalii Babenko
 *         on 01.03.2016.
 */
public interface BingoGameManager {

    List<ActiveRoomDTO> bingoRooms(long brandId, String extPlayerID, String language) throws CommonServiceException;

    List<BingoRoomDTO> availableGames(long brandId, String extPlayerId, Calendar date, int hour, String language) throws CommonServiceException;

    void preorderCards(long brandId, String extPlayerId, String nickname, String currency, Calendar date, int hour, long roomId, List<PreorderCardDTO> cardDTOList) throws CommonServiceException;

    List<PreorderedDTO> preorderedReport(long brandId, String extPlayerId, Calendar from, Calendar to, String language) throws CommonServiceException;

    void cancelPreorderedCards(long brandId, String extPlayerId, List<CancelPreorderedDTO> dtoList) throws CommonServiceException;

    List<JackpotDTO> bingoJackpots(long brandId, String language) throws CommonServiceException;

    BingoGameHistoryDTO bingoHistory(long brandId, long roundId, String language) throws CommonServiceException;

    BingoRoundStatistic roundStatistics(long brandId, long roundId);

    int transactionReportMaxPortion(long brandID);

    long transactionReportCount(long brandID, String playerID, Date startDate, Date endDate, String transactionType);

    List<BingoTransactionReport> transactionReport(long brandID, String playerID, Date startDate, Date endDate, String transactionType, long fromIndex, int pageOffset);

    List<BingoLeaderBoard> leadersBoard(long brandId, long bingoRoomId, Calendar from, Calendar to) throws CommonServiceException;

    List<BingoJackpotWinner> jackpotWinners(long brandId, Calendar date, String language);

    Integer bingoPlayerHistoryCount(long brandID, String playerID, Date startDate, Date endDate, String language);

    List<PlayerHistoryRound> bingoPlayerHistory(long brandID, String playerID, Date startDate, Date endDate, long fromIndex, int pageOffset, String language);
}
