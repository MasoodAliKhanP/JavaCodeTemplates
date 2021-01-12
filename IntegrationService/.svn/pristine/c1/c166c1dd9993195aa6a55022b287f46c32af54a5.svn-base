package biz.digitalhouse.integration.v3.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitalii Babenko
 *         on 04.03.2016.
 */
public class BingoRoundStatistic {

    private long roundId;
    private List<PlayerCards> playersCards;

    public BingoRoundStatistic(long roundId) {
        this.roundId = roundId;
    }

    public long getRoundId() {
        return roundId;
    }

    public List<PlayerCards> getPlayersCards() {
        if(playersCards == null) {
            playersCards = new ArrayList<>();
        }
        return playersCards;
    }

    @Override
    public String toString() {
        return "BingoRoundStatistic{" +
                "roundId=" + roundId +
                ", playersCards=" + playersCards +
                '}';
    }
}
