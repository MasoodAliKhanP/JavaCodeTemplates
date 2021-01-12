package biz.digitalhouse.integration.v3.services.externalClient;

import biz.digitalhouse.integration.v3.services.externalClient.httpRequest.RealCashRequest;
import biz.digitalhouse.integration.v3.services.externalClient.httpRequest.SpecialBonusRequest;
import biz.digitalhouse.integration.v3.services.externalClient.httpResponse.RealCashResponse;
import biz.digitalhouse.integration.v3.services.externalClient.httpResponse.SpecialBonusResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by vitaliy.babenko
 * on 05.01.2017.
 */
public interface DataClient {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("bonus/offerSpecialBonus")
    Call<SpecialBonusResponse> offerSpecialBonus(@Header("accessToken") String accessToken, @Body SpecialBonusRequest request);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("fund/doTransaction")
    Call<RealCashResponse> realCash(@Header("accessToken") String accessToken, @Body RealCashRequest request);
}
