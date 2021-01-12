package biz.digitalhouse.integration.v3.services.casinoGames;

import biz.digitalhouse.integration.v3.exceptions.CommonServiceException;
import biz.digitalhouse.integration.v3.services.casinoGames.dto.CasinoGameInfoDTO;
import biz.digitalhouse.integration.v3.services.casinoGames.dto.JackpotInfoDTO;
import biz.digitalhouse.integration.v3.services.casinoGames.dto.PlaySessionDTO;

import java.util.Date;
import java.util.List;

/**
 * @author Vitalii Babenko
 *         on 29.02.2016.
 */
public interface CasinoGameManager {

    List<CasinoGameInfoDTO> getCasinoGames(long brandId) throws CommonServiceException;
    
    List<CasinoGameInfoDTO> getCasinoGamesByType(long brandId,String type) throws CommonServiceException;
    
    List<PlaySessionDTO> getPlaySessions(long brandId, String extPlayerId, String gameSymbol, Date date) throws Exception;
    
	List<JackpotInfoDTO> getAllJackpotsForCasino(long casinoID) throws Exception;

	double getExchangeRate(String currencySymbol) throws Exception;
}

