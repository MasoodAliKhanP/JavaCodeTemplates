package biz.digitalhouse.integration.v3.web.ws.http.bingoGameAPI.dto;

import java.util.Date;

/**
 * Created by arbuzov
 * on 20.03.2018.
 */
public class BingoPlayerHistoryRequest extends BaseRequest {

    private String playerID;
    private Date startDate;
    private Date endDate;
    private long fromIndex;
    private int pageOffset;
    private String language;

    public String getPlayerID() {
        return playerID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public long getFromIndex() {
        return fromIndex;
    }

    public int getPageOffset() {
        return pageOffset;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    protected String getStringHashBuild() {
        return playerID + startDate + endDate + fromIndex + pageOffset + language;
    }

    @Override
    public String toString() {
        return "TransactionReportRequest{" +
                "playerID='" + playerID + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", fromIndex=" + fromIndex +
                ", pageOffset=" + pageOffset +
                ", language=" + language +
                "} " + super.toString();
    }
}
