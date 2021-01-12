package com.connectpay.ib.payments.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.connectpay.customer.Party;
import com.connectpay.ib.payments.dao.PaymentLimitDao;
import com.connectpay.ib.utils.ApplicationConstants;
import com.connectpay.ib.utils.LoadPropertyFile;
import com.connectpay.ib.utils.LogUtil;
import com.connectpay.payments.bean.CustomerPaymentLimit;
import com.connectpay.payments.bean.CustomerPersonPaymentLimit;
import com.connectpay.payments.bean.DefaultPaymentLimit;
import com.connectpay.payments.bean.PaymentLimitDocuments;
import com.connectpay.payments.bean.PaymentLimitType;
import com.connectpay.payments.request.PaymentLimitRequest;
import com.connectpay.payments.response.PaymentDetailsResponse;

// TODO: Auto-generated Javadoc
/**
 * The Class PaymentLimitDaoImpl.
 */
@Repository
public class PaymentLimitDaoImpl implements PaymentLimitDao {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger(PaymentLimitDaoImpl.class);

    /** The jdbc template. */
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    /** The properties. */
    private final Properties properties;

    /** The Constant GET_PAYMENT_DATA_BY_PARTYID_AND_PERSON_ID. */
    private static final String GET_PAYMENT_DATA_BY_PARTYID_AND_PERSON_ID = "get.payment.data.by.partyId.and.person.id";

    /** The Constant GET_PAYMENT_DATA_BY_PARTYID. */
    private static final String GET_PAYMENT_DATA_BY_PARTYID = "get.payment.data.by.partyId";

    /** The Constant GET_PAYMENT_LIMIT_BY_CUSTOMERID. */
    private static final String GET_PAYMENT_LIMIT_BY_CUSTOMERID = "get.payment.limits.by.customerId";

    /** The Constant GET_PAYMENT_DATA_BY_CUSTOMER_PERSON_MAPID. */
    private static final String GET_PAYMENT_DATA_BY_CUSTOMER_PERSON_MAPID = "get.payment.limits.by.customerPerson.mapId";

    /** The Constant GET_DEFAULT_LIMITS_PAYMENTS_BY_CUSTOMER_TYPE. */
    private static final String GET_DEFAULT_LIMITS_PAYMENTS_BY_CUSTOMER_TYPE = "get.payment.limits.by.customerType";

    /** The Constant BATCH_INSERT_CUSTOMER_PAYMENT_LIMIT. */
    private static final String BATCH_INSERT_CUSTOMER_PAYMENT_LIMIT = "batch.insert.customer.payment.limit";

    /** The Constant BATCH_INSERT_CUSTOMER_PERSON_PAYMENT_LIMIT. */
    private static final String BATCH_INSERT_CUSTOMER_PERSON_PAYMENT_LIMIT = "batch.insert.customer.person.payment.limit";

    /** The Constant BATCH_UPDATE_CUSTOMER_PERSON_PAYMENT_LIMIT. */
    private static final String BATCH_UPDATE_CUSTOMER_PERSON_PAYMENT_LIMIT = "batch.update.customer.person.payment.limit";

    /** The Constant BATCH_UPDATE_CUSTOMER_PAYMENT_LIMIT. */
    private static final String BATCH_UPDATE_CUSTOMER_PAYMENT_LIMIT = "batch.update.customer.payment.limit";

    /** The Constant BATCH_INACTIVE_CUSTOMER_PAYMENT_LIMIT. */
    private static final String BATCH_INACTIVE_CUSTOMER_PAYMENT_LIMIT = "batch.inactive.customer.payment.limit.log";

    /** The Constant BATCH_INACTIVE_CUSTOMER_PERSON_PAYMENT_LIMIT. */
    private static final String BATCH_INACTIVE_CUSTOMER_PERSON_PAYMENT_LIMIT = "batch.inactive.customer.person.payment.limit.log";

    /** The Constant BATCH_INSERT_CUSTOMER_PERSON_PAYMENT_LIMIT_LOG. */
    private static final String BATCH_INSERT_CUSTOMER_PERSON_PAYMENT_LIMIT_LOG = "batch.insert.customer.person.payment.limit.log";

    /** The Constant BATCH_INSERT_CUSTOMER_PAYMENT_LIMIT_LOG. */
    private static final String BATCH_INSERT_CUSTOMER_PAYMENT_LIMIT_LOG = "batch.insert.customer.payment.limit.log";

    /** The Constant INSERT_PAYMENT_LIMITS_DOC_DETAILS. */
    private static final String INSERT_PAYMENT_LIMITS_DOC_DETAILS = "insert.payment.limits.document.details";

    /**
     * Instantiates a new payment limit dao impl.
     */
    public PaymentLimitDaoImpl() {
        properties = LoadPropertyFile.loadQueries(LoadPropertyFile.PAYMENT_FILE, PaymentLimitDaoImpl.class,
                GET_PAYMENT_DATA_BY_PARTYID_AND_PERSON_ID);
    }

    /**
     * Gets the payment data by party id and email.
     *
     * @param paymentLimitRequest the payment limit request
     * @return the payment data by party id and email
     */
    @Override
    public List<PaymentDetailsResponse> getPaymentDataByPartyIdAndPersonId(PaymentLimitRequest paymentLimitRequest) {
        String query = properties.getProperty(GET_PAYMENT_DATA_BY_PARTYID_AND_PERSON_ID);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource parameterSource = new MapSqlParameterSource("startDate", paymentLimitRequest.getStartDate())
                .addValue("endDate", paymentLimitRequest.getEndDate())
                .addValue("partyId", paymentLimitRequest.getPartyId())
                .addValue("personId", paymentLimitRequest.getPersonId())
                .addValue("paymentTypeId", paymentLimitRequest.getPaymentTypeId())
                .addValue("paymentStatusIdList", paymentLimitRequest.getPaymentStatusList());

        try {
            return jdbcTemplate.query(query, parameterSource, new RowMapper<PaymentDetailsResponse>() {

                @Override
                public PaymentDetailsResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                    PaymentDetailsResponse paymentDetailsResponse = new PaymentDetailsResponse();
                    paymentDetailsResponse.setPaymentAmount(rs.getString("total_amount"));
                    paymentDetailsResponse.setPersonId(rs.getLong("person_id"));
                    Party party = new Party();
                    party.setPartyId(rs.getString("party_id"));
                    paymentDetailsResponse.setParty(party);
                    paymentDetailsResponse.setPaymentCurrency(rs.getString("payment_currency"));
                    return paymentDetailsResponse;
                }
            });
        } catch (DataAccessException e) {
            LogUtil.logErrorMessage(LOGGER,
                    " Exception while fetching paymentData for customer Email" + e.getMessage());
            LogUtil.logException(LOGGER, e);
            return null;
        }
    }

    /**
     * Gets the payment data by party id.
     *
     * @param paymentLimitRequest the payment limit request
     * @return the payment data by party id
     */
    @Override
    public List<PaymentDetailsResponse> getPaymentDataByPartyId(PaymentLimitRequest paymentLimitRequest) {
        String query = properties.getProperty(GET_PAYMENT_DATA_BY_PARTYID);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource parameterSource = new MapSqlParameterSource("startDate", paymentLimitRequest.getStartDate())
                .addValue("endDate", paymentLimitRequest.getEndDate())
                .addValue("partyId", paymentLimitRequest.getPartyId())
                .addValue("paymentTypeId", paymentLimitRequest.getPaymentTypeId())
                .addValue("paymentStatusIdList", paymentLimitRequest.getPaymentStatusList());

        try {
            return jdbcTemplate.query(query, parameterSource, new RowMapper<PaymentDetailsResponse>() {

                @Override
                public PaymentDetailsResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
                    PaymentDetailsResponse paymentDetailsResponse = new PaymentDetailsResponse();
                    paymentDetailsResponse.setPaymentAmount(rs.getString("total_amount"));
                    Party party = new Party();
                    party.setPartyId(rs.getString("party_id"));
                    paymentDetailsResponse.setParty(party);
                    paymentDetailsResponse.setPaymentCurrency(rs.getString("payment_currency"));
                    return paymentDetailsResponse;
                }
            });
        } catch (DataAccessException e) {
            LogUtil.logErrorMessage(LOGGER, " Exception while fetching paymentData " + e.getMessage());
            LogUtil.logException(LOGGER, e);
            return null;
        }
    }

    /**
     * Gets the payment limits by customer id.
     *
     * @param customerId the customer id
     * @return the payment limits by customer id
     */
    @Override
    public List<CustomerPaymentLimit> getPaymentLimitsByCustomerId(long customerId) {
        String query = properties.getProperty(GET_PAYMENT_LIMIT_BY_CUSTOMERID);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource parameterSource = new MapSqlParameterSource("customerId", customerId);

        try {
            return jdbcTemplate.query(query, parameterSource, new RowMapper<CustomerPaymentLimit>() {

                @Override
                public CustomerPaymentLimit mapRow(ResultSet rs, int rowNum) throws SQLException {
                    CustomerPaymentLimit customerPaymentLimit = new CustomerPaymentLimit();
                    customerPaymentLimit.setAmount(rs.getString("amount"));
                    customerPaymentLimit.setCustomerId(rs.getLong("customer_id"));
                    customerPaymentLimit.setCurrency(rs.getString("currency"));
                    PaymentLimitType paymentLimitType = new PaymentLimitType();
                    paymentLimitType.setId(rs.getInt("id"));
                    paymentLimitType.setType(rs.getString("type"));
                    customerPaymentLimit.setPaymentLimitType(paymentLimitType);
                    return customerPaymentLimit;
                }
            });
        } catch (DataAccessException e) {
            LogUtil.logErrorMessage(LOGGER, " Exception while fetching customer Limits " + e.getMessage());
            LogUtil.logException(LOGGER, e);
            return null;
        }
    }

    /**
     * Gets the payment limits by customer person map id.
     *
     * @param customerPersonMapId the customer person map id
     * @return the payment limits by customer person map id
     */
    @Override
    public List<CustomerPersonPaymentLimit> getPaymentLimitsByCustomerPersonMapId(long customerPersonMapId) {
        String query = properties.getProperty(GET_PAYMENT_DATA_BY_CUSTOMER_PERSON_MAPID);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource parameterSource = new MapSqlParameterSource("customerPersonMapId", customerPersonMapId);

        try {
            return jdbcTemplate.query(query, parameterSource, new RowMapper<CustomerPersonPaymentLimit>() {

                @Override
                public CustomerPersonPaymentLimit mapRow(ResultSet rs, int rowNum) throws SQLException {
                    CustomerPersonPaymentLimit customerPersonPaymentLimit = new CustomerPersonPaymentLimit();
                    customerPersonPaymentLimit.setAmount(rs.getString("amount"));
                    customerPersonPaymentLimit.setCustomerPersonMapId(rs.getLong("customer_person_map_id"));
                    customerPersonPaymentLimit.setCurrency(rs.getString("currency"));
                    PaymentLimitType paymentLimitType = new PaymentLimitType();
                    paymentLimitType.setId(rs.getInt("id"));
                    paymentLimitType.setType(rs.getString("type"));
                    customerPersonPaymentLimit.setPaymentLimitType(paymentLimitType);
                    return customerPersonPaymentLimit;
                }
            });
        } catch (DataAccessException e) {
            LogUtil.logErrorMessage(LOGGER, " Exception while fetching customer Limits " + e.getMessage());
            LogUtil.logException(LOGGER, e);
            return null;
        }
    }

    /**
     * Gets the default limits by customer type.
     *
     * @param customerTypeId the customer type id
     * @return the default limits by customer type
     */
    @Override
    public List<DefaultPaymentLimit> getDefaultLimitsByCustomerType(int customerTypeId) {
        String query = properties.getProperty(GET_DEFAULT_LIMITS_PAYMENTS_BY_CUSTOMER_TYPE);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource parameterSource = new MapSqlParameterSource("customerTypeId", customerTypeId);
        try {
            return jdbcTemplate.query(query, parameterSource, new RowMapper<DefaultPaymentLimit>() {

                @Override
                public DefaultPaymentLimit mapRow(ResultSet rs, int rowNum) throws SQLException {
                    DefaultPaymentLimit defaultPaymentLimit = new DefaultPaymentLimit();
                    defaultPaymentLimit.setAmount(rs.getString("amount"));
                    defaultPaymentLimit.setCurrency(rs.getString("currency"));
                    defaultPaymentLimit.setCustomerTypeId(rs.getInt("customer_type_id"));
                    PaymentLimitType paymentLimitType = new PaymentLimitType();
                    paymentLimitType.setId(rs.getInt("limit_type_id"));
                    defaultPaymentLimit.setPaymentLimitType(paymentLimitType);
                    return defaultPaymentLimit;
                }
            });
        } catch (DataAccessException e) {
            LogUtil.logErrorMessage(LOGGER,
                    " Exception while fetching defualt Limits by customerType" + e.getMessage());
            LogUtil.logException(LOGGER, e);
            return null;
        }
    }

    /**
     * Insert customer limits.
     *
     * @param customerPaymentLimitList the customer payment limit list
     */
    @Override
    public void insertCustomerLimits(List<CustomerPaymentLimit> customerPaymentLimitList) {
        String query = properties.getProperty(BATCH_INSERT_CUSTOMER_PAYMENT_LIMIT);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(customerPaymentLimitList.toArray());
        jdbcTemplate.batchUpdate(query, params);
    }

    /**
     * Insert customer person limits.
     *
     * @param customerPersonPaymentLimitList the customer person payment limit list
     */
    @Override
    public void insertCustomerPersonLimits(List<CustomerPersonPaymentLimit> customerPersonPaymentLimitList) {
        String query = properties.getProperty(BATCH_INSERT_CUSTOMER_PERSON_PAYMENT_LIMIT);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(customerPersonPaymentLimitList.toArray());
        jdbcTemplate.batchUpdate(query, params);
    }

    /**
     * Update customer person limits.
     *
     * @param customerPersonPaymentLimitList the customer person payment limit list
     */
    @Override
    public void updateCustomerPersonLimits(List<CustomerPersonPaymentLimit> customerPersonPaymentLimitList) {
        String query = properties.getProperty(BATCH_UPDATE_CUSTOMER_PERSON_PAYMENT_LIMIT);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(customerPersonPaymentLimitList.toArray());
        jdbcTemplate.batchUpdate(query, params);
    }

    /**
     * Update customer limits.
     *
     * @param customerPaymentLimitList the customer payment limit list
     */
    @Override
    public void updateCustomerLimits(List<CustomerPaymentLimit> customerPaymentLimitList) {
        String query = properties.getProperty(BATCH_UPDATE_CUSTOMER_PAYMENT_LIMIT);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(customerPaymentLimitList.toArray());
        jdbcTemplate.batchUpdate(query, params);
    }

    /**
     * Inactivate customer payment limits log.
     *
     * @param customerPaymentLimitList the customer payment limit list
     */
    @Override
    public void inactivateCustomerPaymentLimitsLog(List<CustomerPaymentLimit> customerPaymentLimitList) {
        String query = properties.getProperty(BATCH_INACTIVE_CUSTOMER_PAYMENT_LIMIT);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(customerPaymentLimitList.toArray());
        jdbcTemplate.batchUpdate(query, params);
    }

    /**
     * Inactivate customer person payment limits log.
     *
     * @param customerPersonPaymentLimitList the customer person payment limit list
     */
    @Override
    public void inactivateCustomerPersonPaymentLimitsLog(
            List<CustomerPersonPaymentLimit> customerPersonPaymentLimitList) {
        String query = properties.getProperty(BATCH_INACTIVE_CUSTOMER_PERSON_PAYMENT_LIMIT);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(customerPersonPaymentLimitList.toArray());
        jdbcTemplate.batchUpdate(query, params);
    }

    /**
     * Insert customer person limits log.
     *
     * @param customerPersonPaymentLimitList the customer person payment limit list
     */
    @Override
    public void insertCustomerPersonLimitsLog(List<CustomerPersonPaymentLimit> customerPersonPaymentLimitList) {
        String query = properties.getProperty(BATCH_INSERT_CUSTOMER_PERSON_PAYMENT_LIMIT_LOG);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(customerPersonPaymentLimitList.toArray());
        jdbcTemplate.batchUpdate(query, params);
    }

    /**
     * Insert customer limits log.
     *
     * @param customerPaymentLimitList the customer payment limit list
     */
    @Override
    public void insertCustomerLimitsLog(List<CustomerPaymentLimit> customerPaymentLimitList) {
        String query = properties.getProperty(BATCH_INSERT_CUSTOMER_PAYMENT_LIMIT_LOG);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(customerPaymentLimitList.toArray());
        jdbcTemplate.batchUpdate(query, params);
    }

    /**
     * Insert payment limit document.
     *
     * @param paymentLimitDocumentsList the payment limit documents list
     */
    @Override
    public void insertPaymentLimitDocument(List<PaymentLimitDocuments> paymentLimitDocumentsList) {
        String query = properties.getProperty(INSERT_PAYMENT_LIMITS_DOC_DETAILS);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(paymentLimitDocumentsList.toArray());
        jdbcTemplate.batchUpdate(query, params);
    }
}
