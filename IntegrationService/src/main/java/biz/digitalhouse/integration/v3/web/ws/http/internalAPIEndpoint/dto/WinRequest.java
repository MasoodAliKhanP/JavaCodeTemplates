package biz.digitalhouse.integration.v3.web.ws.http.internalAPIEndpoint.dto;

public class WinRequest extends BaseRequest {

    private String transactionID;
    private long playerID;
    private String gameSymbol;
    private String roundID;
    private double win;
    private Boolean gamble;
    private String bonusCode;
    private Boolean endRound;
    private String originalRoundID;

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String value) {
        this.transactionID = value;
    }

    public long getPlayerID() {
        return playerID;
    }

    public void setPlayerID(long value) {
        this.playerID = value;
    }

    public String getGameSymbol() {
        return gameSymbol;
    }

    public void setGameSymbol(String value) {
        this.gameSymbol = value;
    }


    public String getRoundID() {
        return roundID;
    }

    public void setRoundID(String value) {
        this.roundID = value;
    }

    public double getWin() {
        return win;
    }

    public void setWin(double value) {
        this.win = value;
    }

    public Boolean isGamble() {
        return gamble;
    }

    public void setGamble(Boolean value) {
        this.gamble = value;
    }

    public String getBonusCode() {
        return bonusCode;
    }

    public void setBonusCode(String value) {
        this.bonusCode = value;
    }

    public Boolean isEndRound() {
        return endRound;
    }

    public void setEndRound(Boolean value) {
        this.endRound = value;
    }

    public String getOriginalRoundID() {
        return originalRoundID;
    }

    public void setOriginalRoundID(String value) {
        this.originalRoundID = value;
    }

}
