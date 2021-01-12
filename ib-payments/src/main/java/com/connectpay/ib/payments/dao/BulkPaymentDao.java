package com.connectpay.ib.payments.dao;

import java.util.List;

import com.connectpay.payments.bean.BulkPaymentData;
import com.connectpay.payments.bean.BulkPaymentMapping;
import com.connectpay.payments.request.PaymentServiceRequest;

// TODO: Auto-generated Javadoc
/**
 * The Interface BulkPaymentDao.
 */
public interface BulkPaymentDao {

    /**
     * Insert bulk payment data.
     *
     * @param bulkPaymentData the bulk payment data
     * @return the bulk payment data
     */
    BulkPaymentData insertBulkPaymentData(BulkPaymentData bulkPaymentData);

    /**
     * Insert bulk payment map data.
     *
     * @param mappingList the mapping list
     */
    void insertBulkPaymentMapData(List<BulkPaymentMapping> mappingList);

    /**
     * Gets the bulk payment data.
     *
     * @param bulkPaymentReference the bulk payment reference
     * @return the bulk payment data
     */
    BulkPaymentData getBulkPaymentData(String bulkPaymentReference);

    /**
     * Gets the bulk payment data by party id.
     *
     * @param customerPartyId the customer party id
     * @param fileIdList
     * @param startIndex
     * @param size
     * @return the bulk payment data by party id
     */
    List<BulkPaymentData> getBulkPaymentDataByPartyId(String customerPartyId, List<Integer> fileIdList, int size,
            int startIndex);

    /**
     * Update bulk payment file status.
     *
     * @param bulkPaymentData the bulk payment data
     */
    void updateBulkPaymentFileStatus(BulkPaymentData bulkPaymentData);

    /**
     * Update bulk payment transaction details.
     *
     * @param bulkPaymentData the bulk payment data
     */
    void updateBulkPaymentTransactionDetails(BulkPaymentData bulkPaymentData);

    /**
     * Insert bulk payment mapping.
     *
     * @param bulkPaymentMapping the bulk payment mapping
     */
    void insertBulkPaymentMapping(BulkPaymentMapping bulkPaymentMapping);

    /**
     * Update Person Id by payment reference.
     *
     * @param paymentServiceRequest the payment service request
     */
    void updatePersonIdByPaymentReference(PaymentServiceRequest paymentServiceRequest);

    /**
     * Save bulk payment error messages.
     *
     * @param paymentServiceRequest the payment service request
     */
    void saveBulkPaymentErrorMessages(PaymentServiceRequest paymentServiceRequest);

    List<BulkPaymentMapping> getBulkpaymentMappingByBulkRefId(String bulkRefId);

    void updateBulkDataPayerIbanByBulkref(String payerIban, String bulkRefId);

    BulkPaymentData getCustomerPartyIdByBulkReference(String bulkPaymentReference);

    int getBulkPaymentFileCount(String customerPartyId, List<Integer> fileStatusIdList);

}
