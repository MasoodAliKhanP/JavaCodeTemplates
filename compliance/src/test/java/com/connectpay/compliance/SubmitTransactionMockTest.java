package com.connectpay.compliance;

import java.io.IOException;

import org.apache.http.message.BasicHeader;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;

import com.connectpay.compliance.service.helper.ComplianceServiceHelper;
import com.connectpay.compliance.service.impl.ComplianceSerivceImpl;
import com.connectpay.ib.enums.ComplianceEnums;
import com.connectpay.ib.http.constants.IntegrationPropertiesBean;
import com.connectpay.ib.http.helper.IntegrationHelper;
import com.connectpay.payments.request.ComplianceAuthRequest;

@RunWith(MockitoJUnitRunner.class)
@TestPropertySource("classpath:application.properties")
public class SubmitTransactionMockTest {
    @Value("${compliance.api.transactionsurl}")
    private String transactionUrl;

    @Mock
    private IntegrationHelper integrationHelper;

    @Mock
    private ComplianceServiceHelper complianceServiceHelper;

    @InjectMocks
    private ComplianceSerivceImpl complianceServiceImpl = new ComplianceSerivceImpl();

    @Test
    public void testSubmitTransactionForMonitoring() throws JsonParseException, JsonMappingException, IOException {
//        ReadJson readJson = new ReadJson();
//        ComplianceTransactionRequest request = readJson.readComplianceRequestJson("SubmitTransaction/Request.json");
//
//        Mockito.when(complianceServiceHelper.getToken())
//            .thenReturn("443e4c469965f6a1d9726b0bc5332a49204c62ac");
//
//        String mockResponse = readJson.ReadResponseJson("SubmitTransaction/Response.json");
//        Mockito.when(integrationHelper.processRequest(getIntegrationPropBeanWithHeader()))
//            .thenReturn(mockResponse);
//
//        APIResponse<?> actualResponse = complianceServiceImpl.submitTransactionForMonitoring(request);
//        String expectedResponse = readJson.ReadResponseJson("SubmitTransaction/Response.json");
//
//        Assert.assertEquals(expectedResponse, actualResponse.toString());
        Assert.assertEquals("", "");
    }

    private IntegrationPropertiesBean getIntegrationPropBeanWithHeader() {
        ComplianceAuthRequest request = new ComplianceAuthRequest();
        IntegrationPropertiesBean integraionPropBean = integrationHelper.preparePostJsonRequest(request,
                transactionUrl);
        String tokenFormat = ComplianceEnums.TOKEN.getEvent().toUpperCase() + " "
                + "443e4c469965f6a1d9726b0bc5332a49204c62ac";
        integraionPropBean.getHeadersArray().add(new BasicHeader("Authorization", tokenFormat));
        return integraionPropBean;
    }
}
