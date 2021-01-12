package com.octopusgaming.backoffice.dao.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.octopusgaming.backoffice.dao.BackOfficeDao;
import com.octopusgaming.backoffice.dto.ScratchCardPromotionDTO;
import com.octopusgaming.backoffice.model.Administrator;
import com.octopusgaming.backoffice.model.MinDepositConfig;
import com.octopusgaming.backoffice.model.ScratchCardConfig;
import com.octopusgaming.backoffice.model.ScratchCardWinner;
import com.octopusgaming.backoffice.model.ScratchCardWinnersRequest;

@Repository
public class BackOfficeDaoImpl extends JdbcDaoSupport implements BackOfficeDao {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public BackOfficeDaoImpl(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public Administrator getUser(String userName, String password) {
		log.debug("Username: " + userName + "HashPassword: " + password);
		String query = "select * \n" + 
						" from administrators where username = :userName \n" + 
						" and password = :password and frozen = 0 and deleted = 0 \n" + 
						" and (casinoid is null)";

		log.debug(query);
		SqlParameterSource paramMap = new MapSqlParameterSource().addValue("userName", userName).addValue("password",
				password);

		try {
			return jdbcTemplate.queryForObject(query, paramMap, new RowMapper<Administrator>() {
				@Override
				public Administrator mapRow(ResultSet rs, int rowNum) throws SQLException {
					Administrator admin = new Administrator();
					admin.setAdministratorID(rs.getLong("administratorid"));
					admin.setCustomerID(rs.getLong("customerid"));
					admin.setUsername(rs.getString("username"));
					admin.setFirstName(rs.getString("first_name"));
					admin.setLastName(rs.getString("last_name"));
					admin.setEmail(rs.getString("email"));
					return admin;
				}
			});
		} catch (EmptyResultDataAccessException e) {
			log.error(e);
			return null;
		}
	}
 
	
	@Override
	public ScratchCardPromotionDTO getScratchCardPromotion() {
		String promotionQuery = "SELECT * FROM SCRATCH_CARD_PROMOTIONS WHERE IS_ACTIVE = 1";

		try {
			return jdbcTemplate.queryForObject(promotionQuery, new MapSqlParameterSource(), new RowMapper<ScratchCardPromotionDTO>() {
				@Override
				public ScratchCardPromotionDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
					ScratchCardPromotionDTO promotion = new ScratchCardPromotionDTO();
					promotion.setPromotionId(rs.getInt("SCRATCH_CARD_PROMOTIONID"));
					promotion.setName(rs.getString("NAME"));
					promotion.setStartDate(rs.getDate("START_DATE"));
					promotion.setEndDate(rs.getDate("END_DATE"));
					promotion.setCreateDate(rs.getDate("CREATE_DATE"));
					promotion.setConfigs(getScratchCardConfig(promotion.getPromotionId()));
					promotion.setMinDepositConfigs(getMinDepositConfig(promotion.getPromotionId()));
					return promotion;
				}
			});
		} catch (EmptyResultDataAccessException e) {
			log.error(e);
			return null;
		}
		
		
	}
	
	private List<ScratchCardConfig> getScratchCardConfig(int promotionId){
		String configQuery = "SELECT \n" + 
				"    scc.SCRATCH_CARD_CONFIGID SCRATCH_CARD_CONFIGID, \n" + 
				"    scc.SCRATCH_CARD_PRIZE_TYPEID SCRATCH_CARD_PRIZE_TYPEID, \n" + 
				"    scc.AMOUNT AMOUNT, \n" + 
				"    scc.CHANCE_PERCENTAGE CHANCE_PERCENTAGE, \n" + 
				"    scc.CREATE_DATE CREATE_DATE, \n" + 
				"    scc.VALIDITY_HOURS VALIDITY_HOURS, \n" + 
				"    scc.IS_ACTIVE IS_ACTIVE, \n" + 
				" 	 scc.CURRENT_PRIZE_COUNT, \n" +
				" 	 scc.TOTAL_PRIZES, \n" +
				"    scpt.SCRATCH_CARD_PRIZE_NAME SCRATCH_CARD_PRIZE_NAME \n" + 
				"	from SCRATCH_CARD_CONFIG scc \n" + 
				"	join SCRATCH_CARD_PRIZE_TYPES scpt on scc.scratch_card_prize_typeid = scpt.scratch_card_prize_type_id \n" + 
				"	where SCRATCH_CARD_PROMOTIONID  = :promotionId \n" + 
				"	and is_active = 1 \n" + 
				"	order by scc.CHANCE_PERCENTAGE desc"
				;
		SqlParameterSource paramMap = new MapSqlParameterSource().addValue("promotionId", promotionId);
		try {
			return jdbcTemplate.query(configQuery, paramMap, new RowMapper<ScratchCardConfig>(){
				@Override
				public ScratchCardConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
					ScratchCardConfig config = new ScratchCardConfig(); 
					config.setId(rs.getInt("SCRATCH_CARD_CONFIGID"));
					config.setPrizeTypeId(rs.getString("SCRATCH_CARD_PRIZE_TYPEID"));
					config.setPrizeName(rs.getString("SCRATCH_CARD_PRIZE_NAME"));
					config.setAmount(rs.getInt("AMOUNT"));
					config.setChancePercentage(rs.getFloat("CHANCE_PERCENTAGE"));
					config.setCreateDate(rs.getDate("CREATE_DATE"));
					config.setValidityHours(rs.getInt("VALIDITY_HOURS"));
					config.setCurrentPrizeCount(rs.getInt("CURRENT_PRIZE_COUNT"));
					config.setTotalPrizes(rs.getInt("TOTAL_PRIZES"));
					return config;
				}
			});
		}catch (Exception e) {
			log.error(e);
			return new ArrayList<>();
		}
	}

	
	private List<MinDepositConfig> getMinDepositConfig(int promotionId){
		String configQuery = "SELECT * FROM MIN_DEPOSIT_CONFIG WHERE scratch_card_promotionid = :promotionId";
		
		SqlParameterSource paramMap = new MapSqlParameterSource().addValue("promotionId", promotionId);
		try {
			return jdbcTemplate.query(configQuery, paramMap, new RowMapper<MinDepositConfig>(){
				@Override
				public MinDepositConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
					MinDepositConfig config = new MinDepositConfig(); 
					config.setCurrency(rs.getString("CURRENCY"));
					config.setCommonAmount(rs.getFloat("COMMON_PRIZE"));
					config.setLegendaryAmount(rs.getFloat("LEGENDARY_PRIZE"));
					config.setJackpotAmount(rs.getFloat("JACKPOT_PRIZE"));
					return config;
				}
			});
		}catch (Exception e) {
			log.error(e);
			return new ArrayList<>();
		}
	}
	
	@Override
	public int setScratchCardPromotion(ScratchCardPromotionDTO promotionDto){ 
		String query = "Insert into SCRATCH_CARD_PROMOTIONS (SCRATCH_CARD_PROMOTIONID, NAME, START_DATE, END_DATE)  \n" + 
				"	values (SCRATCH_CARD_PROMOTIONS_SEQ.nextval, :name , :startDate, :endDate)";
		
		SqlParameterSource paramMap = new MapSqlParameterSource().addValue("name", promotionDto.getName())
											.addValue("startDate", promotionDto.getStartDate())
											.addValue("endDate", promotionDto.getEndDate());
		KeyHolder holder = new GeneratedKeyHolder();
		try{
			int result =  jdbcTemplate.update(query, paramMap, holder, new String[] {"SCRATCH_CARD_PROMOTIONID" });
			int promotionId =holder.getKey().intValue();
			log.debug("PromotionId: " + promotionId);
			for(ScratchCardConfig config: promotionDto.getConfigs()) {
				config.setPromotionId(promotionId);
			}
			setScratchCardConfig(promotionDto.getConfigs());
			for(MinDepositConfig config: promotionDto.getMinDepositConfigs()) {
				config.setPromotionId(promotionId);
			}
			setScratchCardMinDepositConfig(promotionDto.getMinDepositConfigs());
			return result;
		}catch (Exception e) {
			log.error(e);
			return 0;
		}
	}
	
	public int[] setScratchCardConfig(List<ScratchCardConfig> configs){
		inactivateActiveConfigs();
		
		String query = "INSERT INTO SCRATCH_CARD_CONFIG (SCRATCH_CARD_CONFIGID, SCRATCH_CARD_PROMOTIONID, SCRATCH_CARD_PRIZE_TYPEID, AMOUNT, CHANCE_PERCENTAGE, IS_ACTIVE, TOTAL_PRIZES) \n" + 
				"	VALUES   (SCRATCH_CARD_CONFIG_SEQ.NEXTVAL, :promotionId, :prizeTypeId, :amount, :chancePercentage, 1, :totalPrizes)";
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(configs.toArray());
		try{
			return jdbcTemplate.batchUpdate(query, batch);
		}catch (Exception e) {
			log.error(e);
			return new int[0];
		}
	}
	
	public int[] setScratchCardMinDepositConfig(List<MinDepositConfig> configs){
		
		String minDepositQuery = "INSERT INTO MIN_DEPOSIT_CONFIG\n" + 
				"(MIN_DEPOSIT_CONFIGID, SCRATCH_CARD_PROMOTIONID, CURRENCY, COMMON_PRIZE, LEGENDARY_PRIZE, JACKPOT_PRIZE, CREATE_DATE)\n" + 
				"VALUES(MIN_DEPOSIT_CONFIGID_SEQ.NEXTVAL, :promotionId, :currency, :commonAmount, :legendaryAmount, :jackpotAmount, SYSDATE )\n" ;
		SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(configs.toArray());
		try{
			return jdbcTemplate.batchUpdate(minDepositQuery, batch);
		}catch (Exception e) {
			log.error(e);
			return new int[0];
		}
	}
	
	@Override
	public void deleteAllPromotions() {
		String queryConfig = "DELETE FROM SCRATCH_CARD_CONFIG";
		String queryPromotions = "DELETE FROM SCRATCH_CARD_PROMOTIONS";
		try {
			jdbcTemplate.update(queryConfig, new MapSqlParameterSource());
			jdbcTemplate.update(queryPromotions, new MapSqlParameterSource());
		}catch(Exception e) {
			log.error(e);
		}
	}

	@Override
	public void inactivateActivePromotion() {
		String query = "UPDATE SCRATCH_CARD_PROMOTIONS SET IS_ACTIVE = 0 WHERE IS_ACTIVE = 1";
		try {
			jdbcTemplate.update(query, new MapSqlParameterSource());
		}catch(Exception e) {
			log.error(e);
		}
		
	}

	public void inactivateActiveConfigs() {
		String query = "UPDATE SCRATCH_CARD_CONFIG SET IS_ACTIVE = 0 WHERE IS_ACTIVE = 1";
		try {
			jdbcTemplate.update(query, new MapSqlParameterSource());
		}catch(Exception e) {
			log.error(e);
		}
		
	}
	
	@Override
	public void deletePromotion(int promotionId) {
		String queryConfig = "DELETE FROM SCRATCH_CARD_CONFIG where SCRATCH_CARD_PROMOTIONID = :promotionId";
		String queryPromotions = "DELETE FROM SCRATCH_CARD_PROMOTIONS where SCRATCH_CARD_PROMOTIONID = :promotionId";
		SqlParameterSource paramMap = new MapSqlParameterSource().addValue("promotionId", promotionId);
		try {
			jdbcTemplate.update(queryConfig, paramMap);
			jdbcTemplate.update(queryPromotions, paramMap);
		}catch(Exception e) {
			log.error(e);
		}
		
	}

	@Override
	public List<ScratchCardWinner> winners(ScratchCardWinnersRequest request) {
		String queryWinners = "SELECT\n" + 
				"    m.real_nickname,\n" + 
				"    m.external_playerid,\n" + 
				"    scw.prize_typeid,\n" + 
				"    scpt.scratch_card_prize_name,\n" + 
				"    scw.amount,\n" + 
				"    scw.create_date\n" + 
				"FROM\n" + 
				"         scratch_card_winners scw\n" + 
				"    JOIN members                   m ON scw.memberid = m.memberid\n" + 
				"    JOIN scratch_card_prize_types  scpt ON scw.prize_typeid = scpt.scratch_card_prize_type_id\n" + 
				"WHERE\n" + 
				"        scw.create_date > :fromDate\n" + 
				"    AND scw.create_date <= :toDate + 1\n" + 
				"ORDER BY\n" + 
				"    create_date DESC";
	
		String queryWinnersLegAndJackpot = "SELECT\n" + 
				"    m.real_nickname,\n" + 
				"    m.external_playerid,\n" + 
				"    scw.prize_typeid,\n" + 
				"    scpt.scratch_card_prize_name,\n" + 
				"    scw.amount,\n" + 
				"    scw.create_date\n" + 
				"FROM\n" + 
				"         scratch_card_winners scw\n" + 
				"    JOIN members                   m ON scw.memberid = m.memberid\n" + 
				"    JOIN scratch_card_prize_types  scpt ON scw.prize_typeid = scpt.scratch_card_prize_type_id\n" + 
				"WHERE\n" + 
				"        scw.create_date > :fromDate\n" + 
				"    AND scw.create_date <= :toDate + 1\n" + 
				"    AND prize_typeid IN (5, 6, 7, 8)\n" + 
				"ORDER BY\n" + 
				"    create_date DESC";
		String query;
		
		if(request.getOnlyLegAndJackpot()) {
			query = queryWinnersLegAndJackpot;
		}else {
			query = queryWinners;
		}
		SqlParameterSource paramMap = new MapSqlParameterSource().addValue("fromDate", request.getStartDate())
																.addValue("toDate", request.getEndDate());

		try {
			return jdbcTemplate.query(query, paramMap, new RowMapper<ScratchCardWinner>(){
				@Override
				public ScratchCardWinner mapRow(ResultSet rs, int rowNum) throws SQLException {
					ScratchCardWinner winner = new ScratchCardWinner(); 
					winner.setName(rs.getString("real_nickname"));
					winner.setExternalPlayerId(rs.getString("external_playerid"));
					winner.setPrizeTypeId(rs.getString("prize_typeid"));
					winner.setPrizeName(rs.getString("scratch_card_prize_name"));
					winner.setAmount(rs.getFloat("amount"));
					winner.setCreateDate(rs.getDate("create_date"));
					return winner;
				}
			});
		}catch (Exception e) {
			log.error(e);
			return new ArrayList<>();
		}
	}
}
