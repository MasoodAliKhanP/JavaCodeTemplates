package biz.digitalhouse.integration.v3.web.ws.oldSoap.casinoGameAPI;

import biz.digitalhouse.integration.v3.ServiceResponseCode;
import biz.digitalhouse.integration.v3.exceptions.CommonServiceException;
import biz.digitalhouse.integration.v3.services.brand.BrandManager;
import biz.digitalhouse.integration.v3.services.casinoGames.CasinoGameManager;
import biz.digitalhouse.integration.v3.services.casinoGames.dto.CasinoGameInfoDTO;
import biz.digitalhouse.integration.v3.web.ws.WsAbstractAPI;
import biz.digitalhouse.integration.v3.web.ws.old.dto.casinoGameAPI.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

/**
 * Created by vitaliy.babenko
 * on 16.01.2015.
 */
@Endpoint
public class OldCasinoGameAPIEndpoint extends WsAbstractAPI {

    private final Log log = LogFactory.getLog(getClass());
    private CasinoGameManager casinoGameManager;

    @Autowired
    public OldCasinoGameAPIEndpoint(BrandManager brandManager, CasinoGameManager casinoGameManager) {
        super(brandManager);
        this.casinoGameManager = casinoGameManager;
    }

    @SuppressWarnings("unused")
    @PayloadRoot(localPart = "GetCasinoGamesRequest", namespace = "http://gametechclubs.biz/casino/game/external/schemas")
    @ResponsePayload
    public GetCasinoGamesResponse getCasinoGames(@RequestPayload GetCasinoGamesRequest req) {
        GetCasinoGamesResponse res = new GetCasinoGamesResponse();
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
            res.setError(ServiceResponseCode.OK.getCode());
            res.setDescription(ServiceResponseCode.OK.getDescription());
        } catch (CommonServiceException e) {
            res.setError(e.getResponse().getCode());
            res.setDescription(e.getResponse().getDescription());
        } catch (Exception e) {
            log.error(e.getMessage(), e);

            res.setError(ServiceResponseCode.INTERNAL_ERROR.getCode());
            res.setDescription(ServiceResponseCode.INTERNAL_ERROR.getDescription());
        }
        return res;
    }

}
