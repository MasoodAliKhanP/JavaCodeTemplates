package com.connectpay.ib.payments.dao;

import com.connectpay.payments.bean.CreditorDetails;
import com.connectpay.payments.request.PaymentServiceRequest;

// TODO: Auto-generated Javadoc
/**
 * The Interface CreditorDao.
 */
public interface CreditorDao {

    /**
     * Insert creditor details.
     *
     * @param paymentServiceRequest the payment service request
     * @return the creditor details
     */
    CreditorDetails insertCreditorDetails(PaymentServiceRequest paymentServiceRequest);

}
