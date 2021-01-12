package com.connectpay.ib.payments.dao;

import com.connectpay.payments.bean.PaymentOtpMapping;

// TODO: Auto-generated Javadoc
/**
 * The Interface PaymentOtpDao.
 */
public interface PaymentOtpDao {

    /**
     * Creates the payment otp mapping.
     *
     * @param paymentOtpMapping the payment otp mapping
     * @return the payment otp mapping
     */
    PaymentOtpMapping createPaymentOtpMapping(PaymentOtpMapping paymentOtpMapping);

    /**
     * Update payment otp mapping.
     *
     * @param paymentOtpMapping the payment otp mapping
     * @return the payment otp mapping
     */
    PaymentOtpMapping updatePaymentOtpMapping(PaymentOtpMapping paymentOtpMapping);

    /**
     * Gets the payment otp mapping.
     *
     * @param phoneOtpReference the phone otp reference
     * @return the payment otp mapping
     */
    PaymentOtpMapping getPaymentOtpMapping(String phoneOtpReference);

    void removePaymentOtpMapping(String authCode);

    PaymentOtpMapping checkPaymentOtpMapping(String paymentReference, String authCode);

}
