package biz.digitalhouse.integration.v3.managers;

import biz.digitalhouse.integration.v3.exceptions.InvalidTokenException;

/**
 * Created by pathan.masood
 * on dec 2020.
 */
public interface AuthTokenManager {

    String getToken(Long brandID) throws InvalidTokenException;

    void removeToken(Long brandID);
}
