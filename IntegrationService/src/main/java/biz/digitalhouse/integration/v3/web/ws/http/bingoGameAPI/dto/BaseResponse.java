package biz.digitalhouse.integration.v3.web.ws.http.bingoGameAPI.dto;


/**
 * Created by vitaliy.babenko
 * on 29.03.2017.
 */
public class BaseResponse {

    private int errorCode;
    private String errorDescription;

    public BaseResponse() {
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

}
