package biz.digitalhouse.integration.v3.dao.redis;

import biz.digitalhouse.integration.v3.utils.StringUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

/**
 * @author kirill.arbuzov
 * @created 22/03/2018
 */
@Component
public class CustomRedisCache {
    private StringRedisTemplate stringRedisTemplate;
    private Gson gson = new Gson();

    @Autowired
    public CustomRedisCache(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public <T> void set(String key, T val, int ttlInSeconds) {
        stringRedisTemplate.boundValueOps(key).set(gson.toJson(val), ttlInSeconds, TimeUnit.SECONDS);
    }

    public <T> T get(String key, Type typedef){
        String val = stringRedisTemplate.boundValueOps(key).get();
        if (StringUtils.isEmpty(val))
            return null;

        T target = gson.fromJson(val, typedef);
        return target;
    }
}
