package biz.digitalhouse.integration.v3.services.bingoGame.bingoServerClient;

import biz.digitalhouse.integration.v3.services.bingoGame.dto.JackpotDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by vitaliy.babenko
 * on 31.10.2016.
 */
public class GetBingoJackpotsResponse implements Serializable {

    private String error;
    private String description;
    private List<JackpotDTO> jackpotList;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<JackpotDTO> getJackpotList() {
        return jackpotList;
    }

    public void setJackpotList(List<JackpotDTO> jackpotList) {
        this.jackpotList = jackpotList;
    }

    @Override
    public String toString() {
        return "GetBingoJackpotsResponse{" +
                "error='" + error + '\'' +
                ", description='" + description + '\'' +
                ", jackpotList=" + jackpotList +
                '}';
    }
}
