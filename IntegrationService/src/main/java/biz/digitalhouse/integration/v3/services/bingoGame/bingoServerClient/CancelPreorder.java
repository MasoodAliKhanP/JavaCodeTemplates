package biz.digitalhouse.integration.v3.services.bingoGame.bingoServerClient;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by vitaliy.babenko
 * on 31.10.2016.
 */
public class CancelPreorder implements Serializable {

    private Calendar preorderDate;
    private long roundID;
    private String error;
    private String description;

    public CancelPreorder() {
    }

    public CancelPreorder(Calendar preorderDate, long roundID, String error, String description) {
        this.preorderDate = preorderDate;
        this.roundID = roundID;
        this.error = error;
        this.description = description;
    }

    public Calendar getPreorderDate() {
        return preorderDate;
    }

    public void setPreorderDate(Calendar preorderDate) {
        this.preorderDate = preorderDate;
    }

    public long getRoundID() {
        return roundID;
    }

    public void setRoundID(long roundID) {
        this.roundID = roundID;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CancelPreorder{" +
                "preorderDate=" + preorderDate +
                ", roundID=" + roundID +
                ", error='" + error + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
