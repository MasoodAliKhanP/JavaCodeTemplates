package biz.digitalhouse.integration.v3.web.ws;

import biz.digitalhouse.integration.v3.ServiceResponseCode;
import biz.digitalhouse.integration.v3.exceptions.CommonServiceException;
import biz.digitalhouse.integration.v3.model.Brand;
import biz.digitalhouse.integration.v3.services.brand.BrandManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Vitalii Babenko
 *         on 28.02.2016.
 */
public abstract class WsAbstractAPI {

    private final Log log = LogFactory.getLog(getClass());
    private BrandManager brandManager;

    public WsAbstractAPI(BrandManager brandManager) {
        this.brandManager = brandManager;
    }

    protected long check(String login, String password) throws CommonServiceException {

        if(login == null || login.trim().isEmpty()) {
            throw ServiceResponseCode.AUTH_ERROR.EXCEPTION();
        }

        if(password == null || password.trim().isEmpty()) {
            throw ServiceResponseCode.AUTH_ERROR.EXCEPTION();
        }

        Brand brand = brandManager.getBrandByIdentifier(login);
        if (brand == null) {
            if (log.isWarnEnabled()) {
                log.warn("Brand by Identifier[" + login + "] not found!");
            }
            throw ServiceResponseCode.AUTH_ERROR.EXCEPTION();
        }

        if (!password.equals(brand.getPassword())) {
            if (log.isWarnEnabled()) {
                log.warn("Password by Brand[" + brand.getBrandId() + "] incorrect!");
            }
            throw ServiceResponseCode.AUTH_ERROR.EXCEPTION();
        }
        return brand.getBrandId();
    }
    
    protected long check(String login) throws CommonServiceException {

        if(login == null || login.trim().isEmpty()) {
            throw ServiceResponseCode.AUTH_ERROR.EXCEPTION();
        }

        Brand brand = brandManager.getBrandByIdentifier(login);
        if (brand == null) {
            if (log.isWarnEnabled()) {
                log.warn("Brand by Identifier[" + login + "] not found!");
            }
            throw ServiceResponseCode.AUTH_ERROR.EXCEPTION();
        }

        return brand.getBrandId();
    }
}
