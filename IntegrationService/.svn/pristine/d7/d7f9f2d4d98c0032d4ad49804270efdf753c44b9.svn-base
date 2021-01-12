package biz.digitalhouse.integration.v3.services.bingoGame.dto;

import java.util.Calendar;

/**
 * @author Vitalii Babenko
 *         on 04.03.2016.
 */
public class CancelPreorderedDTO extends BaseResponseDTO {

    private Calendar preorderDate;
    private long roundID;

    public CancelPreorderedDTO(Calendar preorderDate, long roundID) {
        this.preorderDate = preorderDate;
        this.roundID = roundID;
    }

    public Calendar getPreorderDate() {
        return preorderDate;
    }

    public long getRoundID() {
        return roundID;
    }

    @Override
    public String toString() {
        return "CancelPreorderedDTO{" +
                "preorderDate=" + preorderDate +
                ", roundID=" + roundID +
                "} " + super.toString();
    }
}
