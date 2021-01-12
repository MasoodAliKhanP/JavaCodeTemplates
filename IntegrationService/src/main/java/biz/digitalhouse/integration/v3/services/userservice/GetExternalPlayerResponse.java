package biz.digitalhouse.integration.v3.services.userservice;

/**
 * @author vitalii.babenko
 * created: 06.04.2018 12:03
 */
public class GetExternalPlayerResponse implements BaseResponse {

    private int error;
    private String description;
    private long memberID;
    private long casinoID;
    private String externalPlayerID;
    private String nickname;
    private String email;
    private String firstName;
    private String lastName;
    private String language;
    private String currency;

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

    public long getMemberID() {
        return memberID;
    }

    public void setMemberID(long memberID) {
        this.memberID = memberID;
    }

    public long getCasinoID() {
        return casinoID;
    }

    public void setCasinoID(long casinoID) {
        this.casinoID = casinoID;
    }

    public String getExternalPlayerID() {
        return externalPlayerID;
    }

    public void setExternalPlayerID(String externalPlayerID) {
        this.externalPlayerID = externalPlayerID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "GetExternalPlayerResponse{" +
                "error=" + error +
                ", description='" + description + '\'' +
                ", memberID=" + memberID +
                ", casinoID=" + casinoID +
                ", externalPlayerID='" + externalPlayerID + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", language='" + language + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}
