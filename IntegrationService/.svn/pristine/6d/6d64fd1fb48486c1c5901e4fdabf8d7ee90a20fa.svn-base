package biz.digitalhouse.integration.v3.services.bingoGame.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitalii Babenko
 *         on 04.03.2016.
 */
public class BingoGameHistoryDTO {

    private String gameName;
    private long roundID;
    private String bingoCalls;
    private int bingoType;
    private List<BingoWinnerDTO> bingoWinners;

    public BingoGameHistoryDTO(String gameName, long roundID, String bingoCalls, int bingoType) {
        this.gameName = gameName;
        this.roundID = roundID;
        this.bingoCalls = bingoCalls;
        this.bingoType = bingoType;
    }

    public String getGameName() {
        return gameName;
    }

    public long getRoundID() {
        return roundID;
    }

    public String getBingoCalls() {
        return bingoCalls;
    }

    public int getBingoType() {
        return bingoType;
    }

    public List<BingoWinnerDTO> getBingoWinners() {
        if (bingoWinners == null) {
            bingoWinners = new ArrayList<>();
        }
        return bingoWinners;
    }

    @Override
    public String toString() {
        return "BingoGameHistoryDTO{" +
                "gameName='" + gameName + '\'' +
                ", roundID=" + roundID +
                ", bingoCalls='" + bingoCalls + '\'' +
                ", bingoType=" + bingoType +
                ", bingoWinners=" + bingoWinners +
                '}';
    }
}
