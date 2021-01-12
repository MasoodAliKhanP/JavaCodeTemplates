package biz.digitalhouse.integration.v3.dao;

import biz.digitalhouse.integration.v3.model.BingoTransactionReport;
import biz.digitalhouse.integration.v3.web.ws.http.bingoGameAPI.dto.PlayerHistoryRound;

import java.util.Date;
import java.util.List;

/**
 * Created by vitaliy.babenko
 * on 20.06.2017.
 */
public interface BingoTransactionReportDAO {

    long transactionReportCount(long brandID, String playerID, Date startDate, Date endDate, String transactionType);

    List<BingoTransactionReport> transactionReport(long brandID, String playerID, Date startDate, Date endDate, String transactionType, long fromIndex, int pageOffset);

    Integer bingoPlayerHistoryCount(long brandID, String playerID, Date startDate, Date endDate, String language);

    List<PlayerHistoryRound> bingoPlayerHistory(long brandID, String playerID, Date startDate, Date endDate, long fromIndex, int pageOffset, String language);

}
