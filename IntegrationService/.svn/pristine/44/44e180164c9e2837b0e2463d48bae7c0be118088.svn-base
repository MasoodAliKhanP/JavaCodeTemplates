package biz.digitalhouse.integration.v3.dao.jdbc;


import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import biz.digitalhouse.integration.v3.dao.GeneralSettingDAO;
import biz.digitalhouse.integration.v3.utils.BingoJdbcDaoSupport;

/**
 * @author Masood ali khan
 *         on 05.03.2020.
 */
@Repository
public class GeneralSettingDAOJdbc extends BingoJdbcDaoSupport implements GeneralSettingDAO {

    private final Log log = LogFactory.getLog(getClass());

    private static final String queryGeneralSettingValueForCasino =
            "  SELECT a.value " +
                    "    FROM (SELECT gs.* " +
                    "            FROM GENERAL_SETTINGS gs, casino c " +
                    "           WHERE     c.casinoid = ? " +
                    "                 AND NVL (gs.casinoid, c.casinoid) = c.casinoid " +
                    "                 AND NVL (gs.customerid, c.customerid) = c.customerid " +
                    "                 AND gs.name = ? " +
                    "                 AND gs.memberid is null " +
                    "          UNION " +
                    "          SELECT gs.* " +
                    "            FROM GENERAL_SETTINGS gs " +
                    "           WHERE     gs.name = ? " +
                    "                 AND gs.memberid IS NULL " +
                    "                 AND gs.casinoid IS NULL " +
                    "                 AND gs.customerid IS NULL) a " +
                    "ORDER BY a.CASINOID NULLS LAST, " +
                    "         a.CUSTOMERID NULLS LAST";

    @Autowired
    public GeneralSettingDAOJdbc(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    @Cacheable(value = "configuration", unless = "#result == null")
    public String getValue(String key, long brandID) {

//        if(log.isDebugEnabled()) {
//            log.debug(String.format("Load configuration by key[%s] brand[%d]", key, brandID));
//        }

        List<String> list = getJdbcTemplate().queryForList(queryGeneralSettingValueForCasino, String.class, brandID, key, key);
        if (list.isEmpty()){
            if (log.isWarnEnabled()){
                log.warn("key '" + key + "' in the database not found!");
            }
            return null;
        }
        return list.get(0);
    }
    
    @Override
    @Cacheable("generalSettingCache")
    public String getGeneralSettings(String key, Long brandID) {
        if(log.isDebugEnabled()) {
            log.debug("Reload general setting by key[" + key + "] and brand[" + brandID + "]");
        }
        return getJdbcTemplate().queryForObject("SELECT VALUE FROM GENERAL_SETTINGS WHERE NAME = ? AND CASINOID = ?", String.class, key, brandID);
    }

    @Override
    @Cacheable("generalSettingCache")
    public String getGeneralSettingsOrEmpty(String key, Long brandID) {
        if(log.isDebugEnabled()) {
            log.debug("Reload general setting by key[" + key + "] and brand[" + brandID + "]");
        }
        try {
            return getJdbcTemplate().queryForObject("SELECT VALUE FROM GENERAL_SETTINGS WHERE NAME = ? AND CASINOID = ?", String.class, key, brandID);
        } catch (EmptyResultDataAccessException e) {
            return "";
        }
    }

    @Override
    @Cacheable("generalSettingCache")
    public int getIntGeneralSettings(String key, Long brandID) {
        if(log.isDebugEnabled()) {
            log.debug("Reload general setting by key[" + key + "] and brand[" + brandID + "]");
        }
        return Integer.parseInt(getJdbcTemplate().queryForObject("SELECT VALUE FROM GENERAL_SETTINGS WHERE NAME = ? AND CASINOID = ?", String.class, key, brandID));
    }

    @Override
    @Cacheable("brandCache")
    public List<Long> getBrands() {
        return getJdbcTemplate().queryForList("SELECT DISTINCT CASINOID FROM GENERAL_SETTINGS WHERE CASINOID IS NOT NULL AND NAME = ? ", Long.class, KEY_AUTH_PARTNER_ID);
    }

}
