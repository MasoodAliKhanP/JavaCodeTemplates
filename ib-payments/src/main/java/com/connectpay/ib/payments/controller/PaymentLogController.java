package com.connectpay.ib.payments.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.connectpay.ib.payments.constants.Endpoints;
import com.connectpay.ib.payments.service.PaymentLogService;
import com.connectpay.ib.utils.APIResponse;
import com.connectpay.ib.utils.LogUtil;
import com.connectpay.payments.bean.PaymentLog;

// TODO: Auto-generated Javadoc
/**
 * The Class PaymentLogController.
 */
@RestController
@RequestMapping(value = Endpoints.LOG_CONTROLLER_BASE_PATH)
public class PaymentLogController {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger(PaymentLogController.class);

    /** The payment log service. */
    @Autowired
    private PaymentLogService paymentLogService;

    /**
     * Gets the payment log data by email and status.
     *
     * @param paymentLog the payment log
     * @return the payment log data by email and status
     */
    @PostMapping(value = Endpoints.GET_LOG_DATA_BY_ID_AND_STATUS)
    public APIResponse<?> getPaymentLogDataByEmailAndStatus(@RequestBody PaymentLog paymentLog) {
        LogUtil.setLogPrefix(Endpoints.GET_LOG_DATA_BY_ID_AND_STATUS + "/" + paymentLog.getPaymentId());
        LogUtil.log(LOGGER, "Create payment Request is :: " + paymentLog);
        return paymentLogService.getPaymentLogByEmailAndStatus(paymentLog);
    }

}
