package biz.digitalhouse.integration.v3.services.userservice;

/**
 * @author vitalii.babenko
 * created: 06.04.2018 10:52
 */
public class UpdateExternalPlayerRequest {

    private String nickname;
    private String email;
    private String firstName;
    private String lastName;
    private String language;

    public UpdateExternalPlayerRequest(String nickname, String email, String firstName, String lastName, String language) {
        this.nickname = nickname;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.language = language;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public String toString() {
        return "UpdateExternalPlayerRequest{" +
                "nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
