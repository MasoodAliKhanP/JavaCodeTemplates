package biz.digitalhouse.integration.v3.web.ws.http.internalAPIEndpoint.dto;

import java.io.Serializable;

import biz.digitalhouse.integration.v3.services.externalClient.httpResponse.BaseAPIResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScratchCardWinResponse extends BaseAPIResponse  implements Serializable{

	private static final long serialVersionUID = 1L;

	public ScratchCardWinResponse() {
	}
	
}
