package com.connectpay.nano.service.formatter.response;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.connectpay.http.constants.IntegrationConstants;
import com.connectpay.http.constants.IntegrationPropertiesBean;
import com.connectpay.http.service.ResponseDataFormatter;
import com.connectpay.utils.LogUtil;
import com.connectpay.utils.constants.ApplicationConstants;

@Component
public class AddAuthorizedPersonResponseFormatter implements ResponseDataFormatter {
	private static Logger logger = LogManager.getLogger(AddAuthorizedPersonResponseFormatter.class);
	private static final String SUCCESS_RESPONSE = "202";

	
	@Override
	public <T> void handleResponse(T tTypeObject, Map<String, String> responseMap) {
		IntegrationPropertiesBean integrationBean = (IntegrationPropertiesBean) tTypeObject;
		String supplierResponse = responseMap.get(IntegrationConstants.SUPPLIER_HTTPSTATUS_KEY);
		LogUtil.log(logger, "nano add authorized person response ==  " + supplierResponse);
		if(SUCCESS_RESPONSE.equalsIgnoreCase(supplierResponse)){
			integrationBean.setResponse(ApplicationConstants.SUCCESS_MESSAGE);
		} else {
			integrationBean.setResponse(ApplicationConstants.FAIL_MESSAGE);
		}
	}
}
