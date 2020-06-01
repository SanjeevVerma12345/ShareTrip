package de.sharetrip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableCaching
@SpringBootApplication
@EnableTransactionManagement
public class ShareTripApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ShareTripApplication.class, args);

    }
}
