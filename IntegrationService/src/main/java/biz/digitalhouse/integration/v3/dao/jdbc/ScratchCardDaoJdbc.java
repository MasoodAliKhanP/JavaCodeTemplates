package biz.digitalhouse.integration.v3.dao.jdbc;

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
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import biz.digitalhouse.integration.v3.dao.ScratchCardDao;
import biz.digitalhouse.integration.v3.model.BonusDetails;
import biz.digitalhouse.integration.v3.web.ws.http.scratchCardAPI.dto.ScratchCardClaimedWinner;
import biz.digitalhouse.integration.v3.web.ws.http.scratchCardAPI.dto.ScratchCardClaimedWinnersRequest;

@Repository
public class ScratchCardDaoJdbc extends JdbcDaoSupport implements ScratchCardDao {
	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public ScratchCardDaoJdbc(DataSource dataSource) {
		setDataSource(dataSource);
	}

	@Override
	public BonusDetails getBonusDetails(String bonusType) {
		String query = "SELECT BONUS_DETAILSID, BONUSID, ISSUE_MULTIPLIER, BONUS_TYPE"
						+ " FROM BONUS_DETAILS WHERE IS_ACTIVE = 1 AND BONUS_TYPE = :bonusType";

		try {
			return jdbcTemplate.queryForObject(query, new MapSqlParameterSource().addValue("bonusType", bonusType), new RowMapper<BonusDetails>() {
				@Override
				public BonusDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
					BonusDetails bd = new BonusDetails();
					bd.setBonusDetailsId(rs.getInt("BONUS_DETAILSID"));
					bd.setBonusId(rs.getString("BONUSID"));
					bd.setIssueMultiplier(rs.getInt("ISSUE_MULTIPLIER"));
					bd.setBonusType(rs.getString("BONUS_TYPE"));
					return bd;
				}
			});
		} catch (EmptyResultDataAccessException e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public void updateScratchCardWinClaim(String externalReference, long memberId, long promotionId, String prizeTypeId, double amount) {
		String query = "Insert into SCRATCH_CARD_CLAIMS (SCRATCH_CARD_CLAIMID, SCRATCH_CARD_PROMOTIONID, EXTERNAL_REFERENCEID, MEMBERID, PRIZE_TYPEID, AMOUNT) \n" + 
				"values (SQ_SCRATCH_CARD_CLAIMID.nextval, :scratchCardPromotionId, :externalReference, :memberId, :prizeTypeId, :amount)";
		
		
		SqlParameterSource paramMap = new MapSqlParameterSource().addValue("scratchCardPromotionId", promotionId)
													.addValue("externalReference", externalReference)
													.addValue("memberId",memberId)
													.addValue("prizeTypeId",prizeTypeId)
													.addValue("amount",amount);
		
		try {
			jdbcTemplate.update(query, paramMap);
		}catch (Exception e) {
			log.error(e);
		}
	}

	@Override
	public List<ScratchCardClaimedWinner> winners(ScratchCardClaimedWinnersRequest request) {
		String query = "    SELECT\n" + 
				"        m.external_playerid,\n" + 
				"        scc.scratch_card_promotionid,\n" + 
				"        scpt.scratch_card_prize_name,\n" + 
				"        scc.amount         AS prize_amount,\n" + 
				"        scc.create_date    AS win_date\n" + 
				"    FROM\n" + 
				"             scratch_card_claims scc\n" + 
				"        JOIN members                   m ON scc.memberid = m.memberid\n" + 
				"        JOIN scratch_card_prize_types  scpt ON scc.prize_typeid = scpt.scratch_card_prize_type_id\n" + 
				"    WHERE\n" + 
				"            m.external_playerid = :externalPlayerId\n" + 
				"        AND scc.scratch_card_promotionid = :scratchCardPromotionId\n"
				;
		
		SqlParameterSource paramMap = new MapSqlParameterSource().addValue("externalPlayerId", request.getExternalPlayerId())
				.addValue("scratchCardPromotionId", request.getPromotionId());

		try {
			return jdbcTemplate.query(query, paramMap, new RowMapper<ScratchCardClaimedWinner>() {
				@Override
				public ScratchCardClaimedWinner mapRow(ResultSet rs, int rowNum) throws SQLException {
					ScratchCardClaimedWinner winner = new ScratchCardClaimedWinner();
					winner.setPromotionId(rs.getInt("scratch_card_promotionid"));
					winner.setExternalPlayerId(rs.getString("external_playerid"));
					winner.setPrizeName(rs.getString("scratch_card_prize_name"));
					winner.setPrizeAmount(rs.getFloat("prize_amount"));
					winner.setWinDate(rs.getDate("win_date"));
					return winner;
				}
			});
		} catch (Exception e) {
			log.error(e);
			return new ArrayList<>();
		}
	}

}
