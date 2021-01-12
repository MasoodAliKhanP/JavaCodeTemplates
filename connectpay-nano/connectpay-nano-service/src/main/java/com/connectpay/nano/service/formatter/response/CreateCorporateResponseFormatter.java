package com.connectpay.nano.service.formatter.response;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.connectpay.http.constants.IntegrationConstants;
import com.connectpay.http.constants.IntegrationPropertiesBean;
import com.connectpay.http.service.ResponseDataFormatter;
import com.connectpay.utils.LogUtil;

@Component
public class CreateCorporateResponseFormatter implements ResponseDataFormatter {
	private static Logger logger = LogManager.getLogger(CreateCorporateResponseFormatter.class);

	@Override
	public <T> void handleResponse(T tTypeObject, Map<String, String> responseMap) {
		IntegrationPropertiesBean integrationBean = (IntegrationPropertiesBean) tTypeObject;
		String supplierResponse = responseMap.get(IntegrationConstants.SUPPLIER_RESPONSE_KEY);
		LogUtil.log(logger, "nano create corporate response ==  " + supplierResponse);
		integrationBean.setResponse(supplierResponse);

	}

}
