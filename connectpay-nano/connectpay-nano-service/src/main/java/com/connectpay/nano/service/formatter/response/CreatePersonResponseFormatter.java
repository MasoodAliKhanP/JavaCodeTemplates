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
import com.google.gson.Gson;

@Component
public class CreatePersonResponseFormatter implements ResponseDataFormatter {
	private static Logger logger = LogManager.getLogger(CreatePersonResponseFormatter.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> void handleResponse(T tTypeObject, Map<String, String> responseMap) {
		IntegrationPropertiesBean integrationBean = (IntegrationPropertiesBean) tTypeObject;
		String supplierResponse = responseMap.get(IntegrationConstants.SUPPLIER_RESPONSE_KEY);
		Gson gson = new Gson();
		Map<String,String> nanoResponse = gson.fromJson(supplierResponse, Map.class);
		
		if((nanoResponse.containsKey("error"))){
			Map<String, Map<String,String>> nanoResponseMap = gson.fromJson(supplierResponse, Map.class);
			String code = nanoResponseMap.get("error").get("code");
			nanoResponse.put(ApplicationConstants.NANO_ERROR_CODE, code);
		}
		
		String supplierResponseMap = gson.toJson(nanoResponse);
		LogUtil.log(logger, "nano create corporate response ==  " + supplierResponseMap);
		integrationBean.setResponse(supplierResponseMap);
		
	}

}
