package biz.digitalhouse.integration.v3.dao.jdbc;

import biz.digitalhouse.integration.v3.dao.BingoTransactionReportDAO;
import biz.digitalhouse.integration.v3.model.BingoTransactionReport;
import biz.digitalhouse.integration.v3.utils.BingoJdbcDaoSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import biz.digitalhouse.integration.v3.web.ws.http.bingoGameAPI.dto.PlayerHistoryRound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

/**
 * Created by vitaliy.babenko
 * on 20.06.2017.
 */
@Repository
public class BingoTransactionReportDAOJdbc extends BingoJdbcDaoSupport implements BingoTransactionReportDAO {
    private final Log log = LogFactory.getLog(getClass());

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private String baseSql = "select roundID, roomName, roundDate, price, cardsNumber, prize, rownum as rn \n" +
            "FROM ( \n" +
            "        select brm.BINGO_ROUNDID as roundID, \n" +
            "               I18N_PKG.get_value(rms.NAME_KEY, bingo_room_pkg.get_room_supported_language(rms.bingo_roomid, :language)) as roomName, \n" +
            "               rnd.selling_start_date as roundDate, \n" +
            "               round(sum(brm.real_bet_in_player_currency), 2) as price, \n" +
            "               sum(brm.BOUGHT_CARDS) as cardsNumber, \n" +
            "               round(sum(brm.real_win_IN_PLAYER_CURRENCY), 2) as prize \n" +
            "        from bingo_round_members brm, \n" +
            "             bingo_rounds rnd, \n" +
            "             bingo_rooms rms, \n" +
            "             members m\n" +
            "        where m.memberid = brm.memberid \n" +
            "          and m.casinoid = :casinoID \n" +
            "          and m.EXTERNAL_PLAYERID = :playerID \n" +
            "          and rnd.selling_start_date between :startDate and :endDate \n" +
            "        --  and fromIndex = :fromIndex \n" +
            "        --  and pageOffset = :pageOffset \n" +
            "          and brm.BINGO_ROUNDID = rnd.BINGO_ROUNDID \n" +
            "          and rnd.BINGO_ROOMID = rms.BINGO_ROOMID \n" +
            "        group by brm.BINGO_ROUNDID, rms.NAME_KEY, m.languageid, rnd.selling_start_date, rms.bingo_roomid \n" +
            ") order by roundID ";

    private RowMapper<BingoTransactionReport> rowMapper = new RowMapper<BingoTransactionReport>() {
        @Override
        public BingoTransactionReport mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new BingoTransactionReport(
                    rs.getString("EXT_TRANSACTIONID"), rs.getString("TRANSACTION_TYPEID"),
                    rs.getDouble("AMMOUNT"),
                    rs.getDouble("CASH"),
                    rs.getDouble("BONUS"),
                    rs.getString("SYMB"),
                    rs.getTimestamp("TRANSACTION_DATE"));
        }
    };

    private RowMapper<PlayerHistoryRound> rowMapperPlayerHistoryRound = new RowMapper<PlayerHistoryRound>() {
        @Override
        public PlayerHistoryRound mapRow(ResultSet rs, int rowNum) throws SQLException {
            PlayerHistoryRound r = new PlayerHistoryRound();

            r.setRoundID(rs.getString("ROUNDID"));
            r.setRoomName(rs.getString("ROOMNAME"));
            r.setRoundDate(rs.getTimestamp("ROUNDDATE"));
            r.setPrice(rs.getDouble("PRICE"));
            r.setCardsNumber(rs.getInt("CARDSNUMBER"));
            r.setPrize(rs.getDouble("PRIZE"));

            return r;
        }
    };

    @Autowired
    public BingoTransactionReportDAOJdbc(DataSource dataSource) {
        setDataSource(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getJdbcTemplate().getDataSource());
    }

    @Override
    public long transactionReportCount(long brandID, String playerID, Date startDate, Date endDate, String transactionType) {

        List<Object> params = new ArrayList<>();
        params.add(brandID);
        params.add(playerID);
        if(transactionType != null) {
            params.add(transactionType);
        }
        params.add(startDate);
        params.add(endDate);

        return getJdbcTemplate().queryForObject("SELECT COUNT(1)" +
                "  FROM BINGO_PLAY_TRANSACTIONS_VIEW BT, MEMBERS M " +
                "  WHERE " +
                "    M.MEMBERID = BT.MEMBERID" +
                "    AND BT.TRANSACTION_STATUSID = 'A'" +
                "    AND BT.CASINOID = ?" +
                "    AND M.EXTERNAL_PLAYERID = ?" +
                (transactionType != null ? " AND BT.TRANSACTION_TYPEID = ? " : "") +
                "    AND BT.TRANSACTION_DATE >= TRUNC(?, 'DD') " +
                "    AND BT.TRANSACTION_DATE < TRUNC(? + 1, 'DD') ", params.toArray(), Long.class);
    }

    @Override
    public List<BingoTransactionReport> transactionReport(long brandID, String playerID, Date startDate, Date endDate, String transactionType, long fromIndex, int pageOffset) {

        List<Object> params = new ArrayList<>();
        params.add(brandID);
        params.add(playerID);
        if(transactionType != null) {
            params.add(transactionType);
        }
        params.add(startDate);
        params.add(endDate);
        params.add(fromIndex);
        params.add(fromIndex + pageOffset);

        return getJdbcTemplate().query("SELECT ROWN.* FROM " +
                "(SELECT REP.*, ROWNUM RN FROM " +
                "  (SELECT     " +
                "    BT.EXT_TRANSACTIONID,  " +
                "    BT.TRANSACTION_TYPEID, " +
                "    AMOUNT_IN_PLAYER_CURRENCY AS AMMOUNT,  " +
                "    floor(BT.REAL_BALANCE * 100) / 100 AS CASH,  " +
                "    floor(BT.BONUS_BALANCE * 100) / 100 AS BONUS,  " +
                "    CUR.SYMB,  " +
                "    BT.TRANSACTION_DATE " +
                "  FROM BINGO_PLAY_TRANSACTIONS_VIEW BT, CURRENCY CUR, MEMBERS M  " +
                "  WHERE  " +
                "    BT.CURRENCYID = CUR.CURRENCYID " +
                "    AND M.MEMBERID = BT.MEMBERID " +
                "    AND BT.TRANSACTION_STATUSID = 'A' " +
                "    AND BT.CASINOID = ? " +
                "    AND M.EXTERNAL_PLAYERID = ? " +
                (transactionType != null ? " AND BT.TRANSACTION_TYPEID = ? " : "") +
                "    AND BT.TRANSACTION_DATE >= TRUNC(?, 'DD')  " +
                "    AND BT.TRANSACTION_DATE < TRUNC(? + 1, 'DD') " +
                "  ORDER BY TRANSACTIONID DESC) REP " +
                "  ) ROWN  " +
                "WHERE RN > ? AND RN <= ?", params.toArray(), rowMapper);
    }

    @Override
    public Integer bingoPlayerHistoryCount(long brandID, String playerID, Date startDate, Date endDate, String language) {

        MapSqlParameterSource parameters = new MapSqlParameterSource();

        parameters.addValue("casinoID", brandID);
        parameters.addValue("playerID", playerID);
        parameters.addValue("startDate", startDate, Types.TIMESTAMP);
        parameters.addValue("endDate", endDate, Types.TIMESTAMP);
        parameters.addValue("language", language);

        return namedParameterJdbcTemplate.queryForObject("select count(1) from (\n" + baseSql + "\n)", parameters, Integer.class);
    }

    @Override
    public List<PlayerHistoryRound> bingoPlayerHistory(long brandID, String playerID, Date startDate,
                                                       Date endDate, long fromIndex, int pageOffset, String language) {

        MapSqlParameterSource parameters = new MapSqlParameterSource();

        parameters.addValue("casinoID", brandID);
        parameters.addValue("playerID", playerID);
        parameters.addValue("startDate", startDate, Types.TIMESTAMP);
        parameters.addValue("endDate", endDate, Types.TIMESTAMP);
        parameters.addValue("fromIndex", fromIndex);
        parameters.addValue("toIndex", fromIndex + pageOffset);
        parameters.addValue("language", language);

        if (log.isDebugEnabled()) {
            log.debug("query parameters: casinoID: " +  brandID + "\n" +
            "playerID: " + playerID + "\n" +
            "startDate: " + startDate + "\n" +
            "endDate: " + endDate + "\n" +
            "fromIndex: " + fromIndex + "\n" +
            "toIndex: " + fromIndex + pageOffset + "\n" +
            "language: " + language);
        }

        String sql = "select * from (\n" + baseSql + "\n) where rn > :fromIndex and rn <= :toIndex ";
        return namedParameterJdbcTemplate.query(sql, parameters, rowMapperPlayerHistoryRound);
    }
}
