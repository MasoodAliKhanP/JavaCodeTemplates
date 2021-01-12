package biz.digitalhouse.integration.v3.dao.jdbc;

import biz.digitalhouse.integration.v3.dao.TechnicalBreakDAO;
import biz.digitalhouse.integration.v3.utils.BingoJdbcDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * @author Vitalii Babenko
 *         on 04.08.2016.
 */
@Repository
public class TechnicalBreakDAOJdbc extends BingoJdbcDaoSupport implements TechnicalBreakDAO {

    @Autowired
    public TechnicalBreakDAOJdbc(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public boolean isTechnicalBreak() {
        return getJdbcTemplate().queryForObject("select Technical_Break_Pkg.Is_Technical_Break from dual", Integer.class) > 0;
    }
}
