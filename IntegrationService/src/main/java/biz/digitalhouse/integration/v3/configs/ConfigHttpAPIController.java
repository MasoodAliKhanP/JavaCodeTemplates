package biz.digitalhouse.integration.v3.configs;

import com.google.gson.Gson;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author Vitalii Babenko
 *         on 28.02.2016.
 */
@EnableWebMvc
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
@ComponentScan("biz.digitalhouse.integration.v3.web.ws.http")
public class ConfigHttpAPIController {

    @Bean
    @ConditionalOnMissingBean
    public GsonHttpMessageConverter gsonHttpMessageConverter(Gson gson) {
        GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
        converter.setGson(gson);
        return converter;
    }
}
