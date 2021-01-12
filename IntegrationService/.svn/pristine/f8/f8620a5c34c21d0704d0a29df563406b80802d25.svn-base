package biz.digitalhouse.integration.v3.dao.jdbc;

import biz.digitalhouse.integration.v3.DefaultValues;
import biz.digitalhouse.integration.v3.dao.I18nDAO;
import biz.digitalhouse.integration.v3.utils.BingoJdbcDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

/**
 * @author vitalii.babenko
 * created: 23.10.2017 18:10
 */
@Repository
public class I18nDAOJdbc extends BingoJdbcDaoSupport implements I18nDAO {

    @Autowired
    public I18nDAOJdbc(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public String getRoomSupportedLanguage(long brandID, long roomID, String language) {

        try {
            String result = getJdbcTemplate().queryForObject(
                    "select nvl(cas_lang, def_lang) as languages from (" +
                            "select i18n_pkg.get_casino_value(br.DEFAULT_LANGUAGE_KEY, 'en', c.casinoid) as def_lang," +
                                "(select languageid from casino_languages cc where cc.languageid = lower(?) and " +
                                "cc.casinoid = c.casinoid and instr(br.languages, cc.languageid) > 0) as cas_lang " +
                            "from BINGO_ROOMS br, CASINO c where br.bingo_roomid = ? and c.casinoid = ?)",
                    String.class, language, roomID, brandID);

            if(result == null || result.isEmpty()) {
                return DefaultValues.language;
            }
            return result;
        } catch (EmptyResultDataAccessException e) {
            return DefaultValues.language;
        }
    }

    @Override
    public String getI18nValue(String key, String locale) {
        return getJdbcTemplate().queryForObject("select i18n_pkg.get_value(?,?) from dual", String.class, key, locale);
    }
}
