package com.connectpay.ib.payments.service;

import java.util.List;

import com.connectpay.ib.utils.APIResponse;
import com.connectpay.payments.bean.PaymentLimitType;
import com.connectpay.payments.bean.PaymentStatus;
import com.connectpay.payments.bean.PaymentType;

// TODO: Auto-generated Javadoc
/**
 * The Interface MasterDataService.
 */
public interface MasterDataService {

    /**
     * Gets the payment currency.
     *
     * @return the payment currency
     */
    APIResponse<?> getPaymentCurrency();

    /**
     * Gets the payment statuses.
     *
     * @return the payment statuses
     */
    APIResponse<List<PaymentStatus>> getPaymentStatuses();

    /**
     * Gets the payment limit types.
     *
     * @return the payment limit types
     */
    APIResponse<List<PaymentLimitType>> getPaymentLimitTypes();

    /**
     * Gets the payment types.
     *
     * @return the payment types
     */
    APIResponse<List<PaymentType>> getPaymentTypes();
}
