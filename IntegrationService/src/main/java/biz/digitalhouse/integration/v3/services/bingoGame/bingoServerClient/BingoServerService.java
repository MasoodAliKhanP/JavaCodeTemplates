package biz.digitalhouse.integration.v3.services.bingoGame.bingoServerClient;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by vitaliy.babenko
 * on 28.10.2016.
 */
public interface BingoServerService {

    @POST("getRoomList")
    Call<GetRoomListResponse> getRoomList(@Body GetRoomListRequest request);

    @POST("getAvailableGames")
    Call<GetAvailableGamesResponse> getAvailableGames(@Body GetAvailableGamesRequest request);

    @POST("preorderCards")
    Call<PreorderCardsResponse> preorderCards(@Body PreorderCardsRequest request);

    @POST("getPreorderReport")
    Call<GetPreorderReportResponse> getPreorderReport(@Body GetPreorderReportRequest request);

    @POST("cancelPreorderCards")
    Call<CancelPreorderCardsResponse> cancelPreorderCards(@Body CancelPreorderCardsRequest request);

    @POST("getBingoJackpots")
    Call<GetBingoJackpotsResponse> getBingoJackpots(@Body GetBingoJackpotsRequest request);

    @POST("getBingoHistory")
    Call<GetBingoHistoryResponse> getBingoHistory(@Body GetBingoHistoryRequest request);
}
