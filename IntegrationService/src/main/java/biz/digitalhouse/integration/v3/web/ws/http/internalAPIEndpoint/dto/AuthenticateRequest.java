package biz.digitalhouse.integration.v3.web.ws.http.internalAPIEndpoint.dto;

public class AuthenticateRequest extends BaseRequest {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String value) {
        this.token = value;
    }

    @Override
    public String toString() {
        return "AuthenticateRequest{" +
                "token='" + token + '\'' +
                '}';
    }
}
