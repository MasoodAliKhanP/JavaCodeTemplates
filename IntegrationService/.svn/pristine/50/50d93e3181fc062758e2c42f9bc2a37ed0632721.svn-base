package biz.digitalhouse.integration.v3.exceptions;

import biz.digitalhouse.integration.v3.ServiceResponseCode;

/**
 * @author Vitalii Babenko
 *         on 29.02.2016.
 */
public class FRBServiceException extends CommonServiceException {

    public FRBServiceException(int code, String message) {
        super(null);
        switch (code) {
            case 1001:
            case 2001:
                responseCode = ServiceResponseCode.EMPTY_MANDATORY_FIELD.setMessage(message);
                break;
            case 1002:
            case 2002:
                responseCode = ServiceResponseCode.TYPE_MISMATCH.setMessage(message);
                break;
            case 1003:
            case 2012:
                responseCode = ServiceResponseCode.INVALID_PARAMETER.setMessage(message);
                break;
            case 1004:
            case 1005:
                responseCode = ServiceResponseCode.PLAYER_NOT_FOUND;
                break;
            case 1006:
            case 2010:
                responseCode = ServiceResponseCode.GAMES_NOT_SUPPORTED.setMessage(message);
                break;
            case 1007:
                responseCode = ServiceResponseCode.FREE_ROUNDS_NOT_SUPPORTED.setMessage(message);
                break;
            case 1008:
            case 2016:
                responseCode = ServiceResponseCode.BONUS_EXISTS;
                break;
            case 1009:
                responseCode = ServiceResponseCode.ACTIVE_FREE_ROUNDS_NOT_FOUND;
                break;
            case 1010:
                responseCode = ServiceResponseCode.FREE_ROUNDS_NOT_AVAILABLE;
                break;
            case 1011:
                responseCode = ServiceResponseCode.FREE_ROUNDS_SESSION_NOT_FOUND;
                break;
            case 1012:
            case 2025:
                responseCode = ServiceResponseCode.FREE_ROUNDS_ALREADY_CANCELED;
                break;
            case 1013:
            case 2026:
                responseCode = ServiceResponseCode.FREE_ROUNDS_COULD_NOT_BE_CANCELED;
                break;
            case 2014:
                responseCode = ServiceResponseCode.FREE_ROUNDS_DIFFERENT_VENDORS;
                break;
            case 1015:
                responseCode = ServiceResponseCode.EXTERNAL_SERVICE_ERROR;
                break;
            case 2011:
                responseCode = ServiceResponseCode.INVALID_ROUNDS;
                break;
            case 2015:
                responseCode = ServiceResponseCode.BONUS_NOT_FOUND;
                break;
            case 2018:
                responseCode = ServiceResponseCode.INVALID_START_TIME;
                break;
            case 2019:
            case 2022:
                responseCode = ServiceResponseCode.INVALID_EXPIRATION_TIME;
                break;
            case 2020:
                responseCode = ServiceResponseCode.EXPIRATION_TIME_LESS_OR_EQUALS_START_TIME;
                break;
            default:
                responseCode = ServiceResponseCode.INTERNAL_ERROR;
        }

    }
}
