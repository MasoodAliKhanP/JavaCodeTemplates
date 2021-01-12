package com.connectpay.nano.service.formatter.request;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.connectpay.http.constants.IntegrationPropertiesBean;
import com.connectpay.http.service.RequestDataFormatter;
import com.connectpay.http.utils.HttpUtils;

@Component
public class CompletePersonCreationRequestFormatter implements RequestDataFormatter {

	@Value("${nano.url.complete}")
	private String nanoCompletePersonUrl;
	
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
		integrationPropertiesBean.setRequestPayload("{}");
		String partyId = (String) tTypeObject;
		String url = nanoCompletePersonUrl.replace("{id}", partyId);
		integrationPropertiesBean.setUrl(url);

		return integrationPropertiesBean;
	}

}
