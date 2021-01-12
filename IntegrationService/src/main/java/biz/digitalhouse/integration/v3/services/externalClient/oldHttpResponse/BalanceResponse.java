package biz.digitalhouse.integration.v3.services.externalClient.oldHttpResponse;

/**
 * @author vitaliy.babenko
 *         created: 15.08.2014 17:09
 */
public class BalanceResponse extends BaseResponse {

    private String currency;
    private Double cash;
    private Double bonus;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

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
        return "BalanceResponse [currency=" + currency + ", cash=" + cash + ", bonus=" + bonus + ", error=" + error
                + ", description=" + description + ", age=" + age + "]";
    }
}
