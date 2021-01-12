package biz.digitalhouse.integration.v3.services.bingoGame.dto;

/**
 * @author Vitalii Babenko
 *         on 04.03.2016.
 */
public class BaseResponseDTO {

    protected String error;
    protected String description;

    public void setError(String error) {
        this.error = error;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getError() {
        return error;
    }

    public String getDescription() {
        return description;
    }
}
