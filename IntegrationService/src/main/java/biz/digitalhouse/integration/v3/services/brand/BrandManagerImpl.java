package biz.digitalhouse.integration.v3.services.brand;

import biz.digitalhouse.integration.v3.dao.BrandDAO;
import biz.digitalhouse.integration.v3.model.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Vitalii Babenko
 *         on 28.02.2016.
 */
@Component
public class BrandManagerImpl implements BrandManager {

    private BrandDAO brandDAO;

    @Autowired
    public BrandManagerImpl(BrandDAO brandDAO) {
        this.brandDAO = brandDAO;
    }

    @Override
    public Brand getBrandByIdentifier(String identifier) {
        return brandDAO.getBrand(identifier);
    }
}
