package com.connectpay.ib.payments.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.connectpay.ib.payments.constants.Endpoints;
import com.connectpay.ib.payments.service.MasterDataService;
import com.connectpay.ib.utils.APIResponse;
import com.connectpay.ib.utils.LogUtil;
import com.connectpay.payments.bean.PaymentLimitType;
import com.connectpay.payments.bean.PaymentStatus;
import com.connectpay.payments.bean.PaymentType;

// TODO: Auto-generated Javadoc
/**
 * The Class MasterDataController.
 */
@RestController
@RequestMapping(value = Endpoints.MASTER_DATA_BASE_PATH)
public class MasterDataController {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger(MasterDataController.class);

    /** The master data service. */
    @Autowired
    private MasterDataService masterDataService;

    /**
     * Gets the currency list.
     *
     * @return the currency list
     */
    @GetMapping(value = Endpoints.GET_PAYMENT_CURRENCY)
    public APIResponse<?> getCurrencyList() {
        LogUtil.setLogPrefix(Endpoints.GET_PAYMENT_CURRENCY);
        LogUtil.log(LOGGER, "Get Payment Currencies :: ");
        return masterDataService.getPaymentCurrency();
    }

    /**
     * Gets the payment statuses.
     *
     * @return the payment statuses
     */
    @GetMapping(value = Endpoints.GET_PAYMENT_STATUSES)
    public APIResponse<List<PaymentStatus>> getPaymentStatuses() {
        LogUtil.setLogPrefix(Endpoints.GET_PAYMENT_STATUSES);
        LogUtil.log(LOGGER, "Get payment statuses request");
        return masterDataService.getPaymentStatuses();
    }

    /**
     * Gets the payment limit types.
     *
     * @return the payment limit types
     */
    @GetMapping(value = Endpoints.GET_PAYMENT_LIMIT_TYPES)
    public APIResponse<List<PaymentLimitType>> getPaymentLimitTypes() {
        LogUtil.setLogPrefix(Endpoints.GET_PAYMENT_LIMIT_TYPES);
        LogUtil.log(LOGGER, "Get payment limit types");
        return masterDataService.getPaymentLimitTypes();
    }

    /**
     * Gets the payment types.
     *
     * @return the payment types
     */
    @GetMapping(value = Endpoints.GET_PAYMENT_TYPES)
    public APIResponse<List<PaymentType>> getPaymentTypes() {
        LogUtil.setLogPrefix(Endpoints.GET_PAYMENT_TYPES);
        LogUtil.log(LOGGER, "Get payment types");
        return masterDataService.getPaymentTypes();
    }

}
