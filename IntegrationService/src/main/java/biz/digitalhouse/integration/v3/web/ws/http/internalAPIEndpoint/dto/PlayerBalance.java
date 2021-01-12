package biz.digitalhouse.integration.v3.web.ws.http.internalAPIEndpoint.dto;

public class PlayerBalance {

    private long playerID;
    private double cash;
    private double bonus;

    public long getPlayerID() {
        return playerID;
    }

    public void setPlayerID(long value) {
        this.playerID = value;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double value) {
        this.cash = value;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double value) {
        this.bonus = value;
    }

}
