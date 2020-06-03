package de.sharetrip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;

@EnableCaching
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class ShareTripApplication {

    @Autowired
    private RedisCacheManager redisCacheManager;

    public static void main(final String[] args) {
        SpringApplication.run(ShareTripApplication.class, args);

    }

    @PostConstruct
    public void init() {
        redisCacheManager.getCache("users").clear();
    }
}
