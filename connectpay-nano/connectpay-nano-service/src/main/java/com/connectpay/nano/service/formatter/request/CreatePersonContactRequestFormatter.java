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
public class CreatePersonContactRequestFormatter implements RequestDataFormatter {
	private static Logger logger = LogManager.getLogger(CreatePersonContactRequestFormatter.class);
	@Value("${nano.url.add.person.contact}")
	private String nanoCreatePersonContactUrl;
	
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
		String nanoContactPersonDetails = requestMap.get(ApplicationConstants.NANO_CONTACT_DETAILS);
		LogUtil.log(logger, "NANO CREATE CONTACT PERSON REQUEST :: " + nanoContactPersonDetails);

		String url = nanoCreatePersonContactUrl.replace("{id}", requestMap.get(ApplicationConstants.PARTY_ID) + "");
		integrationPropertiesBean.setUrl(url);

		return nanoContactPersonDetails;
	}

}
