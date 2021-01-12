package biz.digitalhouse.integration.v3.services.externalClient.oldHttpResponse;

/**
 * <p>Class: RefundResponse</p>
 * <p>Description: </p>
 *
 * @author Sergey Miliaev
 */

public class RefundResponse extends BaseTransactionalResponse {

    private Double cash;
    private Double bonus;
	private String currency;
	
	public String getCurrency(){
		return this.currency;
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
        return "RefundResponse{} " +
                "currency='" + currency + '\'' +
                ", cash=" + cash +
                ", bonus=" + bonus +
				super.toString();
    }
}
