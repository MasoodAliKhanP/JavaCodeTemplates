package com.connectpay.nano.service.formatter.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.connectpay.http.constants.IntegrationPropertiesBean;
import com.connectpay.http.service.RequestDataFormatter;
import com.connectpay.http.utils.HttpUtils;
import com.connectpay.nano.bean.AuthorizedPersonInfo;
import com.connectpay.nano.bean.NanoAuthorizedPerson;
import com.connectpay.utils.LogUtil;
import com.connectpay.utils.constants.ApplicationConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class AddAuthorizedPersonRequestFormatter implements RequestDataFormatter {
	private static Logger logger = LogManager.getLogger(AddAuthorizedPersonRequestFormatter.class);
	private static final String ROLE = "FIRST_SIGNATORY";

	@Value("${nano.url.add.authorize.person}")
	private String nanoAddAuthorizePersonUrl;
	
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
		String url = nanoAddAuthorizePersonUrl.replace("{id}", requestMap.get(ApplicationConstants.PARTY_ID) + "");
		integrationPropertiesBean.setUrl(url);

		LogUtil.log(logger, "NANO ADD AUTHORIZED PERSON REQUEST :: " + requestMap.get(ApplicationConstants.AUTHORIZED_PERSONS));
		String[] authorizedPersons = requestMap.get(ApplicationConstants.AUTHORIZED_PERSONS).split(",");
		List<AuthorizedPersonInfo> authorizedPersonInfo = new ArrayList<>();
		for(String s : authorizedPersons){
			AuthorizedPersonInfo api = new AuthorizedPersonInfo();
			api.setPerson(s);
			api.setRole(ROLE);
			authorizedPersonInfo.add(api);
		}
		NanoAuthorizedPerson nanoAuthorizedPerson = new NanoAuthorizedPerson();
		nanoAuthorizedPerson.setAuthorizations(authorizedPersonInfo);
		
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		return gson.toJson(nanoAuthorizedPerson);
	}

}
