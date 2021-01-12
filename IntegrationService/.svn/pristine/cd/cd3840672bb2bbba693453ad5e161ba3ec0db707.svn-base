package biz.digitalhouse.integration.v3.services.externalClient.httpRequest;

import lombok.Data;

@Data
public class TokenRequest {

	private String authCode;
	private String grantType;
	private ClientDetail clientDetails;

	public TokenRequest(String authCode, String grantType, String partnerID) {
		this.authCode = authCode;
		this.grantType = grantType;
		this.clientDetails = new ClientDetail(partnerID, null);
	}
}
