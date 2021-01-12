package com.connectpay.ib.payments.service;

import java.util.Map;

import com.connectpay.ib.utils.APIResponse;
import com.connectpay.payments.bean.PaymentDownloadRequest;
import com.connectpay.payments.bean.PaymentOtpMapping;
import com.connectpay.payments.request.InitialPaymentRequest;
import com.connectpay.payments.request.PaymentServiceRequest;
import com.connectpay.payments.request.PaymentStructuresRequest;
import com.connectpay.payments.request.PaymentsListRequest;
import com.connectpay.request.APIListRequest;

// TODO: Auto-generated Javadoc
/**
 * The Interface PaymentService.
 */
public interface PaymentService {

    /**
     * Creates the payments.
     *
     * @param paymentServiceRequest the payment service request
     * @return the API response
     */
    APIResponse<?> createPayments(PaymentServiceRequest paymentServiceRequest);

    /**
     * Update payments.
     *
     * @param paymentServiceRequest the payment service request
     * @return the API response
     */
    APIResponse<?> updatePayments(PaymentServiceRequest paymentServiceRequest);

    /**
     * Store payment request response.
     *
     * @param paymentStructuresRequest the payment structures request
     * @return the API response
     */
    APIResponse<?> storePaymentRequestResponse(PaymentStructuresRequest paymentStructuresRequest);

    /**
     * Gets the payment details.
     *
     * @param paymentId the payment id
     * @return the payment details
     */
    APIResponse<?> getPaymentDetails(String paymentId);

    /**
     * Gets the payment error details.
     *
     * @param providerErrorCode the provider error code
     * @return the payment error details
     */
    APIResponse<?> getPaymentErrorDetails(String providerErrorCode);

    /**
     * Creates the initial payment request.
     *
     * @param initialPaymentRequest the initial payment request
     * @return the API response
     */
    APIResponse<?> createInitialPaymentRequest(InitialPaymentRequest initialPaymentRequest);

    /**
     * Update initial payment request.
     *
     * @param initialPaymentRequest the initial payment request
     * @return the API response
     */
    APIResponse<?> updateInitialPaymentRequest(InitialPaymentRequest initialPaymentRequest);

    /**
     * Creates the payment otp mapping.
     *
     * @param paymentOtpMapping the payment otp mapping
     * @return the API response
     */
    APIResponse<?> createPaymentOtpMapping(PaymentOtpMapping paymentOtpMapping);

    /**
     * Update payment otp mapping.
     *
     * @param paymentOtpMapping the payment otp mapping
     * @return the API response
     */
    APIResponse<?> updatePaymentOtpMapping(PaymentOtpMapping paymentOtpMapping);

    /**
     * Gets the payment otp mapping.
     *
     * @param phoneOtpReference the phone otp reference
     * @return the payment otp mapping
     */
    APIResponse<?> getPaymentOtpMapping(String phoneOtpReference);

    /**
     * Gets the payments list.
     *
     * @param paymentsListRequest the payments list request
     * @return the payments list
     */
    APIResponse<Map<String, Object>> getPaymentsList(PaymentsListRequest paymentsListRequest);

    /**
     * Creates the currency exchange payment details.
     *
     * @param paymentServiceRequest the payment service request
     * @return the API response
     */
    APIResponse<?> createCurrencyExchangePaymentDetails(PaymentServiceRequest paymentServiceRequest);

    /**
     * Gets the payment other details by payment id.
     *
     * @param paymentId the payment id
     * @return the payment other details by payment id
     */
    APIResponse<?> getPaymentOtherDetailsByPaymentId(long paymentId);

    /**
     * Update payment by payment ref list.
     *
     * @param paymentServiceRequest the payment service request
     * @return the API response
     */
    APIResponse<?> updatePaymentByPaymentRefList(PaymentServiceRequest paymentServiceRequest);

    /**
     * Gets the payment details by reference list.
     *
     * @param paymentRefList the payment ref list
     * @return the payment details by reference list
     */
    APIResponse<?> getPaymentDetailsByReferenceList(APIListRequest<String> paymentRefList);

    /**
     * Gets the payments details list.
     *
     * @param paymentDownloadRequest the payment download request
     * @return the payments details list
     */
    APIResponse<?> getPaymentsDetailsList(PaymentDownloadRequest paymentDownloadRequest);

    /**
     * Update nano intiated count.
     *
     * @param paymentServiceRequest the payment service request
     */
    void updateNanoIntiatedCount(PaymentServiceRequest paymentServiceRequest);

    /**
     * Update nano waiting count.
     *
     * @param paymentServiceRequest the payment service request
     */
    void updateNanoWaitingCount(PaymentServiceRequest paymentServiceRequest);

    APIResponse<?> removePaymentOtpMapping(String authCode);

    APIResponse<?> checkPaymentOtpMapping(String paymentReference, String authCode);

}
