package biz.digitalhouse.integration.v3.web.ws.http.bingoGameAPI;

import biz.digitalhouse.integration.v3.ServiceResponseCode;
import biz.digitalhouse.integration.v3.exceptions.CommonServiceException;
import biz.digitalhouse.integration.v3.model.BingoTransactionReport;
import biz.digitalhouse.integration.v3.model.Brand;
import biz.digitalhouse.integration.v3.services.bingoGame.BingoGameManager;
import biz.digitalhouse.integration.v3.services.brand.BrandManager;
import biz.digitalhouse.integration.v3.utils.HashValidator;
import biz.digitalhouse.integration.v3.utils.StringUtils;
import biz.digitalhouse.integration.v3.utils.UnixDateSerializer;
import biz.digitalhouse.integration.v3.web.ws.http.bingoGameAPI.dto.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Vitalii Babenko
 *         on 28.02.2016.
 */
@RestController
@RequestMapping("/BingoGameAPI")
public class BingoGameAPIController {

    private final Log log = LogFactory.getLog(getClass());
    private BingoGameManager bingoGameManager;
    private BrandManager brandManager;
    private Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new UnixDateSerializer()).create();

    @Autowired
    public BingoGameAPIController(BingoGameManager bingoGameManager, BrandManager brandManager) {
        this.bingoGameManager = bingoGameManager;
        this.brandManager = brandManager;
    }

    @RequestMapping("/TransactionReport")
    public TransactionReportResponse transactionReport(HttpServletRequest request, RequestEntity<String> requestEntity) {
        TransactionReportResponse res = new TransactionReportResponse();
        try {

            TransactionReportRequest req = gson.fromJson(requestEntity.getBody(), TransactionReportRequest.class);
            long brandID = check(req, request, requestEntity.getBody());
            int maxPortion = bingoGameManager.transactionReportMaxPortion(brandID);

            if(req.getPageOffset() > maxPortion) {
                ServiceResponseCode code = ServiceResponseCode.INVALID_PARAMETER.setMessage("Parameter 'size' can not be more than " + maxPortion);
                populateServiceResponse(res, code);
                return res;
            }

            long countReport = bingoGameManager.transactionReportCount(brandID, req.getPlayerID(), req.getStartDate(), req.getEndDate(),
                    req.getTransactionType() != null ? req.getTransactionType().name() : null);

            if(countReport > 0 && countReport > req.getFromIndex()) {

                List<BingoTransactionReport> result = bingoGameManager.transactionReport(brandID, req.getPlayerID(), req.getStartDate(), req.getEndDate(),
                        req.getTransactionType() != null ? req.getTransactionType().name() : null, req.getFromIndex(), req.getPageOffset());

                if (result != null) {
                    for (BingoTransactionReport rep : result) {
                        Balance bal = new Balance();
                        bal.setCash(rep.getCashBalance());
                        bal.setBonus(rep.getBonusBalance());
                        TransactionType type = null;
                        if(rep.getTransactionType() != null) {
                            try {
                                type = TransactionType.valueOf(rep.getTransactionType());
                            } catch (Exception e) {
                                log.error("DB return Transaction Type " + rep.getTransactionType() + ". This type not covert to enum.\n" + e.getMessage(), e);
                            }
                        }
                        res.getTransactions().add(new Transaction(rep.getTransactionID(), type, rep.getAmount(), bal, rep.getCurrency(), rep.getTransactionDate()));
                    }
                }
            }
            res.setCount(countReport);
            populateServiceResponse(res, ServiceResponseCode.OK);
        } catch (CommonServiceException e) {
            populateServiceResponse(res, e.getResponse());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            populateServiceResponse(res, ServiceResponseCode.INTERNAL_ERROR);
        }
        return res;
    }


    @RequestMapping("/BingoPlayerHistory")
    public BingoPlayerHistoryResponse bingoPlayerHistory(HttpServletRequest request, RequestEntity<String> requestEntity) {
        BingoPlayerHistoryResponse res = new BingoPlayerHistoryResponse();

        BingoPlayerHistoryRequest req = gson.fromJson(requestEntity.getBody(), BingoPlayerHistoryRequest.class);
        res.setCount(0L);

        if (StringUtils.isEmpty(req.getPlayerID())){
            ServiceResponseCode code = ServiceResponseCode.INVALID_PARAMETER.setMessage("Bad parameter 'playerID' ");
            populateServiceResponse(res, code);
            return res;
        }
        try {

            long brandID = check(req, request, requestEntity.getBody());
            int maxPortion = bingoGameManager.transactionReportMaxPortion(brandID);

            if(req.getPageOffset() > maxPortion) {
                if (log.isDebugEnabled()){
                    log.debug("req.getPageOffset --> " + req.getPageOffset() + ", maxPortion --> " + maxPortion);
                }
                ServiceResponseCode code = ServiceResponseCode.INVALID_PARAMETER.setMessage("Parameter 'size' can not be more than " + maxPortion);
                populateServiceResponse(res, code);
                return res;
            }

            int countReport = bingoGameManager.bingoPlayerHistoryCount(brandID, req.getPlayerID(), req.getStartDate(), req.getEndDate(), req.getLanguage());

            List<PlayerHistoryRound> result = null;
            if (countReport > 0 && countReport > req.getFromIndex()) {
                result = bingoGameManager.bingoPlayerHistory(brandID, req.getPlayerID(), req.getStartDate(), req.getEndDate(),
                        req.getFromIndex(), req.getPageOffset(), req.getLanguage());
            }
            if (result != null) {
                res.setRounds(result);
                res.setCount((long) countReport);
            }
        } catch (CommonServiceException e) {
            populateServiceResponse(res, e.getResponse());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            populateServiceResponse(res, ServiceResponseCode.INTERNAL_ERROR);
        }
        return res;
    }

    private long check(BaseRequest req, HttpServletRequest request, String requestBody) throws CommonServiceException, IOException {
        if(req.getSecureLogin() == null || req.getSecureLogin().trim().isEmpty()) {
            if (log.isWarnEnabled()) {
                log.warn("SecureLogin is empty");
            }
            throw ServiceResponseCode.AUTH_ERROR.EXCEPTION();
        }

        Brand brand = brandManager.getBrandByIdentifier(req.getSecureLogin());
        if (brand == null) {
            if (log.isWarnEnabled()) {
                log.warn("Brand by Identifier[" + req.getSecureLogin() + "] not found!");
            }
            throw ServiceResponseCode.AUTH_ERROR.EXCEPTION();
        }

        String hash = request.getHeader("X-hash");

        if(hash == null || hash.trim().isEmpty()) {
            if (log.isWarnEnabled()) {
                log.warn("hash is empty");
            }
            throw ServiceResponseCode.AUTH_ERROR.EXCEPTION();
        }

        if (!HashValidator.checkRequestHash(brand.getPassword(), requestBody, hash)) {
            if (log.isWarnEnabled()) {
                log.warn("Hash incorrect!");
            }
            throw ServiceResponseCode.AUTH_ERROR.EXCEPTION();
        }else{
            if (log.isDebugEnabled()) {
                log.debug("Hash is ok");
            }

        }
        return brand.getBrandId();
    }

    private void populateServiceResponse(BaseResponse res, ServiceResponseCode code) {
        res.setErrorCode(code.getIntCode());
        res.setErrorDescription(code.getDescription());
    }



}
