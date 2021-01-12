package com.connectpay.compliance;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.connectpay.compliance.service.helper.ComplianceServiceHelper;
import com.connectpay.compliance.test.utils.ReadJson;
import com.connectpay.ib.utils.APIResponse;
import com.connectpay.ib.utils.ConnectPayUtils;
import com.connectpay.payments.compliance.request.ComplianceTransactionRequest;
import com.connectpay.payments.compliance.response.ComplianceTransactionResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CpComplianceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ComplianceTestCases {
    private static final Logger logger = LogManager.getLogger(ComplianceTestCases.class);

    @LocalServerPort
    private int port;

    @Value("${username}")
    private String username;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ReadJson readJson;

    @Autowired
    ComplianceServiceHelper complianceServiceHelper;

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void readProfileVariable() {
        assertEquals("masood", username);
    }

    @Test
    public void DuplicateTransactionIDCheck() throws JsonParseException, JsonMappingException, IOException {

        ComplianceTransactionRequest compliRequest = readJson
                .readComplianceRequestJson("DuplicateTransaction/Request.json");
        HttpEntity<ComplianceTransactionRequest> entity = new HttpEntity<ComplianceTransactionRequest>(compliRequest,
                headers);
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/compliance/submitTransaction"),
                HttpMethod.POST, entity, String.class);
        String expected = readJson.ReadResponseJson("DuplicateTransaction/Response.json");
        String actual = response.getBody();
        assertEquals(expected, actual);
    }

    @Test
    public void ComplianceSuccessTransaction() throws JsonParseException, JsonMappingException, IOException {
        ComplianceTransactionRequest compliRequest = readJson.readComplianceRequestJson("Compliance/Request.json");
        String uniqueID = UUID.randomUUID().toString();
        compliRequest.getData().setId(uniqueID);
        HttpEntity<ComplianceTransactionRequest> entity = new HttpEntity<ComplianceTransactionRequest>(compliRequest,
                headers);
        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/compliance/submitTransaction"),
                HttpMethod.POST, entity, String.class);

        Gson gson = new GsonBuilder().serializeNulls().create();
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        String expectedJson = readJson.ReadResponseJson("Compliance/Response.json");
        ComplianceTransactionResponse complianceTransactionResponse = gson.fromJson(expectedJson,
                ComplianceTransactionResponse.class);
        complianceTransactionResponse.getData().setId(uniqueID);
        String expectedResponseStr = gson.toJson(complianceTransactionResponse);

        // Additional step to convert 0 to float (0.0)
        Map<String, Object> myMapResponse = gson.fromJson(expectedResponseStr, type);
        String expectedResponse = gson.toJson(myMapResponse);

        Map<String, Object> myMap = gson.fromJson(response.getBody(), type);
        String actualResponse = gson.toJson(myMap.get("response"));

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testProcessResponse() throws JsonParseException, JsonMappingException, IOException {
        String input = "{\"supplierResponse\":\"{\\\"data\\\":{\\\"attributes\\\":{\\\"datetime\\\":\\\"2019-07-21T00:00:00+00:00\\\"},\\\"id\\\":\\\"990898-098-98-98-908989wer8r7w09870\\\",\\\"relationships\\\":{\\\"transaction_results\\\":{\\\"data\\\":[],\\\"meta\\\":{\\\"count\\\":0}}},\\\"type\\\":\\\"RealtimeTransaction\\\"},\\\"included\\\":[]}\",\"httpStatusCode\":\"201\"}";
        APIResponse<?> actualApiResponse = complianceServiceHelper.processResponse(input);

        Gson gson = new GsonBuilder().serializeNulls().create();
        String complianceResponse = readJson.ReadResponseJson("ProcessResponse/Response.json");
        ComplianceTransactionResponse complianceTransactionResponse = gson.fromJson(complianceResponse,
                ComplianceTransactionResponse.class);
        ;
        APIResponse<?> expectedApiResponse = ConnectPayUtils.getSuccessResponse(complianceTransactionResponse);

        assertEquals(expectedApiResponse.toString(), actualApiResponse.toString());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
