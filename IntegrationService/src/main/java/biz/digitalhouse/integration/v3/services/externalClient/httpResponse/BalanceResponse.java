package biz.digitalhouse.integration.v3.services.externalClient.httpResponse;


/**
 * @author Vitalii Babenko
 *         on 01.04.2016.
 */
public class BalanceResponse extends BaseResponse {

    private Double cash;
    private Double bonus;

    public Double getCash() {
        return cash;
    }

    public void setCash(Double cash) {
        this.cash = cash;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    @Override
    public String toString() {
        return "BalanceResponse{" +
                "cash=" + cash +
                ", bonus=" + bonus +
                "} " + super.toString();
    }
}
