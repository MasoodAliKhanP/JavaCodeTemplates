package biz.digitalhouse.integration.v3.services.currency;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biz.digitalhouse.integration.v3.dao.CurrencyDAO;

@Service
public class CurrencyManagerImpl implements CurrencyManager {
	private final Log log = LogFactory.getLog(CurrencyManagerImpl.class);
	private CurrencyDAO currencyDAO;
	
	@Autowired
	public CurrencyManagerImpl(CurrencyDAO currencyDAO) {
		this.currencyDAO = currencyDAO;
	}

//	@Override
	private double getExchangeRate(final long dateMillis, final long memberID) {
		final double exchangeRate = currencyDAO.getExchangeRate(dateMillis, memberID);
		
		if (log.isDebugEnabled()) {
			log.debug("Exchange rate of currency of player " + memberID + " is: " + exchangeRate);
		}
		
		return exchangeRate;
	}
}
