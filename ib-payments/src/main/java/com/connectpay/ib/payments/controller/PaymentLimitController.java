package com.connectpay.ib.payments.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.connectpay.ib.payments.constants.Endpoints;
import com.connectpay.ib.payments.service.PaymentLimitService;
import com.connectpay.ib.utils.APIResponse;
import com.connectpay.ib.utils.LogUtil;
import com.connectpay.payments.bean.CustomerPaymentLimit;
import com.connectpay.payments.bean.CustomerPersonPaymentLimit;
import com.connectpay.payments.bean.DefaultPaymentLimit;
import com.connectpay.payments.bean.PaymentLimitDocuments;
import com.connectpay.payments.request.PaymentLimitRequest;
import com.connectpay.payments.response.PaymentDetailsResponse;
import com.connectpay.request.APIListRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class PaymentLimitController.
 */
@RestController
@RequestMapping(Endpoints.PAYMENT_LIMIT_BASE_PATH)
public class PaymentLimitController {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger(PaymentLimitController.class);

    /** The payment limit service. */
    @Autowired
    private PaymentLimitService paymentLimitService;

    /**
     * Gets the payment limits by customer id.
     *
     * @param customerId the customer id
     * @return the payment limits by customer id
     */
    @GetMapping(Endpoints.GET_PAYMENT_LIMITS_BY_CUSTOMER_ID)
    public APIResponse<List<CustomerPaymentLimit>> getPaymentLimitsByCustomerId(
            @PathVariable("customerId") long customerId) {
        LogUtil.setLogPrefix("/getPaymentLimitsByCustomerId/" + customerId);
        LogUtil.log(LOGGER, "Fetching paymentLimits by customerId");
        return paymentLimitService.getPaymentLimitsByCustomerId(customerId);
    }

    /**
     * Gets the payment data by customer person map id.
     *
     * @param customerPersonMapId the customer person map id
     * @return the payment data by customer person map id
     */
    @GetMapping(Endpoints.GET_PAYMENT_LIMITS_BY_CUSTOMER_PERSON_MAP_ID)
    public APIResponse<List<CustomerPersonPaymentLimit>> getPaymentDataByCustomerPersonMapId(
            @PathVariable("mapId") long customerPersonMapId) {
        LogUtil.setLogPrefix("/getPaymentLimitsByMapId/" + customerPersonMapId);
        LogUtil.log(LOGGER, "Fetching payment Limits by customerPerson Map Id");
        return paymentLimitService.getPaymentLimitsByCustomerPersonMapId(customerPersonMapId);
    }

    /**
     * Gets the payment data by party id and PersonId.
     *
     * @param paymentLimitRequest the payment limit request
     * @return the payment data by party id and PersonId
     */
    @PostMapping(Endpoints.GET_PAYMENT_DATA_BY_PARTY_ID_PERSON_ID)
    public APIResponse<List<PaymentDetailsResponse>> getPaymentDataByPartyIdAndPersonId(
            @RequestBody PaymentLimitRequest paymentLimitRequest) {
        LogUtil.setLogPrefix(Endpoints.GET_PAYMENT_DATA_BY_PARTY_ID_PERSON_ID + paymentLimitRequest.getPersonId());
        LogUtil.log(LOGGER, "Fetching payment data by partyId" + paymentLimitRequest.getPartyId() + " personId:: "
                + paymentLimitRequest.getPersonId());
        return paymentLimitService.getPaymentDataByPartyIdAndPersonId(paymentLimitRequest);
    }

    /**
     * Gets the payment data by party id.
     *
     * @param paymentLimitRequest the payment limit request
     * @return the payment data by party id
     */
    @PostMapping(Endpoints.GET_PAYMENT_DATA_BY_PARTY_ID)
    public APIResponse<List<PaymentDetailsResponse>> getPaymentDataByPartyId(
            @RequestBody PaymentLimitRequest paymentLimitRequest) {
        LogUtil.setLogPrefix(Endpoints.GET_PAYMENT_DATA_BY_PARTY_ID + paymentLimitRequest.getPartyId());
        LogUtil.log(LOGGER, "Fetching payment data by partyId" + paymentLimitRequest.getPartyId());
        return paymentLimitService.getPaymentDataByPartyId(paymentLimitRequest);
    }

    /**
     * Gets the default payment limits.
     *
     * @param customerTypeId the customer type id
     * @return the default payment limits
     */
    @GetMapping(Endpoints.GET_DEFAULT_PAYMENT_LIMITS)
    public APIResponse<List<DefaultPaymentLimit>> getDefaultPaymentLimits(
            @PathVariable("customerTypeId") int customerTypeId) {
        LogUtil.setLogPrefix(Endpoints.GET_DEFAULT_PAYMENT_LIMITS);
        LogUtil.log(LOGGER, "default payment limits fetching for" + customerTypeId);
        return paymentLimitService.getDefaultLimitsByCustomerType(customerTypeId);
    }

    /**
     * Insert customer limits.
     *
     * @param apiRequest the api request
     * @return the API response
     */
    @PostMapping(Endpoints.SAVE_CUSTOMER_PAYMENT_LIMITS)
    public APIResponse<?> insertCustomerLimits(@RequestBody APIListRequest<CustomerPaymentLimit> apiRequest) {
        LogUtil.setLogPrefix(Endpoints.SAVE_CUSTOMER_PAYMENT_LIMITS);
        LogUtil.log(LOGGER, "save customer paymentlimits request" + apiRequest);
        return paymentLimitService.insertCustomerLimits(apiRequest.getDataList());
    }

    /**
     * Insert customer person payment limits.
     *
     * @param apiRequest the api request
     * @return the API response
     */
    @PostMapping(Endpoints.SAVE_CUSTOMER_PERSON_PAYMENT_LIMITS)
    public APIResponse<?> insertCustomerPersonPaymentLimits(
            @RequestBody APIListRequest<CustomerPersonPaymentLimit> apiRequest) {
        LogUtil.setLogPrefix(Endpoints.SAVE_CUSTOMER_PERSON_PAYMENT_LIMITS);
        LogUtil.log(LOGGER, "save customer person paymentlimits request" + apiRequest);
        return paymentLimitService.insertCustomerPersonLimits(apiRequest.getDataList());
    }

    /**
     * Update customer person payment limits.
     *
     * @param apiRequest the api request
     * @return the API response
     */
    @PutMapping(Endpoints.SAVE_CUSTOMER_PERSON_PAYMENT_LIMITS)
    public APIResponse<?> updateCustomerPersonPaymentLimits(
            @RequestBody APIListRequest<CustomerPersonPaymentLimit> apiRequest) {
        LogUtil.setLogPrefix(Endpoints.SAVE_CUSTOMER_PERSON_PAYMENT_LIMITS);
        LogUtil.log(LOGGER, "update customer person paymentlimits request" + apiRequest);
        return paymentLimitService.updateCustomerPersonPaymentLimits(apiRequest.getDataList());
    }

    /**
     * Update customer payment limits.
     *
     * @param apiRequest the api request
     * @return the API response
     */
    @PutMapping(Endpoints.SAVE_CUSTOMER_PAYMENT_LIMITS)
    public APIResponse<?> updateCustomerPaymentLimits(@RequestBody APIListRequest<CustomerPaymentLimit> apiRequest) {
        LogUtil.setLogPrefix(Endpoints.SAVE_CUSTOMER_PAYMENT_LIMITS);
        LogUtil.log(LOGGER, "update customer paymentlimits request" + apiRequest);
        return paymentLimitService.updateCustomerPaymentLimits(apiRequest.getDataList());
    }

    /**
     * Insert payment limit document.
     *
     * @param apiRequest the api request
     * @return the API response
     */
    @PostMapping(Endpoints.SAVE_PAYMENT_LIMITS_DOCUMENTS)
    public APIResponse<?> insertPaymentLimitDocument(@RequestBody APIListRequest<PaymentLimitDocuments> apiRequest) {
        LogUtil.setLogPrefix(Endpoints.SAVE_PAYMENT_LIMITS_DOCUMENTS);
        LogUtil.log(LOGGER, "update customer person paymentlimits request" + apiRequest);
        return paymentLimitService.insertPaymentLimitDocument(apiRequest.getDataList());
    }

}
