package biz.digitalhouse.integration.v3.web.ws.http.scratchCardAPI.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * @author masood
 *
 * 07-Jan-2021
 */

@Data
public class BaseRequest {
	@NotNull(message = "Identifier is mandatory")
    private String identifier;
	@NotNull(message = "Password is mandatory")
    private String password;
}
