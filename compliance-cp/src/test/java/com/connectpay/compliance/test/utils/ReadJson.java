
package com.connectpay.compliance.test.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

import com.connectpay.payments.compliance.request.ComplianceTransactionRequest;

@Component
public class ReadJson {

    ObjectMapper mapper = new ObjectMapper();

    public ComplianceTransactionRequest readComplianceRequestJson(String path)
            throws JsonParseException, JsonMappingException, IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        ComplianceTransactionRequest compliRequest = mapper.readValue(new File(classLoader.getResource(path).getFile()),
                ComplianceTransactionRequest.class);
        return compliRequest;

    }

    public String ReadResponseJson(String path) throws JsonParseException, JsonMappingException, IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(path);
        File file = new File(resource.getPath());
        try {
            return new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
