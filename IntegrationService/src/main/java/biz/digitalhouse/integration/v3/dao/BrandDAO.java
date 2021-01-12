package biz.digitalhouse.integration.v3.dao;

import biz.digitalhouse.integration.v3.model.Brand;

import java.util.List;

/**
 * @author Vitalii Babenko
 *         on 28.02.2016.
 */
public interface BrandDAO {

    Brand getBrand(String identifier);

    List<Long> getAllExternalBrandIDs();
}
