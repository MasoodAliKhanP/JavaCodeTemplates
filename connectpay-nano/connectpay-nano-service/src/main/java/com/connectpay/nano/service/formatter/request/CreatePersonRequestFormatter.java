package com.connectpay.nano.service.formatter.request;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.connectpay.http.constants.IntegrationPropertiesBean;
import com.connectpay.http.service.RequestDataFormatter;
import com.connectpay.http.utils.HttpUtils;
import com.connectpay.nano.bean.NanoCreatePerson;
import com.connectpay.nano.bean.PersonBasicInfo;
import com.connectpay.utils.LogUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class CreatePersonRequestFormatter implements RequestDataFormatter {
	private static Logger logger = LogManager.getLogger(CreatePersonRequestFormatter.class);
	@Value("${nano.url.create.person}")
	private String nanoCreatePerson;
	
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
		integrationPropertiesBean.setRequestPayload(prepareJSONRequest(tTypeObject));
		integrationPropertiesBean.setUrl(nanoCreatePerson);
		return integrationPropertiesBean;
	}

	private <T> String prepareJSONRequest(T tTypeObject) {
		PersonBasicInfo personBasicInfo = (PersonBasicInfo) tTypeObject;
		NanoCreatePerson nanoCreatePersonRequest = new NanoCreatePerson();
		nanoCreatePersonRequest.setBasicInfo(personBasicInfo);
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		LogUtil.log(logger, "NANO CREATE CORPORATE REQUEST :: " + nanoCreatePersonRequest);
		return gson.toJson(nanoCreatePersonRequest);
	}

}
