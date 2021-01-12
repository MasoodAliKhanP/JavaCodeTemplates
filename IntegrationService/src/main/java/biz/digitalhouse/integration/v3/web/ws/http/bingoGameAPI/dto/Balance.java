package biz.digitalhouse.integration.v3.web.ws.http.bingoGameAPI.dto;

/**
 * Created by vitaliy.babenko
 * on 15.06.2017.
 */
public class Balance {

    private double cash;
    private double bonus;

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "cash=" + cash +
                ", bonus=" + bonus +
                '}';
    }
}
