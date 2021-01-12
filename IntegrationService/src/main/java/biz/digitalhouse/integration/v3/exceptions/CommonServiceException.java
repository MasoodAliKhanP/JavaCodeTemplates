package biz.digitalhouse.integration.v3.exceptions;

import biz.digitalhouse.integration.v3.ServiceResponseCode;

/**
 * @author Vitalii Babenko
 *         on 28.02.2016.
 */
public class CommonServiceException extends Exception {

    protected ServiceResponseCode responseCode;

    public CommonServiceException(ServiceResponseCode responseCode) {
        super(null, null, true, false);
        this.responseCode = responseCode;
    }

    public ServiceResponseCode getResponse() {
        return responseCode;
    }
}
