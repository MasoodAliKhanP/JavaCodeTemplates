package biz.digitalhouse.integration.v3.model;

import java.io.Serializable;

/**
 * @author Vitalii Babenko
 *         on 01.04.2016.
 */
public class Balance implements Serializable {

    private long playerId;
    private String vendorId;
    private double cash;
    private double bonus;

    public Balance() {
    }

    public Balance(long playerId, String vendorId, double cash, double bonus) {
        this.playerId = playerId;
        this.vendorId = vendorId;
        this.cash = cash;
        this.bonus = bonus;
    }

    public long getPlayerId() {
        return playerId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public double getCash() {
        return cash;
    }

    public double getBonus() {
        return bonus;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "playerId=" + playerId +
                ", vendorId='" + vendorId + '\'' +
                ", cash=" + cash +
                ", bonus=" + bonus +
                '}';
    }
}
