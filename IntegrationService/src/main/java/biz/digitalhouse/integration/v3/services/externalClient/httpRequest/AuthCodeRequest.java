package biz.digitalhouse.integration.v3.services.externalClient.httpRequest;

/**
 * Created by pathan masood
 * on dec 2020.
 */


public class AuthCodeRequest {

    private String grantType;
    private ClientDetail clientDetails;

    public AuthCodeRequest(String grantType, String partnerID, String secretCode) {
        this.grantType = grantType;
        this.clientDetails = new ClientDetail(partnerID, secretCode);
    }
}
