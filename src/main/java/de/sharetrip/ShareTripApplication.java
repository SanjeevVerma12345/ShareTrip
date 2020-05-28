package de.sharetrip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ShareTripApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ShareTripApplication.class, args);

    }
}
