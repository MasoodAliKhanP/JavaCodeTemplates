package biz.digitalhouse.integration.v3.web.ws.soap.casinoGameAPI;

import biz.digitalhouse.integration.v3.ServiceResponseCode;
import biz.digitalhouse.integration.v3.exceptions.CommonServiceException;
import biz.digitalhouse.integration.v3.services.brand.BrandManager;
import biz.digitalhouse.integration.v3.services.casinoGames.CasinoGameManager;
import biz.digitalhouse.integration.v3.services.casinoGames.dto.CasinoGameInfoDTO;
import biz.digitalhouse.integration.v3.web.ws.WsAbstractAPI;
import biz.digitalhouse.integration.v3.web.ws.dto.casinoGameAPI.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

/**
 * @author Vitalii Babenko
 *         on 29.02.2016.
 */
@Endpoint
public class CasinoGameAPIEndpoint extends WsAbstractAPI {

    private final static String NAMESPACE_URI = "http://gametechclubs.biz/casino/external/schemas";
    private final Log log = LogFactory.getLog(getClass());
    private CasinoGameManager casinoGameManager;

    @Autowired
    public CasinoGameAPIEndpoint(BrandManager brandManager, CasinoGameManager casinoGameManager) {
        super(brandManager);
        this.casinoGameManager = casinoGameManager;
    }

    @SuppressWarnings("unused")
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "casinoGamesRequest")
    @ResponsePayload
    public CasinoGamesResponse casinoGames(@RequestPayload CasinoGamesRequest req) {
        CasinoGamesResponse res = new CasinoGamesResponse();
        try {
            long brandId = check(req.getSecureLogin(), req.getSecurePassword());
            List<CasinoGameInfoDTO> result = casinoGameManager.getCasinoGames(brandId);
            for(CasinoGameInfoDTO dto : result) {
                CasinoGame game = new CasinoGame();
                game.setGameID(dto.getGameSymbol());
                game.setGameName(dto.getGameName());
                game.setTechnology(dto.getGameTechnology());
                res.getGameList().add(game);
            }
            if(log.isDebugEnabled()) {
                log.debug(result);
            }
            putMessage(res, ServiceResponseCode.OK);
        } catch (CommonServiceException e) {
            putException(res, e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            putMessage(res, ServiceResponseCode.INTERNAL_ERROR);
        }
        return res;
    }

    private void putMessage(BaseResponse res, ServiceResponseCode en) {
        res.setError(en.getCode());
        res.setDescription(en.getDescription());
    }

    private void putException(BaseResponse res, CommonServiceException e) {
        res.setError(e.getResponse().getCode());
        res.setDescription(e.getResponse().getDescription());
    }
}
