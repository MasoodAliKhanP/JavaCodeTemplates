package biz.digitalhouse.integration.v3.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author kirill.arbuzov
 * @created 26/03/2018
 */
public class HashValidator {
    private static Log log = LogFactory.getLog(EncodeUtil.class);

    public static boolean checkRequestHash(String securePassword, String requestData, String hash){

        String calculatedHash = EncodeUtil.md5(requestData + securePassword);
        boolean res = calculatedHash.equals(hash);
        if (!res){
            if (log.isWarnEnabled()){
                log.warn("Invalid hash! original: '" + hash + "', calculated '" + calculatedHash + "'");
            }
        }
        return res;
    }

}
