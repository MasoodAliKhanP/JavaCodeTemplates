package biz.digitalhouse.integration.v3.configs;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.constructs.CacheDecoratorFactory;
import net.sf.ehcache.constructs.blocking.BlockingCache;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Properties;

/**
 * Created by vitaliy.babenko
 * on 28.08.2017.
 */
public class BlockingCacheDecoratorFactory extends CacheDecoratorFactory {

    private static final Log log = LogFactory.getLog(BlockingCacheDecoratorFactory.class);
    private static final int TIMEOUT_MILLIS = 15000;

    @Override
    public Ehcache createDecoratedEhcache(final Ehcache cache, final Properties properties) {
        if(log.isDebugEnabled()) {
            log.debug("Create Blocking Cache");
        }
        final BlockingCache blockingCache = new BlockingCache(cache);
        blockingCache.setTimeoutMillis(TIMEOUT_MILLIS);
        return blockingCache;
    }

    @Override
    public Ehcache createDefaultDecoratedEhcache(final Ehcache cache, final Properties properties) {
        return this.createDecoratedEhcache(cache, properties);
    }
}
