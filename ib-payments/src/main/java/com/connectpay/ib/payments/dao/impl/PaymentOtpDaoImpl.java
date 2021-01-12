package com.connectpay.ib.payments.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
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

import com.connectpay.ib.payments.dao.PaymentOtpDao;
import com.connectpay.ib.utils.ApplicationConstants;
import com.connectpay.ib.utils.LoadPropertyFile;
import com.connectpay.ib.utils.LogUtil;
import com.connectpay.payments.bean.PaymentOtpMapping;

// TODO: Auto-generated Javadoc
/**
 * The Class PaymentOtpDaoImpl.
 */
@Repository
public class PaymentOtpDaoImpl implements PaymentOtpDao {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger(PaymentOtpDaoImpl.class);

    /** The Constant CREATE_PAYMENT_OTP_MAPPING. */
    private static final String CREATE_PAYMENT_OTP_MAPPING = "create.payment.otp.mapping";

    /** The Constant UPDATE_PAYMENT_OTP_MAPPING. */
    private static final String UPDATE_PAYMENT_OTP_MAPPING = "update.payment.otp.mapping";

    /** The Constant GET_PAYMENT_OTP_MAPPING. */
    private static final String GET_PAYMENT_OTP_MAPPING = "get.payment.otp.mapping";
    
    private static final String REMOVE_PAYMENT_OTP_MAPPING = "remove.payment.otp.mapping";

    private static final String GET_PAYMENT_OTP_MAPPING_BY_PAYMENT_AND_AUTHCODE = "check.payment.otp.mapping";

    /** The jdbc template. */
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    /** The properties. */
    private final Properties properties;

    /**
     * Instantiates a new payment otp dao impl.
     */
    public PaymentOtpDaoImpl() {
        properties = LoadPropertyFile.loadQueries(LoadPropertyFile.PAYMENT_FILE, PaymentDaoImpl.class,
                CREATE_PAYMENT_OTP_MAPPING);
    }

    /**
     * Creates the payment otp mapping.
     *
     * @param paymentOtpMapping the payment otp mapping
     * @return the payment otp mapping
     */
    @Override
    public PaymentOtpMapping createPaymentOtpMapping(PaymentOtpMapping paymentOtpMapping) {
        String query = properties.getProperty(CREATE_PAYMENT_OTP_MAPPING);
        LogUtil.log(LOGGER, "createPaymentOtpMapping query is  = " + query);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        BeanPropertySqlParameterSource beanPropertyResourse = new BeanPropertySqlParameterSource(paymentOtpMapping);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(query, beanPropertyResourse, keyHolder);
        paymentOtpMapping.setId(keyHolder.getKey().longValue());

        return paymentOtpMapping;
    }

    /**
     * Update payment otp mapping.
     *
     * @param paymentOtpMapping the payment otp mapping
     * @return the payment otp mapping
     */
    @Override
    public PaymentOtpMapping updatePaymentOtpMapping(PaymentOtpMapping paymentOtpMapping) {
        String query = properties.getProperty(UPDATE_PAYMENT_OTP_MAPPING);
        LogUtil.log(LOGGER, "updatePaymentOtpMapping query is  = " + query);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        BeanPropertySqlParameterSource beanPropertyResourse = new BeanPropertySqlParameterSource(paymentOtpMapping);

        jdbcTemplate.update(query, beanPropertyResourse);

        return paymentOtpMapping;
    }

    /**
     * Gets the payment otp mapping.
     *
     * @param phoneOtpReference the phone otp reference
     * @return the payment otp mapping
     */
    @Override
    public PaymentOtpMapping getPaymentOtpMapping(String phoneOtpReference) {
        String query = properties.getProperty(GET_PAYMENT_OTP_MAPPING);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource parameterSource = new MapSqlParameterSource("phoneOtpReference", phoneOtpReference);

        try {
            return jdbcTemplate.queryForObject(query, parameterSource, new RowMapper<PaymentOtpMapping>() {

                @Override
                public PaymentOtpMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
                    PaymentOtpMapping paymentOtpMapping = new PaymentOtpMapping();
                    paymentOtpMapping.setId(rs.getLong("id"));
                    paymentOtpMapping.setLoginAttempts(rs.getInt("login_attempts"));
                    paymentOtpMapping.setPaymentReference(rs.getString("payment_reference"));
                    paymentOtpMapping.setPhoneOtpReference(rs.getString("phone_otp_reference"));
                    paymentOtpMapping.setPhoneOtpStatus(rs.getString("phone_otp_status"));
                    return paymentOtpMapping;

                }
            });
        } catch (DataAccessException e) {
            LogUtil.logErrorMessage(LOGGER,
                    " :: in getPaymentOtpMapping unable to get PaymentOtpMapping :: DataAccessException :: "
                            + e.getMessage());
            LogUtil.logException(LOGGER, e);
            return null;
        }
    }

    @Override
    public void removePaymentOtpMapping(String authCode) {
        // TODO REMOVE_PAYMENT_OTP_MAPPING
        String query = properties.getProperty(REMOVE_PAYMENT_OTP_MAPPING);
        LogUtil.log(LOGGER, "removePaymentOtpMapping query is  = " + query);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource parameterSource = new MapSqlParameterSource("authCode", authCode);

        jdbcTemplate.update(query, parameterSource);

    }

    @Override
    public PaymentOtpMapping checkPaymentOtpMapping(String paymentReference, String authCode) {
        String query = properties.getProperty(GET_PAYMENT_OTP_MAPPING_BY_PAYMENT_AND_AUTHCODE);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource parameterSource = new MapSqlParameterSource("paymentReference", paymentReference).addValue("authCode", authCode);

        try {
            return jdbcTemplate.queryForObject(query, parameterSource, new RowMapper<PaymentOtpMapping>() {

                @Override
                public PaymentOtpMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
                    PaymentOtpMapping paymentOtpMapping = new PaymentOtpMapping();
                    paymentOtpMapping.setId(rs.getLong("id"));
                    paymentOtpMapping.setPhoneOtpReference(rs.getString("phone_otp_reference"));
                    return paymentOtpMapping;

                }
            });
        } catch (DataAccessException e) {
            LogUtil.logErrorMessage(LOGGER,
                    " :: in getPaymentOtpMapping unable to get PaymentOtpMapping :: DataAccessException :: "
                            + e.getMessage());
            LogUtil.logException(LOGGER, e);
            return null;
        }
    }
}
