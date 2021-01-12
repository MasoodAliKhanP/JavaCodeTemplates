package com.connectpay.ib.payments.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.connectpay.customer.Party;
import com.connectpay.ib.payments.dao.PaymentDao;
import com.connectpay.ib.payments.service.helper.PaymentServiceHelper;
import com.connectpay.ib.utils.ApplicationConstants;
import com.connectpay.ib.utils.LoadPropertyFile;
import com.connectpay.ib.utils.LogUtil;
import com.connectpay.payments.bean.CreditorDetails;
import com.connectpay.payments.bean.PaymentDetails;
import com.connectpay.payments.bean.PaymentDownloadRequest;
import com.connectpay.payments.bean.PaymentErrorLibrary;
import com.connectpay.payments.bean.PaymentOperations;
import com.connectpay.payments.bean.PaymentOtherDetails;
import com.connectpay.payments.bean.PaymentStatus;
import com.connectpay.payments.bean.PaymentStatusDetails;
import com.connectpay.payments.bean.PaymentType;
import com.connectpay.payments.request.InitialPaymentRequest;
import com.connectpay.payments.request.PaymentServiceRequest;
import com.connectpay.payments.request.PaymentStructuresRequest;
import com.connectpay.payments.request.PaymentsListRequest;
import com.connectpay.payments.response.PaymentDetailsResponse;

// TODO: Auto-generated Javadoc
/**
 * The Class PaymentDaoImpl.
 */
@Repository
public class PaymentDaoImpl implements PaymentDao {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger(PaymentDaoImpl.class);

    /** The Constant GET_PAYMENT_STATUS. */
    private static final String GET_PAYMENT_STATUS = "get.payment.status";

    /** The Constant INSERT_PAYMENT_DETAILS. */
    private static final String INSERT_PAYMENT_DETAILS = "insert.payment.details";

    /** The Constant UPDATE_PAYMENT_DETAILS. */
    private static final String UPDATE_PAYMENT_DETAILS = "update.payment.details";

    /** The Constant PAYMENT_REQUEST_RESPONSE_DETAILS. */
    private static final String PAYMENT_REQUEST_RESPONSE_DETAILS = "payment.request.response";

    /** The Constant GET_PAYMENT_DETAILS_BY_ID. */
    private static final String GET_PAYMENT_DETAILS_BY_ID = "get.payment.details.by.id";

    /** The Constant GET_PAYMENT_ERROR_DETAILS_BY_ERROR_CODE. */
    private static final String GET_PAYMENT_ERROR_DETAILS_BY_ERROR_CODE = "get.payment.error.details.by.error.code";

    /** The Constant CREATE_INITIAL_PAYMENT_REQUEST. */
    private static final String CREATE_INITIAL_PAYMENT_REQUEST = "create.initial.payment.request";

    /** The Constant UPDATE_INITIAL_PAYMENT_REQUEST. */
    private static final String UPDATE_INITIAL_PAYMENT_REQUEST = "update.initial.payment.request";

    /** The Constant CREATE_CURRENCY_EXCHANGE_PAYMENT_DETAILS. */
    private static final String CREATE_CURRENCY_EXCHANGE_PAYMENT_DETAILS = "create.currency.exchange.payment.details";

    /** The Constant GET_PAYMENTS_LIST. */
    private static final String GET_PAYMENTS_LIST = "get.payments.list";

    /** The Constant GET_PAYENT_OTHER_DETAILS. */
    private static final String GET_PAYENT_OTHER_DETAILS = "get.payments.other.details";

    /** The Constant UPDATE_PAYMENT_STATUS_BY_PAYMENT_REF_LIST. */
    private static final String UPDATE_PAYMENT_STATUS_BY_PAYMENT_REF_LIST = "update.payments.status.by.paymentRef.list";

    /** The Constant GET_PAYMENT_DETAILS_BY_DATE. */
    private static final String GET_PAYMENT_DETAILS_BY_DATE = "get.payment.details.by.date";

    /** The Constant GET_PAYMENT_DETAILS_BY_REF_ID_LIST. */
    private static final String GET_PAYMENT_DETAILS_BY_REF_ID_LIST = "get.payment.details.by.paymentRef.list";

    private static final String UPDATE_NANO_INITIATED_COUNT = "update.nano.initiated.count";

    private static final String UPDATE_NANO_WAITING_COUNT = "update.nano.waiting.count";

    /** The jdbc template. */
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    /** The payment service helper. */
    @Autowired
    private PaymentServiceHelper paymentServiceHelper;

    /** The properties. */
    private final Properties properties;

    /**
     * Instantiates a new payment dao impl.
     */
    public PaymentDaoImpl() {
        properties = LoadPropertyFile.loadQueries(LoadPropertyFile.PAYMENT_FILE, PaymentDaoImpl.class,
                GET_PAYMENT_STATUS);
    }

    /**
     * Gets the payment status.
     *
     * @param paymentStatus the payment status
     * @return the payment status
     */
    @Override
    public PaymentStatusDetails getPaymentStatus(String paymentStatus) {
        String query = properties.getProperty(GET_PAYMENT_STATUS);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource parameterSource = new MapSqlParameterSource("status", paymentStatus);

        try {
            return jdbcTemplate.queryForObject(query, parameterSource, new RowMapper<PaymentStatusDetails>() {

                @Override
                public PaymentStatusDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                    PaymentStatusDetails psd = new PaymentStatusDetails();
                    psd.setId(rs.getInt("id"));
                    psd.setStatus(rs.getString("status"));
                    psd.setActive(rs.getBoolean("active"));
                    return psd;
                }
            });
        } catch (DataAccessException e) {
            LogUtil.logErrorMessage(LOGGER,
                    " :: in getPaymentStatus unable to get PaymentStatus :: DataAccessException :: " + e.getMessage());
            LogUtil.logException(LOGGER, e);
            return null;
        }
    }

    /**
     * Insert payment details.
     *
     * @param paymentServiceRequest the payment service request
     * @param creditorDetails       the creditor details
     * @return the payment details
     */
    @Override
    public PaymentDetails insertPaymentDetails(PaymentServiceRequest paymentServiceRequest,
            CreditorDetails creditorDetails) {

        String query = properties.getProperty(INSERT_PAYMENT_DETAILS);
        LogUtil.log(LOGGER, "insertPaymentDetails query is  = " + query);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        BeanPropertySqlParameterSource beanPropertyResourse = new BeanPropertySqlParameterSource(paymentServiceRequest);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(query, beanPropertyResourse, keyHolder);

        PaymentDetails paymentDetails = paymentServiceHelper.buildPaymentDetails(creditorDetails,
                paymentServiceRequest);
        paymentDetails.setId(keyHolder.getKey().longValue());

        LogUtil.log(LOGGER, "paymentDetails :: " + paymentDetails);
        return paymentDetails;
    }

    /**
     * Update payment details.
     *
     * @param paymentServiceRequest the payment service request
     */
    @Override
    public void updatePaymentDetails(PaymentServiceRequest paymentServiceRequest) {
        String query = properties.getProperty(UPDATE_PAYMENT_DETAILS);
        LogUtil.log(LOGGER, "updatePaymentDetails query is  = " + query);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        BeanPropertySqlParameterSource beanPropertyResourse = new BeanPropertySqlParameterSource(paymentServiceRequest);

        jdbcTemplate.update(query, beanPropertyResourse);

    }

    /**
     * Store payment request response.
     *
     * @param paymentStructuresRequest the payment structures request
     */
    @Override
    public void storePaymentRequestResponse(PaymentStructuresRequest paymentStructuresRequest) {
        String query = properties.getProperty(PAYMENT_REQUEST_RESPONSE_DETAILS);
        LogUtil.log(LOGGER, "storePaymentRequestResponse query is  = " + query);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        BeanPropertySqlParameterSource beanPropertyResourse = new BeanPropertySqlParameterSource(
                paymentStructuresRequest);

        jdbcTemplate.update(query, beanPropertyResourse);

    }

    /**
     * Gets the payment details.
     *
     * @param paymentId the payment id
     * @return the payment details
     */
    @Override
    public PaymentDetails getPaymentDetails(String paymentId) {
        String query = properties.getProperty(GET_PAYMENT_DETAILS_BY_ID);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource parameterSource = new MapSqlParameterSource("paymentReference", paymentId);

        try {
            return jdbcTemplate.queryForObject(query, parameterSource, new RowMapper<PaymentDetails>() {
                @Override
                public PaymentDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                    PaymentDetails paymentDetails = new PaymentDetails();
                    paymentDetails.setAccountNumber(rs.getString("account_number"));
                    paymentDetails.setBankAddress(rs.getString("bank_address"));
                    paymentDetails.setBankCode(rs.getString("bank_code"));
                    paymentDetails.setBankName(rs.getString("bank_name"));
                    paymentDetails.setBeneficiaryName(rs.getString("name"));
                    paymentDetails.setCreditorDetaildId(rs.getLong("creditor_details_id"));
                    paymentDetails.setDebitorIban(rs.getString("debitor_iban"));
                    paymentDetails.setEmail(rs.getString("email"));
                    paymentDetails.setPersonId(rs.getLong("person_id"));
                    paymentDetails.setFeeAccountCurrency(rs.getString("fee_account_currency"));
                    paymentDetails.setFeeAccountNumber(rs.getString("fee_account_number"));
                    paymentDetails.setPartyId(rs.getString("party_id"));
                    paymentDetails.setPaymentAmount(rs.getString("payment_amount"));
                    paymentDetails.setPaymentCurrency(rs.getString("payment_currency"));
                    paymentDetails.setPaymentCurrencyName(rs.getString("currency_country_name"));
                    paymentDetails.setPaymentStatus(rs.getString("status"));
                    paymentDetails.setPaymentReference(rs.getString("payment_reference"));
                    paymentDetails.setPaymentStatusId(rs.getInt("payment_status_id"));
                    paymentDetails.setId(rs.getLong("id"));
                    paymentDetails.setSupplierReference(rs.getString("supplier_reference"));
                    paymentDetails.setStructurePayment(rs.getBoolean("structured_payment"));
                    paymentDetails.setStructurePaymentDetails(rs.getString("structured_payment_details"));
                    paymentDetails.setUnStructurePayment(rs.getBoolean("unstructured_payment"));
                    paymentDetails.setUnStructurePaymentDetails(rs.getString("unstructured_payment_details"));
                    paymentDetails.setErrorCode(rs.getString("provider_error_code"));
                    paymentDetails.setErrorMsg(rs.getString("provider_error_msg"));
                    paymentDetails.setProviderStatus(rs.getString("provider_status"));
                    paymentDetails.setExchangeCurrency(rs.getString("exchange_currency"));
                    paymentDetails.setExchangeAmount(rs.getString("exchange_amount"));
                    paymentDetails.setFxCurrencyRate(rs.getString("fx_rate"));
                    paymentDetails.setPaymentDate(rs.getString("create_time"));
                    paymentDetails.setPaymentUpdateDate(rs.getString("update_time"));
                    PaymentType paymentType = new PaymentType();
                    paymentType.setType(rs.getString("payment_type"));
                    paymentType.setId(rs.getInt("payment_type_id"));
                    paymentDetails.setPaymentType(paymentType);
                    paymentDetails.setServiceCharge(rs.getString("service_charge"));
                    paymentDetails.setNanoWaitingLoopCount(rs.getInt("nano_waiting_loop_count"));
                    paymentDetails.setNanoInitiatedLoopCount(rs.getInt("nano_initiated_loop_count"));
                    return paymentDetails;
                }
            });
        } catch (DataAccessException e) {
            LogUtil.logErrorMessage(LOGGER,
                    " :: in getPaymentDetails unable to get paymentdetails :: DataAccessException :: ");
            LogUtil.logException(LOGGER, e);
            return null;
        }
    }

    /**
     * Gets the payments list.
     *
     * @param paymentsListRequest the payments list request
     * @return the payments list
     */
    @Override
    public List<PaymentOperations> getPaymentsList(PaymentsListRequest paymentsListRequest) {
        if (paymentsListRequest.getStatuses() == null || paymentsListRequest.getStatuses().size() == 0) {
            return Collections.emptyList();
        }

        StringBuilder query = new StringBuilder(properties.getProperty(GET_PAYMENTS_LIST));
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("customerPartyId", paymentsListRequest.getCustomerPartyId());

        if (paymentsListRequest.getStatuses().size() > 0) {
            query.append(" WHERE a.status in (:statuses) ");
            parameters.addValue("statuses", paymentsListRequest.getStatuses());
        }

        try {
            return jdbcTemplate.query(query.toString(), parameters, new RowMapper<PaymentOperations>() {
                @Override
                public PaymentOperations mapRow(ResultSet rs, int rowNum) throws SQLException {
                    PaymentOperations po = new PaymentOperations();
                    po.setId(rs.getLong("id"));
                    po.setPaymentAmount(rs.getString("payment_amount"));
                    po.setPaymentCurrency(rs.getString("payment_currency"));
                    po.setPaymentStatus(rs.getString("status"));
                    po.setPaymentReference(rs.getString("payment_reference"));
                    po.setPaymentDetails(rs.getString("payment_details"));
                    po.setAccountNumber(rs.getString("account_number"));
                    po.setBeneficiaryName(rs.getString("name"));
                    po.setDebitorIban(rs.getString("debitor_iban"));
                    po.setUpdateTime(rs.getString("update_time"));
                    po.setExchangeCurrency(rs.getString("exchange_currency"));
                    po.setBeneficiaryDetails(rs.getString("beneficiary_details"));
                    PaymentType paymentType = new PaymentType();
                    paymentType.setType(rs.getString("payment_type"));
                    paymentType.setId(rs.getInt("payment_type_id"));
                    po.setPaymentType(paymentType);
                    return po;
                }
            });
        } catch (DataAccessException e) {
            LogUtil.logErrorMessage(LOGGER, " :: in getPaymentsList :: DataAccessException :: " + e);
            LogUtil.logException(LOGGER, e);
            return null;
        }
    }

    /**
     * Gets the payment errors.
     *
     * @param providerErrorCode the provider error code
     * @return the payment errors
     */
    @Override
    public PaymentErrorLibrary getPaymentErrors(String providerErrorCode) {
        String query = properties.getProperty(GET_PAYMENT_ERROR_DETAILS_BY_ERROR_CODE);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource parameterSource = new MapSqlParameterSource("providerErrorCode", providerErrorCode);

        try {
            return jdbcTemplate.queryForObject(query, parameterSource, new RowMapper<PaymentErrorLibrary>() {

                @Override
                public PaymentErrorLibrary mapRow(ResultSet rs, int rowNum) throws SQLException {
                    PaymentErrorLibrary paymentErrorLibrary = new PaymentErrorLibrary();
                    paymentErrorLibrary.setApiErrorCode(rs.getString("api_error_code"));
                    paymentErrorLibrary.setApiErrorMsg(rs.getString("api_error_msg"));
                    paymentErrorLibrary.setErrorCode(rs.getString("error_code"));
                    paymentErrorLibrary.setErrorHash(rs.getString("hash_error_code_error_msg"));
                    paymentErrorLibrary.setErrorMsg(rs.getString("error_msg"));
                    paymentErrorLibrary.setUiErrorCode(rs.getString("ui_error_code"));
                    paymentErrorLibrary.setUiErrorMsg(rs.getString("ui_error_msg"));
                    return paymentErrorLibrary;

                }
            });
        } catch (DataAccessException e) {
            LogUtil.logErrorMessage(LOGGER,
                    " :: in getPaymentErrors unable to get paymentErrors :: DataAccessException :: " + e.getMessage());
            LogUtil.logException(LOGGER, e);
            return null;
        }
    }

    /**
     * Creates the initial payment request.
     *
     * @param initialPaymentRequest the initial payment request
     * @return the initial payment request
     */
    @Override
    public InitialPaymentRequest createInitialPaymentRequest(InitialPaymentRequest initialPaymentRequest) {
        String query = properties.getProperty(CREATE_INITIAL_PAYMENT_REQUEST);
        LogUtil.log(LOGGER, "createInitialPaymentRequest query is  = " + query);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        BeanPropertySqlParameterSource beanPropertyResourse = new BeanPropertySqlParameterSource(initialPaymentRequest);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(query, beanPropertyResourse, keyHolder);
        initialPaymentRequest.setId(keyHolder.getKey().longValue());

        return initialPaymentRequest;
    }

    /**
     * Update initial payment request.
     *
     * @param initialPaymentRequest the initial payment request
     * @return the initial payment request
     */
    @Override
    public InitialPaymentRequest updateInitialPaymentRequest(InitialPaymentRequest initialPaymentRequest) {
        String query = properties.getProperty(UPDATE_INITIAL_PAYMENT_REQUEST);
        LogUtil.log(LOGGER, "updateInitialPaymentRequest query is  = " + query);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        BeanPropertySqlParameterSource beanPropertyResourse = new BeanPropertySqlParameterSource(initialPaymentRequest);

        jdbcTemplate.update(query, beanPropertyResourse);

        return initialPaymentRequest;
    }

    /**
     * Insert currency exchange payment details.
     *
     * @param paymentServiceRequest the payment service request
     * @return the payment details
     */
    @Override
    public PaymentDetails insertCurrencyExchangePaymentDetails(PaymentServiceRequest paymentServiceRequest) {

        String query = properties.getProperty(CREATE_CURRENCY_EXCHANGE_PAYMENT_DETAILS);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        BeanPropertySqlParameterSource beanPropertyResourse = new BeanPropertySqlParameterSource(paymentServiceRequest);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(query, beanPropertyResourse, keyHolder);

        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setExchangeAmount(paymentServiceRequest.getExchangeAmount());
        paymentDetails.setFxCurrencyRate(paymentServiceRequest.getFxCurrencyRate());
        paymentDetails.setExchangeCurrency(paymentServiceRequest.getExchangeCurrency());
        paymentDetails.setId(keyHolder.getKey().longValue());
        LogUtil.log(LOGGER, "paymentDetails :: " + paymentDetails);
        return paymentDetails;
    }

    /**
     * Gets the currency exchange payment details.
     *
     * @param paymentId the payment id
     * @return the currency exchange payment details
     */
    @Override
    public PaymentOtherDetails getCurrencyExchangePaymentDetails(long paymentId) {
        String query = properties.getProperty(GET_PAYENT_OTHER_DETAILS);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource parameterSource = new MapSqlParameterSource("paymentId", paymentId);

        try {
            return jdbcTemplate.queryForObject(query, parameterSource, new RowMapper<PaymentOtherDetails>() {

                @Override
                public PaymentOtherDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                    PaymentOtherDetails paymentOtherDetails = new PaymentOtherDetails();
                    paymentOtherDetails.setExchangeAmount(rs.getString("exchange_amount"));
                    paymentOtherDetails.setExchangeCurrency(rs.getString("exchange_currency"));
                    paymentOtherDetails.setFxRate(rs.getString("fx_rate"));
                    return paymentOtherDetails;

                }
            });
        } catch (DataAccessException e) {
            LogUtil.logErrorMessage(LOGGER,
                    " :: in fetching payment other details :: DataAccessException :: " + e.getMessage());
            LogUtil.logException(LOGGER, e);
            return null;
        }
    }

    /**
     * Update payment status by payment ref list.
     *
     * @param paymentRefList  the payment ref list
     * @param paymentStatusId the payment status id
     */
    @Override
    public void updatePaymentStatusByPaymentRefList(String paymentRefList, int paymentStatusId) {
        String query = properties.getProperty(UPDATE_PAYMENT_STATUS_BY_PAYMENT_REF_LIST);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource parameterSource = new MapSqlParameterSource("paymentStatusId", paymentStatusId)
                .addValue("paymentRefList", paymentRefList);
        jdbcTemplate.update(query, parameterSource);

    }

    /**
     * Gets the payments details list.
     *
     * @param paymentDownloadRequest the payment download request
     * @return the payments details list
     */
    @Override
    public List<PaymentDetailsResponse> getPaymentsDetailsList(PaymentDownloadRequest paymentDownloadRequest) {
        String query = properties.getProperty(GET_PAYMENT_DETAILS_BY_DATE);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        BeanPropertySqlParameterSource beanPropertyResourse = new BeanPropertySqlParameterSource(
                paymentDownloadRequest);

        try {
            return jdbcTemplate.query(query, beanPropertyResourse, new RowMapper<PaymentDetailsResponse>() {
                @Override
                public PaymentDetailsResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                    PaymentDetailsResponse paymentDetailsResponse = new PaymentDetailsResponse();

                    paymentDetailsResponse.setPaymentReference(rs.getString("payment_reference"));

                    Party party = new Party();
                    party.setPartyId(rs.getString("party_id"));
                    paymentDetailsResponse.setParty(party);

                    paymentDetailsResponse.setEmail(rs.getString("email"));

                    CreditorDetails creditorDetails = new CreditorDetails();
                    creditorDetails.setAccountNumber(rs.getString("account_number"));
                    creditorDetails.setBankAddress(rs.getString("bank_address"));
                    creditorDetails.setBankCode(rs.getString("bank_code"));
                    creditorDetails.setBankName(rs.getString("bank_name"));
                    creditorDetails.setName(rs.getString("name"));
                    paymentDetailsResponse.setCreditorDetails(creditorDetails);

                    PaymentStatus paymentStatus = new PaymentStatus();
                    paymentStatus.setStatus(rs.getString("status"));
                    paymentDetailsResponse.setPaymentStatus(paymentStatus);

                    paymentDetailsResponse.setAccountNumber(rs.getString("debitor_iban"));
                    paymentDetailsResponse.setPaymentAmount(rs.getString("payment_amount"));
                    paymentDetailsResponse.setPaymentCurrency(rs.getString("payment_currency"));
                    paymentDetailsResponse.setFeeAccountCurrency(rs.getString("fee_account_currency"));
                    paymentDetailsResponse.setFeeAccountNumber(rs.getString("fee_account_number"));
                    paymentDetailsResponse.setFeeAmount(rs.getString("service_charge"));
                    paymentDetailsResponse.setSupplierReference(rs.getString("supplier_reference"));
                    paymentDetailsResponse.setNanoInitiatedLoopCount(rs.getInt("nano_initiated_loop_count"));
                    paymentDetailsResponse.setNanoWaitingLoopCount(rs.getInt("nano_waiting_loop_count"));

                    // TODO

                    String paymentDetailsType = "UnStructuredPaymentDetails";
                    if (rs.getBoolean("structured_payment")) {
                        paymentDetailsType = "StructuredPaymentDetails";
                    }

                    paymentDetailsResponse.setPaymentDetailsType(paymentDetailsType);
                    paymentDetailsResponse.setStructurePaymentDetails(rs.getString("structured_payment_details"));
                    paymentDetailsResponse.setUnStructurePaymentDetails(rs.getString("unstructured_payment_details"));
                    paymentDetailsResponse.setPaymentDate(rs.getString("create_time"));
                    paymentDetailsResponse.setPaymentUpdateDate(rs.getString("update_time"));
                    paymentDetailsResponse.setPaymentCreateDate(rs.getString("pay_create_time"));

                    PaymentType paymentType = new PaymentType();
                    paymentType.setType(rs.getString("payment_type"));
                    paymentDetailsResponse.setPaymentType(paymentType);

                    PaymentOtherDetails paymentOtherDetails = new PaymentOtherDetails();
                    paymentOtherDetails.setExchangeAmount(rs.getString("exchange_amount"));
                    paymentOtherDetails.setExchangeCurrency(rs.getString("exchange_currency"));
                    paymentOtherDetails.setFxRate(rs.getString("fx_rate"));
                    paymentDetailsResponse.setPaymentOtherDetails(paymentOtherDetails);

                    return paymentDetailsResponse;
                }
            });
        } catch (DataAccessException e) {
            LogUtil.logErrorMessage(LOGGER,
                    " :: in unable to fetch payment list for report :: DataAccessException :: " + e.getMessage());
            LogUtil.logException(LOGGER, e);
            return null;
        }
    }

    /**
     * Gets the payment details by reference list.
     *
     * @param paymentRefList the payment ref list
     * @return the payment details by reference list
     */
    @Override
    public List<PaymentDetails> getPaymentDetailsByReferenceList(List<String> paymentRefList) {
        String query = properties.getProperty(GET_PAYMENT_DETAILS_BY_REF_ID_LIST);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource parameterSource = new MapSqlParameterSource("paymentRefList", paymentRefList);
        try {
            return jdbcTemplate.query(query, parameterSource, new RowMapper<PaymentDetails>() {

                @Override
                public PaymentDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                    PaymentDetails paymentDetails = new PaymentDetails();
                    paymentDetails.setPaymentReference(rs.getString("payment_reference"));
                    paymentDetails.setPaymentDate(rs.getString("create_time"));
                    paymentDetails.setPartyId(rs.getString("party_id"));
                    paymentDetails.setPaymentAmount(rs.getString("payment_amount"));
                    paymentDetails.setPaymentCurrency(rs.getString("payment_currency"));
                    paymentDetails.setPaymentStatus(rs.getString("status"));
                    return paymentDetails;

                }
            });
        } catch (DataAccessException e) {
            LogUtil.log(LOGGER,
                    " :: in unable to fetch payment list by Ref list :: DataAccessException :: " + e.getMessage());
            LogUtil.logException(LOGGER, e);
            return null;
        }
    }

    /**
     * Update nano intiated count.
     *
     * @param paymentServiceRequest the payment service request
     */
    @Override
    public void updateNanoIntiatedCount(PaymentServiceRequest paymentServiceRequest) {
        String query = properties.getProperty(UPDATE_NANO_INITIATED_COUNT);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource parameterSource = new MapSqlParameterSource("paymentReference",
                paymentServiceRequest.getPaymentReference()).addValue("nanoInitiatedCount",
                        paymentServiceRequest.getNanoInitiatedLoopCount());
        jdbcTemplate.update(query, parameterSource);

    }

    /**
     * Update nano waiting count.
     *
     * @param paymentServiceRequest the payment service request
     */
    @Override
    public void updateNanoWaitingCount(PaymentServiceRequest paymentServiceRequest) {
        String query = properties.getProperty(UPDATE_NANO_WAITING_COUNT);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource parameterSource = new MapSqlParameterSource("paymentReference",
                paymentServiceRequest.getPaymentReference()).addValue("nanoWaitingCount",
                        paymentServiceRequest.getNanoWaitingLoopCount());
        jdbcTemplate.update(query, parameterSource);

    }

}
