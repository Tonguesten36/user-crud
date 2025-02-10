package com.toshiba.intern.usercrud.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FirebaseConfig
{
    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {
        String credentialsPath = "src/main/resources/service_account.json";

        GoogleCredentials googleCredentials;
        try (FileInputStream serviceAccount = new FileInputStream(credentialsPath)) {
            googleCredentials = GoogleCredentials.fromStream(serviceAccount);
        }

        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(googleCredentials)
                .build();

        // Initialize the default FirebaseApp only if not already initialized
        FirebaseApp firebaseApp;
        if (FirebaseApp.getApps().isEmpty()) {
            firebaseApp = FirebaseApp.initializeApp(firebaseOptions);
            log.info("FirebaseApp initialized successfully with default name.");
        } else {
            firebaseApp = FirebaseApp.getInstance();
            log.info("FirebaseApp already initialized.");
        }

        return FirebaseMessaging.getInstance(firebaseApp);
    }
}
