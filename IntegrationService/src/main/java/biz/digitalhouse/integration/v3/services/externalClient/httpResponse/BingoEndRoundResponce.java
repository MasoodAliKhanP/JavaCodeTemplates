package biz.digitalhouse.integration.v3.services.externalClient.httpResponse;

/**
 * @author Vitalii Babenko
 *         on 01.04.2016.
 */
public class BingoEndRoundResponce extends BaseResponse {

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
        return "BingoEndRoundResponce{" +
                "cash=" + cash +
                ", bonus=" + bonus +
                "} " + super.toString();
    }
}
