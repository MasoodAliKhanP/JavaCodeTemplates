package biz.digitalhouse.integration.v3.services.casinoGames;

import biz.digitalhouse.gamemanagement.webservice.fromWSDL.GameManagementService;
import biz.digitalhouse.gamemanagement.webservice.fromWSDL.GamesListRequest;
import biz.digitalhouse.gamemanagement.webservice.fromWSDL.GamesListResponse;
import biz.digitalhouse.integration.v3.ServiceResponseCode;
import biz.digitalhouse.integration.v3.dao.GameDAO;
import biz.digitalhouse.integration.v3.dao.PlaySessionDAO;
import biz.digitalhouse.integration.v3.exceptions.CommonServiceException;
import biz.digitalhouse.integration.v3.model.Game;
import biz.digitalhouse.integration.v3.model.PlaySession;
import biz.digitalhouse.integration.v3.model.Player;
import biz.digitalhouse.integration.v3.services.casinoGames.dto.CasinoGameInfoDTO;
import biz.digitalhouse.integration.v3.services.casinoGames.dto.PlaySessionDTO;
import biz.digitalhouse.integration.v3.services.players.PlayerManager;
import biz.digitalhouse.integration.v3.utils.StringUtils;
import biz.digitalhouse.integration.v3.services.casinoGames.dto.JackpotInfoDTO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Vitalii Babenko
 *         on 29.02.2016.
 */
@Service
public class CasinoGameManagerImpl implements CasinoGameManager {

    private final Log log = LogFactory.getLog(getClass());
    private GameManagementService service;
    private PlaySessionDAO playSessionDAO;
    private PlayerManager playerManager;
    private GameDAO gameDAO;

    @Autowired
    public CasinoGameManagerImpl(GameManagementService service, PlaySessionDAO playSessionDAO, PlayerManager playerManager, GameDAO gameDAO) {
        this.service = service;
        this.playSessionDAO = playSessionDAO;
        this.playerManager = playerManager;
        this.gameDAO = gameDAO;
    }

    @Cacheable("getCasinoGames")
    public List<CasinoGameInfoDTO> getCasinoGames(long brandId) throws CommonServiceException {

        try {
            GamesListRequest gamesListRequest = new GamesListRequest();
            gamesListRequest.setCasinoID(brandId);
            GamesListResponse response = service.gamesList(gamesListRequest);

            if (response == null) {
                log.error("Error while access to Game Management Service! GamesListResponse is null");
                throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
            }

            if (!"0".equals(response.getError())) {
                log.error("Game Management Service error! Error: " + response.getError() + " Description: " + response.getDescription());
                throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
            }

            List<CasinoGameInfoDTO> result = new ArrayList<CasinoGameInfoDTO>();
            for (biz.digitalhouse.gamemanagement.webservice.fromWSDL.Game game : response.getGameList()) {
                result.add(new CasinoGameInfoDTO(game.getName(), game.getGameID(), game.getTechnologiesList()));
            }
            return result;
        } catch (CommonServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
        }
    }
    
    @Cacheable("getCasinoGamesByType")
    public List<CasinoGameInfoDTO> getCasinoGamesByType(long brandId,String type) throws CommonServiceException {

        try {
            GamesListRequest gamesListRequest = new GamesListRequest();
            gamesListRequest.setCasinoID(brandId);
            gamesListRequest.setGameType(type);
            GamesListResponse response = service.gamesList(gamesListRequest);

            if (response == null) {
                log.error("Error while access to Game Management Service! GamesListResponse is null");
                throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
            }

            if (!"0".equals(response.getError())) {
                log.error("Game Management Service error! Error: " + response.getError() + " Description: " + response.getDescription());
                throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
            }

            List<CasinoGameInfoDTO> result = new ArrayList<CasinoGameInfoDTO>();
            for (biz.digitalhouse.gamemanagement.webservice.fromWSDL.Game game : response.getGameList()) {
                result.add(new CasinoGameInfoDTO(game.getName(), game.getGameID(), game.getTechnologiesList()));
            }
            return result;
        } catch (CommonServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
        }
    }

    @Override
    public List<PlaySessionDTO> getPlaySessions(long brandId, String extPlayerId, String gameSymbol, Date date) throws Exception {
        List<PlaySession> playSessions;
        Long playerId = null;
        Long gameId = null;

        if (StringUtils.isNotEmpty(extPlayerId)) {
            Player player = playerManager.getPlayer(brandId, extPlayerId);
            if (player == null) {
                throw ServiceResponseCode.PLAYER_NOT_FOUND.EXCEPTION();
            }
            playerId = player.getPlayerID();
        }

        if (StringUtils.isNotEmpty(gameSymbol)) {
            Game game = gameDAO.getGameBySymbol(gameSymbol);
            if (game == null) {
                throw ServiceResponseCode.GAME_FORBIDDEN.EXCEPTION();
            }
            gameId = game.getGameID();
        }

        Calendar archive = Calendar.getInstance();
        archive.add(Calendar.MONTH, -1);

        if (date != null && date.before(archive.getTime())) {
            playSessions = playSessionDAO.getArcPlaySession(brandId, playerId, gameId, date);
        } else {
            playSessions = playSessionDAO.getPlaySession(brandId, playerId, gameId, date);
        }

        List<PlaySessionDTO> playSessionDTOList = new ArrayList<>();

        if (playSessions != null) {
            final TimeZone est = TimeZone.getTimeZone("EST");
            final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.setTimeZone(est);

            for (PlaySession playSession : playSessions) {
                PlaySessionDTO playSessionDTO = new PlaySessionDTO();
                playSessionDTO.setPlaySessionID(playSession.getPlayerSessionID());
                playSessionDTO.setExtPlayerID(playSession.getExtMemberID());
                playSessionDTO.setGameSymbol(playSession.getGameSymbol());
                playSessionDTO.setCurrency(playSession.getCurrency());
                playSessionDTO.setBetAmount(playSession.getTotalBet());
                playSessionDTO.setWinAmount(playSession.getTotalWin());
                playSessionDTO.setCreated(dateFormat.format(playSession.getPlayerSessionDate()));
                playSessionDTOList.add(playSessionDTO);
            }
        }

        return playSessionDTOList;
    }
    
    public List<JackpotInfoDTO> getAllJackpotsForCasino(long casinoID) throws Exception {
        try {
        	return gameDAO.getAllJackpotsForCasino(casinoID);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
        }
    }

    @Override
    public double getExchangeRate(String currencySymbol) throws Exception{
    	try {
        	return gameDAO.getExchangeRate(currencySymbol);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw ServiceResponseCode.INTERNAL_ERROR.EXCEPTION();
        }
    }
}

