package biz.digitalhouse.integration.v3.web.ws.http.internalAPIEndpoint.dto;

public class CreatePlaySessionRequest extends BaseRequest {

    private long playerID;
    private int vipLevel;
    private String gameSymbol;
    private String incomingRoundID;
    private Long originalRoundID;
    private String bonusCode;

    public long getPlayerID() {
        return playerID;
    }

    public void setPlayerID(long value) {
        this.playerID = value;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int value) {
        this.vipLevel = value;
    }

    public String getGameSymbol() {
        return gameSymbol;
    }

    public void setGameSymbol(String value) {
        this.gameSymbol = value;
    }

    public String getIncomingRoundID() {
        return incomingRoundID;
    }

    public void setIncomingRoundID(String value) {
        this.incomingRoundID = value;
    }

    public Long getOriginalRoundID() {
        return originalRoundID;
    }

    public void setOriginalRoundID(Long value) {
        this.originalRoundID = value;
    }

    public String getBonusCode() {
        return bonusCode;
    }

    public void setBonusCode(String value) {
        this.bonusCode = value;
    }

}
