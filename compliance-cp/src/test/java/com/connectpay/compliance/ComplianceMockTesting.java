package com.connectpay.compliance;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;

import com.connectpay.compliance.service.helper.ComplianceServiceHelper;
import com.connectpay.ib.http.constants.IntegrationPropertiesBean;
import com.connectpay.ib.http.helper.IntegrationHelper;
import com.connectpay.payments.request.ComplianceAuthRequest;

@RunWith(MockitoJUnitRunner.class)
@TestPropertySource("classpath:application.properties")
public class ComplianceMockTesting {
    @Value("${compliance.api.transactionsurl}")
    private String transactionUrl;

    @Mock
    private IntegrationHelper integrationHelper;

    @InjectMocks
    private ComplianceServiceHelper complianceServiceHelp = new ComplianceServiceHelper();

    @Test
    public void testGetToken() {
        Mockito.when(integrationHelper.processRequest(getIntegrationPropBean())).thenReturn(
                "{\"supplierResponse\":\"{\\\"token\\\":\\\"443e4c469965f6a1d9726b0bc5332a49204c62ac\\\"}\",\"httpStatusCode\":\"200\"}");

        String actualtoken = complianceServiceHelp.getToken();
        String expectedToken = "443e4c469965f6a1d9726b0bc5332a49204c62ac";

        Assert.assertEquals(expectedToken, actualtoken);
    }

    private IntegrationPropertiesBean getIntegrationPropBean() {
        ComplianceAuthRequest authRequest = new ComplianceAuthRequest();
        return integrationHelper.preparePostJsonRequest(authRequest, transactionUrl);
    }

}
