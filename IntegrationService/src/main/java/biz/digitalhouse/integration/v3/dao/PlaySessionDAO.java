package biz.digitalhouse.integration.v3.dao;

import biz.digitalhouse.integration.v3.model.PlaySession;

import java.util.Date;
import java.util.List;

/**
 * Created by maxim.zhukovskiy on 13/01/2017.
 */
public interface PlaySessionDAO {

   List<PlaySession> getPlaySession(Long brandId, Long playerId, Long gameId, Date date) throws Exception;

   List<PlaySession> getArcPlaySession(Long brandId, Long playerId, Long gameId, Date date) throws Exception;
}
