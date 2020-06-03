package de.sharetrip.core.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
@Component("defaultCacheManager")
public class CacheManager<T> {

    private final ObjectMapper objectMapper;

    private final RedisCacheManager redisCacheManager;

    @PostConstruct
    private void init() {
        redisCacheManager.setTransactionAware(true);
    }

    public T getValueFromCache(final String cacheName,
                               final String key,
                               final Class<T> destinationClass) throws JsonProcessingException {
        final Cache.ValueWrapper valueWrapper = getValueByCacheNameAndKey(cacheName, key);

        Objects.requireNonNull(valueWrapper);

        final String valueAsString = objectMapper.writeValueAsString(valueWrapper.get());
        return objectMapper.readValue(valueAsString, destinationClass);
    }

    private Cache.ValueWrapper getValueByCacheNameAndKey(final String cacheName,
                                                         final String key) {
        Objects.requireNonNull(cacheName);
        Objects.requireNonNull(key);

        log.info(String.format("Fetching value from cache for cache name [%s] and key [%s]",
                cacheName,
                key));

        return redisCacheManager
                .getCache(cacheName)
                .get(key);
    }
}
