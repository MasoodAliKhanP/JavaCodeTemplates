package biz.digitalhouse.integration.v3.services.externalClient;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * Created by vitaliy.babenko
 * on 03.08.2017.
 */
public interface ExternalSiteServiceClient {


    @FormUrlEncoded
    @POST
    Call<List<Long>> getRoomIDsByPlayer(@Url String url, @Field("playerID") String playerID);
}
