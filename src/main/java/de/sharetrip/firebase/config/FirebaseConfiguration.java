package de.sharetrip.firebase.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Configuration
public class FirebaseConfiguration {

    @Value("${authentication.firebase.databaseUrl}")
    private String firebaseDatabaseUrl;

    @Value("${authentication.firebase.configObject}")
    private String configObject;

    @PostConstruct
    private void init() throws IOException {

        log.info("Initializing firebase.....");

        final InputStream inputStream = new ClassPathResource(configObject).getInputStream();
        final GoogleCredentials googleCredentials = GoogleCredentials.fromStream(inputStream);

        final FirebaseOptions options = new FirebaseOptions
                .Builder()
                .setCredentials(googleCredentials)
                .setDatabaseUrl(firebaseDatabaseUrl)
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }
}
