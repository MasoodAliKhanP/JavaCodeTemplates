package com.connectpay.ib.payments.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connectpay.ib.payments.dao.MasterDataDao;
import com.connectpay.ib.payments.service.MasterDataService;
import com.connectpay.ib.utils.APIResponse;
import com.connectpay.ib.utils.ConnectPayUtils;
import com.connectpay.payments.bean.PaymentCurrency;
import com.connectpay.payments.bean.PaymentLimitType;
import com.connectpay.payments.bean.PaymentStatus;
import com.connectpay.payments.bean.PaymentType;

// TODO: Auto-generated Javadoc
/**
 * The Class MasterDataServiceImpl.
 */
@Service
public class MasterDataServiceImpl implements MasterDataService {

    /** The master data dao. */
    @Autowired
    private MasterDataDao masterDataDao;

    /**
     * Gets the payment currency.
     *
     * @return the payment currency
     */
    @Override
    public APIResponse<?> getPaymentCurrency() {
        List<PaymentCurrency> paymentCurrencyList = masterDataDao.getPaymentCurrency();
        return ConnectPayUtils.getSuccessResponse(paymentCurrencyList);
    }

    /**
     * Gets the payment statuses.
     *
     * @return the payment statuses
     */
    @Override
    public APIResponse<List<PaymentStatus>> getPaymentStatuses() {
        return ConnectPayUtils.getSuccessResponse(masterDataDao.getPaymentStatuses());
    }

    /**
     * Gets the payment limit types.
     *
     * @return the payment limit types
     */
    @Override
    public APIResponse<List<PaymentLimitType>> getPaymentLimitTypes() {
        return ConnectPayUtils.getSuccessResponse(masterDataDao.getPaymentLimitType());
    }

    /**
     * Gets the payment types.
     *
     * @return the payment types
     */
    @Override
    public APIResponse<List<PaymentType>> getPaymentTypes() {
        return ConnectPayUtils.getSuccessResponse(masterDataDao.getPaymentType());
    }

}
