package biz.digitalhouse.integration.v3.dao.mongo;

import biz.digitalhouse.integration.v3.dao.TransactionDAO;
import biz.digitalhouse.integration.v3.model.Transaction;
import biz.digitalhouse.integration.v3.model.Transaction.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Vitalii Babenko
 *         on 10.04.2016.
 */
@Repository
public class TransactionDAOMongo implements TransactionDAO {

    private final Log log = LogFactory.getLog(getClass());
    private MongoTemplate mongoTemplate;

    @Autowired
    public TransactionDAOMongo(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Transaction generateTransaction(String vendorID,
                                           String incomingTransactionID,
                                           String incomingRoundID,
                                           String incomingOriginalRoundID,
                                           long brandID,
                                           double amount,
                                           long roundID,
                                           Long originalRoundID,
                                           long gameID, String gameSymbol, Boolean gambling,
                                           String bonusCode, TransactionType type,
                                           long playerID,
                                           String externalPlayerID) {

        if (brandID == 0) {
            throw new RuntimeException("brandID = 0!");
        }

        Transaction tr = new Transaction();
        tr.setVendorID(vendorID);
        tr.setIncomingTransactionID(incomingTransactionID);
        tr.setIncomingRoundID(incomingRoundID);
        tr.setIncomingOriginalRoundID(incomingOriginalRoundID);
        tr.setBrandID(brandID);
        tr.setAmount(amount);
        tr.setRoundID(roundID);
        tr.setOriginalRoundID(originalRoundID);
        tr.setGambling(gambling);
        tr.setGameID(gameID);
        tr.setGameSymbol(gameSymbol);
        tr.setBonusCode(bonusCode);
        tr.setTransactionType(type);
        tr.setTransactionStatus(Transaction.TransactionStatus.PROCESSING);
        tr.setPlayerID(playerID);
        tr.setExternalPlayerID(externalPlayerID);
        mongoTemplate.save(tr);

        return tr;
    }

    public void updateTransaction(Transaction transaction) {
        transaction.setLastProcessingDate(new Date());
        mongoTemplate.save(transaction);
    }

    public Transaction getTransactionForProcessing(String transactionID, Calendar operationDate) {

        Query query = new Query();
        query.addCriteria(Criteria.where("transactionID").is(transactionID));
        query.addCriteria(Criteria.where("transactionStatus").in(TransactionStatus.ERROR));
        if(operationDate != null) {
            query.addCriteria(Criteria.where("createDate").gte(operationDate));
        }

        Update update = new Update();
        update.set("transactionStatus", TransactionStatus.PROCESSING);
        update.set("lastProcessingDate", new Date());

        return mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Transaction.class);
    }

    public Transaction findTransactionByIncomingTransactionID(String vendorID, String incomingTransactionID, TransactionType type, String incomingRoundID) {

        Query query = new Query();
        query.addCriteria(Criteria.where("transactionStatus").ne(TransactionStatus.INTERNAL_ERROR));
        query.addCriteria(Criteria.where("vendorID").is(vendorID));
        query.addCriteria(Criteria.where("transactionType").is(type));
        query.addCriteria(Criteria.where("incomingTransactionID").is(incomingTransactionID));
        if(incomingRoundID != null) {
            query.addCriteria(Criteria.where("incomingRoundID").is(incomingRoundID));
        }
        return mongoTemplate.findOne(query, Transaction.class);
    }

    public Transaction findTransactionByIncomingRoundID(String vendorID, String incomingRoundID, TransactionType type) {

        Query query = new Query();
        query.addCriteria(Criteria.where("vendorID").is(vendorID));
        query.addCriteria(Criteria.where("transactionType").is(type));
        query.addCriteria(Criteria.where("incomingRoundID").is(incomingRoundID));

        return mongoTemplate.findOne(query, Transaction.class);
    }

    @Override
    public List<Transaction> findTransactionsForRetry(Long brandID, Date createDate, Date lastProcessingDate, TransactionType type) {
        Query query = new Query();
        query.addCriteria(Criteria.where("transactionStatus").is(TransactionStatus.ERROR));
        query.addCriteria(Criteria.where("transactionType").is(type));
        query.addCriteria(Criteria.where("brandID").is(brandID));
        query.addCriteria(Criteria.where("createDate").gt(createDate));
        query.addCriteria(Criteria.where("lastProcessingDate").lt(lastProcessingDate));
        query.limit(10);
        return mongoTemplate.find(query, Transaction.class);

    }

    @Override
    public Transaction lockTransactionForRetry(String transactionID) {

        Query query = new Query();

        query.addCriteria(Criteria.where("transactionID").is(transactionID));
        query.addCriteria(Criteria.where("transactionStatus").in(TransactionStatus.ERROR));

        Update update = new Update();
        update.set("transactionStatus", TransactionStatus.PROCESSING);
        update.set("lastProcessingDate", new Date());

        return mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Transaction.class);
    }

}
