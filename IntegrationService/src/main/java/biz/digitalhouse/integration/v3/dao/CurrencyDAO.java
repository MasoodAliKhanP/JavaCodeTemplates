package biz.digitalhouse.integration.v3.dao;

/**
 * @author Vitalii Babenko
 *         on 28.02.2016.
 */
public interface CurrencyDAO {

	Double getExchangeRate(long dateMillis, Long memberID);
}
