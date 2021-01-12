package biz.digitalhouse.integration.v3.services.userservice;

/**
 * @author vitalii.babenko
 * created: 05.04.2018 13:05
 */
public class CreateExternalPlayerResponse implements BaseResponse {

    private int error;
    private String description;
    private Long memberID;

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

    public Long getMemberID() {
        return memberID;
    }

    public void setMemberID(Long memberID) {
        this.memberID = memberID;
    }

    @Override
    public String toString() {
        return "CreateExternalPlayerResponse{" +
                "error=" + error +
                ", description='" + description + '\'' +
                ", memberID=" + memberID +
                '}';
    }
}
