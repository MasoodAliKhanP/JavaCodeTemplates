package biz.digitalhouse.integration.v3.dao;

import biz.digitalhouse.integration.v3.model.BingoJackpotWinner;
import biz.digitalhouse.integration.v3.model.BingoLeaderBoard;
import biz.digitalhouse.integration.v3.model.BingoRoundStatistic;

import java.util.Calendar;
import java.util.List;

/**
 * @author Vitalii Babenko
 *         on 04.03.2016.
 */
public interface BingoHistoryDAO {

    BingoRoundStatistic roundStatistics(long brandId, long roundId);

    List<BingoLeaderBoard> leaderBoards(long brandId, long bingoRoomId, Calendar from, Calendar to);

    List<BingoJackpotWinner> jackpotWinners(long brandId, Calendar date);
}
