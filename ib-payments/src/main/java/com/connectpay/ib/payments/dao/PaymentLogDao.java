package com.connectpay.ib.payments.dao;

import com.connectpay.payments.bean.PaymentLog;
import com.connectpay.payments.request.PaymentServiceRequest;
// TODO: Auto-generated Javadoc

/**
 * The Interface PaymentLogDao.
 */
public interface PaymentLogDao {

    /**
     * Gets the payment log by email.
     *
     * @param paymentId the payment id
     * @param email     the email
     * @param statusId  the status id
     * @return the payment log by email
     */
    PaymentLog getPaymentLogByEmail(long paymentId, int statusId);

    /**
     * Insert payment log.
     *
     * @param paymentServiceRequest the payment service request
     */
    void insertPaymentLog(PaymentServiceRequest paymentServiceRequest);
}
