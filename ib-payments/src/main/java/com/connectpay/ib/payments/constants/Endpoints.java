package com.connectpay.ib.payments.constants;

// TODO: Auto-generated Javadoc
/**
 * The Interface Endpoints.
 */
public interface Endpoints {

    /** The payment base path. */
    String PAYMENT_BASE_PATH = "/payments";

    /** The create payments. */
    String CREATE_PAYMENTS = "/create";

    /** The update payments. */
    String UPDATE_PAYMENTS = "/update";

    /** The create initial payments. */
    String CREATE_INITIAL_PAYMENTS = "/initial/request";

    /** The payment request response. */
    String PAYMENT_REQUEST_RESPONSE = "/request/reponse";

    /** The get payment details. */
    String GET_PAYMENT_DETAILS = "/getDetails/{paymentReference}";

    /** The get payment error details. */
    String GET_PAYMENT_ERROR_DETAILS = "/get/{providerErrorCode}";

    /** The update payments status. */
    String UPDATE_PAYMENTS_STATUS = "/update/status";

    /** The payments otp mapping. */
    String PAYMENTS_OTP_MAPPING = "/otp/mapping";

    /** The payments otp mapping. */
    String REMOVE_OTP_MAPPING = "/otp/mapping/authCode/{authCode}";

    /** The get payments otp mapping. */
    String GET_PAYMENTS_OTP_MAPPING = "/otp/mapping/{phoneOtpReference}";

    /** The get payments list. */
    String GET_PAYMENTS_LIST = "/getPaymentsList";

    /** The get payment statuses. */
    String GET_PAYMENT_STATUSES = "/getPaymentStatuses";

    /** The master data base path. */
    String MASTER_DATA_BASE_PATH = "/payments/masterData";

    /** The get payment currency. */
    String GET_PAYMENT_CURRENCY = "/getPaymentCurrency";

    /** The create payment currency exchange. */
    String CREATE_PAYMENT_CURRENCY_EXCHANGE = "/createExchangeCurrencyPayment";

    /** The log controller base path. */
    String LOG_CONTROLLER_BASE_PATH = "/payments/log";

    /** The get log data by email and status. */
    String GET_LOG_DATA_BY_ID_AND_STATUS = "/getLogDataByIdAndStatus";

    /** The get payment other details by payment id. */
    String GET_PAYMENT_OTHER_DETAILS_BY_PAYMENT_ID = "/getCurrExchangeDetailsByPaymentId/{paymentId}";

    /** The update payment status by ref list. */
    String UPDATE_PAYMENT_STATUS_BY_REF_LIST = "/updatePaymentStatusByRefList";

    /** The bulk payment base path. */
    String BULK_PAYMENT_BASE_PATH = "/bulkPayment";

    /** The save bulk payment data. */
    String SAVE_BULK_PAYMENT_DATA = "/saveBulkPaymentData";

    /** The save bulk payment mapping data list. */
    String SAVE_BULK_PAYMENT_MAPPING_DATA_LIST = "/saveBulkPaymentMappingDataList";

    /** The get bulk payment data. */
    String GET_BULK_PAYMENT_DATA = "/getBulkPaymentData/{bulkPaymentReference}";

    /** The get payments details list. */
    String GET_PAYMENTS_DETAILS_LIST = "/getList";

    /** The get payment details by ref list. */
    String GET_PAYMENT_DETAILS_BY_REF_LIST = "/getPaymentDetailsByRefList";

    /** The get bulk payment files. */
    String GET_BULK_PAYMENT_FILES = "/paymentFiles/{customerPartyId}";

    /** The update bulk payment file status. */
    String UPDATE_BULK_PAYMENT_FILE_STATUS = "/paymentFiles/updateFileStatus";

    /** The update bulk payment transaction by ref id. */
    String UPDATE_BULK_PAYMENT_TRANSACTION_BY_REF_ID = "/paymentFiles/updateTransactionData";

    /** The save bulk payment mapping data. */
    String SAVE_BULK_PAYMENT_MAPPING_DATA = "/saveBulkPaymentMappingData";

    /** The get payment limit types. */
    String GET_PAYMENT_LIMIT_TYPES = "/getPaymentLimitTypes";

    /** The get payment limit types. */
    String GET_PAYMENT_TYPES = "/getPaymentTypes";

    /** The payment limit base path. */
    String PAYMENT_LIMIT_BASE_PATH = "/paymentLimit";

    /** The get payment limits by customer id. */
    String GET_PAYMENT_LIMITS_BY_CUSTOMER_ID = "/customer/{customerId}";

    /** The get payment limits by customer person map id. */
    String GET_PAYMENT_LIMITS_BY_CUSTOMER_PERSON_MAP_ID = "/customerPersonMap/{mapId}";

    /** The get payment data by party id mail id. */
    String GET_PAYMENT_DATA_BY_PARTY_ID_PERSON_ID = "/getCustomerPersonPaymentData";

    /** The get payment data by party id. */
    String GET_PAYMENT_DATA_BY_PARTY_ID = "/getCustomerPaymentData";

    /** The save customer payment limits. */
    String SAVE_CUSTOMER_PAYMENT_LIMITS = "/customerPaymentLimits";

    /** The save customer person payment limits. */
    String SAVE_CUSTOMER_PERSON_PAYMENT_LIMITS = "/customerPersonPaymentLimits";

    /** The get default payment limits. */
    String GET_DEFAULT_PAYMENT_LIMITS = "/defaultPaymentLimits/{customerTypeId}";

    /** The update nano initiated count. */
    String UPDATE_NANO_INITIATED_COUNT = "/nanoIntitiatedCount";

    /** The update nano waiting count. */
    String UPDATE_NANO_WAITING_COUNT = "/nanoWaitingCount";

    /** The save payment limits documents. */
    String SAVE_PAYMENT_LIMITS_DOCUMENTS = "/paymentLimitsDocuments";

    /** The update personId by ref id. */
    String UPDATE_PERSON_BY_REF_ID = "/updatePersonId";

    /** The save error messages. */
    String SAVE_ERROR_MESSAGES = "/saveErrorMessages";

    String GET_BULK_PAYMENT_DATA_WITH_REF_ID = "/getbulkMapping/{bulkRefId}";

    String CHECK_PAYMENT_OTP_MAPPING = "/check/{paymentReference}/{authCode}";

    String UPDATE_PAYER_BY_BULK_REF_ID = "/payerIban/{payerIban}/bulkRefId/{bulkRefId}";

    String GET_CUSTOMER_PARTY_ID_BY_BULK_REFERENCE = "/partyId/bulk/{bulkPaymentReference}";

}
