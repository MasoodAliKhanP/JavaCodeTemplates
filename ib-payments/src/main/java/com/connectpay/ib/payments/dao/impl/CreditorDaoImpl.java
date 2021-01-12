package com.connectpay.ib.payments.dao.impl;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.connectpay.ib.payments.dao.CreditorDao;
import com.connectpay.ib.utils.ApplicationConstants;
import com.connectpay.ib.utils.LoadPropertyFile;
import com.connectpay.ib.utils.LogUtil;
import com.connectpay.payments.bean.CreditorDetails;
import com.connectpay.payments.request.PaymentServiceRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class CreditorDaoImpl.
 */
@Repository
public class CreditorDaoImpl implements CreditorDao {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger(CreditorDaoImpl.class);

    /** The Constant INSERT_CREDITOR_DETAILS. */
    private static final String INSERT_CREDITOR_DETAILS = "insert.creditor.details";

    /** The jdbc template. */
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    /** The properties. */
    private final Properties properties;

    /**
     * Instantiates a new creditor dao impl.
     */
    public CreditorDaoImpl() {
        properties = LoadPropertyFile.loadQueries(LoadPropertyFile.CREDITOR_FILE, CreditorDaoImpl.class,
                INSERT_CREDITOR_DETAILS);
    }

    /**
     * Insert creditor details.
     *
     * @param paymentServiceRequest the payment service request
     * @return the creditor details
     */
    @Override
    public CreditorDetails insertCreditorDetails(PaymentServiceRequest paymentServiceRequest) {
        String query = properties.getProperty(INSERT_CREDITOR_DETAILS);
        LogUtil.log(LOGGER, "insertPaymentDetails query is  = " + query);
        Assert.notEmpty(properties, LoadPropertyFile.CREDITOR_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        BeanPropertySqlParameterSource beanPropertyResourse = new BeanPropertySqlParameterSource(paymentServiceRequest);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(query, beanPropertyResourse, keyHolder);

        CreditorDetails creditorDetails = new CreditorDetails();
        creditorDetails.setAccountNumber(paymentServiceRequest.getAccountNumber());
        creditorDetails.setBankAddress(paymentServiceRequest.getBankAddress());
        creditorDetails.setBankCode(paymentServiceRequest.getBankCode());
        creditorDetails.setBankName(paymentServiceRequest.getBankName());
        creditorDetails.setName(paymentServiceRequest.getBeneficiaryName());
        creditorDetails.setId(keyHolder.getKey().longValue());

        LogUtil.log(LOGGER, "creditorDetails :: " + creditorDetails);
        return creditorDetails;
    }

}
