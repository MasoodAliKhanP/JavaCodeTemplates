package biz.digitalhouse.integration.v3.services.userservice;

/**
 * @author vitalii.babenko
 * created: 06.04.2018 10:53
 */
public class UpdateExternalPlayerResponse implements BaseResponse {

    private int error;
    private String description;

    public int getError() {
        return error;
    }

    public void setError(int error) {
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
        return "UpdateExternalPlayerResponse{" +
                "error=" + error +
                ", description='" + description + '\'' +
                '}';
    }
}
