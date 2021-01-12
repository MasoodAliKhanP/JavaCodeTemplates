package biz.digitalhouse.integration.v3.services.freeRoundsBonus.dto;

/**
 * Created by Vitalii Babenko
 * on 13.01.2016.
 */
public abstract class BaseObjectFRBonus {

    private int status;
    private String description;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "BaseObjectFRBonus{" +
                "status=" + status +
                ", description='" + description + '\'' +
                '}';
    }
}
