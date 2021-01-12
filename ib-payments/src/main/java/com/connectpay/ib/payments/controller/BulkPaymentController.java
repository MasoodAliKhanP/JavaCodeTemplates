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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.connectpay.ib.payments.constants.Endpoints;
import com.connectpay.ib.payments.service.BulkPaymentService;
import com.connectpay.ib.utils.APIResponse;
import com.connectpay.ib.utils.LogUtil;
import com.connectpay.payments.bean.BulkPaymentData;
import com.connectpay.payments.bean.BulkPaymentFileResponse;
import com.connectpay.payments.bean.BulkPaymentMapping;
import com.connectpay.payments.request.PaymentSaveRequest;
import com.connectpay.payments.request.PaymentServiceRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class BulkPaymentController.
 */
@RestController
@RequestMapping(Endpoints.BULK_PAYMENT_BASE_PATH)
public class BulkPaymentController {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger(BulkPaymentController.class);

    /** The bulk payment service. */
    @Autowired
    private BulkPaymentService bulkPaymentService;

    /**
     * Save bulkpayment data.
     *
     * @param bulkPaymentData the bulk payment data
     * @return the API response
     */
    @PostMapping(Endpoints.SAVE_BULK_PAYMENT_DATA)
    public APIResponse<?> saveBulkpaymentData(@RequestBody BulkPaymentData bulkPaymentData) {
        LogUtil.setLogPrefix(Endpoints.SAVE_BULK_PAYMENT_DATA + "/" + bulkPaymentData.getEmail());
        LogUtil.log(LOGGER, "bulkpayment data request is :: " + bulkPaymentData);
        return bulkPaymentService.savePaymentData(bulkPaymentData);
    }

    /**
     * Save bulk payment mapping data.
     *
     * @param apiRequest the api request
     * @return the API response
     */
    @PostMapping(Endpoints.SAVE_BULK_PAYMENT_MAPPING_DATA_LIST)
    public APIResponse<?> saveBulkPaymentMappingData(@RequestBody PaymentSaveRequest apiRequest) {
        LogUtil.setLogPrefix(Endpoints.SAVE_BULK_PAYMENT_MAPPING_DATA_LIST);
        LogUtil.log(LOGGER, "saving bulkpayment mapping data:: ");
        return bulkPaymentService.saveBulkPaymentMappingData(apiRequest);
    }

    /**
     * Gets the bulk payment data.
     *
     * @param bulkPaymentReference the bulk payment reference
     * @return the bulk payment data
     */
    @PostMapping(Endpoints.GET_BULK_PAYMENT_DATA)
    public APIResponse<?> getBulkPaymentData(@PathVariable("bulkPaymentReference") String bulkPaymentReference) {
        LogUtil.setLogPrefix("/GetBulkPaymentData/" + bulkPaymentReference);
        LogUtil.log(LOGGER, "get bulk payment data for:: " + bulkPaymentReference);
        return bulkPaymentService.getBulkPaymentData(bulkPaymentReference);
    }

    @GetMapping(Endpoints.GET_CUSTOMER_PARTY_ID_BY_BULK_REFERENCE)
    public APIResponse<?> getCustomerPartyIdByBulkReference(
            @PathVariable("bulkPaymentReference") String bulkPaymentReference) {
        LogUtil.setLogPrefix(Endpoints.GET_CUSTOMER_PARTY_ID_BY_BULK_REFERENCE + bulkPaymentReference);
        LogUtil.log(LOGGER, "get bulk payment data for:: " + bulkPaymentReference);
        return bulkPaymentService.getCustomerPartyIdByBulkReference(bulkPaymentReference);
    }

    /**
     * Gets the bulk payment files.
     *
     * @param customerPartyId the customer party id
     * @return the bulk payment files
     */
    @GetMapping(Endpoints.GET_BULK_PAYMENT_FILES)
    public APIResponse<BulkPaymentFileResponse> getBulkPaymentFiles(
            @PathVariable("customerPartyId") String customerPartyId, @RequestParam int size,
            @RequestParam int startIndex) {
        LogUtil.setLogPrefix("/GetBulkPaymentFiles/" + customerPartyId + " with index starting at :" + startIndex
                + " size :" + size);
        LogUtil.log(LOGGER, "get bulk payment data for partyId :: " + customerPartyId);
        return bulkPaymentService.getBulkPaymentFiles(customerPartyId, size, startIndex);
    }

    /**
     * Update bulk payment file status.
     *
     * @param bulkPaymentData the bulk payment data
     * @return the API response
     */
    @PostMapping(Endpoints.UPDATE_BULK_PAYMENT_FILE_STATUS)
    public APIResponse<?> updateBulkPaymentFileStatus(@RequestBody BulkPaymentData bulkPaymentData) {
        LogUtil.setLogPrefix(
                Endpoints.UPDATE_BULK_PAYMENT_FILE_STATUS + "/" + bulkPaymentData.getBulkPaymentReference());
        LogUtil.log(LOGGER, "update bulk payment  file status for ref :: " + bulkPaymentData.getBulkPaymentReference());
        return bulkPaymentService.updateBulkPaymentFileStatus(bulkPaymentData);
    }

    /**
     * Update bulk payment transaction by ref id.
     *
     * @param bulkPaymentData the bulk payment data
     * @return the API response
     */
    @PostMapping(Endpoints.UPDATE_BULK_PAYMENT_TRANSACTION_BY_REF_ID)
    public APIResponse<?> updateBulkPaymentTransactionByRefId(@RequestBody BulkPaymentData bulkPaymentData) {
        LogUtil.setLogPrefix(
                Endpoints.UPDATE_BULK_PAYMENT_TRANSACTION_BY_REF_ID + "/" + bulkPaymentData.getBulkPaymentReference());
        LogUtil.log(LOGGER,
                "update bulk payment Transaction Data by RefId :: " + bulkPaymentData.getBulkPaymentReference());
        return bulkPaymentService.updateBulkPaymentTransactionDataByRefId(bulkPaymentData);
    }

    /**
     * Save bulk payment mapping.
     *
     * @param bulkPaymentMapping the bulk payment mapping
     * @return the API response
     */
    @PostMapping(Endpoints.SAVE_BULK_PAYMENT_MAPPING_DATA)
    public APIResponse<?> saveBulkPaymentMapping(@RequestBody BulkPaymentMapping bulkPaymentMapping) {
        LogUtil.setLogPrefix(
                Endpoints.SAVE_BULK_PAYMENT_MAPPING_DATA + "/" + bulkPaymentMapping.getBulkPaymentReference());
        LogUtil.log(LOGGER,
                "save bulk payment mapping Data by RefId :: " + bulkPaymentMapping.getBulkPaymentReference());
        return bulkPaymentService.saveBulkPaymentMappingData(bulkPaymentMapping);
    }

    /**
     * Update email by payment reference id.
     *
     * @param paymentServiceRequest the payment service request
     * @return the API response
     */
    @PutMapping(Endpoints.UPDATE_PERSON_BY_REF_ID)
    public APIResponse<?> updatePersonIdByPaymentReference(@RequestBody PaymentServiceRequest paymentServiceRequest) {
        LogUtil.setLogPrefix(Endpoints.UPDATE_PERSON_BY_REF_ID + "updating person Id with paymentReference ::"
                + paymentServiceRequest.getPaymentReference());

        LogUtil.log(LOGGER, "updating person Id by paymentReferenceId" + paymentServiceRequest.getPaymentReference());
        return bulkPaymentService.updatePersonIdByPaymentReference(paymentServiceRequest);
    }

    /**
     * Save bulk payment error messages.
     *
     * @param paymentServiceRequest the payment service request
     * @return the API response
     */
    @PostMapping(Endpoints.SAVE_ERROR_MESSAGES)
    public APIResponse<?> saveBulkPaymentErrorMessages(@RequestBody PaymentServiceRequest paymentServiceRequest) {
        LogUtil.setLogPrefix(Endpoints.SAVE_ERROR_MESSAGES + "saving error messages with ::"
                + paymentServiceRequest.getApiErrorCode());

        LogUtil.log(LOGGER, "saving error messages with ::" + paymentServiceRequest.getApiErrorCode());
        return bulkPaymentService.saveBulkPaymentErrorMessages(paymentServiceRequest);
    }

    @GetMapping(Endpoints.GET_BULK_PAYMENT_DATA_WITH_REF_ID)
    public APIResponse<List<BulkPaymentMapping>> getBulkPaymentMappingByRefId(
            @PathVariable("bulkRefId") String bulkRefId) {
        LogUtil.setLogPrefix("GET_BULK_PAYMENT_DATA_WITH_REF_ID/" + bulkRefId);
        LogUtil.log(LOGGER, "Get Mapping data with RefId" + bulkRefId);
        return bulkPaymentService.getBulkPaymentMappingByRefId(bulkRefId);
    }

    @PutMapping(Endpoints.UPDATE_PAYER_BY_BULK_REF_ID)
    public APIResponse<?> updateBulkDataPayerIbanByBulkref(@PathVariable("payerIban") String payerIban,
            @PathVariable("bulkRefId") String bulkRefId) {
        LogUtil.setLogPrefix("UPDATE_PAYER_IBAN_BY_BULK_REF/" + bulkRefId);
        LogUtil.log(LOGGER, "Get Mapping data with RefId" + bulkRefId);
        return bulkPaymentService.updateBulkDataPayerIbanByBulkref(payerIban, bulkRefId);
    }

}
