package com.connectpay.compliance.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.connectpay.compliance.constants.Endpoints;
import com.connectpay.compliance.service.impl.ComplianceSerivceImpl;
import com.connectpay.ib.utils.APIResponse;
import com.connectpay.ib.utils.LogUtil;
import com.connectpay.payments.compliance.request.ComplianceTransactionRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class ComplianceController.
 */
@RestController
@RequestMapping(value = Endpoints.COMPLIANCE_BASE)
public class ComplianceController {

    /** The Constant logger. */
    private static final Logger logger = LogManager.getLogger(ComplianceController.class);

    /** The compliance serivce impl. */
    @Autowired
    ComplianceSerivceImpl complianceSerivceImpl;

    /**
     * Submit transaction for monitoring.
     *
     * @param request the request
     * @return the API response
     */
    @PostMapping(value = Endpoints.SUBMIT_TRANSACTION)
    public APIResponse<?> submitTransactionForMonitoring(@RequestBody ComplianceTransactionRequest request) {
        LogUtil.setLogPrefix(Endpoints.SUBMIT_TRANSACTION + "/"
                + request.getData().getAttributes().getTransaction_data().getTx_id());
        LogUtil.log(logger, "ComplianceTransactionRequest : " + request);
        return complianceSerivceImpl.submitTransactionForMonitoring(request);
    }
}
