package com.connectpay.ib.payments.dao;

import java.util.List;

import com.connectpay.payments.bean.BulkPaymentFileStatus;
import com.connectpay.payments.bean.BulkPaymentType;
import com.connectpay.payments.bean.PaymentCurrency;
import com.connectpay.payments.bean.PaymentLimitType;
import com.connectpay.payments.bean.PaymentStatus;
import com.connectpay.payments.bean.PaymentType;

// TODO: Auto-generated Javadoc
/**
 * The Interface MasterDataDao.
 */
public interface MasterDataDao {

    /**
     * Gets the payment currency.
     *
     * @return the payment currency
     */
    List<PaymentCurrency> getPaymentCurrency();

    /**
     * Gets the payment statuses.
     *
     * @return the payment statuses
     */
    List<PaymentStatus> getPaymentStatuses();

    /**
     * Gets the bulk payment type.
     *
     * @return the bulk payment type
     */
    List<BulkPaymentType> getBulkPaymentType();

    /**
     * Gets the bulk payment file status.
     *
     * @return the bulk payment file status
     */
    List<BulkPaymentFileStatus> getBulkPaymentFileStatus();

    /**
     * Gets the payment limit type.
     *
     * @return the payment limit type
     */
    List<PaymentLimitType> getPaymentLimitType();

    /**
     * Gets the payment type.
     *
     * @return the payment type
     */
    List<PaymentType> getPaymentType();

}
