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
import com.connectpay.nano.bean.DocumentBasicInfo;
import com.connectpay.nano.bean.NanoCreatePersonDocument;
import com.connectpay.utils.LogUtil;
import com.connectpay.utils.constants.ApplicationConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class CreatePersonDocumentRequestFormatter implements RequestDataFormatter {
	private static Logger logger = LogManager.getLogger(CreatePersonDocumentRequestFormatter.class);
	@Value("${nano.url.add.person.documents}")
	private String nanoCreatePersonDocumentUrl;
	
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
		List<DocumentBasicInfo> documentBasicInfoList = new ArrayList<>();
		Map<String, String> requestMap = (Map<String, String>) tTypeObject;
		String nanoDocumentBasicInfo = requestMap.get(ApplicationConstants.NANO_DOCUMENT_BASIC_INFO);
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();

		DocumentBasicInfo documentBasicInfo = gson.fromJson(nanoDocumentBasicInfo, DocumentBasicInfo.class);
		documentBasicInfoList.add(documentBasicInfo);
		NanoCreatePersonDocument nanoCreatePersonDocument = new NanoCreatePersonDocument();
		nanoCreatePersonDocument.setIdDocuments(documentBasicInfoList);

		String request = gson.toJson(nanoCreatePersonDocument);

		LogUtil.log(logger, "NANO CREATE DOCUMENT PERSON REQUEST :: " + request);
		

		String url = nanoCreatePersonDocumentUrl.replace("{id}", requestMap.get(ApplicationConstants.PARTY_ID) + "");
		integrationPropertiesBean.setUrl(url);
		return request;
	}
}
