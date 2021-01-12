package biz.digitalhouse.integration.v3.dao.jdbc;

import biz.digitalhouse.integration.v3.dao.BingoHistoryDAO;
import biz.digitalhouse.integration.v3.model.BingoJackpotWinner;
import biz.digitalhouse.integration.v3.model.BingoLeaderBoard;
import biz.digitalhouse.integration.v3.model.BingoRoundStatistic;
import biz.digitalhouse.integration.v3.model.PlayerCards;
import biz.digitalhouse.integration.v3.utils.BingoJdbcDaoSupport;
import biz.digitalhouse.integration.v3.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Vitalii Babenko
 *         on 04.03.2016.
 */
@Repository
public class BingoHistoryDAOJdbc extends BingoJdbcDaoSupport implements BingoHistoryDAO {

    private LeaderBoardPlayerRowMapper leaderBoardPlayerRowMapper = new LeaderBoardPlayerRowMapper();
    private JackpotWinnerRowMapper jackpotWinnerRowMapper = new JackpotWinnerRowMapper();

    @Autowired
    public BingoHistoryDAOJdbc(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public BingoRoundStatistic roundStatistics(long brandId, long roundId) {
        String query = "SELECT BR.BINGO_ROUNDID, BRP.PRICE_VALUE as CARD_COST, BRM.MEMBERID, BRM.BOUGHT_CARDS, BRM.GOT_CARDS, M.EXTERNAL_PLAYERID, brp.CURRENCY_SYMB as CURRENCY_SYMBOL \n" +
                "FROM bingo_round_members brm, \n" +
                "     bingo_rounds br, \n" +
                "     members m, \n" +
                "     bingo_round_prices brp\n" +
                "WHERE BRM.BINGO_ROUNDID = BR.BINGO_ROUNDID \n" +
                "  AND BRM.MEMBERID = M.MEMBERID \n" +
                "  AND BR.ROUND_STATUSID = 'F' \n" +
                "  AND m.CURRENCYID = brp.CURRENCYID \n" +
                "  AND BR.BINGO_ROUNDID = brp.BINGO_ROUNDID\n" +
                "  AND instr(',' || BR.CASINOIDS || ',' , ',' || ? || ',') > 0 \n" +
                "  AND BR.BINGO_ROUNDID = ?";

        List<Map<String, Object>> resultList = getJdbcTemplate().queryForList(query, brandId, roundId);
        BingoRoundStatistic bingoRoundStatistic = null;
        if (resultList != null && !resultList.isEmpty()) {
            for (Map<String, Object> result : resultList) {
                if (bingoRoundStatistic == null) {
                    bingoRoundStatistic = new BingoRoundStatistic(((BigDecimal) result.get("BINGO_ROUNDID")).longValue());
                }
                PlayerCards playerCards = new PlayerCards((String) result.get("EXTERNAL_PLAYERID"),
                                                        ((BigDecimal) result.get("BOUGHT_CARDS")).intValue(),
                                                        ((BigDecimal) result.get("GOT_CARDS")).intValue(),
                                                        ((BigDecimal) result.get("CARD_COST")).doubleValue(),
                                                        ((String) result.get("CURRENCY_SYMBOL")).toString());
                bingoRoundStatistic.getPlayersCards().add(playerCards);
            }
        }
        return bingoRoundStatistic;
    }

    @Override
    public List<BingoLeaderBoard> leaderBoards(long brandId, long bingoRoomId, Calendar from, Calendar to) {
        String query = "SELECT s.memberid, \n" +
                "     s.EXTERNAL_PLAYERID AS EXTERNAL_PLAYERID, \n" +
                "     s.REAL_NICKNAME AS USERNAME, \n" +
                "     s.POINTS AS POINTS, \n" +
                "     c.symb as CURRENCY_SYMBOL,\n" +
                "     SUM (brm.real_bet + brm.bonus_bet) AS TOTAL_AMOUNT \n" +
                "FROM (     \n" +
                "        SELECT brw.memberid, \n" +
                "               M.EXTERNAL_PLAYERID, \n" +
                "               M.REAL_NICKNAME, \n" +
                "               COUNT (*) AS points \n" +
                "        FROM bingo_rounds br, bingo_round_winners brw,\n" +
                "                   members m \n" +
                "        WHERE BR.ROUND_STATUSID = 'F' \n" +
                "             AND instr(',' || BR.CASINOIDS || ',' , ',' || ? || ',') > 0 \n" +
                "             AND M.CASINOID = ? \n" +
                "             AND br.bingo_roomid = ? \n" +
                "             AND BR.playing_start_date >= trunc(?, 'HH24')\n" +
                "             AND BR.playing_start_date <= trunc(?, 'HH24')+1/24 \n" +
                "             AND br.selling_start_date < trunc(?, 'HH24')\n" +
                "             AND brw.bingo_roundID = br.BINGO_ROUNDID  \n" +
                "             AND M.MEMBERID = brw.MEMBERID \n" +
                "        GROUP BY brw.memberid, \n" +
                "               M.EXTERNAL_PLAYERID, \n" +
                "               M.REAL_NICKNAME, \n" +
                "               m.casinoid \n" +
                "        HAVING COUNT (*) > 0\n" +
                "        ) s, \n" +
                "     bingo_round_members brm, bingo_rounds br, currency c\n" +
                "WHERE  brm.memberid = s.memberid \n" +
                "    AND  br.bingo_roundid =  brm.bingo_roundid\n" +
                "    AND   BR.ROUND_STATUSID = 'F' \n" +
                "    AND brm.currencyid = c.currencyid \n" +
                "    AND instr(',' || BR.CASINOIDS || ',' , ',' || ? || ',') > 0 \n" +
                "    AND BR.playing_start_date >= trunc(?, 'HH24')\n" +
                "    AND BR.playing_start_date <= trunc(?, 'HH24')+1/24 \n" +
                "    AND br.selling_start_date < trunc(?, 'HH24')\n" +
                "GROUP BY s.memberid, \n" +
                "         s.EXTERNAL_PLAYERID, \n" +
                "         s.REAL_NICKNAME, \n" +
                "         s.POINTS,\n" +
                "         c.symb\n" +
                "ORDER BY s.POINTS DESC, total_amount DESC";

        Object[] params = new Object[]{brandId, brandId, bingoRoomId, from, to, to, brandId, from, to, to};

        return getJdbcTemplate().query(query, params, leaderBoardPlayerRowMapper);
    }

    @Override
    public List<BingoJackpotWinner> jackpotWinners(long brandId, Calendar date) {

        String sqlQuery = "select m.memberID memberID, m.external_playerid playerID, m.real_nickname nickname" +
                "     , brm.JACKPOT_WIN_IN_PLAYER_CURRENCY amount, br.playing_start_date startDate, bj.NAME_KEY name \n" +
                "     , br.bingo_roomid roomID, bg.name_key gameName, br.bingo_roundid roundID \n" +
                "     , JWIN.WIN_DATE, br.BINGO_TYPEID, c.symb as CURRENCY_SYMBOL \n" +
                "from bingo_rounds br, members m, bingo_jackpots bj, bingo_games bg, jackpot_wins jwin, bingo_round_members brm, currency c \n" +
                "where br.round_statusid='F' \n" +
                "  AND instr(',' || BR.CASINOIDS || ',' , ',' || ? || ',') > 0 \n" +
                "  AND BR.SESSION_START_DATE >= TRUNC(?, 'DD') \n" +
                "  AND BR.SESSION_START_DATE < TRUNC(?, 'DD') + 1 \n" +
                "  AND nvl(brm.JACKPOT_WIN_IN_PLAYER_CURRENCY, 0) != 0 \n" +
                "  AND BJ.BINGO_JACKPOTID = BR.BINGO_JACKPOTID \n" +
                "  AND BG.BINGO_GAMEID = BR.BINGO_GAMEID \n" +
                "  AND JWIN.JACKPOTID = BR.BINGO_JACKPOTID \n" +
                "  AND JWIN.JACKPOT_INSTANCE = BR.JACKPOT_INSTANCE \n" +
                "  AND M.MEMBERID = JWIN.MEMBERID \n" +
                "  AND M.MEMBERID = brm.MEMBERID \n" +
                "  AND br.BINGO_ROUNDID = brm.BINGO_ROUNDID \n" +
                "  AND c.CURRENCYID = brm.CURRENCYID";

        return getJdbcTemplate().query(sqlQuery, jackpotWinnerRowMapper, brandId, date, date);
    }

    private class LeaderBoardPlayerRowMapper implements RowMapper<BingoLeaderBoard> {

        @Override
        public BingoLeaderBoard mapRow(ResultSet resultSet, int i) throws SQLException {
            return new BingoLeaderBoard(
                    resultSet.getString("EXTERNAL_PLAYERID"),
                    resultSet.getString("USERNAME"),
                    resultSet.getInt("POINTS"),
                    resultSet.getDouble("TOTAL_AMOUNT"),
                    resultSet.getString("CURRENCY_SYMBOL"));

        }
    }

    private class JackpotWinnerRowMapper implements RowMapper<BingoJackpotWinner> {
        @Override
        public BingoJackpotWinner mapRow(ResultSet resultSet, int i) throws SQLException {
            return new BingoJackpotWinner(
                    resultSet.getString("playerID"),
                    resultSet.getString("nickname"),
                    resultSet.getDouble("amount"),
                    DateTimeUtil.convertDateToCalendar(resultSet.getTimestamp("WIN_DATE")),
                    resultSet.getString("name"),
                    resultSet.getLong("roomID"),
                    resultSet.getString("gameName"),
                    resultSet.getLong("roundID"),
                    resultSet.getInt("BINGO_TYPEID"),
                    resultSet.getString("CURRENCY_SYMBOL")
            );
        }
    }
}
