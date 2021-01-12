package biz.digitalhouse.integration.v3.managers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biz.digitalhouse.integration.v3.dao.GeneralSettingDAO;
import biz.digitalhouse.integration.v3.exceptions.InvalidTokenException;
import biz.digitalhouse.integration.v3.services.externalClient.AuthClient;
import biz.digitalhouse.integration.v3.services.externalClient.httpRequest.AuthCodeRequest;
import biz.digitalhouse.integration.v3.services.externalClient.httpRequest.RefreshTokenRequest;
import biz.digitalhouse.integration.v3.services.externalClient.httpRequest.TokenRequest;
import biz.digitalhouse.integration.v3.services.externalClient.httpResponse.AuthCodeResponse;
import biz.digitalhouse.integration.v3.services.externalClient.httpResponse.RefreshTokenResponse;
import biz.digitalhouse.integration.v3.services.externalClient.httpResponse.TokenResponse;
import biz.digitalhouse.integration.v3.utils.TimeUtils;
import retrofit2.Response;

/**
 * Created by Masood
 * on 2020.
 */
@Service
public class AuthTokenManagerImpl implements AuthTokenManager {

    private final Log log = LogFactory.getLog(getClass());

    private Map<Long, Long> expiresIn = new HashMap<>();
    private Map<Long, String> accessToken = new HashMap<>();
    private Map<Long, String> refreshToken = new HashMap<>();

    private GeneralSettingDAO generalSettingsDAO;
    private ClientManager clientManager;

    @Autowired
    public AuthTokenManagerImpl(GeneralSettingDAO generalSettingsDAO, ClientManager clientManager) {
        this.generalSettingsDAO = generalSettingsDAO;
        this.clientManager = clientManager;
    }

    public String getToken(Long brandID) throws InvalidTokenException {
        try {

            String grantType = generalSettingsDAO.getGeneralSettings(GeneralSettingDAO.KEY_AUTH_GRANT_TYPE, brandID);
            String partnerID = generalSettingsDAO.getGeneralSettings(GeneralSettingDAO.KEY_AUTH_PARTNER_ID, brandID);
            String secretCode = generalSettingsDAO.getGeneralSettings(GeneralSettingDAO.KEY_AUTH_SECRET_CODE, brandID);
            AuthClient authClient = clientManager.getAuthClient(brandID);

            if (!accessToken.containsKey(brandID)) {

                Response<AuthCodeResponse> authorize = authClient.authorize(new AuthCodeRequest(grantType, partnerID, secretCode)).execute();
                if (!authorize.isSuccessful()) {
                    if (log.isWarnEnabled()) {
                        log.warn(authorize.errorBody().string());
                    }
                    return null;
                }

                AuthCodeResponse authorizeResp = authorize.body();
                if (!authorizeResp.checkStatus()) {
                    return null;
                }

                Response<TokenResponse> token = authClient.getToken(new TokenRequest(authorizeResp.getAuthCode(), grantType, partnerID)).execute();

                if (!authorize.isSuccessful()) {
                    if (log.isWarnEnabled()) {
                        log.warn(authorize.errorBody().string());
                    }
                    return null;
                }

                TokenResponse tokenResp = token.body();
                if (!tokenResp.checkStatus()) {
                    return null;
                }

                accessToken.put(brandID, tokenResp.getAccessToken());
                refreshToken.put(brandID, tokenResp.getRefreshToken());
                expiresIn.put(brandID, TimeUtils.requestTime() + tokenResp.getExpiresIn() * 1000);

            }
            else if (expiresIn.get(brandID) <= TimeUtils.requestTime()) {

                Response<RefreshTokenResponse> rt = authClient.refreshToken(new RefreshTokenRequest(refreshToken.get(brandID), partnerID)).execute();

                if (!rt.isSuccessful()) {
                    if (log.isWarnEnabled()) {
                        log.warn(rt.errorBody().string());
                    }
                    return null;
                }

                RefreshTokenResponse refreshResp = rt.body();
                if (!refreshResp.checkStatus()) {
                    accessToken.remove(brandID);
                    return getToken(brandID);
                }

                accessToken.put(brandID, refreshResp.getAccessToken());
                expiresIn.put(brandID, TimeUtils.requestTime() + refreshResp.getExpiresIn() * 1000);
            }

            return accessToken.get(brandID);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void removeToken(Long brandID) {
        try {
            accessToken.remove(brandID);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
