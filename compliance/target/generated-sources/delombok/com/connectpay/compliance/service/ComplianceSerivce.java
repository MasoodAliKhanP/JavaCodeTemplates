package com.connectpay.compliance.service;

import com.connectpay.ib.utils.APIResponse;
import com.connectpay.payments.compliance.request.ComplianceTransactionRequest;

// TODO: Auto-generated Javadoc
/**
 * The Interface ComplianceSerivce.
 */
public interface ComplianceSerivce {

    /**
     * Submit transaction for monitoring.
     *
     * @param request the request
     * @return the API response
     */
    APIResponse<?> submitTransactionForMonitoring(ComplianceTransactionRequest request);
}
