package biz.digitalhouse.integration.v3.services.userservice;

import retrofit2.Call;
import retrofit2.http.*;

/**
 * @author vitalii.babenko
 * created: 11.04.2018 10:30
 */
public interface UserServiceClient {

    @GET("PlayerAPI/getExternalPlayerByExtID/{brandID}/{externalPlayerID}/")
    Call<GetExternalPlayerResponse> getExternalPlayerByExtID(@Path("brandID") long brandID, @Path("externalPlayerID") String externalPlayerID);

    @GET("PlayerAPI/getExternalPlayer/{playerID}/")
    Call<GetExternalPlayerResponse> getExternalPlayer(@Path("playerID") long playerID);

    @POST("PlayerAPI/createExternalPlayer/{brandID}/{externalPlayerID}/")
    Call<CreateExternalPlayerResponse> createExternalPlayer(@Path("brandID") long brandID, @Path("externalPlayerID") String externalPlayerID, @Body CreateExternalPlayerRequest request);

    @POST("PlayerAPI/updateExternalPlayer/{playerID}/")
    Call<UpdateExternalPlayerResponse> updateExternalPlayer(@Path("playerID") long playerID, @Body UpdateExternalPlayerRequest request);

    @POST("PlayerAPI/banPlayerByExtID/{brandID}/{externalPlayerID}/")
    Call<BanPlayerResponse> banPlayerByExtID(@Path("brandID") long brandID, @Path("externalPlayerID") String externalPlayerID, @Query("ban") Boolean ban);

}
