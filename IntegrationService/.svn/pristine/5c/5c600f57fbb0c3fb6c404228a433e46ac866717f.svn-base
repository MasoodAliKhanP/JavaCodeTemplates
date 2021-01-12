package biz.digitalhouse.integration.v3.configs;

import net.sf.ehcache.config.FactoryConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;

/**
 * @author Vitalii Babenko
 *         on 28.02.2016.
 */
@Configuration
public class CacheConfig {

    private static final Log log = LogFactory.getLog(CacheConfig.class);

//    @Value("${ehcache.multicast.group.port}")
//    private int port;

    @Bean(name="cacheManager")
    @Primary
    public CacheManager getcacheManager(){

//        net.sf.ehcache.config.Configuration c = cacheManagerUtils.parseConfiguration(new ClassPathResource("ehcache.xml"));
//
//        FactoryConfiguration factoryConfiguration = c.getCacheManagerPeerProviderFactoryConfiguration().get(0);
//        factoryConfiguration.setProperties(factoryConfiguration.getProperties().replace("${ehcache.multicast.group.port}", String.valueOf(port)));
//        if(log.isInfoEnabled()) {
//            log.info("Cache manager peer provider factory properties:" + c.getCacheManagerPeerProviderFactoryConfiguration().get(0).getProperties());
//        }
        net.sf.ehcache.CacheManager cm = new net.sf.ehcache.CacheManager();
        return new EhCacheCacheManager(cm);
    }
}
