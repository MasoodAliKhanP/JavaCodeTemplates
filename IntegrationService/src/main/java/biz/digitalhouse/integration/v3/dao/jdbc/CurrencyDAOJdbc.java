package biz.digitalhouse.integration.v3.dao.jdbc;

import biz.digitalhouse.integration.v3.dao.CurrencyDAO;
import biz.digitalhouse.integration.v3.utils.BingoJdbcDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Types;

import javax.sql.DataSource;

/**
 * @author Vitalii Babenko
 *         on 28.02.2016.
 */
@Repository
public class CurrencyDAOJdbc extends BingoJdbcDaoSupport implements CurrencyDAO {

    @Autowired
    public CurrencyDAOJdbc(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
	public Double getExchangeRate(final long dateMillis, final Long memberID) {
    	final Object[] params = { new SqlParameterValue(Types.DATE, new Date(dateMillis)), 
    			new SqlParameterValue(Types.NUMERIC, memberID)};
		return getJdbcTemplate().queryForObject(
				"select currency_pkg.get_exchange_rate(?, m.currencyid) curr from members m where m.memberid = ?",
				params, Double.class);
	}
}
