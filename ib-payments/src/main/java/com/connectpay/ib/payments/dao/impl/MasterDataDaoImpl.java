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
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.connectpay.ib.payments.dao.MasterDataDao;
import com.connectpay.ib.utils.ApplicationConstants;
import com.connectpay.ib.utils.LoadPropertyFile;
import com.connectpay.ib.utils.LogUtil;
import com.connectpay.payments.bean.BulkPaymentFileStatus;
import com.connectpay.payments.bean.BulkPaymentType;
import com.connectpay.payments.bean.PaymentCurrency;
import com.connectpay.payments.bean.PaymentLimitType;
import com.connectpay.payments.bean.PaymentStatus;
import com.connectpay.payments.bean.PaymentType;

// TODO: Auto-generated Javadoc
/**
 * The Class MasterDataDaoImpl.
 */
@Repository
public class MasterDataDaoImpl implements MasterDataDao {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger(MasterDataDaoImpl.class);

    /** The Constant GET_PAYMENT_CURRENCY. */
    private static final String GET_PAYMENT_CURRENCY = "get.payment.currency";

    /** The Constant GET_PAYMENTS_STATUSES. */
    private static final String GET_PAYMENTS_STATUSES = "get.payment.statuses";

    /** The Constant GET_BULK_PAYMENT_TYPES. */
    private static final String GET_BULK_PAYMENT_TYPES = "get.bulk.payment.types";

    /** The Constant GET_BULK_PAYMENT_FILE_STATUS. */
    private static final String GET_BULK_PAYMENT_FILE_STATUS = "get.bulk.payment.file.status";

    /** The Constant GET_PAYMENT_LIMIT_TYPE. */
    private static final String GET_PAYMENT_LIMIT_TYPE = "get.payment.limit.type";

    /** The Constant GET_PAYMENT_TYPE. */
    private static final String GET_PAYMENT_TYPE = "get.payment.type";

    /** The jdbc template. */
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    /** The properties. */
    private final Properties properties;

    /**
     * Instantiates a new master data dao impl.
     */
    public MasterDataDaoImpl() {
        properties = LoadPropertyFile.loadQueries(LoadPropertyFile.PAYMENT_FILE, MasterDataDaoImpl.class,
                GET_PAYMENT_CURRENCY);
    }

    /**
     * Gets the payment currency.
     *
     * @return the payment currency
     */
    @Override
    public List<PaymentCurrency> getPaymentCurrency() {
        String query = properties.getProperty(GET_PAYMENT_CURRENCY);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);

        try {
            return jdbcTemplate.query(query, new RowMapper<PaymentCurrency>() {
                @Override
                public PaymentCurrency mapRow(ResultSet rs, int rowNum) throws SQLException {
                    PaymentCurrency paymentCurrency = new PaymentCurrency();
                    paymentCurrency.setId(rs.getInt("id"));
                    paymentCurrency.setActive(rs.getBoolean("active"));
                    paymentCurrency.setCurrencyCode(rs.getString("currency_code"));
                    paymentCurrency.setCurrenyCountryName(rs.getString("currency_country_name"));
                    paymentCurrency.setCurrencyISOCode(rs.getString("currency_iso_code"));
                    return paymentCurrency;
                }
            });
        } catch (DataAccessException e) {
            LogUtil.logException(LOGGER, e);
            LogUtil.logErrorMessage(LOGGER, " :Exception in fetching getPaymentCurrency " + e.getMessage());
        }
        return null;
    }

    /**
     * Gets the payment statuses.
     *
     * @return the payment statuses
     */
    @Override
    public List<PaymentStatus> getPaymentStatuses() {
        StringBuilder query = new StringBuilder(properties.getProperty(GET_PAYMENTS_STATUSES));
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        try {
            return jdbcTemplate.query(query.toString(), parameters, new RowMapper<PaymentStatus>() {
                @Override
                public PaymentStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
                    PaymentStatus ps = new PaymentStatus();
                    ps.setId(rs.getInt("id"));
                    ps.setStatus(rs.getString("status"));
                    return ps;
                }
            });
        } catch (DataAccessException e) {
            LogUtil.logException(LOGGER, e);
            LogUtil.logErrorMessage(LOGGER,
                    " :: in getPaymentStatus unable to get PaymentStatus :: DataAccessException :: " + e.getMessage());
            return null;
        }
    }

    /**
     * Gets the bulk payment type.
     *
     * @return the bulk payment type
     */
    @Override
    public List<BulkPaymentType> getBulkPaymentType() {
        StringBuilder query = new StringBuilder(properties.getProperty(GET_BULK_PAYMENT_TYPES));
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        try {
            return jdbcTemplate.query(query.toString(), parameters, new RowMapper<BulkPaymentType>() {
                @Override
                public BulkPaymentType mapRow(ResultSet rs, int rowNum) throws SQLException {
                    BulkPaymentType bulkPaymentType = new BulkPaymentType();
                    bulkPaymentType.setActive(rs.getBoolean("is_active"));
                    bulkPaymentType.setPaymentType(rs.getString("payment_type"));
                    bulkPaymentType.setDescription(rs.getString("description"));
                    bulkPaymentType.setId(rs.getInt("id"));
                    return bulkPaymentType;
                }
            });
        } catch (DataAccessException e) {
            LogUtil.logException(LOGGER, e);
            LogUtil.logErrorMessage(LOGGER,
                    " ::  unable to get bulk payment types  :: DataAccessException :: " + e.getMessage());
            return null;
        }
    }

    /**
     * Gets the bulk payment file status.
     *
     * @return the bulk payment file status
     */
    @Override
    public List<BulkPaymentFileStatus> getBulkPaymentFileStatus() {
        StringBuilder query = new StringBuilder(properties.getProperty(GET_BULK_PAYMENT_FILE_STATUS));
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        try {
            return jdbcTemplate.query(query.toString(), parameters, new RowMapper<BulkPaymentFileStatus>() {
                @Override
                public BulkPaymentFileStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
                    BulkPaymentFileStatus bulkPaymentFileStatus = new BulkPaymentFileStatus();
                    bulkPaymentFileStatus.setActive(rs.getBoolean("active"));
                    bulkPaymentFileStatus.setFileStatus(rs.getString("status"));
                    bulkPaymentFileStatus.setDescription(rs.getString("description"));
                    bulkPaymentFileStatus.setId(rs.getInt("id"));
                    return bulkPaymentFileStatus;
                }
            });
        } catch (DataAccessException e) {
            LogUtil.logException(LOGGER, e);
            LogUtil.logErrorMessage(LOGGER,
                    " ::  unable to get bulk payment types  :: DataAccessException :: " + e.getMessage());
            return null;
        }
    }

    /**
     * Gets the payment limit type.
     *
     * @return the payment limit type
     */
    @Override
    public List<PaymentLimitType> getPaymentLimitType() {
        String query = properties.getProperty(GET_PAYMENT_LIMIT_TYPE);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        try {
            return jdbcTemplate.query(query, parameters, new RowMapper<PaymentLimitType>() {
                @Override
                public PaymentLimitType mapRow(ResultSet rs, int rowNum) throws SQLException {
                    PaymentLimitType paymentLimitType = new PaymentLimitType();
                    paymentLimitType.setActive(rs.getBoolean("is_active"));
                    paymentLimitType.setType(rs.getString("type"));
                    paymentLimitType.setDescription(rs.getString("description"));
                    paymentLimitType.setId(rs.getInt("id"));
                    return paymentLimitType;
                }
            });
        } catch (DataAccessException e) {
            LogUtil.logException(LOGGER, e);
            LogUtil.logErrorMessage(LOGGER,
                    " ::  unable to get payment limit types  :: DataAccessException :: " + e.getMessage());
            return null;
        }
    }

    /**
     * Gets the payment type.
     *
     * @return the payment type
     */
    @Override
    public List<PaymentType> getPaymentType() {
        String query = properties.getProperty(GET_PAYMENT_TYPE);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        try {
            return jdbcTemplate.query(query, parameters, new RowMapper<PaymentType>() {
                @Override
                public PaymentType mapRow(ResultSet rs, int rowNum) throws SQLException {
                    PaymentType paymentType = new PaymentType();
                    paymentType.setActive(rs.getBoolean("is_active"));
                    paymentType.setType(rs.getString("type"));
                    paymentType.setDescription(rs.getString("description"));
                    paymentType.setId(rs.getInt("id"));
                    return paymentType;
                }
            });
        } catch (DataAccessException e) {
            LogUtil.logException(LOGGER, e);
            LogUtil.logErrorMessage(LOGGER,
                    " ::  unable to get payment types  :: DataAccessException :: " + e.getMessage());
            return null;
        }
    }
}
