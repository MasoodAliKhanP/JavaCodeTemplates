package com.connectpay.ib.payments.service;

import java.util.List;

import com.connectpay.ib.utils.APIResponse;
import com.connectpay.payments.bean.BulkPaymentData;
import com.connectpay.payments.bean.BulkPaymentFileResponse;
import com.connectpay.payments.bean.BulkPaymentMapping;
import com.connectpay.payments.request.PaymentSaveRequest;
import com.connectpay.payments.request.PaymentServiceRequest;

// TODO: Auto-generated Javadoc
/**
 * The Interface BulkPaymentService.
 */
public interface BulkPaymentService {

    /**
     * Save payment data.
     *
     * @param bulkPaymentData the bulk payment data
     * @return the API response
     */
    APIResponse<?> savePaymentData(BulkPaymentData bulkPaymentData);

    /**
     * Save bulk payment mapping data.
     *
     * @param apiRequest the api request
     * @return the API response
     */
    APIResponse<?> saveBulkPaymentMappingData(PaymentSaveRequest apiRequest);

    /**
     * Gets the bulk payment data.
     *
     * @param bulkPaymentReference the bulk payment reference
     * @return the bulk payment data
     */
    APIResponse<?> getBulkPaymentData(String bulkPaymentReference);

    /**
     * Gets the bulk payment files.
     *
     * @param customerPartyId the customer party id
     * @param startindex
     * @param size
     * @return the bulk payment files
     */
    APIResponse<BulkPaymentFileResponse> getBulkPaymentFiles(String customerPartyId, int size, int startIndex);

    /**
     * Update bulk payment file status.
     *
     * @param bulkPaymentData the bulk payment data
     * @return the API response
     */
    APIResponse<?> updateBulkPaymentFileStatus(BulkPaymentData bulkPaymentData);

    /**
     * Update bulk payment transaction data by ref id.
     *
     * @param bulkPaymentData the bulk payment data
     * @return the API response
     */
    APIResponse<?> updateBulkPaymentTransactionDataByRefId(BulkPaymentData bulkPaymentData);

    /**
     * Save bulk payment mapping data.
     *
     * @param bulkPaymentMapping the bulk payment mapping
     * @return the API response
     */
    APIResponse<?> saveBulkPaymentMappingData(BulkPaymentMapping bulkPaymentMapping);

    /**
     * Update Person Id by payment reference.
     *
     * @param paymentServiceRequest the payment service request
     * @return the API response
     */
    APIResponse<?> updatePersonIdByPaymentReference(PaymentServiceRequest paymentServiceRequest);

    /**
     * Save bulk payment error messages.
     *
     * @param paymentServiceRequest the payment service request
     * @return the API response
     */
    APIResponse<?> saveBulkPaymentErrorMessages(PaymentServiceRequest paymentServiceRequest);

    APIResponse<List<BulkPaymentMapping>> getBulkPaymentMappingByRefId(String bulkRefId);

    APIResponse<?> updateBulkDataPayerIbanByBulkref(String payerIban, String bulkRefId);

    APIResponse<?> getCustomerPartyIdByBulkReference(String bulkPaymentReference);

}
