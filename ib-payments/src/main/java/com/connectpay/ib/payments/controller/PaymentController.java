package com.connectpay.ib.payments.controller;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.connectpay.ib.payments.constants.Endpoints;
import com.connectpay.ib.payments.service.PaymentService;
import com.connectpay.ib.utils.APIResponse;
import com.connectpay.ib.utils.LogUtil;
import com.connectpay.payments.bean.PaymentDownloadRequest;
import com.connectpay.payments.bean.PaymentOtpMapping;
import com.connectpay.payments.request.InitialPaymentRequest;
import com.connectpay.payments.request.PaymentServiceRequest;
import com.connectpay.payments.request.PaymentStructuresRequest;
import com.connectpay.payments.request.PaymentsListRequest;
import com.connectpay.request.APIListRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class PaymentController.
 */
@RestController
@RequestMapping(value = Endpoints.PAYMENT_BASE_PATH)
public class PaymentController {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger(PaymentController.class);

    /** The payment service. */
    @Autowired
    private PaymentService paymentService;

    /**
     * Do payments.
     *
     * @param paymentServiceRequest the payment service request
     * @return the API response
     */
    @PostMapping(value = Endpoints.CREATE_PAYMENTS)
    public APIResponse<?> doPayments(@RequestBody PaymentServiceRequest paymentServiceRequest) {
        LogUtil.setLogPrefix(Endpoints.CREATE_PAYMENTS + "/" + paymentServiceRequest.getEmail());
        LogUtil.log(LOGGER, "Create payment Request is :: " + paymentServiceRequest);
        return paymentService.createPayments(paymentServiceRequest);
    }

    /**
     * Update payments.
     *
     * @param paymentServiceRequest the payment service request
     * @return the API response
     */
    @PostMapping(value = Endpoints.UPDATE_PAYMENTS)
    public APIResponse<?> updatePayments(@RequestBody PaymentServiceRequest paymentServiceRequest) {
        LogUtil.setLogPrefix(Endpoints.UPDATE_PAYMENTS + "/" + paymentServiceRequest.getPaymentReference());
        LogUtil.log(LOGGER, "Update payment Request is :: " + paymentServiceRequest);
        return paymentService.updatePayments(paymentServiceRequest);
    }

    /**
     * Store nano payment request response.
     *
     * @param paymentStructuresRequest the payment structures request
     * @return the API response
     */
    @PostMapping(value = Endpoints.PAYMENT_REQUEST_RESPONSE)
    public APIResponse<?> storeNanoPaymentRequestResponse(
            @RequestBody PaymentStructuresRequest paymentStructuresRequest) {
        LogUtil.setLogPrefix(Endpoints.PAYMENT_REQUEST_RESPONSE + "/" + paymentStructuresRequest.getPaymentId());
        LogUtil.log(LOGGER, "create payment Request response is :: " + paymentStructuresRequest);
        return paymentService.storePaymentRequestResponse(paymentStructuresRequest);
    }

    /**
     * Creates the initial payment request.
     *
     * @param initialPaymentRequest the initial payment request
     * @return the API response
     */
    @PostMapping(value = Endpoints.CREATE_INITIAL_PAYMENTS)
    public APIResponse<?> createInitialPaymentRequest(@RequestBody InitialPaymentRequest initialPaymentRequest) {
        LogUtil.setLogPrefix(Endpoints.CREATE_INITIAL_PAYMENTS);
        LogUtil.log(LOGGER, "create initial payment Request is :: " + initialPaymentRequest);
        return paymentService.createInitialPaymentRequest(initialPaymentRequest);
    }

    /**
     * Update initial payment request.
     *
     * @param initialPaymentRequest the initial payment request
     * @return the API response
     */
    @PutMapping(value = Endpoints.CREATE_INITIAL_PAYMENTS)
    public APIResponse<?> updateInitialPaymentRequest(@RequestBody InitialPaymentRequest initialPaymentRequest) {
        LogUtil.setLogPrefix(Endpoints.CREATE_INITIAL_PAYMENTS);
        LogUtil.log(LOGGER, "update initial payment Request is :: " + initialPaymentRequest);
        return paymentService.updateInitialPaymentRequest(initialPaymentRequest);
    }

    /**
     * Gets the payment details.
     *
     * @param paymentReference the payment reference
     * @return the payment details
     */
    @GetMapping(value = Endpoints.GET_PAYMENT_DETAILS)
    public APIResponse<?> getPaymentDetails(@PathVariable("paymentReference") String paymentReference) {
        LogUtil.setLogPrefix(Endpoints.GET_PAYMENT_DETAILS + "/" + paymentReference);
        LogUtil.log(LOGGER, "get payment details for :: " + paymentReference);
        return paymentService.getPaymentDetails(paymentReference);
    }

    /**
     * Gets the payment error details.
     *
     * @param providerErrorCode the provider error code
     * @return the payment error details
     */
    @GetMapping(value = Endpoints.GET_PAYMENT_ERROR_DETAILS)
    public APIResponse<?> getPaymentErrorDetails(@PathVariable("providerErrorCode") String providerErrorCode) {
        LogUtil.setLogPrefix(Endpoints.GET_PAYMENT_ERROR_DETAILS + "/" + providerErrorCode);
        LogUtil.log(LOGGER, "get payment Error details for :: " + providerErrorCode);
        return paymentService.getPaymentErrorDetails(providerErrorCode);
    }

    /**
     * Creates the payment otp mapping.
     *
     * @param paymentOtpMapping the payment otp mapping
     * @return the API response
     */
    @PostMapping(value = Endpoints.PAYMENTS_OTP_MAPPING)
    public APIResponse<?> createPaymentOtpMapping(@RequestBody PaymentOtpMapping paymentOtpMapping) {
        LogUtil.setLogPrefix(Endpoints.PAYMENTS_OTP_MAPPING + "/" + paymentOtpMapping.getPaymentReference());
        LogUtil.log(LOGGER, "create payment otp mapping Request is :: " + paymentOtpMapping);
        return paymentService.createPaymentOtpMapping(paymentOtpMapping);
    }

    @GetMapping(value = Endpoints.REMOVE_OTP_MAPPING)
    public APIResponse<?> removePaymentOtpMapping(@PathVariable("authCode") String authCode) {
        LogUtil.setLogPrefix(Endpoints.REMOVE_OTP_MAPPING + "/" + authCode);
        LogUtil.log(LOGGER, "remove payment otp mapping Request is :: " + authCode);
        return paymentService.removePaymentOtpMapping(authCode);
    }

    /**
     * Update payment otp mapping.
     *
     * @param paymentOtpMapping the payment otp mapping
     * @return the API response
     */
    @PutMapping(value = Endpoints.PAYMENTS_OTP_MAPPING)
    public APIResponse<?> updatePaymentOtpMapping(@RequestBody PaymentOtpMapping paymentOtpMapping) {
        LogUtil.setLogPrefix(Endpoints.PAYMENTS_OTP_MAPPING + "/" + paymentOtpMapping.getPaymentReference());
        LogUtil.log(LOGGER, "update payment otp mapping Request is :: " + paymentOtpMapping);
        return paymentService.updatePaymentOtpMapping(paymentOtpMapping);
    }

    /**
     * Gets the payment otp mapping.
     *
     * @param phoneOtpReference the phone otp reference
     * @return the payment otp mapping
     */
    @GetMapping(value = Endpoints.GET_PAYMENTS_OTP_MAPPING)
    public APIResponse<?> getPaymentOtpMapping(@PathVariable("phoneOtpReference") String phoneOtpReference) {
        LogUtil.setLogPrefix("/getOtpMapping/" + phoneOtpReference);
        LogUtil.log(LOGGER, "get payment otp mapping Request is :: " + phoneOtpReference);
        return paymentService.getPaymentOtpMapping(phoneOtpReference);
    }

    /**
     * Gets the payments list.
     *
     * @param paymentsListRequest the payments list request
     * @return the payments list
     */
    @PostMapping(value = Endpoints.GET_PAYMENTS_LIST)
    public APIResponse<Map<String, Object>> getPaymentsList(@RequestBody PaymentsListRequest paymentsListRequest) {
        LogUtil.setLogPrefix(Endpoints.GET_PAYMENTS_LIST + "/" + paymentsListRequest.getCustomerPartyId());
        LogUtil.log(LOGGER, "get payments list for :: " + paymentsListRequest);
        return paymentService.getPaymentsList(paymentsListRequest);
    }

    /**
     * Gets the payments details list.
     *
     * @param paymentDownloadRequest the payment download request
     * @return the payments details list
     */
    @PostMapping(value = Endpoints.GET_PAYMENTS_DETAILS_LIST)
    public APIResponse<?> getPaymentsDetailsList(@RequestBody PaymentDownloadRequest paymentDownloadRequest) {
        LogUtil.setLogPrefix(Endpoints.GET_PAYMENTS_DETAILS_LIST);
        LogUtil.log(LOGGER, "get payments Details list for :: " + paymentDownloadRequest);
        return paymentService.getPaymentsDetailsList(paymentDownloadRequest);
    }

    /**
     * Creates the exchange currency payment details.
     *
     * @param paymentServiceRequest the payment service request
     * @return the API response
     */
    @PostMapping(value = Endpoints.CREATE_PAYMENT_CURRENCY_EXCHANGE)
    public APIResponse<?> createExchangeCurrencyPaymentDetails(
            @RequestBody PaymentServiceRequest paymentServiceRequest) {
        LogUtil.setLogPrefix(Endpoints.CREATE_PAYMENT_CURRENCY_EXCHANGE + "/" + paymentServiceRequest.getId());
        LogUtil.log(LOGGER, "Create currency exchange request :: " + paymentServiceRequest);
        return paymentService.createCurrencyExchangePaymentDetails(paymentServiceRequest);
    }

    /**
     * Gets the currency exchange details by payment id.
     *
     * @param paymentId the payment id
     * @return the currency exchange details by payment id
     */
    @GetMapping(value = Endpoints.GET_PAYMENT_OTHER_DETAILS_BY_PAYMENT_ID)
    public APIResponse<?> getCurrencyExchangeDetailsByPaymentId(@PathVariable("paymentId") long paymentId) {
        LogUtil.setLogPrefix("/getCurrExchangeDetailsByPaymentId/" + paymentId);
        LogUtil.log(LOGGER, "Get paymentOther Details for:: " + paymentId);
        return paymentService.getPaymentOtherDetailsByPaymentId(paymentId);
    }

    @GetMapping(value = Endpoints.CHECK_PAYMENT_OTP_MAPPING)
    public APIResponse<?> checkPaymentOtpMapping(@PathVariable("paymentReference") String paymentReference,
            @PathVariable("authCode") String authCode) {
        LogUtil.setLogPrefix("/checkPaymentOtpMapping/" + paymentReference + "/" + authCode);
        return paymentService.checkPaymentOtpMapping(paymentReference, authCode);
    }

    /**
     * Removes the payments.
     *
     * @param paymentServiceRequest the payment service request
     * @return the API response
     */
    @PostMapping(value = Endpoints.UPDATE_PAYMENT_STATUS_BY_REF_LIST)
    public APIResponse<?> removePayments(@RequestBody PaymentServiceRequest paymentServiceRequest) {
        LogUtil.setLogPrefix(Endpoints.UPDATE_PAYMENT_STATUS_BY_REF_LIST);
        LogUtil.log(LOGGER, "List of paymentRef to be removed :: ");
        return paymentService.updatePaymentByPaymentRefList(paymentServiceRequest);
    }

    /**
     * Gets the payment details by ref list.
     *
     * @param paymentRefList the payment ref list
     * @return the payment details by ref list
     */
    @PostMapping(value = Endpoints.GET_PAYMENT_DETAILS_BY_REF_LIST)
    public APIResponse<?> getPaymentDetailsByRefList(@RequestBody APIListRequest<String> paymentRefList) {
        LogUtil.setLogPrefix(Endpoints.GET_PAYMENT_DETAILS_BY_REF_LIST);
        LogUtil.log(LOGGER, "fetching payment details by paymentRef list:: ");
        return paymentService.getPaymentDetailsByReferenceList(paymentRefList);
    }

    /**
     * Update nano intiated count.
     *
     * @param paymentServiceRequest the payment service request
     */
    @PutMapping(value = Endpoints.UPDATE_NANO_INITIATED_COUNT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void updateNanoIntiatedCount(@RequestBody PaymentServiceRequest paymentServiceRequest) {
        LogUtil.setLogPrefix(Endpoints.UPDATE_NANO_INITIATED_COUNT);
        LogUtil.log(LOGGER,
                "updating nano initiated count for paymentReference :: " + paymentServiceRequest.getPaymentReference());
        paymentService.updateNanoIntiatedCount(paymentServiceRequest);
    }

    /**
     * Update nano waiting count.
     *
     * @param paymentServiceRequest the payment service request
     */
    @PutMapping(value = Endpoints.UPDATE_NANO_WAITING_COUNT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void updateNanoWaitingCount(@RequestBody PaymentServiceRequest paymentServiceRequest) {
        LogUtil.setLogPrefix(Endpoints.UPDATE_NANO_WAITING_COUNT);
        LogUtil.log(LOGGER,
                "updating nano waiting count for paymentReference :: " + paymentServiceRequest.getPaymentReference());
        paymentService.updateNanoWaitingCount(paymentServiceRequest);
    }

}
