package de.sharetrip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableRetry
@EnableCaching
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class ShareTripApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ShareTripApplication.class, args);

    }
}
