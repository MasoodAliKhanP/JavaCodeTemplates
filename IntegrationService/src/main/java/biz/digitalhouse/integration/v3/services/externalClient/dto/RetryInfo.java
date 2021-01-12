package biz.digitalhouse.integration.v3.services.externalClient.dto;

import biz.digitalhouse.integration.v3.GeneralSettingKey;
import biz.digitalhouse.integration.v3.dao.GeneralSettingDAO;
import biz.digitalhouse.integration.v3.utils.ConfUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Edited by arbuzov on 20/04/2016.
 */
public class RetryInfo {
    private static final Log log = LogFactory.getLog(RetryInfo.class);

    private int count;
    private int delay;

    public RetryInfo(GeneralSettingDAO generalSettingDAO, long casinoID) {
        this(generalSettingDAO, casinoID, GeneralSettingKey.REQUEST_RETRY_DELAY_BETWEEN_ATTEMPTS,
                GeneralSettingKey.REQUEST_RETRY_MAX_ATTEMPT_NUMBER);
    }

    public RetryInfo(GeneralSettingDAO generalSettingDAO, long casinoID, GeneralSettingKey delayKey, GeneralSettingKey countKey) {
        count = ConfUtils.getIntValue(casinoID, 0, countKey, generalSettingDAO);
        delay = ConfUtils.getIntValue(casinoID, 0, delayKey, generalSettingDAO);
    }

    public boolean isRetryConfigured() {
        if (count > 0 && delay > 0) {
            return true;
        } else if (log.isWarnEnabled()) {
            log.warn(String.format("Property '%s' or '%s' are not configurated, or value is less than or equals 0.",
                    GeneralSettingKey.REQUEST_RETRY_MAX_ATTEMPT_NUMBER.key(),
                    GeneralSettingKey.REQUEST_RETRY_DELAY_BETWEEN_ATTEMPTS.key()));
        }
        return false;
    }

    public int getCount() {
        return count;
    }

    public int getDelay() {
        return delay;
    }
}