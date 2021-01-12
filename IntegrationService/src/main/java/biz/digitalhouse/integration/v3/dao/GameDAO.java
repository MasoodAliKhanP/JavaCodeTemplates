package biz.digitalhouse.integration.v3.dao;

import java.util.List;

import biz.digitalhouse.integration.v3.model.Game;
import biz.digitalhouse.integration.v3.services.casinoGames.dto.JackpotInfoDTO;

/**
 * @author Vitalii Babenko
 *         on 15.04.2016.
 */
public interface GameDAO {

    Long findGameID(String vendorId, String externalGameId);

    Game getGame(long gameId);

    Game getGameBySymbol(String gameSymbol);

    boolean isGameIsActiveForBrand(long brandId, long gameId);

    long getGameIdByFreeRoundId(String bonusCode, String vendorId);

	List<JackpotInfoDTO> getAllJackpotsForCasino(long casinoID);
	
	double getExchangeRate(String currencySymbol);
}
