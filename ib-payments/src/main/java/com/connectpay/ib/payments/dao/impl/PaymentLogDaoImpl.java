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
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.connectpay.ib.payments.dao.PaymentLogDao;
import com.connectpay.ib.utils.ApplicationConstants;
import com.connectpay.ib.utils.LoadPropertyFile;
import com.connectpay.ib.utils.LogUtil;
import com.connectpay.payments.bean.PaymentLog;
import com.connectpay.payments.request.PaymentServiceRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class PaymentLogDaoImpl.
 */
@Repository
public class PaymentLogDaoImpl implements PaymentLogDao {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger(PaymentLogDaoImpl.class);

    /** The Constant GET_PAYMENT_LOG_BY_STATUS_ID_AND_EMAIL. */
    private static final String GET_PAYMENT_LOG_BY_STATUS_ID = "get.payment.log.by.status.id";

    /** The Constant INSERT_PAYMENT_LOG_DETAILS. */
    private static final String INSERT_PAYMENT_LOG_DETAILS = "insert.payment.log.details";

    /** The jdbc template. */
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    /** The properties. */
    private final Properties properties;

    /**
     * Instantiates a new payment log dao impl.
     */
    public PaymentLogDaoImpl() {
        properties = LoadPropertyFile.loadQueries(LoadPropertyFile.PAYMENT_FILE, PaymentDaoImpl.class,
                GET_PAYMENT_LOG_BY_STATUS_ID);
    }

    /**
     * Gets the payment log by email.
     *
     * @param paymentId the payment id
     * @param email     the email
     * @param statusId  the status id
     * @return the payment log by email
     */
    @Override
    public PaymentLog getPaymentLogByEmail(long paymentId, int statusId) {
        String query = properties.getProperty(GET_PAYMENT_LOG_BY_STATUS_ID);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource parameterSource = new MapSqlParameterSource("statusId", statusId).addValue("paymentId",
                paymentId);

        try {
            return jdbcTemplate.queryForObject(query, parameterSource, new RowMapper<PaymentLog>() {

                @Override
                public PaymentLog mapRow(ResultSet rs, int rowNum) throws SQLException {
                    PaymentLog paymentLog = new PaymentLog();
                    paymentLog.setId(rs.getLong("id"));
                    paymentLog.setCreateTime(rs.getString("create_time"));
                    paymentLog.setUpdateTime(rs.getString("update_time"));
                    paymentLog.setPersonId(rs.getLong("person_id"));
                    return paymentLog;
                }
            });
        } catch (DataAccessException e) {
            LogUtil.logErrorMessage(LOGGER, " Exception while fetching log " + e.getMessage());
            LogUtil.logException(LOGGER, e);
            return null;
        }
    }

    /**
     * Insert payment log.
     *
     * @param paymentServiceRequest the payment service request
     */
    @Override
    public void insertPaymentLog(PaymentServiceRequest paymentServiceRequest) {
        String query = properties.getProperty(INSERT_PAYMENT_LOG_DETAILS);
        LogUtil.log(LOGGER, "insertPaymentLog query is  = " + query);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        BeanPropertySqlParameterSource beanPropertyResourse = new BeanPropertySqlParameterSource(paymentServiceRequest);

        jdbcTemplate.update(query, beanPropertyResourse);

    }

}
