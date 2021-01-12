package biz.digitalhouse.integration.v3.utils;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * @author kirill.arbuzov
 * @created 13/04/2018
 */
public class BingoJdbcDaoSupport extends JdbcDaoSupport{

    public BingoJdbcDaoSupport(){
        super();
    }

    @Override
    protected void initTemplateConfig() {
        getJdbcTemplate().setFetchSize(50);
    }
}
