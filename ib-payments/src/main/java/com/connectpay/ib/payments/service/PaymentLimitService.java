package com.connectpay.ib.payments.service;

import java.util.List;

import com.connectpay.ib.utils.APIResponse;
import com.connectpay.payments.bean.CustomerPaymentLimit;
import com.connectpay.payments.bean.CustomerPersonPaymentLimit;
import com.connectpay.payments.bean.DefaultPaymentLimit;
import com.connectpay.payments.bean.PaymentLimitDocuments;
import com.connectpay.payments.request.PaymentLimitRequest;
import com.connectpay.payments.response.PaymentDetailsResponse;

// TODO: Auto-generated Javadoc
/**
 * The Interface PaymentLimitService.
 */
public interface PaymentLimitService {

    /**
     * Gets the payment limits by customer id.
     *
     * @param customerId the customer id
     * @return the payment limits by customer id
     */
    APIResponse<List<CustomerPaymentLimit>> getPaymentLimitsByCustomerId(long customerId);

    /**
     * Gets the payment limits by customer person map id.
     *
     * @param customerPersonMapId the customer person map id
     * @return the payment limits by customer person map id
     */
    APIResponse<List<CustomerPersonPaymentLimit>> getPaymentLimitsByCustomerPersonMapId(long customerPersonMapId);

    /**
     * Gets the payment data by party id and PersonId.
     *
     * @param paymentLimitRequest the payment limit request
     * @return the payment data by party id and PersonId
     */
    APIResponse<List<PaymentDetailsResponse>> getPaymentDataByPartyIdAndPersonId(
            PaymentLimitRequest paymentLimitRequest);

    /**
     * Gets the payment data by party id.
     *
     * @param paymentLimitRequest the payment limit request
     * @return the payment data by party id
     */
    APIResponse<List<PaymentDetailsResponse>> getPaymentDataByPartyId(PaymentLimitRequest paymentLimitRequest);

    /**
     * Gets the default limits by customer type.
     *
     * @param customerTypeId the customer type id
     * @return the default limits by customer type
     */
    APIResponse<List<DefaultPaymentLimit>> getDefaultLimitsByCustomerType(int customerTypeId);

    /**
     * Insert customer limits.
     *
     * @param customerPaymentLimitList the customer payment limit list
     * @return the API response
     */
    APIResponse<?> insertCustomerLimits(List<CustomerPaymentLimit> customerPaymentLimitList);

    /**
     * Insert customer person limits.
     *
     * @param customerPersonPaymentLimitList the customer person payment limit list
     * @return the API response
     */
    APIResponse<?> insertCustomerPersonLimits(List<CustomerPersonPaymentLimit> customerPersonPaymentLimitList);

    /**
     * Update customer person payment limits.
     *
     * @param customerPersonPaymentLimitList the customer person payment limit list
     * @return the API response
     */
    APIResponse<?> updateCustomerPersonPaymentLimits(List<CustomerPersonPaymentLimit> customerPersonPaymentLimitList);

    /**
     * Update customer payment limits.
     *
     * @param customerPaymentLimitList the customer payment limit list
     * @return the API response
     */
    APIResponse<?> updateCustomerPaymentLimits(List<CustomerPaymentLimit> customerPaymentLimitList);

    /**
     * Insert payment limit document.
     *
     * @param paymentLimitDocumentsList the payment limit documents list
     * @return the API response
     */
    APIResponse<?> insertPaymentLimitDocument(List<PaymentLimitDocuments> paymentLimitDocumentsList);

}
