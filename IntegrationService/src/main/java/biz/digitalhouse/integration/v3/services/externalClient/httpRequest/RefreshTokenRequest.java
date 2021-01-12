package biz.digitalhouse.integration.v3.services.externalClient.httpRequest;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
    private ClientDetail clientDetails;
    
    public RefreshTokenRequest(String refreshToken, String partnerID) {
        this.refreshToken = refreshToken;
        this.clientDetails = new ClientDetail(partnerID, null);
    }
}
