package biz.digitalhouse.integration.v3.services.externalClient.httpResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import biz.digitalhouse.integration.v3.exceptions.InvalidTokenException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Masood
 */

@Data
public class BaseAPIResponse {
    private final Log log = LogFactory.getLog(getClass());

	private String status;
	private String errorCode;
	private Integer errorId;
	private String errorDescription;
	private String errorDesc;
	
	public boolean checkStatus() throws InvalidTokenException {

        if ("SUCCESS".equals(this.getStatus())) {
            return true;
        } else if ("INVALID_REQUEST".equalsIgnoreCase(this.getErrorCode()) && "Invalid token".equalsIgnoreCase(this.getErrorDesc())) {
            if(log.isWarnEnabled()) {
                log.warn("Returned Invalid token.");
            }
            throw new InvalidTokenException();
        }

        if (log.isWarnEnabled()) {
            log.warn("Response failed. " + this);
        }
        return false;
    }
}
