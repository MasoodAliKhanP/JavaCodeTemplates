package biz.digitalhouse.integration.v3.dao.jdbc;

import biz.digitalhouse.integration.v3.dao.GameDAO;
import biz.digitalhouse.integration.v3.model.Game;
import biz.digitalhouse.integration.v3.services.casinoGames.dto.JackpotInfoDTO;
import biz.digitalhouse.integration.v3.utils.BingoJdbcDaoSupport;
	
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitalii Babenko
 *         on 18.04.2016.
 */
@Repository
public class GameDAOJdbc extends BingoJdbcDaoSupport implements GameDAO {

    private final Log log = LogFactory.getLog(getClass());
    private GameRowMapper mapper = new GameRowMapper();
    private JackpotDTORowMapper jackpotDTORowMapper = new JackpotDTORowMapper();
    private ExchangeRateRowMapper exchangeRateRowMapper = new ExchangeRateRowMapper();

    @Autowired
    public GameDAOJdbc(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    @Cacheable("findGameID")
    public Long findGameID(String vendorId, String externalGameId) {
        try {
            return getJdbcTemplate().queryForObject("SELECT gameid FROM external_games WHERE vendorID = ? AND name = ?",
                    new Object[]{vendorId, externalGameId}, Long.class);
        } catch (IncorrectResultSizeDataAccessException e) {
            if (log.isWarnEnabled()) {
                log.warn(String.format("Could not find the game for query \"SELECT gameid FROM external_games WHERE vendorID = '%s' AND name = '%s'\".\n", vendorId, externalGameId));
            }
        } catch (Exception e) {
            String msg = String.format("Could not load game for query \"SELECT gameid FROM external_games WHERE vendorID = '%s' AND name = '%s'\".\n", vendorId, externalGameId);
            log.error(msg + e.getMessage(), e);
        }
        return null;
    }

    @Override
    @Cacheable(value = "getGame", unless = "#result == null")
    public Game getGame(long gameId) {
        try {
            return getJdbcTemplate().queryForObject("SELECT g.gameid, g.symbol, g.vendorID \n" +
                            "FROM games g WHERE g.symbol = ?",
                    mapper, gameId);
        } catch (Exception e) {
            String msg = "Could not load external game by id = " + gameId + ".\n";
            log.error(msg + e.getMessage(), e);
        }
        return null;
    }

    @Override
    @Cacheable(value = "getGame", unless = "#result == null")
    public Game getGameBySymbol(String gameSymbol) {
        try {
            return getJdbcTemplate().queryForObject("SELECT g.gameid, g.symbol, g.vendorID \n" +
                            "FROM games g WHERE g.symbol = ?",
                    mapper, gameSymbol);
        } catch (Exception e) {
            String msg = "Could not load external game by gameSymbol = " + gameSymbol + ".\n";
            log.error(msg + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public boolean isGameIsActiveForBrand(long brandId, long gameId) {
        try {
            return !getJdbcTemplate().queryForList(
                    "SELECT 1 FROM casino_games cg, games g WHERE cg.casinoid = ? AND cg.gameID = ? AND cg.game_statusID = 'E' " +
                            "AND g.gameID = cg.gameID AND g.deleted = 0 AND g.game_statusID = 'E'",
                    Long.class,
                    brandId, gameId).isEmpty();
        } catch (Exception e) {
            String msg = "Could not load external game by id = " + gameId + ". ";
            log.error(msg + e.getMessage());
            throw e;
        }
    }

    @Override
    public long getGameIdByFreeRoundId(String bonusCode, String vendorId) {

        final String sql = "select gameid\n" +
                "        from GAMES\n" +
                "        where symbol = (select substr(FR.GAME_SYMBOL_LIST||',', 1, instr(FR.GAME_SYMBOL_LIST||',', ',')-1)\n" +
                "        from FREE_ROUND_BONUSES fr\n" +
                "        where fr.external_bonusid = trim(?)\n" +
                "        and fr.vendorid = ?)\n" +
                "        and rownum < 2";

        return getJdbcTemplate().queryForObject(sql, Long.class, bonusCode, vendorId);
    }

    @Override
    public List<JackpotInfoDTO> getAllJackpotsForCasino(long casinoID) {
    	final String  queryCasinoJackpots = "select j.jackpotid as jackpot_id, "
    			+ "j.name as jackpot_name, "
    			+ "j.real_amount as jackpot_amount, "
    			+ "j.tier as tier, "
    			+ "wm_concat(g.symbol) as game_symbol, "
    			+ "cj.casinoid as casino_id "
    			+ "from casino_jackpots cj, "
    			+ "jackpots j, "
    			+ "jackpot_games jg, "
    			+ "casino_games cg ,games g  "
    			+ "where (cj.casinoid = ? or cj.casinoid is null) "
    			+ "and j.jackpotid = cj.jackpotid "
    			+ "and j.jackpotid =jg.jackpotid "
    			+ "and jg.gameid = cg.gameid "
    			+ "and jg.gameid = g.gameid "
    			+ "and j.real_amount >= 0 and j.jackpot_statusid = 'A' and j.deleted = 0  "
    			+ "and g.game_statusid = 'E'  and g.deleted = 0  "
    			+ "and cg.game_statusid = 'E'  and cg.jackpot_statusid = 'E' and cg.casinoid = ? "
    			+ "group by j.jackpotid, j.name, j.real_amount, j.tier, cj.casinoid";
    	List<Object> params = new ArrayList<>();
        params.add(casinoID);
        params.add(casinoID);
        return getJdbcTemplate().query(queryCasinoJackpots,params.toArray(), jackpotDTORowMapper);
    }
    
    public  double getExchangeRate(String currencySymbol) {
    	Double rate;
    	try{
    		final String queryForExchangeRate = 
    	    		" select rate_value from currency_rates cr, currency c " + 
    	    		" where cr.currencyid = c.currencyid " + 
    	    		" and lower(c.symb) = lower(?) " + 
    	    		" and cr.CREATE_DATE >= sysdate-1";
    		 rate = getJdbcTemplate().query(queryForExchangeRate, 
    			new Object[] { currencySymbol },
    			exchangeRateRowMapper).get(0);
    	}catch (Exception e) {
    		log.error(e.getMessage());
    		return 1.0;
		}
    	return rate;
    }
    
    private  class ExchangeRateRowMapper implements RowMapper<Double> {
        @Override
        public Double mapRow(ResultSet resultSet, int x) 
        		throws SQLException {
        	if(resultSet != null)
        		return resultSet.getDouble("rate_value");
        	else 
        		return 1.0;
			
        }
    }  
    private  class JackpotDTORowMapper implements RowMapper<JackpotInfoDTO> {
        @Override
        public JackpotInfoDTO mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            JackpotInfoDTO jackpotInfoDTO = new JackpotInfoDTO();
            jackpotInfoDTO.setCasinoID(resultSet.getLong("casino_id"));
            jackpotInfoDTO.setGameSymbol(resultSet.getString("game_symbol"));
            jackpotInfoDTO.setJackpotID(resultSet.getLong("jackpot_id"));
            jackpotInfoDTO.setJackpotName(resultSet.getString("jackpot_name"));
            jackpotInfoDTO.setJackpotRealAmount(resultSet.getDouble("jackpot_amount"));
            jackpotInfoDTO.setTier(resultSet.getInt("tier"));
            
            if(jackpotInfoDTO.getTier() == 2){
            	String queryJackpotTierSum = "select sum(real_amount) "
            			+ " from jackpot_tiers "
            			+ " where jackpotid = ? "
            			+ " and jackpot_tier_statusid = 'A'";

            	Object[] params = new Object[] { jackpotInfoDTO.getJackpotID() };
                Double sum_real_amount = getJdbcTemplate().queryForObject(queryJackpotTierSum, params, Double.class);
                jackpotInfoDTO.setJackpotRealAmount(sum_real_amount);
            }
            return jackpotInfoDTO;
        }
    }
    
    
    private class GameRowMapper implements RowMapper<Game> {
        @Override
        public Game mapRow(ResultSet resultSet, int x) throws SQLException {

            Game game = new Game();
            game.setGameID(resultSet.getLong("gameid"));
            game.setSymbol(resultSet.getString("symbol"));
//            game.setExternalTableID(resultSet.getString("table_number"));
            game.setVendorID(resultSet.getString("vendorID"));

            return game;
        }
    }
}
