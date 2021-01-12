package biz.digitalhouse.integration.v3.services.freeRoundsBonus.dto;

/**
 * Created by Vitalii Babenko
 * on 13.01.2016.
 */
public class ResultCheckFRBonus extends BaseObjectFRBonus {

    private String externalBonusId;
    private long freeRoundBonusID;

    public String getExternalBonusId() {
        return externalBonusId;
    }

    public void setExternalBonusId(String externalBonusId) {
        this.externalBonusId = externalBonusId;
    }
    
    public long getFreeRoundBonusID() {
        return freeRoundBonusID;
    }

    public void setFreeRoundBonusID(long createdFreeRoundID) {
        this.freeRoundBonusID = createdFreeRoundID;
    }
    @Override
    public String toString() {
        return "ResultCheckFRBonus{" +
                "externalBonusId='" + externalBonusId + '\'' +
                "} " + super.toString();
    }
}
