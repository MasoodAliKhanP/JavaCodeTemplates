package com.connectpay.ib.payments.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connectpay.ib.payments.dao.PaymentLimitDao;
import com.connectpay.ib.payments.service.PaymentLimitService;
import com.connectpay.ib.utils.APIResponse;
import com.connectpay.ib.utils.ConnectPayUtils;
import com.connectpay.payments.bean.CustomerPaymentLimit;
import com.connectpay.payments.bean.CustomerPersonPaymentLimit;
import com.connectpay.payments.bean.DefaultPaymentLimit;
import com.connectpay.payments.bean.PaymentLimitDocuments;
import com.connectpay.payments.request.PaymentLimitRequest;
import com.connectpay.payments.response.PaymentDetailsResponse;

// TODO: Auto-generated Javadoc
/**
 * The Class PaymentLimitServiceImpl.
 */
@Service
public class PaymentLimitServiceImpl implements PaymentLimitService {

    /** The payment limit dao. */
    @Autowired
    private PaymentLimitDao paymentLimitDao;

    /**
     * Gets the payment limits by customer id.
     *
     * @param customerId the customer id
     * @return the payment limits by customer id
     */
    @Override
    public APIResponse<List<CustomerPaymentLimit>> getPaymentLimitsByCustomerId(long customerId) {
        List<CustomerPaymentLimit> customerLimitList = paymentLimitDao.getPaymentLimitsByCustomerId(customerId);
        return ConnectPayUtils.getSuccessResponse(customerLimitList);
    }

    /**
     * Gets the payment limits by customer person map id.
     *
     * @param customerPersonMapId the customer person map id
     * @return the payment limits by customer person map id
     */
    @Override
    public APIResponse<List<CustomerPersonPaymentLimit>> getPaymentLimitsByCustomerPersonMapId(
            long customerPersonMapId) {
        List<CustomerPersonPaymentLimit> customerPersonPaymentLimits = paymentLimitDao
                .getPaymentLimitsByCustomerPersonMapId(customerPersonMapId);
        return ConnectPayUtils.getSuccessResponse(customerPersonPaymentLimits);
    }

    /**
     * Gets the payment data by party id and PersonId.
     *
     * @param paymentLimitRequest the payment limit request
     * @return the payment data by party id and PersonId
     */
    @Override
    public APIResponse<List<PaymentDetailsResponse>> getPaymentDataByPartyIdAndPersonId(
            PaymentLimitRequest paymentLimitRequest) {
        List<PaymentDetailsResponse> paymentData = paymentLimitDao
                .getPaymentDataByPartyIdAndPersonId(paymentLimitRequest);
        return ConnectPayUtils.getSuccessResponse(paymentData);
    }

    /**
     * Gets the payment data by party id.
     *
     * @param paymentLimitRequest the payment limit request
     * @return the payment data by party id
     */
    @Override
    public APIResponse<List<PaymentDetailsResponse>> getPaymentDataByPartyId(PaymentLimitRequest paymentLimitRequest) {
        List<PaymentDetailsResponse> paymentData = paymentLimitDao.getPaymentDataByPartyId(paymentLimitRequest);
        return ConnectPayUtils.getSuccessResponse(paymentData);
    }

    /**
     * Gets the default limits by customer type.
     *
     * @param customerTypeId the customer type id
     * @return the default limits by customer type
     */
    @Override
    public APIResponse<List<DefaultPaymentLimit>> getDefaultLimitsByCustomerType(int customerTypeId) {
        List<DefaultPaymentLimit> paymentLimits = paymentLimitDao.getDefaultLimitsByCustomerType(customerTypeId);
        return ConnectPayUtils.getSuccessResponse(paymentLimits);

    }

    /**
     * Insert customer limits.
     *
     * @param customerPaymentLimitList the customer payment limit list
     * @return the API response
     */
    @Override
    public APIResponse<?> insertCustomerLimits(List<CustomerPaymentLimit> customerPaymentLimitList) {
        paymentLimitDao.insertCustomerLimits(customerPaymentLimitList);
        paymentLimitDao.insertCustomerLimitsLog(customerPaymentLimitList);
        return ConnectPayUtils.getSuccessResponse(null);
    }

    /**
     * Insert customer person limits.
     *
     * @param customerPersonPaymentLimitList the customer person payment limit list
     * @return the API response
     */
    @Override
    public APIResponse<?> insertCustomerPersonLimits(List<CustomerPersonPaymentLimit> customerPersonPaymentLimitList) {
        paymentLimitDao.insertCustomerPersonLimits(customerPersonPaymentLimitList);
        paymentLimitDao.insertCustomerPersonLimitsLog(customerPersonPaymentLimitList);
        return ConnectPayUtils.getSuccessResponse(null);
    }

    /**
     * Update customer person payment limits.
     *
     * @param customerPersonPaymentLimitList the customer person payment limit list
     * @return the API response
     */
    @Override
    public APIResponse<?> updateCustomerPersonPaymentLimits(
            List<CustomerPersonPaymentLimit> customerPersonPaymentLimitList) {
        paymentLimitDao.updateCustomerPersonLimits(customerPersonPaymentLimitList);
        paymentLimitDao.inactivateCustomerPersonPaymentLimitsLog(customerPersonPaymentLimitList);
        paymentLimitDao.insertCustomerPersonLimitsLog(customerPersonPaymentLimitList);
        return ConnectPayUtils.getSuccessResponse(null);
    }

    /**
     * Update customer payment limits.
     *
     * @param customerPaymentLimitList the customer payment limit list
     * @return the API response
     */
    @Override
    public APIResponse<?> updateCustomerPaymentLimits(List<CustomerPaymentLimit> customerPaymentLimitList) {
        paymentLimitDao.updateCustomerLimits(customerPaymentLimitList);
        paymentLimitDao.inactivateCustomerPaymentLimitsLog(customerPaymentLimitList);
        paymentLimitDao.insertCustomerLimitsLog(customerPaymentLimitList);
        return ConnectPayUtils.getSuccessResponse(null);
    }

    /**
     * Insert payment limit document.
     *
     * @param paymentLimitDocumentsList the payment limit documents list
     * @return the API response
     */
    @Override
    public APIResponse<?> insertPaymentLimitDocument(List<PaymentLimitDocuments> paymentLimitDocumentsList) {
        paymentLimitDao.insertPaymentLimitDocument(paymentLimitDocumentsList);
        return ConnectPayUtils.getSuccessResponse(null);
    }

}
