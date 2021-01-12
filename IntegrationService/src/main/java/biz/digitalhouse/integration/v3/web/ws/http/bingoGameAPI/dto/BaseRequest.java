package biz.digitalhouse.integration.v3.web.ws.http.bingoGameAPI.dto;

import biz.digitalhouse.integration.v3.utils.EncodeUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by vitaliy.babenko
 * on 15.06.2017.
 */
public abstract class BaseRequest {
    private final Log log = LogFactory.getLog(getClass());

    private String secureLogin;
    private String hash;

    public String getSecureLogin() {
        return secureLogin;
    }

    public void setSecureLogin(String id) {
        this.secureLogin = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public String toString() {
        return "BaseRequest{" +
                "secureLogin='" + secureLogin + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }

    public boolean checkHash(String password) {
        String shouldBe = EncodeUtil.md5(secureLogin + getStringHashBuild() + password);
        boolean res = shouldBe.equals(hash);
        if (!res) {
            log.warn("invalid hash! original: '" + hash + "', should be '" + shouldBe + "'");
        }
        return res;
    }

    protected abstract String getStringHashBuild();
}
