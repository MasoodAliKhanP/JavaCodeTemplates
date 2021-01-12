package com.connectpay.compliance.service.impl;

import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.connectpay.compliance.controller.ComplianceController;
import com.connectpay.compliance.service.ComplianceSerivce;
import com.connectpay.compliance.service.helper.ComplianceServiceHelper;
import com.connectpay.ib.enums.ComplianceEnums;
import com.connectpay.ib.http.constants.IntegrationPropertiesBean;
import com.connectpay.ib.http.helper.IntegrationHelper;
import com.connectpay.ib.utils.APIResponse;
import com.connectpay.ib.utils.LogUtil;
import com.connectpay.payments.compliance.request.ComplianceTransactionRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class ComplianceSerivceImpl.
 */
@Component
public class ComplianceSerivceImpl implements ComplianceSerivce {

    /** The Constant logger. */
    private static final Logger logger = LogManager.getLogger(ComplianceController.class);

    /** The transaction url. */
    @Value("${compliance.api.transactionsurl}")
    private String transactionUrl;

    /** The integration helper. */
    @Autowired
    private IntegrationHelper integrationHelper;

    /** The compliance service helper. */
    @Autowired
    ComplianceServiceHelper complianceServiceHelper;

    /**
     * Submit transaction for monitoring.
     *
     * @param request the request
     * @return the API response
     */
    @Override
    public APIResponse<?> submitTransactionForMonitoring(ComplianceTransactionRequest request) {
        String token = complianceServiceHelper.getToken();
        String tokenFormat = ComplianceEnums.TOKEN.getEvent().toUpperCase() + " " + token;
        LogUtil.log(logger, "Token format: " + tokenFormat);

        IntegrationPropertiesBean integraionPropBean = integrationHelper.preparePostJsonRequest(request,
                transactionUrl);
        /* This is to remove 'accept' header: should be written in a cleaner way */
        integraionPropBean.getHeadersArray().remove(1);
        integraionPropBean.getHeadersArray().add(new BasicHeader("Authorization", tokenFormat));
        String apiResponse = integrationHelper.processRequest(integraionPropBean);

        return complianceServiceHelper.processResponse(apiResponse);
    }

}
