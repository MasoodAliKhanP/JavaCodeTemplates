package biz.digitalhouse.integration.v3.services.externalClient.oldHttpResponse;

import java.util.Map;

/**
* <p>Class: AuthorizeResponse</p>
* <p>Description: </p>
*
* @author Sergey Miliaev
*/
public class AuthorizeResponse extends BaseResponse {
    private String userId;
    private String currency;
    private Double cash;
    private Double bonus;
    private String token;
    private String nickname;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getCash() {
        return cash;
    }

    public void setCash(Double cash) {
        this.cash = cash;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "AuthorizeResponse{" +
                "userId='" + userId + '\'' +
                ", currency='" + currency + '\'' +
                ", cash=" + cash +
                ", bonus=" + bonus +
                ", token='" + token + '\'' +
                ", nickname='" + nickname + '\'' +
                "} " + super.toString();
    }
}
