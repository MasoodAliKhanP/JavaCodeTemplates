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
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.connectpay.ib.payments.dao.BulkPaymentDao;
import com.connectpay.ib.utils.ApplicationConstants;
import com.connectpay.ib.utils.LoadPropertyFile;
import com.connectpay.ib.utils.LogUtil;
import com.connectpay.payments.bean.BulkPaymentData;
import com.connectpay.payments.bean.BulkPaymentFileStatus;
import com.connectpay.payments.bean.BulkPaymentMapping;
import com.connectpay.payments.bean.BulkPaymentType;
import com.connectpay.payments.request.PaymentServiceRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class BulkPaymentDaoImpl.
 */
@Repository
public class BulkPaymentDaoImpl implements BulkPaymentDao {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LogManager.getLogger(BulkPaymentDao.class);

    /** The Constant INSERT_BULK_PAYMENT_DATA. */
    private static final String INSERT_BULK_PAYMENT_DATA = "insert.bulk.payment.data";

    /** The Constant INSERT_BULK_PAYMENT_MAP_DATA. */
    private static final String INSERT_BULK_PAYMENT_MAP_DATA = "insert.bulk.payment.map.data";

    /** The Constant GET_BULK_PAYMENT_DATA. */
    private static final String GET_BULK_PAYMENT_DATA = "get.bulk.payment.data";

    /** The Constant GET_BULK_PAYMENT_DATA_BY_PARTYID. */
    private static final String GET_BULK_PAYMENT_DATA_BY_PARTYID = "get.bulk.payment.data.by.partyId";

    /** The Constant UPDATE_BULK_PAYMENT_DATA_BY_REF_ID. */
    private static final String UPDATE_BULK_PAYMENT_DATA_BY_REF_ID = "update.bulk.payment.data.by.refId";

    /** The Constant UPDATE_BULK_PAYMENT_TRANSACTION_DATA_BY_REF_ID. */
    private static final String UPDATE_BULK_PAYMENT_TRANSACTION_DATA_BY_REF_ID = "update.bulk.payment.trans.data.by.refId";

    /** The Constant UPDATE_EMAIL_BY_PAYMENT_REFERENCE_ID. */
    private static final String UPDATE_PERSON_BY_PAYMENT_REFERENCE_ID = "update.person.by.payment.ref.id";

    /** The Constant SAVE_BULK_PAYMENT_ERROR_MESSAGE. */
    private static final String SAVE_BULK_PAYMENT_ERROR_MESSAGE = "save.bulk.payment.error.message";

    private static final String GET_BULK_PAYMENT_MAPPING_BY_REF_ID = "get.bulk.payment.mapping.by.ref.Id";

    private static final String UPDATE_IBAN_BY_BULK_REF_ID = "update.iban.by.bulk.ref.id";

    private static final String GET_CUSTTOMER_PARTY_ID_BULK_PAYMENT_DATA = "get.customer.partyId.By.bulkReference";

    /** The Constant GET_BULK_PAYMENT_FILE_COUNT. */
    private static final String GET_BULK_PAYMENT_FILE_COUNT = "get.bulk.payment.file.count";

    /** The jdbc template. */
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    /** The properties. */
    private final Properties properties;

    /**
     * Instantiates a new bulk payment dao impl.
     */
    public BulkPaymentDaoImpl() {
        properties = LoadPropertyFile.loadQueries(LoadPropertyFile.PAYMENT_FILE, PaymentDaoImpl.class,
                INSERT_BULK_PAYMENT_DATA);
    }

    /**
     * Insert bulk payment data.
     *
     * @param bulkPaymentData the bulk payment data
     * @return the bulk payment data
     */
    @Override
    public BulkPaymentData insertBulkPaymentData(BulkPaymentData bulkPaymentData) {
        String query = properties.getProperty(INSERT_BULK_PAYMENT_DATA);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource parameterSource = new MapSqlParameterSource("customerPartyId",
                bulkPaymentData.getCustomerPartyId())
                        .addValue("bulkPaymentReference", bulkPaymentData.getBulkPaymentReference())
                        .addValue("email", bulkPaymentData.getEmail())
                        .addValue("fileLocation", bulkPaymentData.getFileLocation())
                        .addValue("fileName", bulkPaymentData.getFileName())
                        .addValue("uploadedBy", bulkPaymentData.getUploadedBy())
                        .addValue("bulkPaymentTypeId", bulkPaymentData.getBulkPaymentType().getId())
                        .addValue("bulkPaymentFileStatusId",
                                bulkPaymentData.getBulkPaymentFileStatus() == null ? null
                                        : bulkPaymentData.getBulkPaymentFileStatus().getId())
                        .addValue("personId", bulkPaymentData.getPersonId());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(query, parameterSource, keyHolder);
        bulkPaymentData.setId(keyHolder.getKey().longValue());
        return bulkPaymentData;

    }

    /**
     * Insert bulk payment map data.
     *
     * @param mappingList the mapping list
     */
    @Override
    public void insertBulkPaymentMapData(List<BulkPaymentMapping> mappingList) {
        String query = properties.getProperty(INSERT_BULK_PAYMENT_MAP_DATA);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(mappingList.toArray());
        jdbcTemplate.batchUpdate(query, params);
    }

    /**
     * Insert bulk payment mapping.
     *
     * @param bulkPaymentMapping the bulk payment mapping
     */
    @Override
    public void insertBulkPaymentMapping(BulkPaymentMapping bulkPaymentMapping) {
        String query = properties.getProperty(INSERT_BULK_PAYMENT_MAP_DATA);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        BeanPropertySqlParameterSource beanPropertyResourse = new BeanPropertySqlParameterSource(bulkPaymentMapping);
        jdbcTemplate.update(query, beanPropertyResourse);

    }

    /**
     * Gets the bulk payment data.
     *
     * @param bulkPaymentReference the bulk payment reference
     * @return the bulk payment data
     */
    @Override
    public BulkPaymentData getBulkPaymentData(String bulkPaymentReference) {
        String query = properties.getProperty(GET_BULK_PAYMENT_DATA);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource parameterSource = new MapSqlParameterSource("bulkPaymentReference", bulkPaymentReference);
        try {
            return jdbcTemplate.queryForObject(query, parameterSource, new RowMapper<BulkPaymentData>() {

                public BulkPaymentData mapRow(ResultSet rs, int rowNum) throws SQLException {
                    BulkPaymentData bulkPaymentData = new BulkPaymentData();
                    bulkPaymentData.setEmail(rs.getString("email"));
                    bulkPaymentData.setBulkPaymentReference(rs.getString("bulk_payment_reference"));
                    bulkPaymentData.setFileName(rs.getString("payment_file_name"));
                    bulkPaymentData.setTotalTransactionAmount(rs.getString("total_transaction_amount"));
                    bulkPaymentData.setTotalTransactionCount(rs.getInt("total_transaction_count"));
                    bulkPaymentData.setCustomerPartyId(rs.getString("customer_party_Id"));
                    BulkPaymentType bulkPaymentType = new BulkPaymentType();
                    bulkPaymentType.setPaymentType(rs.getString("payment_type"));
                    bulkPaymentData.setBulkPaymentType(bulkPaymentType);
                    BulkPaymentFileStatus filesStatus = new BulkPaymentFileStatus();
                    filesStatus.setFileStatus(rs.getString("status"));
                    bulkPaymentData.setBulkPaymentFileStatus(filesStatus);
                    bulkPaymentData.setPayerIban(rs.getString("payer_account_number"));
                    bulkPaymentData.setPersonId(rs.getLong("person_id"));
                    return bulkPaymentData;
                }
            });
        } catch (DataAccessException e) {
            LogUtil.logErrorMessage(LOGGER,
                    " :: in fetching bulk payment details :: DataAccessException :: " + e.getMessage());
            LogUtil.logException(LOGGER, e);
            return null;
        }
    }

    /**
     * Gets the bulk payment data by party id.
     *
     * @param customerPartyId the customer party id
     * @return the bulk payment data by party id
     */
    @Override
    public List<BulkPaymentData> getBulkPaymentDataByPartyId(String customerPartyId, List<Integer> fileStatusIdList,
            int size, int startIndex) {
        String query = properties.getProperty(GET_BULK_PAYMENT_DATA_BY_PARTYID);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource parameterSource = new MapSqlParameterSource("customerPartyId", customerPartyId)
                .addValue("fileStatusIdList", fileStatusIdList).addValue("startIndex", startIndex)
                .addValue("size", size);
        try {
            return jdbcTemplate.query(query, parameterSource, new RowMapper<BulkPaymentData>() {
                @Override
                public BulkPaymentData mapRow(ResultSet rs, int rowNum) throws SQLException {
                    BulkPaymentData bulkPaymentData = new BulkPaymentData();
                    bulkPaymentData.setEmail(rs.getString("email"));
                    bulkPaymentData.setBulkPaymentReference(rs.getString("bulk_payment_reference"));
                    bulkPaymentData.setCustomerPartyId(rs.getString("customer_party_Id"));
                    bulkPaymentData.setCreateTime(rs.getString("create_time"));
                    bulkPaymentData.setFileName(rs.getString("payment_file_name"));
                    BulkPaymentFileStatus bulkPaymentFileStatus = new BulkPaymentFileStatus();
                    bulkPaymentFileStatus.setFileStatus(rs.getString("status"));
                    bulkPaymentFileStatus.setId(rs.getInt("payment_file_status_id"));
                    bulkPaymentData.setBulkPaymentFileStatus(bulkPaymentFileStatus);
                    bulkPaymentData.setUploadedBy(rs.getString("uploaded_by"));
                    bulkPaymentData.setPersonId(rs.getLong("person_id"));
                    return bulkPaymentData;

                }
            });
        } catch (DataAccessException e) {
            LogUtil.logErrorMessage(LOGGER,
                    " :: in fetching bulk payment details :: DataAccessException :: " + e.getMessage());
            LogUtil.logException(LOGGER, e);
            return null;
        }
    }

    /**
     * Update bulk payment file status.
     *
     * @param bulkPaymentData the bulk payment data
     */
    @Override
    public void updateBulkPaymentFileStatus(BulkPaymentData bulkPaymentData) {
        String query = properties.getProperty(UPDATE_BULK_PAYMENT_DATA_BY_REF_ID);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource parameterSource = new MapSqlParameterSource("bulkPaymentReference",
                bulkPaymentData.getBulkPaymentReference()).addValue("bulkPaymentFileStatusId",
                        bulkPaymentData.getBulkPaymentFileStatus() == null ? null
                                : bulkPaymentData.getBulkPaymentFileStatus().getId());
        jdbcTemplate.update(query, parameterSource);
    }

    /**
     * Update bulk payment transaction details.
     *
     * @param bulkPaymentData the bulk payment data
     */
    @Override
    public void updateBulkPaymentTransactionDetails(BulkPaymentData bulkPaymentData) {
        String query = properties.getProperty(UPDATE_BULK_PAYMENT_TRANSACTION_DATA_BY_REF_ID);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource parameterSource = new MapSqlParameterSource("bulkPaymentReference",
                bulkPaymentData.getBulkPaymentReference())
                        .addValue("totalTransactionAmt", bulkPaymentData.getTotalTransactionAmount())
                        .addValue("totalTransactionCount", bulkPaymentData.getTotalTransactionCount());
        jdbcTemplate.update(query, parameterSource);
    }

    /**
     * Update Person Id by payment reference.
     *
     * @param paymentServiceRequest the payment service request
     */
    @Override
    public void updatePersonIdByPaymentReference(PaymentServiceRequest paymentServiceRequest) {
        String query = properties.getProperty(UPDATE_PERSON_BY_PAYMENT_REFERENCE_ID);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource parameterSource = new MapSqlParameterSource("paymentReference",
                paymentServiceRequest.getPaymentReference()).addValue("personId", paymentServiceRequest.getPersonId())
                        .addValue("email", paymentServiceRequest.getEmail());
        jdbcTemplate.update(query, parameterSource);

    }

    /**
     * Save bulk payment error messages.
     *
     * @param paymentServiceRequest the payment service request
     */
    @Override
    public void saveBulkPaymentErrorMessages(PaymentServiceRequest paymentServiceRequest) {
        String query = properties.getProperty(SAVE_BULK_PAYMENT_ERROR_MESSAGE);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource parameterSource = new MapSqlParameterSource("paymentReference",
                paymentServiceRequest.getPaymentReference())
                        .addValue("apiErrorCode", paymentServiceRequest.getApiErrorCode())
                        .addValue("apiErrorMessage", paymentServiceRequest.getApiErrorMsg())
                        .addValue("paymentStatusId", paymentServiceRequest.getPaymentStatusId());
        jdbcTemplate.update(query, parameterSource);

    }

    @Override
    public List<BulkPaymentMapping> getBulkpaymentMappingByBulkRefId(String bulkRefId) {
        String query = properties.getProperty(GET_BULK_PAYMENT_MAPPING_BY_REF_ID);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource parameterSource = new MapSqlParameterSource("bulkPaymentRef", bulkRefId);
        try {
            return jdbcTemplate.query(query, parameterSource, new RowMapper<BulkPaymentMapping>() {
                @Override
                public BulkPaymentMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
                    BulkPaymentMapping bulkPaymentMapping = new BulkPaymentMapping();
                    bulkPaymentMapping.setBulkPaymentReference(rs.getString("bulk_payment_reference"));
                    bulkPaymentMapping.setPaymentReference(rs.getString("payment_reference"));
                    return bulkPaymentMapping;

                }
            });
        } catch (DataAccessException e) {
            LogUtil.logErrorMessage(LOGGER,
                    " :: in fetching bulk payment details :: DataAccessException :: " + e.getMessage());
            LogUtil.logException(LOGGER, e);
            return null;
        }
    }

    @Override
    public void updateBulkDataPayerIbanByBulkref(String payerIban, String bulkRefId) {
        String query = properties.getProperty(UPDATE_IBAN_BY_BULK_REF_ID);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource parameterSource = new MapSqlParameterSource("bulkPaymentReference", bulkRefId)
                .addValue("payerIban", payerIban);
        jdbcTemplate.update(query, parameterSource);
    }

    @Override
    public BulkPaymentData getCustomerPartyIdByBulkReference(String bulkPaymentReference) {
        String query = properties.getProperty(GET_CUSTTOMER_PARTY_ID_BULK_PAYMENT_DATA);
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        SqlParameterSource parameterSource = new MapSqlParameterSource("bulkPaymentReference", bulkPaymentReference);
        try {
            return jdbcTemplate.queryForObject(query, parameterSource, new RowMapper<BulkPaymentData>() {

                public BulkPaymentData mapRow(ResultSet rs, int rowNum) throws SQLException {
                    BulkPaymentData bulkPaymentData = new BulkPaymentData();
                    bulkPaymentData.setBulkPaymentReference(rs.getString("bulk_payment_reference"));
                    bulkPaymentData.setCustomerPartyId(rs.getString("customer_party_Id"));
                    return bulkPaymentData;
                }
            });
        } catch (DataAccessException e) {
            LogUtil.logErrorMessage(LOGGER,
                    " :: in fetching bulk payment details :: DataAccessException :: " + e.getMessage());
            LogUtil.logException(LOGGER, e);
            return null;
        }
    }

    @Override
    public int getBulkPaymentFileCount(String customerPartyId, List<Integer> fileStatusIdList) {
        StringBuilder query = new StringBuilder(properties.getProperty(GET_BULK_PAYMENT_FILE_COUNT));
        Assert.notEmpty(properties, LoadPropertyFile.PAYMENT_FILE);
        Assert.notNull(query, ApplicationConstants.QUERY_NOT_NULL);
        MapSqlParameterSource parameters = new MapSqlParameterSource("customerPartyId", customerPartyId)
                .addValue("fileStatusIdList", fileStatusIdList);
        try {
            return jdbcTemplate.queryForObject(query.toString(), parameters, Integer.class);
        } catch (DataAccessException e) {
            LogUtil.logException(LOGGER, e);
            LogUtil.logErrorMessage(LOGGER,
                    " ::  unable to get bulk payment types  :: DataAccessException :: " + e.getMessage());
            return 0;
        }
    }

}
