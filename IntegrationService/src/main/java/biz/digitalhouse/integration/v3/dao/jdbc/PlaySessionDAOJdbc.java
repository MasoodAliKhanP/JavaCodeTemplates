package biz.digitalhouse.integration.v3.dao.jdbc;

import biz.digitalhouse.integration.v3.dao.PlaySessionDAO;
import biz.digitalhouse.integration.v3.model.PlaySession;
import biz.digitalhouse.integration.v3.utils.BingoJdbcDaoSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by maxim.zhukovskiy on 16/01/2017.
 */

@Repository
public class PlaySessionDAOJdbc extends BingoJdbcDaoSupport implements PlaySessionDAO, RowMapper<PlaySession> {

    private final Log log = LogFactory.getLog(getClass());
    private static final String playSessionFilds = "p.PLAY_SESSIONID, p.MEMBERID, p.PLAY_SESSION_DATE, p.REAL_BET, g.SYMBOL, m.EXTERNAL_PLAYERID, " +
                                                   "p.FUN_BET, p.REAL_WIN, p.FUN_WIN, p.BALANCE, p.FUN_BALANCE, (p.BONUS_BALANCE + p.FREE_MONEY_BALANCE) as BONUS_BALANCE, " +
                                                   "(p.BONUS_BET) as BONUS_BET, (p.BONUS_WIN + NVL(p.FREE_MONEY_WIN, 0.0)) as BONUS_WIN, " +
                                                   "c.SYMB, p.FREE_ROUND_BONUS_CODE, p.CASH_BET, p.CASH_WIN, p.CASH_BALANCE, " +
                                                   "ROUND(p.REAL_BET + p.BONUS_BET, 2) as TOTAL_BET, ROUND(p.REAL_WIN + p.BONUS_WIN + NVL(p.FREE_MONEY_WIN, 0.0), 2) as TOTAL_WIN";
    @Autowired
    public PlaySessionDAOJdbc(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public List<PlaySession> getPlaySession(Long brandId, Long playerId, Long gameId, Date date) throws Exception {
        String sql = "SELECT " + playSessionFilds + " FROM PLAY_SESSIONS_ACTUAL p, MEMBERS m, GAMES g, CURRENCY c " +
                     "WHERE m.CURRENCYID = c.CURRENCYID AND g.GAMEID = p.GAMEID AND m.MEMBERID = p.MEMBERID AND " +
                     "p.INVALID = 0 AND p.ACCOUNT_TYPEID = 'R' AND g.GAME_CATEGORYID != 7 AND m.CASINOID = " + brandId;

        if (playerId != null && playerId > 0) {
            sql += " AND p.MEMBERID = " + playerId;
        }

        if (gameId != null && gameId > 0) {
            sql += " AND p.GAMEID = " + gameId;
        }

        final TimeZone est = TimeZone.getTimeZone("EST");
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(est);

        if (date != null) {
            sql += " AND p.PLAY_SESSION_DATE >= to_date('" + dateFormat.format(date) + "', 'yyyy-mm-dd hh24:mi:ss') " +
                    "AND p.PLAY_SESSION_DATE < to_date('" + dateFormat.format(new Date(date.getTime() + 600000)) + "', 'yyyy-mm-dd hh24:mi:ss') ";
        } else {
            sql += " AND p.PLAY_SESSION_DATE >= to_date('" + dateFormat.format(new Date(new Date().getTime() - 600000)) + "', 'yyyy-mm-dd hh24:mi:ss')";
        }

        log.debug("TODO-->"+sql);

        return getJdbcTemplate().query(sql, this);
    }

    @Override
    public List<PlaySession> getArcPlaySession(Long brandId, Long playerId, Long gameId, Date date) throws Exception {
        String sql = "SELECT " + playSessionFilds + " FROM PLAY_SESSIONS_REMOTE p, MEMBERS m, GAMES g, CURRENCY c " +
                "WHERE m.CURRENCYID = c.CURRENCYID AND g.GAMEID = p.GAMEID AND m.MEMBERID = p.MEMBERID AND " +
                "p.INVALID = 0 AND p.ACCOUNT_TYPEID = 'R' AND g.GAME_CATEGORYID != 7 AND m.CASINOID = " + brandId;

        if (playerId != null && playerId > 0) {
            sql += " AND p.MEMBERID = " + playerId;
        }

        if (gameId != null && gameId > 0) {
            sql += " AND p.GAMEID = " + gameId;
        }

        final TimeZone est = TimeZone.getTimeZone("EST");
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(est);

        sql += " AND p.PLAY_SESSION_DATE >= to_date('" + dateFormat.format(date) + "', 'yyyy-mm-dd hh24:mi:ss') " +
                "AND p.PLAY_SESSION_DATE < to_date('" + dateFormat.format(new Date(date.getTime() + 600000)) + "', 'yyyy-mm-dd hh24:mi:ss') ";

        return getJdbcTemplate().query(sql, this);
    }

    @Override
    public PlaySession mapRow(ResultSet rs, int rowNum) throws SQLException {
        PlaySession playSession = new PlaySession();
        playSession.setPlayerSessionID(rs.getLong("PLAY_SESSIONID"));
        playSession.setMemberID(rs.getLong("MEMBERID"));
        playSession.setExtMemberID(rs.getString("EXTERNAL_PLAYERID"));
        playSession.setGameSymbol(rs.getString("SYMBOL"));
        if (rs.getDate("PLAY_SESSION_DATE") != null)  {
            playSession.setPlayerSessionDate(new Date(rs.getTimestamp("PLAY_SESSION_DATE").getTime()));
        }
        playSession.setRealBet(rs.getDouble("REAL_BET"));
        playSession.setBonusBet(rs.getDouble("BONUS_BET"));
        playSession.setFunBet(rs.getDouble("FUN_BET"));
        playSession.setRealWin(rs.getDouble("REAL_WIN"));
        playSession.setBonusWin(rs.getDouble("BONUS_WIN"));
        playSession.setFunWin(rs.getDouble("FUN_WIN"));
        playSession.setBalance(rs.getDouble("BALANCE"));
        playSession.setFunBalance(rs.getDouble("FUN_BALANCE"));
        playSession.setBonusBalance(rs.getDouble("BONUS_BALANCE"));
        playSession.setCurrency(rs.getString("SYMB"));
        playSession.setFreeRoundBonusCode(rs.getString("FREE_ROUND_BONUS_CODE"));
        playSession.setCashBet(rs.getDouble("CASH_BET"));
        playSession.setCashWin(rs.getDouble("CASH_WIN"));
        playSession.setCashBalance(rs.getDouble("CASH_BALANCE"));
        playSession.setTotalBet(rs.getDouble("TOTAL_BET"));
        playSession.setTotalWin(rs.getDouble("TOTAL_WIN"));
        return playSession;
    }
}
