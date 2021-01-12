package biz.digitalhouse.integration.v3.web.ws.http.bingoGameAPI.dto;

/**
 * Created by vitaliy.babenko
 * on 28.04.2017.
 */
public class AvailableGamesRequest extends BaseRequest {

    protected String playerID;
    protected long dateTime;

    @Override
    protected String getStringHashBuild() {
        return playerID + dateTime;
    }

}
