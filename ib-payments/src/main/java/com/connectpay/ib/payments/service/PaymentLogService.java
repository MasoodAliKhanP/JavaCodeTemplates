package com.connectpay.ib.payments.service;

import com.connectpay.ib.utils.APIResponse;
import com.connectpay.payments.bean.PaymentLog;

// TODO: Auto-generated Javadoc
/**
 * The Interface PaymentLogService.
 */
public interface PaymentLogService {

    /**
     * Gets the payment log by email and status.
     *
     * @param paymentLog the payment log
     * @return the payment log by email and status
     */
    APIResponse<?> getPaymentLogByEmailAndStatus(PaymentLog paymentLog);
}
