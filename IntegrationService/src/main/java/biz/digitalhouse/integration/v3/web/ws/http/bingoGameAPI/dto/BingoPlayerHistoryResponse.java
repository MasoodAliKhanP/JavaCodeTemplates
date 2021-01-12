package biz.digitalhouse.integration.v3.web.ws.http.bingoGameAPI.dto;

import java.util.List;

/**
 * Created by arbuzov
 * on 20.03.2018.
 */
public class BingoPlayerHistoryResponse extends BaseResponse {

    private List<PlayerHistoryRound> rounds;
    private Long count;


    public List<PlayerHistoryRound> getRounds() {
        return rounds;
    }

    public void setRounds(List<PlayerHistoryRound> rounds) {
        this.rounds = rounds;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "BingoPlayerHistoryResponse{" +
                "rounds=" + rounds +
                ", count=" + count +
                '}';
    }
}
