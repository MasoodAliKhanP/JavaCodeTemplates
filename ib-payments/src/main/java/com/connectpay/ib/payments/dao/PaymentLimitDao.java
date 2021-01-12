package com.connectpay.ib.payments.dao;

import java.util.List;

import com.connectpay.payments.bean.CustomerPaymentLimit;
import com.connectpay.payments.bean.CustomerPersonPaymentLimit;
import com.connectpay.payments.bean.DefaultPaymentLimit;
import com.connectpay.payments.bean.PaymentLimitDocuments;
import com.connectpay.payments.request.PaymentLimitRequest;
import com.connectpay.payments.response.PaymentDetailsResponse;

// TODO: Auto-generated Javadoc
/**
 * The Interface PaymentLimitDao.
 */
public interface PaymentLimitDao {

    /**
     * Gets the payment limits by customer id.
     *
     * @param customerId the customer id
     * @return the payment limits by customer id
     */
    List<CustomerPaymentLimit> getPaymentLimitsByCustomerId(long customerId);

    /**
     * Gets the payment limits by customer person map id.
     *
     * @param customerPersonMapId the customer person map id
     * @return the payment limits by customer person map id
     */
    List<CustomerPersonPaymentLimit> getPaymentLimitsByCustomerPersonMapId(long customerPersonMapId);

    /**
     * Gets the payment data by party id and email.
     *
     * @param paymentLimitRequest the payment limit request
     * @return the payment data by party id and email
     */
    List<PaymentDetailsResponse> getPaymentDataByPartyIdAndPersonId(PaymentLimitRequest paymentLimitRequest);

    /**
     * Gets the payment data by party id.
     *
     * @param paymentLimitRequest the payment limit request
     * @return the payment data by party id
     */
    List<PaymentDetailsResponse> getPaymentDataByPartyId(PaymentLimitRequest paymentLimitRequest);

    /**
     * Gets the default limits by customer type.
     *
     * @param customerTypeId the customer type id
     * @return the default limits by customer type
     */
    List<DefaultPaymentLimit> getDefaultLimitsByCustomerType(int customerTypeId);

    /**
     * Insert customer limits.
     *
     * @param customerPaymentLimitList the customer payment limit list
     */
    void insertCustomerLimits(List<CustomerPaymentLimit> customerPaymentLimitList);

    /**
     * Insert customer person limits.
     *
     * @param customerPersonPaymentLimitList the customer person payment limit list
     */
    void insertCustomerPersonLimits(List<CustomerPersonPaymentLimit> customerPersonPaymentLimitList);

    /**
     * Update customer person limits.
     *
     * @param customerPersonPaymentLimitList the customer person payment limit list
     */
    void updateCustomerPersonLimits(List<CustomerPersonPaymentLimit> customerPersonPaymentLimitList);

    /**
     * Update customer limits.
     *
     * @param customerPaymentLimitList the customer payment limit list
     */
    void updateCustomerLimits(List<CustomerPaymentLimit> customerPaymentLimitList);

    /**
     * Inactivate customer payment limits log.
     *
     * @param customerPaymentLimitList the customer payment limit list
     */
    void inactivateCustomerPaymentLimitsLog(List<CustomerPaymentLimit> customerPaymentLimitList);

    /**
     * Inactivate customer person payment limits log.
     *
     * @param customerPersonPaymentLimitList the customer person payment limit list
     */
    void inactivateCustomerPersonPaymentLimitsLog(List<CustomerPersonPaymentLimit> customerPersonPaymentLimitList);

    /**
     * Insert customer person limits log.
     *
     * @param customerPersonPaymentLimitList the customer person payment limit list
     */
    void insertCustomerPersonLimitsLog(List<CustomerPersonPaymentLimit> customerPersonPaymentLimitList);

    /**
     * Insert customer limits log.
     *
     * @param customerPaymentLimitList the customer payment limit list
     */
    void insertCustomerLimitsLog(List<CustomerPaymentLimit> customerPaymentLimitList);

    /**
     * Insert payment limit document.
     *
     * @param paymentLimitDocumentsList the payment limit documents list
     */
    void insertPaymentLimitDocument(List<PaymentLimitDocuments> paymentLimitDocumentsList);
}
