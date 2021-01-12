package biz.digitalhouse.integration.v3.dao.jdbc;

import biz.digitalhouse.integration.v3.GeneralSettingKey;
import biz.digitalhouse.integration.v3.dao.BrandDAO;
import biz.digitalhouse.integration.v3.model.Brand;
import biz.digitalhouse.integration.v3.utils.BingoJdbcDaoSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Vitalii Babenko
 *         on 28.02.2016.
 */
@Repository
public class BrandDAOJdbc extends BingoJdbcDaoSupport implements BrandDAO {

    private final Log log = LogFactory.getLog(getClass());
    private BrandRowMapper mapper = new BrandRowMapper();

    @Autowired
    public BrandDAOJdbc(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    @Cacheable("getBrandByIdentifier")
    public Brand getBrand(String identifier) {
        try {
            String sql = "SELECT c.casinoID, c.prefix, " +
                    " (select gs_P.VALUE from general_settings gs_P where gs_P.customerid IS NULL AND gs_p.name = '" + GeneralSettingKey.OPERATOR_PASS.key() + "' AND gs_P.casinoid = c.casinoid) pass, " +
                    " gs_L.VALUE as login" +
                    "  FROM casino c, general_settings gs_L" +
                    " WHERE     c.deleted = 0" +
                    "   AND c.casinoid = gs_L.casinoid" +
                    "   AND gs_L.customerid IS NULL" +
                    "   AND gs_L.name = '" + GeneralSettingKey.OPERATOR_LOGIN.key() + "' " +
                    "   AND gs_L.VALUE = ?";

            return getJdbcTemplate().queryForObject(sql
                    , mapper, identifier);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    @Cacheable("getAllExternalBrandIDs")
    public List<Long> getAllExternalBrandIDs() {
        return getJdbcTemplate().queryForList("SELECT casinoID FROM casino WHERE deleted = 0 AND external_brand = 1", Long.class);
    }

    private class BrandRowMapper implements RowMapper<Brand> {
        @Override
        public Brand mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Brand(
                    resultSet.getLong("casinoID"),
                    resultSet.getString("login"),
                    resultSet.getString("pass"),
                    resultSet.getString("prefix")
            );
        }
    }
}
