package com.connectpay.ib.payments.service.impl;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.connectpay.ib.enums.PaymentStatus;
import com.connectpay.ib.enums.ResponseStatus;
import com.connectpay.ib.payments.dao.CreditorDao;
import com.connectpay.ib.payments.dao.PaymentDao;
import com.connectpay.ib.payments.dao.PaymentLogDao;
import com.connectpay.ib.payments.dao.PaymentOtpDao;
import com.connectpay.ib.payments.service.PaymentService;
import com.connectpay.ib.utils.APIResponse;
import com.connectpay.ib.utils.ConnectPayUtils;
import com.connectpay.ib.utils.LogUtil;
import com.connectpay.payments.bean.CreditorDetails;
import com.connectpay.payments.bean.PaymentDetails;
import com.connectpay.payments.bean.PaymentDownloadRequest;
import com.connectpay.payments.bean.PaymentOperations;
import com.connectpay.payments.bean.PaymentOtherDetails;
import com.connectpay.payments.bean.PaymentOtpMapping;
import com.connectpay.payments.bean.PaymentStatusDetails;
import com.connectpay.payments.request.InitialPaymentRequest;
import com.connectpay.payments.request.PaymentServiceRequest;
import com.connectpay.payments.request.PaymentStructuresRequest;
import com.connectpay.payments.request.PaymentsListRequest;
import com.connectpay.request.APIListRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class PaymentServiceImpl.
 */
@Component
public class PaymentServiceImpl implements PaymentService {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger(PaymentServiceImpl.class);

    /** The payment dao. */
    @Autowired
    private PaymentDao paymentDao;

    /** The creditor dao. */
    @Autowired
    private CreditorDao creditorDao;

    /** The payment otp dao. */
    @Autowired
    private PaymentOtpDao paymentOtpDao;

    /** The payment log dao. */
    @Autowired
    private PaymentLogDao paymentLogDao;

    /**
     * Creates the payments.
     *
     * @param paymentServiceRequest the payment service request
     * @return the API response
     */
    @Override
    public APIResponse<?> createPayments(PaymentServiceRequest paymentServiceRequest) {
        String paymentReference = ConnectPayUtils.generatePaymentReference();
        CreditorDetails creditorDetails = creditorDao.insertCreditorDetails(paymentServiceRequest);
        PaymentStatusDetails paymentStatusDetails = paymentDao
                .getPaymentStatus(PaymentStatus.PO_INITIATED.getPaymentStatus());
        paymentServiceRequest.setPaymentReference(paymentServiceRequest.getPaymentReference() == null ? paymentReference
                : paymentServiceRequest.getPaymentReference());
        LogUtil.log(LOGGER, " :: Creating payment :: " + paymentReference);
        paymentServiceRequest.setPaymentStatusId(paymentStatusDetails.getId());
        paymentServiceRequest.setPaymentStatus(paymentStatusDetails.getStatus());
        paymentServiceRequest.setCreditorDetaildId(creditorDetails.getId());
        paymentServiceRequest.setCreateTime(paymentServiceRequest.getCreateTime() == null
                ? ConnectPayUtils.getCurrentDate(Calendar.getInstance().getTime())
                : paymentServiceRequest.getCreateTime());
        PaymentDetails paymentDetails = paymentDao.insertPaymentDetails(paymentServiceRequest, creditorDetails);
        paymentServiceRequest.setId(paymentDetails.getId());
        paymentLogDao.insertPaymentLog(paymentServiceRequest);

        LogUtil.log(LOGGER, " :: PO_INITIATED for :: " + paymentReference);
        return ConnectPayUtils.getSuccessResponse(paymentDetails);
    }

    /**
     * Update payments.
     *
     * @param paymentServiceRequest the payment service request
     * @return the API response
     */
    @Override
    public APIResponse<?> updatePayments(PaymentServiceRequest paymentServiceRequest) {
        PaymentStatusDetails paymentStatusDetails = paymentDao
                .getPaymentStatus(paymentServiceRequest.getPaymentStatus());
        LogUtil.log(LOGGER, " :: Updating payment details to :: " + paymentStatusDetails.getStatus()
                + " :: for reference :: " + paymentServiceRequest.getPaymentReference());
        paymentServiceRequest.setPaymentStatusId(paymentStatusDetails.getId());
        paymentServiceRequest.setPaymentStatus(paymentStatusDetails.getStatus());
        paymentServiceRequest.setCreateTime(paymentServiceRequest.getCreateTime() == null
                ? ConnectPayUtils.getCurrentDate(Calendar.getInstance().getTime())
                : paymentServiceRequest.getCreateTime());
        paymentServiceRequest.setUpdateTime(paymentServiceRequest.getUpdateTime() == null
                ? ConnectPayUtils.getCurrentDate(Calendar.getInstance().getTime())
                : paymentServiceRequest.getUpdateTime());
        paymentDao.updatePaymentDetails(paymentServiceRequest);

        paymentLogDao.insertPaymentLog(paymentServiceRequest);

        LogUtil.log(LOGGER, " :: Updated payment details to :: " + paymentStatusDetails.getStatus()
                + " :: for reference :: " + paymentServiceRequest.getPaymentReference());
        return ConnectPayUtils.getSuccessResponse(ResponseStatus.SUCCESS.status());
    }

    /**
     * Store payment request response.
     *
     * @param paymentStructuresRequest the payment structures request
     * @return the API response
     */
    @Override
    public APIResponse<?> storePaymentRequestResponse(PaymentStructuresRequest paymentStructuresRequest) {
        paymentDao.storePaymentRequestResponse(paymentStructuresRequest);
        return ConnectPayUtils.getSuccessResponse(ResponseStatus.SUCCESS);
    }

    /**
     * Gets the payment details.
     *
     * @param paymentReference the payment reference
     * @return the payment details
     */
    @Override
    public APIResponse<?> getPaymentDetails(String paymentReference) {
        return ConnectPayUtils.getSuccessResponse(paymentDao.getPaymentDetails(paymentReference));
    }

    /**
     * Gets the payment error details.
     *
     * @param providerErrorCode the provider error code
     * @return the payment error details
     */
    @Override
    public APIResponse<?> getPaymentErrorDetails(String providerErrorCode) {
        return ConnectPayUtils.getSuccessResponse(paymentDao.getPaymentErrors(providerErrorCode));
    }

    /**
     * Creates the initial payment request.
     *
     * @param initialPaymentRequest the initial payment request
     * @return the API response
     */
    @Override
    public APIResponse<?> createInitialPaymentRequest(InitialPaymentRequest initialPaymentRequest) {
        return ConnectPayUtils.getSuccessResponse(paymentDao.createInitialPaymentRequest(initialPaymentRequest));
    }

    /**
     * Update initial payment request.
     *
     * @param initialPaymentRequest the initial payment request
     * @return the API response
     */
    @Override
    public APIResponse<?> updateInitialPaymentRequest(InitialPaymentRequest initialPaymentRequest) {
        return ConnectPayUtils.getSuccessResponse(paymentDao.updateInitialPaymentRequest(initialPaymentRequest));
    }

    /**
     * Creates the payment otp mapping.
     *
     * @param paymentOtpMapping the payment otp mapping
     * @return the API response
     */
    @Override
    public APIResponse<?> createPaymentOtpMapping(PaymentOtpMapping paymentOtpMapping) {
        return ConnectPayUtils.getSuccessResponse(paymentOtpDao.createPaymentOtpMapping(paymentOtpMapping));
    }

    /**
     * Update payment otp mapping.
     *
     * @param paymentOtpMapping the payment otp mapping
     * @return the API response
     */
    @Override
    public APIResponse<?> updatePaymentOtpMapping(PaymentOtpMapping paymentOtpMapping) {
        return ConnectPayUtils.getSuccessResponse(paymentOtpDao.updatePaymentOtpMapping(paymentOtpMapping));
    }

    /**
     * Gets the payment otp mapping.
     *
     * @param phoneOtpReference the phone otp reference
     * @return the payment otp mapping
     */
    @Override
    public APIResponse<?> getPaymentOtpMapping(String phoneOtpReference) {
        return ConnectPayUtils.getSuccessResponse(paymentOtpDao.getPaymentOtpMapping(phoneOtpReference));
    }

    /**
     * Creates the currency exchange payment details.
     *
     * @param paymentServiceRequest the payment service request
     * @return the API response
     */
    @Override
    public APIResponse<?> createCurrencyExchangePaymentDetails(PaymentServiceRequest paymentServiceRequest) {
        return ConnectPayUtils
                .getSuccessResponse(paymentDao.insertCurrencyExchangePaymentDetails(paymentServiceRequest));
    }

    /**
     * Gets the payments list.
     *
     * @param paymentsListRequest the payments list request
     * @return the payments list
     */
    @Override
    public APIResponse<Map<String, Object>> getPaymentsList(PaymentsListRequest paymentsListRequest) {
        List<PaymentOperations> paymentOpsList = paymentDao.getPaymentsList(paymentsListRequest);

        if (paymentOpsList == null || paymentOpsList.isEmpty()) {
            return ConnectPayUtils.getSuccessResponse(new HashMap<>());
        }

        String searchString = paymentsListRequest.getSearchString() != null
                ? paymentsListRequest.getSearchString().toLowerCase()
                : "";

        List<PaymentOperations> filteredList = paymentOpsList.stream().filter(t -> t.getBeneficiaryDetails() != null
                && t.getBeneficiaryDetails().toLowerCase().contains(searchString)).collect(Collectors.toList());

        int startIndex = paymentsListRequest.getStartIndex();
        int lastIndex = paymentsListRequest.getStartIndex() + paymentsListRequest.getNumberOfRecords();

        Map<String, Object> operations = new HashMap<>();
        operations.put("totalCount", filteredList.size());

        // For instance: list size is 10 and request is (20,20)
        if (filteredList.size() <= startIndex) {
            operations.put("operations", Collections.emptyList());
            return ConnectPayUtils.getSuccessResponse(operations);
        }

        // For instance: list size is 10 and request (0,20) || size is 30 and
        // request is
        // (20, 20)
        if (filteredList.size() > startIndex && filteredList.size() <= lastIndex) {
            operations.put("operations", filteredList.subList(startIndex, filteredList.size()));
            return ConnectPayUtils.getSuccessResponse(operations);
        }

        operations.put("operations", filteredList.subList(startIndex, lastIndex));
        return ConnectPayUtils.getSuccessResponse(operations);
    }

    /**
     * Gets the payment other details by payment id.
     *
     * @param paymentId the payment id
     * @return the payment other details by payment id
     */
    @Override
    public APIResponse<?> getPaymentOtherDetailsByPaymentId(long paymentId) {
        PaymentOtherDetails paymentOtherDetails = paymentDao.getCurrencyExchangePaymentDetails(paymentId);
        return ConnectPayUtils.getSuccessResponse(paymentOtherDetails);
    }

    /**
     * Update payment by payment ref list.
     *
     * @param paymentServiceRequest the payment service request
     * @return the API response
     */
    @Override
    public APIResponse<?> updatePaymentByPaymentRefList(PaymentServiceRequest paymentServiceRequest) {
        PaymentStatusDetails paymentStatusDetails = paymentDao
                .getPaymentStatus(paymentServiceRequest.getPaymentStatus());

        for (String paymentRef : paymentServiceRequest.getPaymentRefList()) {
            PaymentDetails paymentDetails = paymentDao.getPaymentDetails(paymentRef);
            if (PaymentStatus.PO_SAVED.getPaymentStatus().equals(paymentDetails.getPaymentStatus())) {
                paymentDao.updatePaymentStatusByPaymentRefList(paymentRef, paymentStatusDetails.getId());
                paymentDetails.setPaymentStatus(paymentStatusDetails.getStatus());
                paymentDetails.setPaymentStatusId(paymentStatusDetails.getId());
            }
            BeanUtils.copyProperties(paymentDetails, paymentServiceRequest);
            paymentLogDao.insertPaymentLog(paymentServiceRequest);
        }

        LogUtil.log(LOGGER, " :: Updated payment status to :: " + paymentStatusDetails.getStatus()
                + " :: for reference :: " + paymentServiceRequest.getPaymentRefList());
        return ConnectPayUtils.getSuccessResponse(ResponseStatus.SUCCESS.status());
    }

    /**
     * Gets the payments details list.
     *
     * @param paymentDownloadRequest the payment download request
     * @return the payments details list
     */
    @Override
    public APIResponse<?> getPaymentsDetailsList(PaymentDownloadRequest paymentDownloadRequest) {
        return ConnectPayUtils.getSuccessResponse(paymentDao.getPaymentsDetailsList(paymentDownloadRequest));
    }

    /**
     * Gets the payment details by reference list.
     *
     * @param paymentRefList the payment ref list
     * @return the payment details by reference list
     */
    @Override
    public APIResponse<?> getPaymentDetailsByReferenceList(APIListRequest<String> paymentRefList) {
        return ConnectPayUtils
                .getSuccessResponse(paymentDao.getPaymentDetailsByReferenceList(paymentRefList.getDataList()));
    }

    /**
     * Update nano intiated count.
     *
     * @param paymentServiceRequest the payment service request
     */
    @Override
    public void updateNanoIntiatedCount(PaymentServiceRequest paymentServiceRequest) {
        paymentDao.updateNanoIntiatedCount(paymentServiceRequest);

    }

    /**
     * Update nano waiting count.
     *
     * @param paymentServiceRequest the payment service request
     */
    @Override
    public void updateNanoWaitingCount(PaymentServiceRequest paymentServiceRequest) {
        paymentDao.updateNanoWaitingCount(paymentServiceRequest);
    }

    @Override
    public APIResponse<?> removePaymentOtpMapping(String authCode) {
        paymentOtpDao.removePaymentOtpMapping(authCode);
        return ConnectPayUtils.getSuccessResponse(ResponseStatus.SUCCESS.name());
    }

    @Override
    public APIResponse<?> checkPaymentOtpMapping(String paymentReference, String authCode) {
        PaymentOtpMapping paymentOtpMapping = paymentOtpDao.checkPaymentOtpMapping(paymentReference, authCode);
        if (null != paymentOtpMapping) {
            return ConnectPayUtils.getSuccessResponse(paymentOtpMapping);
        }
        return ConnectPayUtils.getFailureResponse(ResponseStatus.FAILED, null, null, null);
    }

}
