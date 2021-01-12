package biz.digitalhouse.integration.v3.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 * @author Vitalii Babenko
 *         on 26.02.2016.
 */

@EnableWs
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
@ComponentScan("biz.digitalhouse.integration.v3.web.ws.soap.freeRoundsBonusAPI")
public class ConfigFreeRoundsBonusAPIEndpoint {


    @Bean(name = "freeRoundsBonusAPI-definition")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema freeRoundsBonusSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("FreeRoundsBonusAPIPort");
        wsdl11Definition.setLocationUri("/SOAP/FreeRoundsBonusAPI");
        wsdl11Definition.setTargetNamespace("http://gametechclubs.biz/frb/external/schemas");
        wsdl11Definition.setSchema(freeRoundsBonusSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema freeRoundsBonusSchema() {
        return new SimpleXsdSchema(new ClassPathResource("xsd/freeRoundsBonusAPI.xsd"));
    }
}
