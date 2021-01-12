package com.connectpay.compliance.service.helper;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.connectpay.ib.enums.ComplianceEnums;
import com.connectpay.ib.enums.ResponseCodes;
import com.connectpay.ib.enums.ResponseStatus;
import com.connectpay.ib.http.constants.IntegrationConstants;
import com.connectpay.ib.http.constants.IntegrationPropertiesBean;
import com.connectpay.ib.http.helper.IntegrationHelper;
import com.connectpay.ib.utils.APIResponse;
import com.connectpay.ib.utils.ConnectPayUtils;
import com.connectpay.payments.compliance.response.ComplianceTransactionErrorResponse;
import com.connectpay.payments.compliance.response.ComplianceTransactionResponse;
import com.connectpay.payments.request.ComplianceAuthRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

// TODO: Auto-generated Javadoc
/**
 * The Class ComplianceServiceHelper.
 */
@Component
public class ComplianceServiceHelper {

    /** The username. */
    @Value("${compliance.api.basic.auth.username}")
    private String username;

    /** The password. */
    @Value("${compliance.api.basic.auth.password}")
    private String password;

    /** The auth url. */
    @Value("${compliance.api.authurl}")
    private String authUrl;

    /** The integration helper. */
    @Autowired
    IntegrationHelper integrationHelper;

    /**
     * Gets the token for every request.
     *
     * @return the token
     */
    @SuppressWarnings("unchecked")
    public String getToken() {
        ComplianceAuthRequest authRequest = new ComplianceAuthRequest();
        authRequest.setUsername(username);
        authRequest.setPassword(password);
        IntegrationPropertiesBean integraionPropBean = integrationHelper.preparePostJsonRequest(authRequest, authUrl);
        String apiResponse = integrationHelper.processRequest(integraionPropBean);

        Gson gson = new Gson();
        Map<String, String> responseMap = gson.fromJson(apiResponse, Map.class);
        String tokenResponse = responseMap.get(IntegrationConstants.SUPPLIER_RESPONSE_KEY);

        Map<String, String> tokenMap = gson.fromJson(tokenResponse, Map.class);
        return tokenMap.get(ComplianceEnums.TOKEN.getEvent());
    }

    /**
     * Process response.
     *
     * @param response the response
     * @return the API response
     */
    @SuppressWarnings("unchecked")
    public APIResponse<?> processResponse(String response) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        Map<String, String> responseMap = gson.fromJson(response, Map.class);
        String complianceResponse = responseMap.get(IntegrationConstants.SUPPLIER_RESPONSE_KEY);
        String httpStatusCode = responseMap.get(IntegrationConstants.SUPPLIER_HTTPSTATUS_KEY);

        APIResponse<?> apiResponse = null;
        if (ResponseCodes.SUCCESS_CODE.responseCode().contains(httpStatusCode)) {
            ComplianceTransactionResponse complianceTransactionResponse = gson.fromJson(complianceResponse,
                    ComplianceTransactionResponse.class);
            apiResponse = ConnectPayUtils.getSuccessResponse(complianceTransactionResponse);
        } else {
            ComplianceTransactionErrorResponse compliFailureResponse = gson.fromJson(complianceResponse,
                    ComplianceTransactionErrorResponse.class);
            apiResponse = ConnectPayUtils.getFailureResponse(ResponseStatus.FAILED, httpStatusCode, null,
                    compliFailureResponse);
        }
        return apiResponse;
    }
}
