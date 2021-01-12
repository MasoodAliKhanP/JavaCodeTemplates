package biz.digitalhouse.integration.v3.services.freeRoundsBonus;

import biz.digitalhouse.integration.v3.exceptions.CommonServiceException;
import biz.digitalhouse.integration.v3.services.freeRoundsBonus.dto.InfoFRBonus;

import java.util.Calendar;
import java.util.List;

/**
 * @author Vitalii Babenko
 *         on 28.02.2016.
 */
public interface FreeRoundsBonusManager {

    String awardFRB(long brandId, String externalPlayerId, String currency, String nickname, String gamesList, int rounds,
                    String bonusCode, double coinValue, Calendar expirationDate) throws Exception;

    String checkFRB(long brandId, String bonusCode) throws CommonServiceException;

    void cancelFRB(long brandId, String bonusId) throws CommonServiceException;

    List<InfoFRBonus> infoFRB(long brandId, String externalPlayerId) throws Exception;

	List<String[]> getFreeRoundsApplicableGames(long brandId, String technology) throws CommonServiceException;
}
