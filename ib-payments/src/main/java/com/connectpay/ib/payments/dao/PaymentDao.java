package com.connectpay.ib.payments.dao;

import java.util.List;

import com.connectpay.payments.bean.CreditorDetails;
import com.connectpay.payments.bean.PaymentDetails;
import com.connectpay.payments.bean.PaymentDownloadRequest;
import com.connectpay.payments.bean.PaymentErrorLibrary;
import com.connectpay.payments.bean.PaymentOperations;
import com.connectpay.payments.bean.PaymentOtherDetails;
import com.connectpay.payments.bean.PaymentStatusDetails;
import com.connectpay.payments.request.InitialPaymentRequest;
import com.connectpay.payments.request.PaymentServiceRequest;
import com.connectpay.payments.request.PaymentStructuresRequest;
import com.connectpay.payments.request.PaymentsListRequest;
import com.connectpay.payments.response.PaymentDetailsResponse;

// TODO: Auto-generated Javadoc
/**
 * The Interface PaymentDao.
 */
public interface PaymentDao {

    /**
     * Gets the payment status.
     *
     * @param paymentStatus the payment status
     * @return the payment status
     */
    PaymentStatusDetails getPaymentStatus(String paymentStatus);

    /**
     * Insert payment details.
     *
     * @param paymentServiceRequest the payment service request
     * @param creditorDetails       the creditor details
     * @return the payment details
     */
    PaymentDetails insertPaymentDetails(PaymentServiceRequest paymentServiceRequest, CreditorDetails creditorDetails);

    /**
     * Update payment details.
     *
     * @param paymentServiceRequest the payment service request
     */
    void updatePaymentDetails(PaymentServiceRequest paymentServiceRequest);

    /**
     * Store payment request response.
     *
     * @param paymentStructuresRequest the payment structures request
     */
    void storePaymentRequestResponse(PaymentStructuresRequest paymentStructuresRequest);

    /**
     * Gets the payment details.
     *
     * @param paymentId the payment id
     * @return the payment details
     */
    PaymentDetails getPaymentDetails(String paymentId);

    /**
     * Gets the payment errors.
     *
     * @param providerErrorCode the provider error code
     * @return the payment errors
     */
    PaymentErrorLibrary getPaymentErrors(String providerErrorCode);

    /**
     * Creates the initial payment request.
     *
     * @param initialPaymentRequest the initial payment request
     * @return the initial payment request
     */
    InitialPaymentRequest createInitialPaymentRequest(InitialPaymentRequest initialPaymentRequest);

    /**
     * Update initial payment request.
     *
     * @param initialPaymentRequest the initial payment request
     * @return the initial payment request
     */
    InitialPaymentRequest updateInitialPaymentRequest(InitialPaymentRequest initialPaymentRequest);

    /**
     * Gets the payments list.
     *
     * @param paymentsListRequest the payments list request
     * @return the payments list
     */
    List<PaymentOperations> getPaymentsList(PaymentsListRequest paymentsListRequest);

    /**
     * Insert currency exchange payment details.
     *
     * @param paymentServiceRequest the payment service request
     * @return the payment details
     */
    PaymentDetails insertCurrencyExchangePaymentDetails(PaymentServiceRequest paymentServiceRequest);

    /**
     * Gets the currency exchange payment details.
     *
     * @param paymentId the payment id
     * @return the currency exchange payment details
     */
    PaymentOtherDetails getCurrencyExchangePaymentDetails(long paymentId);

    /**
     * Update payment status by payment ref list.
     *
     * @param paymentRefList  the payment ref list
     * @param paymentStatusId the payment status id
     */
    void updatePaymentStatusByPaymentRefList(String paymentRefList, int paymentStatusId);

    /**
     * Gets the payment details by reference list.
     *
     * @param paymentRefList the payment ref list
     * @return the payment details by reference list
     */
    List<PaymentDetails> getPaymentDetailsByReferenceList(List<String> paymentRefList);

    /**
     * Gets the payments details list.
     *
     * @param paymentDownloadRequest the payment download request
     * @return the payments details list
     */
    List<PaymentDetailsResponse> getPaymentsDetailsList(PaymentDownloadRequest paymentDownloadRequest);

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

}
