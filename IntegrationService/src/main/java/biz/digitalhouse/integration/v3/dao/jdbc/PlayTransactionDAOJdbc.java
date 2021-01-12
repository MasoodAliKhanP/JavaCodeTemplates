package biz.digitalhouse.integration.v3.dao.jdbc;

import biz.digitalhouse.integration.v3.dao.PlayTransactionDAO;
import biz.digitalhouse.integration.v3.exceptions.FrozenException;
import biz.digitalhouse.integration.v3.exceptions.GameDisabledException;
import biz.digitalhouse.integration.v3.utils.BingoJdbcDaoSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @author Vitalii Babenko
 *         on 15.04.2016.
 */
@Repository
public class PlayTransactionDAOJdbc extends BingoJdbcDaoSupport implements PlayTransactionDAO {

    private final Log log = LogFactory.getLog(getClass());

    @Autowired
    public PlayTransactionDAOJdbc(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public long createPlaySession(final String gameSymbol, final long playerID, int vipLevel, final Long originalRoundID, final String bonusCode, final String incomingRoundID) {

        if (log.isDebugEnabled()) {
            log.debug("Call Game_Outward_Pkg.Create_Play_Session. Arguments: " +
                    "\n\r\t" + "p_symbol = " + gameSymbol +
                    "\n\r\t" + "p_memberid = " + playerID +
                    "\n\r\t" + "p_vip_levelid = " + vipLevel +
                    "\n\r\t" + "p_parent_play_sessionid = " + originalRoundID +
                    "\n\r\t" + "p_ext_roundid = " + incomingRoundID +
                    "\n\r\t" + "p_free_round_bonus_code = " + bonusCode
            );
        }

        CallableStatementCallback<Long> action = new CallableStatementCallback<Long>() {
            @Override
            public Long doInCallableStatement(CallableStatement cs)
                    throws SQLException, DataAccessException {
                int i = 1;

                cs.setString(i++, gameSymbol);
                cs.setLong(i++, playerID);
                cs.setInt(i++, 0);
                if (originalRoundID != null) {
                    cs.setLong(i++, originalRoundID);
                } else {
                    cs.setNull(i++, Types.NUMERIC);
                }
                if(incomingRoundID != null) {
                    cs.setString(i++, incomingRoundID);
                } else {
                    cs.setNull(i++, Types.VARCHAR);
                }
                cs.registerOutParameter(i++, Types.NUMERIC);        // p_play_sessionid
                cs.setString(i--, bonusCode);

                cs.execute();

                return cs.getLong(i);
            }
        };
        // TODO remove p_play_session_location
        return getJdbcTemplate().execute("{call Game_Outward_Pkg.Create_Play_Session(" +
                "  p_symbol                 => ?" +
                ", p_memberID               => ?" +
                ", p_vip_levelid            => ?" +
                ", p_parent_play_sessionid  => ?" +
                ", p_ext_roundid            => ?" +
                ", p_play_sessionid         => ?" +
                ", p_free_round_bonus_code  => ?)}", action);
    }

    @Override
    public Long findPlaySessionID(final String playSessionID, final long playerId) {
        CallableStatementCallback<Long> action = new CallableStatementCallback<Long>() {
            @Override
            public Long doInCallableStatement(CallableStatement cs)
                    throws SQLException, DataAccessException {
                cs.registerOutParameter(1, Types.VARCHAR);
                cs.setString(2, playSessionID);
                cs.setLong(3, playerId);

                cs.execute();
                return cs.getLong(1) > 0 ? cs.getLong(1) : null;
            }
        };

        return getJdbcTemplate().execute("{call ? := Game_Outward_Pkg.find_play_sessionID(p_play_sessionID => ?, p_memberID => ?)}", action);
    }

    @Override
    public Long getPossibleParentPlaySession(final long playerId, final long gameId, final String incomingOriginalRoundId) {

        Long result = null;
        try {
            CallableStatementCallback<Long> action = new CallableStatementCallback<Long>() {
                @Override
                public Long doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {

                    int i = 1;
                    //code of result
                    cs.registerOutParameter(i, java.sql.Types.INTEGER);

                    cs.setLong(++i, playerId);
                    cs.setLong(++i, gameId);
                    cs.setString(++i, incomingOriginalRoundId);
                    cs.execute();

                    return cs.getLong(1) > 0 ? cs.getLong(1) : null;
                }
            };

            result = getJdbcTemplate().execute("{call ? := game_outward_pkg.get_possible_parent_ps(" +
                    "  p_memberid             => ?" +
                    ", p_gameid               => ?" +
                    ", p_ext_original_roundid => ?)}", action);
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("Result by execute GAME_OUTWARD_PKG.GET_POSSIBLE_PARENT_PS(" + playerId + ", " + gameId + ", '" + incomingOriginalRoundId + "') = " + result);
            }
        }

        return result;
    }

    @Override
    public void transactionBet(final String transactionID, final long playerID, final long roundID, final double bet, final double usedBonus, final double cash,
                               final double bonus, final String gameSymbol, final String externalTransactionID) throws FrozenException, GameDisabledException {

        if (log.isDebugEnabled()) {
            log.debug("Call Game_Outward_Pkg.Play_Session_Bet. Arguments: " +
                    "\n\r\t" + "p_play_sessionid = " + roundID +
                    "\n\r\t" + "p_bet_amount = " + bet +
                    "\n\r\t" + "p_memberid = " + playerID +
                    "\n\r\t" + "p_symbol = " + gameSymbol +
                    "\n\r\t" + "p_ext_transactionid = " + externalTransactionID +
                    "\n\r\t" + "p_referenceID = " + transactionID +
                    "\n\r\t" + "p_real_balance = " + cash +
                    "\n\r\t" + "p_bonus_balance = " + bonus +
                    "\n\r\t" + "p_bonus_bet = " + usedBonus
            );
        }

        CallableStatementCallback<Integer> action = new CallableStatementCallback<Integer>() {
            @Override
            public Integer doInCallableStatement(CallableStatement cs)
                    throws SQLException, DataAccessException {

                int i = 1;
                cs.setLong(i++, roundID);
                cs.setDouble(i++, bet);
                cs.setLong(i++, playerID);
                cs.setString(i++, gameSymbol);
                cs.setString(i++, externalTransactionID);
                cs.setString(i++, transactionID);
                cs.setDouble(i++, cash);
                cs.setDouble(i++, bonus);
                cs.setDouble(i++, usedBonus);

                cs.registerOutParameter(i, java.sql.Types.INTEGER);         //p_error

                cs.execute();

                return cs.getInt(i);

            }
        };

        // TODO remove p_play_session_location
        int statusCode = getJdbcTemplate().execute("{call Game_Outward_Pkg.Play_Session_Bet(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}", action);


        StringBuilder exceptionInfo = null;
        if (statusCode != 0) {
            exceptionInfo = new StringBuilder()
                    .append(" playerID=").append(playerID)
                    .append(" roundID=").append(roundID)
                    .append(" gameSymbol=").append(gameSymbol);
        }

        if (statusCode == 2) {
            throw new FrozenException("Frozen user account." + exceptionInfo);
        } else if (statusCode == 3) {
            throw new GameDisabledException("Try to play in disabled game." + exceptionInfo);
        } else if (exceptionInfo != null) {
            exceptionInfo.append(" statusCode=").append(statusCode);
            throw new RuntimeException("Couldn't make a bet. DB returned an error for" + exceptionInfo);
        }
    }

    @Override
    public long transactionWin(String transactionID, final long playerID, final long roundID, final double win, final String gameSymbol) {

        if(log.isDebugEnabled()) {
            log.debug("Call Game_Outward_Pkg.Play_Session_Win. Arguments: " +
                    "\n\r\t" + "p_play_sessionid = " + roundID +
                    "\n\r\t" + "p_win_amount = " + win +
                    "\n\r\t" + "p_jackpot_won = 0" +
                    "\n\r\t" + "p_memberid = " + playerID +
                    "\n\r\t" + "p_symbol = " + gameSymbol
            );
        }

        CallableStatementCallback<Long> action = new CallableStatementCallback<Long>() {
            @Override
            public Long doInCallableStatement(CallableStatement cs)
                    throws SQLException, DataAccessException {

                int i = 1;
                cs.registerOutParameter(i++, java.sql.Types.INTEGER);
                cs.setLong(i++, roundID);
                cs.setDouble(i++, win);
                cs.setInt(i++, 0);
                cs.setLong(i++, playerID);
                cs.setString(i, gameSymbol);
                cs.execute();

                return cs.getLong(1);
            }
        };

        return getJdbcTemplate().execute("{call ? := Game_Outward_Pkg.Play_Session_Win(?, ?, ?, ?, ?)}", action);

    }



    @Override
    public long transactionJackpot(String transactionID, final long playerID, final long roundID, final double win, final long jackpotID, String tierTypeID, final String gameSymbol) {

        if(log.isDebugEnabled()) {
            log.debug("Call Game_Outward_Pkg.Jackpot_Win. Arguments: " +
                    "\n\r\t" + "p_play_sessionid = " + roundID +
                    "\n\r\t" + "p_jackpot_win_amount = " + win +
                    "\n\r\t" + "p_jackpotID = " + jackpotID +
                    "\n\r\t" + "p_memberID = " + playerID +
                    "\n\r\t" + "p_symbol = " + gameSymbol
            );
        }

        CallableStatementCallback<Long> action = new CallableStatementCallback<Long>() {
            @Override
            public Long doInCallableStatement(CallableStatement cs)
                    throws SQLException, DataAccessException {

                int i = 1;
                cs.registerOutParameter(i++, java.sql.Types.INTEGER);
                cs.setLong(i++, roundID);
                cs.setDouble(i++, win);
                cs.setLong(i++, jackpotID);
                cs.setString(i++, tierTypeID);
                cs.setLong(i++, playerID);
                cs.setString(i, gameSymbol);
                cs.execute();

                return cs.getLong(1);
            }
        };

        return getJdbcTemplate().execute("{call ? := Game_Outward_Pkg.Jackpot_Win(?, ?, ?, ?, ?, ?)}", action);

    }

    @Override
    public void gamblingBegin(final String transactionID, final long playerID, final long roundID, final double win, final double usedBonus, final double cash, final double bonus, final String gameSymbol, final String externalTransactionID) throws FrozenException {
        log.debug("Call Game_Outward_Pkg.Gambling_begin. Arguments: " +
                "\n\r\t" + "p_play_sessionid = " + roundID +
                "\n\r\t" + "p_win_amount = " + win +
                "\n\r\t" + "p_memberid = " + playerID +
                "\n\r\t" + "p_symbol = " + gameSymbol +
                "\n\r\t" + "p_ext_transactionid = " + externalTransactionID +
                "\n\r\t" + "p_referenceID = " + transactionID +
                "\n\r\t" + "p_real_balance = " + cash +
                "\n\r\t" + "p_bonus_balance = " + bonus +
                "\n\r\t" + "p_bonus_bet = " + usedBonus
        );


        CallableStatementCallback<Integer> action = new CallableStatementCallback<Integer>() {
            @Override
            public Integer doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {

                int i = 1;

                cs.setLong(i++, roundID);
                cs.setDouble(i++, win);
                cs.setLong(i++, playerID);
                cs.setString(i++, gameSymbol);
                cs.setString(i++, externalTransactionID);
                cs.setString(i++, transactionID);
                cs.setDouble(i++, cash);
                cs.setDouble(i++, bonus);
                cs.setDouble(i++, usedBonus);

                cs.registerOutParameter(i, java.sql.Types.VARCHAR);     //p_error

                cs.execute();

                return cs.getInt(i); //p_error
            }
        };

        // TODO Remove p_play_session_location
        int statusCode = getJdbcTemplate().execute("{call Game_Outward_Pkg.Gambling_begin(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}", action);

        if (statusCode == 2) {
            throw new FrozenException("Frozen user account. Player[" + playerID + "]");
        } else if (statusCode != 0) {
            throw new RuntimeException("Transaction Win failed with errorCode[" + statusCode + "]! playerID[" + playerID + "] gameSymbol[" + gameSymbol + "] roundID[" + roundID + "]");
        }
    }

    @Override
    public long gamblingEnd(String transactionID, final long playerID, final long roundID, final double win, final String gameSymbol) {
        if(log.isDebugEnabled()) {
            log.debug("Call Game_Outward_Pkg.Gambling_end. Arguments: " +
                    "\n\r\t" + "p_play_sessionid = " + roundID +
                    "\n\r\t" + "p_win_amount = " + win +
                    "\n\r\t" + "p_jackpot_won = " + 0 +
                    "\n\r\t" + "p_memberid = " + playerID +
                    "\n\r\t" + "p_symbol = " + gameSymbol
            );
        }

        CallableStatementCallback<Long> action = new CallableStatementCallback<Long>() {
            @Override
            public Long doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                int i = 1;

                //code of result
                cs.registerOutParameter(i++, java.sql.Types.BIGINT);

                cs.setLong(i++, roundID);
                cs.setDouble(i++, win);
                cs.setDouble(i++, 0);
                cs.setLong(i++, playerID);
                cs.setString(i, gameSymbol);

                cs.execute();

                return cs.getLong(1);
            }
        };

        //TODO Remove p_play_session_location (transaction ID move to result parameter)
        return   getJdbcTemplate().execute("{call ? := Game_Outward_Pkg.Gambling_end(?, ?, ?, ?, ?)}", action);
    }

    @Override
    public void updatePlaySession(final long roundID, final long internalTransactionID, final long playerID,
                                  final double cash, final double bonus, final double bonusPart, final String externalTransactionID, final String transactionID) {
        if (log.isDebugEnabled()) {
            log.debug("Call Game_Outward.Play_Session_Update. Arguments: " +
                    "\n\r\tplay_sessionid = " + roundID +
                    "\n\r\tp_internal_transactionid = " + internalTransactionID +
                    "\n\r\tp_memberID = " + playerID +
                    "\n\r\tp_ext_transactionid = " + externalTransactionID +
                    "\n\r\tp_referenceID = " + transactionID +
                    "\n\r\tp_real_balance = " + cash +
                    "\n\r\tp_bonus_balance = " + bonus +
                    "\n\r\tp_bonus_part = " + bonusPart
            );
        }


        CallableStatementCallback<Integer> action = new CallableStatementCallback<Integer>() {
            @Override
            public Integer doInCallableStatement(CallableStatement cs)
                    throws SQLException, DataAccessException {

                int i = 1;

                //code of result
                cs.registerOutParameter(i++, Types.INTEGER);

                cs.setLong(i++, roundID);
                cs.setLong(i++, internalTransactionID);
                cs.setLong(i++, playerID);
                cs.setString(i++, externalTransactionID);
                cs.setString(i++, transactionID);
                cs.setDouble(i++, cash);
                cs.setDouble(i++, bonus);
                cs.setDouble(i++, bonusPart);
                cs.execute();

                return cs.getInt(1);
            }
        };
        int error = getJdbcTemplate().execute("{call ? := Game_Outward_Pkg.Play_Session_Update(?, ?, ?, ?, ?, ?, ?, ?)}", action);
        if (error != 0) {
            throw new RuntimeException("Play Session Update failed! Function:  Game_Outward.Play_Session_Update!" + " memberID[" + playerID + "] internalTransactionID=" + internalTransactionID + " playSessionID=" + roundID + ", DB ERROR CODE:" + error);
        }
    }

}
