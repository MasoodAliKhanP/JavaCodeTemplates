package biz.digitalhouse.integration.v3.services.freeRoundsBonus;


import biz.digitalhouse.integration.v3.exceptions.CommonServiceException;
import biz.digitalhouse.integration.v3.services.freeRoundsBonus.dto.*;

/**
 * <p>Class: FRBServiceClient</p>
 * <p>Description: </p>
 *
 * @author Sergey Miliaev
 */

public interface FRBServiceClient {

    ResultAwardFRBonus createFRB(long brandId, long playerId, String bonusCode, int freeRoundsNumber,
                                 String gameSymbols, double coinValue, String expDate) throws CommonServiceException;

    ResultCheckFRBonus checkFRB(long brandId, String bonusCode) throws CommonServiceException;

    ResultCancelFRBonus cancelFRB(long brandId, String bonusId) throws CommonServiceException;

    ResultInfoFRBonus infoFRB(long brandId, long playerId) throws CommonServiceException;

	ResultFreeRoundsGames getFreeRoundsApplicableGames(long brandId, String technology) throws CommonServiceException;
}
