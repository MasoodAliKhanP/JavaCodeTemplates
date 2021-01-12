package biz.digitalhouse.integration.v3.services.bingoGame.bingoServerClient;

import java.io.Serializable;
import java.util.List;

/**
 * Created by vitaliy.babenko
 * on 31.10.2016.
 */
public class GetBingoHistoryResponse implements Serializable {

    private String error;
    private String description;
    private String gameName;
    private int bingoType;
    private long roundID;
    private String bingoCalls;
    private List<BingoWinner> bingoWinners;

    public String getError() {
        return error;
    }

    public String getDescription() {
        return description;
    }

    public String getGameName() {
        return gameName;
    }

    public int getBingoType() {
        return bingoType;
    }

    public long getRoundID() {
        return roundID;
    }

    public String getBingoCalls() {
        return bingoCalls;
    }

    public List<BingoWinner> getBingoWinners() {
        return bingoWinners;
    }

    @Override
    public String toString() {
        return "GetBingoHistoryResponse{" +
                "error='" + error + '\'' +
                ", description='" + description + '\'' +
                ", gameName='" + gameName + '\'' +
                ", bingoType=" + bingoType +
                ", roundID=" + roundID +
                ", bingoCalls='" + bingoCalls + '\'' +
                ", bingoWinners=" + bingoWinners +
                '}';
    }
}
