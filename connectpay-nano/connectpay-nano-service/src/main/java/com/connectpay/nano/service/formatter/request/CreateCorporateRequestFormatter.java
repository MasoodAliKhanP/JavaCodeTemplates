package com.connectpay.nano.service.formatter.request;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.connectpay.http.constants.IntegrationPropertiesBean;
import com.connectpay.http.service.RequestDataFormatter;
import com.connectpay.http.utils.HttpUtils;
import com.connectpay.nano.bean.BasicInfo;
import com.connectpay.nano.bean.NanoCreateCorporate;
import com.connectpay.utils.LogUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class CreateCorporateRequestFormatter implements RequestDataFormatter {
	private static Logger logger = LogManager.getLogger(CreateCorporateRequestFormatter.class);
	private static final String BUSINESS_CATEGORY = "11300-032";
	
	@Value("${nano.url.create.corporate}")
	private String nanoCreateCorporate;
	
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
		integrationPropertiesBean.setUrl(nanoCreateCorporate);
		
		return integrationPropertiesBean;
	}

	private <T> String prepareJSONRequest(T tTypeObject) {
		BasicInfo basicInfo = (BasicInfo) tTypeObject;
		basicInfo.setBusinessCategory(BUSINESS_CATEGORY);
		NanoCreateCorporate nanoCreateCorporateRequest = new NanoCreateCorporate();
		nanoCreateCorporateRequest.setBasicInfo(basicInfo);
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		LogUtil.log(logger, "NANO CREATE CORPORATE REQUEST :: " + nanoCreateCorporateRequest.toString());
		return  gson.toJson(nanoCreateCorporateRequest);
	}

}
