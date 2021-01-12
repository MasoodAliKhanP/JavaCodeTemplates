package biz.digitalhouse.integration.v3.services.externalClient;

import biz.digitalhouse.integration.v3.services.externalClient.httpRequest.AuthCodeRequest;
import biz.digitalhouse.integration.v3.services.externalClient.httpRequest.RefreshTokenRequest;
import biz.digitalhouse.integration.v3.services.externalClient.httpRequest.TokenRequest;
import biz.digitalhouse.integration.v3.services.externalClient.httpResponse.AuthCodeResponse;
import biz.digitalhouse.integration.v3.services.externalClient.httpResponse.RefreshTokenResponse;
import biz.digitalhouse.integration.v3.services.externalClient.httpResponse.TokenResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by pathan.masood
 * on 2020.
 */
public interface AuthClient {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("authorize")
    Call<AuthCodeResponse> authorize(@Body AuthCodeRequest req);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("getToken")
    Call<TokenResponse> getToken(@Body TokenRequest req);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("refreshToken")
    Call<RefreshTokenResponse> refreshToken(@Body RefreshTokenRequest req);
}
