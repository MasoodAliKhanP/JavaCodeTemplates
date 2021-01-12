package com.connectpay.ib.payments.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connectpay.ib.enums.BulkPaymentFileEnum.PaymentFileStatus;
import com.connectpay.ib.payments.dao.BulkPaymentDao;
import com.connectpay.ib.payments.dao.MasterDataDao;
import com.connectpay.ib.payments.dao.PaymentDao;
import com.connectpay.ib.payments.service.BulkPaymentService;
import com.connectpay.ib.utils.APIResponse;
import com.connectpay.ib.utils.ConnectPayUtils;
import com.connectpay.ib.utils.LogUtil;
import com.connectpay.payments.bean.BulkPaymentData;
import com.connectpay.payments.bean.BulkPaymentFileResponse;
import com.connectpay.payments.bean.BulkPaymentFileStatus;
import com.connectpay.payments.bean.BulkPaymentMapping;
import com.connectpay.payments.bean.BulkPaymentType;
import com.connectpay.payments.bean.PaymentStatusDetails;
import com.connectpay.payments.request.PaymentSaveRequest;
import com.connectpay.payments.request.PaymentServiceRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class BulkPaymentServiceImpl.
 */
@Service
public class BulkPaymentServiceImpl implements BulkPaymentService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger(BulkPaymentServiceImpl.class);

    /** The bulk payment dao. */
    @Autowired
    private BulkPaymentDao bulkPaymentDao;

    /** The master data dao. */
    @Autowired
    private MasterDataDao masterDataDao;

    /** The payment dao. */
    @Autowired
    private PaymentDao paymentDao;

    /**
     * Save payment data.
     *
     * @param bulkPaymentData the bulk payment data
     * @return the API response
     */
    @Override
    public APIResponse<?> savePaymentData(BulkPaymentData bulkPaymentData) {
        LogUtil.log(LOGGER, "Saving bulk payment data");
        List<BulkPaymentType> bulkPaymentTypeList = masterDataDao.getBulkPaymentType();
        BulkPaymentType bulkPaymentType = bulkPaymentTypeList.stream()
                .filter(e -> e.getPaymentType().equals(bulkPaymentData.getBulkPaymentType().getPaymentType()))
                .findFirst().orElseGet(null);
        bulkPaymentData.getBulkPaymentType().setId(bulkPaymentType.getId());
        if (bulkPaymentData.getBulkPaymentFileStatus() != null) {
            List<BulkPaymentFileStatus> fileStatusList = masterDataDao.getBulkPaymentFileStatus();
            BulkPaymentFileStatus bulkPaymentFileStatus = fileStatusList.stream().filter(
                    e -> e.getFileStatus().equalsIgnoreCase(bulkPaymentData.getBulkPaymentFileStatus().getFileStatus()))
                    .findFirst().orElseGet(null);
            bulkPaymentData.getBulkPaymentFileStatus().setId(bulkPaymentFileStatus.getId());
        }
        BulkPaymentData bulkPaymentResponse = bulkPaymentDao.insertBulkPaymentData(bulkPaymentData);
        return ConnectPayUtils.getSuccessResponse(bulkPaymentResponse);
    }

    /**
     * Save bulk payment mapping data.
     *
     * @param apiRequest the api request
     * @return the API response
     */
    @Override
    public APIResponse<?> saveBulkPaymentMappingData(PaymentSaveRequest apiRequest) {
        bulkPaymentDao.insertBulkPaymentMapData(apiRequest.getDataList());
        return ConnectPayUtils.getSuccessResponse(null);
    }

    /**
     * Gets the bulk payment data.
     *
     * @param bulkPaymentReference the bulk payment reference
     * @return the bulk payment data
     */
    @Override
    public APIResponse<?> getBulkPaymentData(String bulkPaymentReference) {
        LogUtil.log(LOGGER, "Get bulkPayment data for " + bulkPaymentReference);
        BulkPaymentData bulkPaymentData = bulkPaymentDao.getBulkPaymentData(bulkPaymentReference);
        return ConnectPayUtils.getSuccessResponse(bulkPaymentData);
    }

    /**
     * Gets the bulk payment files.
     *
     * @param customerPartyId the customer party id
     * @return the bulk payment files
     */
    @Override
    public APIResponse<BulkPaymentFileResponse> getBulkPaymentFiles(String customerPartyId, int size, int startIndex) {
        LogUtil.log(LOGGER, "Get bulkPayment data by PartyId:: " + customerPartyId);
        BulkPaymentFileResponse bulkPaymentFileResponse = new BulkPaymentFileResponse();
        List<BulkPaymentFileStatus> fileStatusList = masterDataDao.getBulkPaymentFileStatus();
        List<Integer> fileIdList = fileStatusList.parallelStream()
                .filter(e -> e.getFileStatus().equalsIgnoreCase(PaymentFileStatus.BPO_INITIATED.toString())
                        || e.getFileStatus().equalsIgnoreCase(PaymentFileStatus.BPO_REMOVED.toString()))
                .map(BulkPaymentFileStatus::getId).collect(Collectors.toList());
        List<BulkPaymentData> paymentFilesList = bulkPaymentDao.getBulkPaymentDataByPartyId(customerPartyId, fileIdList,
                size, startIndex);
        bulkPaymentFileResponse.setBulkPaymentDataList(paymentFilesList);
        bulkPaymentFileResponse.setTotalRecords(bulkPaymentDao.getBulkPaymentFileCount(customerPartyId, fileIdList));
        return ConnectPayUtils.getSuccessResponse(bulkPaymentFileResponse);
    }

    /**
     * Update bulk payment file status.
     *
     * @param bulkPaymentData the bulk payment data
     * @return the API response
     */
    @Override
    public APIResponse<?> updateBulkPaymentFileStatus(BulkPaymentData bulkPaymentData) {
        List<BulkPaymentFileStatus> fileStatusList = masterDataDao.getBulkPaymentFileStatus();
        BulkPaymentFileStatus bulkPaymentFileStatus = fileStatusList.stream().filter(
                e -> e.getFileStatus().equalsIgnoreCase(bulkPaymentData.getBulkPaymentFileStatus().getFileStatus()))
                .findFirst().orElseGet(null);
        bulkPaymentData.getBulkPaymentFileStatus().setId(bulkPaymentFileStatus.getId());
        bulkPaymentDao.updateBulkPaymentFileStatus(bulkPaymentData);
        return ConnectPayUtils.getSuccessResponse(null);
    }

    /**
     * Update bulk payment transaction data by ref id.
     *
     * @param bulkPaymentData the bulk payment data
     * @return the API response
     */
    @Override
    public APIResponse<?> updateBulkPaymentTransactionDataByRefId(BulkPaymentData bulkPaymentData) {
        bulkPaymentDao.updateBulkPaymentTransactionDetails(bulkPaymentData);
        return ConnectPayUtils.getSuccessResponse(null);
    }

    /**
     * Save bulk payment mapping data.
     *
     * @param bulkPaymentMapping the bulk payment mapping
     * @return the API response
     */
    @Override
    public APIResponse<?> saveBulkPaymentMappingData(BulkPaymentMapping bulkPaymentMapping) {
        bulkPaymentDao.insertBulkPaymentMapping(bulkPaymentMapping);
        return ConnectPayUtils.getSuccessResponse(null);
    }

    /**
     * Update email by payment reference.
     *
     * @param paymentServiceRequest the payment service request
     * @return the API response
     */
    @Override
    public APIResponse<?> updatePersonIdByPaymentReference(PaymentServiceRequest paymentServiceRequest) {
        bulkPaymentDao.updatePersonIdByPaymentReference(paymentServiceRequest);
        return ConnectPayUtils.getSuccessResponse(null);
    }

    /**
     * Save bulk payment error messages.
     *
     * @param paymentServiceRequest the payment service request
     * @return the API response
     */
    @Override
    public APIResponse<?> saveBulkPaymentErrorMessages(PaymentServiceRequest paymentServiceRequest) {
        PaymentStatusDetails paymentStatusDetails = paymentDao
                .getPaymentStatus(paymentServiceRequest.getPaymentStatus());
        LogUtil.log(LOGGER, " :: Updating payment details to :: " + paymentStatusDetails.getStatus()
                + " :: for reference :: " + paymentServiceRequest.getPaymentReference());
        paymentServiceRequest.setPaymentStatusId(paymentStatusDetails.getId());
        paymentServiceRequest.setPaymentStatus(paymentStatusDetails.getStatus());
        bulkPaymentDao.saveBulkPaymentErrorMessages(paymentServiceRequest);
        return ConnectPayUtils.getSuccessResponse(null);
    }

    @Override
    public APIResponse<List<BulkPaymentMapping>> getBulkPaymentMappingByRefId(String bulkRefId) {
        List<BulkPaymentMapping> list = bulkPaymentDao.getBulkpaymentMappingByBulkRefId(bulkRefId);
        return ConnectPayUtils.getSuccessResponse(list);
    }

    @Override
    public APIResponse<?> updateBulkDataPayerIbanByBulkref(String payerIban, String bulkRefId) {
        bulkPaymentDao.updateBulkDataPayerIbanByBulkref(payerIban, bulkRefId);
        return ConnectPayUtils.getSuccessResponse(null);
    }

    @Override
    public APIResponse<?> getCustomerPartyIdByBulkReference(String bulkPaymentReference) {
        LogUtil.log(LOGGER, "Get customer partyId data for " + bulkPaymentReference);
        BulkPaymentData bulkPaymentData = bulkPaymentDao.getCustomerPartyIdByBulkReference(bulkPaymentReference);
        return ConnectPayUtils.getSuccessResponse(bulkPaymentData);
    }

}
