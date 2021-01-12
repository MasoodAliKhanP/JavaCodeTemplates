package biz.digitalhouse.integration.v3.dao;

import java.util.List;

/**
 * @author Vitalii Babenko
 *         on 05.03.2016.
 */
public interface GeneralSettingDAO {


    String KEY_AUTH_URL = "AUTH.URL";
    String KEY_AUTH_PARTNER_ID = "AUTH.PARTNER_ID";
    String KEY_AUTH_LABEL_ID = "AUTH.LABEL_ID";
    String KEY_AUTH_SECRET_CODE = "AUTH.SECRET_CODE";
    String KEY_AUTH_GRANT_TYPE = "AUTH.GRANT_TYPE";
    String KEY_SCRATCH_CARD_WIN_URL = "SCRATCH_CARD_WIN.URL";

    String getGeneralSettings(String key, Long brandID);

    String getGeneralSettingsOrEmpty(String key, Long brandID);

    int getIntGeneralSettings(String key, Long brandID);

    List<Long> getBrands();
    
    String getValue(String key, long brandId);
}
