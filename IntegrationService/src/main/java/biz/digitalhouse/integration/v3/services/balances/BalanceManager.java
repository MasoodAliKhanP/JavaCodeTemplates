package biz.digitalhouse.integration.v3.services.balances;

import biz.digitalhouse.integration.v3.model.Balance;

import java.util.List;

/**
 * @author Vitalii Babenko
 *         on 01.04.2016.
 */
public interface BalanceManager {

    Balance putBalance(Balance balance);

    Balance getBalance(long brandId, long playerId, String vendorId);
}
