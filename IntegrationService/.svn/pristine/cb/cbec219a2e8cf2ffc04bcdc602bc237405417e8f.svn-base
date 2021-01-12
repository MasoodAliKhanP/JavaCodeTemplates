package biz.digitalhouse.integration.v3.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.server.endpoint.interceptor.PayloadLoggingInterceptor;

import java.util.List;

/**
 * Created by Vitalii B
 * on 25.04.2016.
 */
@Configuration
public class WsConfigurer extends WsConfigurerAdapter {

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        interceptors.add(new PayloadLoggingInterceptor());
    }
}
