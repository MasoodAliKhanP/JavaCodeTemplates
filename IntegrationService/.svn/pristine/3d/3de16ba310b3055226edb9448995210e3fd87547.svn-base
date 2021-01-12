package biz.digitalhouse.integration.v3.utils;

import biz.digitalhouse.integration.v3.GeneralSettingKey;
import biz.digitalhouse.integration.v3.dao.GeneralSettingDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

/**
 * @author Vitalii Babenko
 *         on 18.04.2016.
 */
public class ConfUtils {

    private static final Log log = LogFactory.getLog(ConfUtils.class);
    private static final String SEND_PLATFORM = "SendPlatform";
    private static final String SEND_BONUS_CODE = "SendBonusCode";
    private static final String SEND_END_ROUND = "SendEndRound";

    public static int getIntValue(long brand, GeneralSettingKey key, GeneralSettingDAO dao) {
        Assert.notNull(key);
        Assert.notNull(dao);

        return Integer.parseInt(dao.getValue(key.key(), brand));
    }

    public static int getIntValue(long brand, int defaultValues, GeneralSettingKey key, GeneralSettingDAO dao) {
        Assert.notNull(key);
        Assert.notNull(dao);
        String res = null;
        try {
            res =  dao.getValue(key.key(), brand);
            if(res != null) {
                return Integer.parseInt(res);
            }
        } catch (Exception e) {
            log.error(String.format("General settings value[%s] by key[%s] and brad[%d] incorrect. %s", res, key.key(), brand, e.getMessage()));
        }
        if(log.isDebugEnabled()) {
            log.debug("General settings result is null or incorrect int value. Returned default value[" + defaultValues + "]");
        }
        return defaultValues;
    }

    public static boolean isTransferPlatformParameter(long brandID, GeneralSettingDAO dao) {
        String configurations = dao.getValue(GeneralSettingKey.OPERATOR_CONFIGURATIONS.key(), brandID);
        return null != configurations && configurations.contains(SEND_PLATFORM);

    }

    public static boolean isTransferBonusCode(long brandID, GeneralSettingDAO dao) {
        String configurations = dao.getValue(GeneralSettingKey.OPERATOR_CONFIGURATIONS.key(), brandID);
        return null != configurations && configurations.contains(SEND_BONUS_CODE);
    }

    public static boolean isSendEndRound(long brandID, GeneralSettingDAO dao) {
        String configurations = dao.getValue(GeneralSettingKey.OPERATOR_CONFIGURATIONS.key(), brandID);
        return null != configurations && configurations.contains(SEND_END_ROUND);
    }
}
