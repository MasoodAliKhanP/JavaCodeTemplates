package biz.digitalhouse.integration.v3.web.ws.http.casinoGameAPI.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxim.zhukovskiy on 16/01/2017.
 */
public class PlaySessionsResponse extends BaseResponse {

    private List<PlaySession> playSessionList = new ArrayList<>();

    public List<PlaySession> getPlaySessionList() {
        return playSessionList;
    }

    public void setPlaySessionList(List<PlaySession> playSessionList) {
        this.playSessionList = playSessionList;
    }

    public static class PlaySession {

        public PlaySession(Long playSessionID, String extPlayerID, String game, String currency, Double betAmount, Double winAmount, String created) {
            this.playSessionID = playSessionID;
            this.extPlayerID = extPlayerID;
            this.game = game;
            this.currency = currency;
            this.betAmount = betAmount;
            this.winAmount = winAmount;
            this.created = created;
        }

        private Long playSessionID;
        private String extPlayerID;
        private String game;
        private String currency;
        private Double betAmount;
        private Double winAmount;
        private String created;

        public Long getPlaySessionID() {
            return playSessionID;
        }

        public void setPlaySessionID(Long playSessionID) {
            this.playSessionID = playSessionID;
        }

        public String getExtPlayerID() {
            return extPlayerID;
        }

        public void setExtPlayerID(String extPlayerID) {
            this.extPlayerID = extPlayerID;
        }

        public String getGame() {
            return game;
        }

        public void setGame(String game) {
            this.game = game;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public Double getBetAmount() {
            return betAmount;
        }

        public void setBetAmount(Double betAmount) {
            this.betAmount = betAmount;
        }

        public Double getWinAmount() {
            return winAmount;
        }

        public void setWinAmount(Double winAmount) {
            this.winAmount = winAmount;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        @Override
        public String toString() {
            return "PlaySession{" +
                    "playSessionID=" + playSessionID +
                    ", extPlayerID='" + extPlayerID + '\'' +
                    ", game='" + game + '\'' +
                    ", currency='" + currency + '\'' +
                    ", betAmount=" + betAmount +
                    ", winAmount=" + winAmount +
                    ", created=" + created +
                    '}';
        }
    }
}
