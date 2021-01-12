package com.connectpay.nano.service.formatter.request;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.connectpay.http.constants.IntegrationPropertiesBean;
import com.connectpay.http.service.RequestDataFormatter;
import com.connectpay.http.utils.HttpUtils;
import com.connectpay.utils.LogUtil;
import com.connectpay.utils.constants.ApplicationConstants;

@Component
public class AddCorporateAddressRequestFormatter implements RequestDataFormatter {
	private static Logger logger = LogManager.getLogger(AddCorporateAddressRequestFormatter.class);

	@Value("${nano.url.add.corporate.address}")
	private String nanoAddCorporateAddressUrl;
	
	@Value("${nano.auth.username}")
	private String username;
	
	@Value("${nano.auth.password}")
	private String password;
	
	@Override
	public <T> IntegrationPropertiesBean fetchRequestBody(T tTypeObject) {
		IntegrationPropertiesBean integrationPropertiesBean = HttpUtils.giveBasicJson();
		integrationPropertiesBean.setAuthUser(username);
		integrationPropertiesBean.setAuthPass(password);
		HttpUtils.setBasicAuthHeader(integrationPropertiesBean);
		integrationPropertiesBean.setRequestPayload(prepareJSONRequest(tTypeObject, integrationPropertiesBean));
		return integrationPropertiesBean;
	}

	@SuppressWarnings("unchecked")
	private <T> String prepareJSONRequest(T tTypeObject, IntegrationPropertiesBean integrationPropertiesBean) {

		Map<String, String> requestMap = (Map<String, String>) tTypeObject;
		String url = nanoAddCorporateAddressUrl.replace("{id}", requestMap.get(ApplicationConstants.PARTY_ID) + "");
		integrationPropertiesBean.setUrl(url);
		LogUtil.log(logger, "nanoAddCorporateAddressUrl is :: "+url);

		LogUtil.log(logger, "NANO CREATE ADDRESS CORPORATE REQUEST :: " + requestMap.get(ApplicationConstants.NANO_CORPORATE_ADDRESS));
		return requestMap.get(ApplicationConstants.NANO_CORPORATE_ADDRESS);
	}

}
