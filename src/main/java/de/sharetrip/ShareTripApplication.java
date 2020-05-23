package de.sharetrip;

import de.sharetrip.core.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class ShareTripApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ShareTripApplication.class, args);
    }
}
