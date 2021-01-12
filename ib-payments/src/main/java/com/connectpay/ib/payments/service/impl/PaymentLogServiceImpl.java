package com.connectpay.ib.payments.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.connectpay.ib.payments.dao.PaymentLogDao;
import com.connectpay.ib.payments.service.PaymentLogService;
import com.connectpay.ib.utils.APIResponse;
import com.connectpay.ib.utils.ConnectPayUtils;
import com.connectpay.payments.bean.PaymentLog;

// TODO: Auto-generated Javadoc
/**
 * The Class PaymentLogServiceImpl.
 */
@Service
public class PaymentLogServiceImpl implements PaymentLogService {

    /** The payment log dao. */
    @Autowired
    private PaymentLogDao paymentLogDao;

    /**
     * Gets the payment log by email and status.
     *
     * @param paymentLog the payment log
     * @return the payment log by email and status
     */
    @Override
    public APIResponse<?> getPaymentLogByEmailAndStatus(PaymentLog paymentLog) {
        PaymentLog paymentLogData = paymentLogDao.getPaymentLogByEmail(paymentLog.getPaymentId(),
                paymentLog.getPaymentStatusId());
        return ConnectPayUtils.getSuccessResponse(paymentLogData);
    }
}
