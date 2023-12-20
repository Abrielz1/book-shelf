package ru.skillbox.bookshelf.configuration;

import com.google.common.cache.CacheBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import ru.skillbox.bookshelf.configuration.properties.AppCacheProperties;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
@EnableConfigurationProperties(AppCacheProperties.class)
public class CacheConfiguration {

    @Bean
    @ConditionalOnExpression("'${app.cache.cacheType}'.equals('inMemory')")
    public ConcurrentMapCacheManager inMemoryCacheManager(AppCacheProperties properties) {

        var cacheManager = new ConcurrentMapCacheManager(){

        @Override
        protected Cache createConcurrentMapCache(String name) {
            return new ConcurrentMapCache(
                    name,
                    CacheBuilder.newBuilder()
                            .expireAfterWrite(properties.getCachePropertiesMap().get(name).getExpiry())
                            .build()
                            .asMap(),
                    true
            );
            }
        };

        var cacheNames = properties.getCacheNames();

        if (!cacheNames.isEmpty()) {
            cacheManager.setCacheNames(cacheNames);
        }

        return cacheManager;
    }

    @Bean
    @ConditionalOnProperty(prefix = "app.redis", name = "enable", havingValue = "true")
    @ConditionalOnExpression("'${app.cache.cacheType}'.equals('redis')")//npe
    public CacheManager redisCacheManager(AppCacheProperties appCacheProperties,
                                          LettuceConnectionFactory lettuceConnectionFactory) {

        var defaultConfig = RedisCacheConfiguration.defaultCacheConfig();
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();

        appCacheProperties.getCacheNames().forEach(cacheNames -> {
            redisCacheConfigurationMap.put(cacheNames, RedisCacheConfiguration.defaultCacheConfig().entryTtl(
                    appCacheProperties.getCachePropertiesMap().get(cacheNames).getExpiry() //npe
            ));
        });

        return RedisCacheManager.builder(lettuceConnectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(redisCacheConfigurationMap)
                .build();
    }
}
