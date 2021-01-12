package biz.digitalhouse.integration.v3.web.ws.http.internalAPIEndpoint.dto;

public class BetRequest extends BaseRequest {

    private String transactionID;
    private double bet;
    private long playerID;
    private String roundID;
    private String gameSymbol;
    private Boolean gamble;
    private String bonusCode;
    private String originalRoundID;


    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String value) {
        this.transactionID = value;
    }

    public double getBet() {
        return bet;
    }

    public void setBet(double value) {
        this.bet = value;
    }

    public long getPlayerID() {
        return playerID;
    }

    public void setPlayerID(long value) {
        this.playerID = value;
    }

    public String getRoundID() {
        return roundID;
    }

    public void setRoundID(String value) {
        this.roundID = value;
    }

    public String getGameSymbol() {
        return gameSymbol;
    }

    public void setGameSymbol(String value) {
        this.gameSymbol = value;
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

    public String getOriginalRoundID() {
        return originalRoundID;
    }

    public void setOriginalRoundID(String value) {
        this.originalRoundID = value;
    }

}
