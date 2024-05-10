package com.zulfiqor.z_crm_zulfiqor.services.notification;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import com.zulfiqor.z_crm_zulfiqor.model.dto.ReqHeader;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.FirebaseRequest;
import com.zulfiqor.z_crm_zulfiqor.model.entity.User;
import com.zulfiqor.z_crm_zulfiqor.repository.UserTokenRepository;
import com.zulfiqor.z_crm_zulfiqor.utils.FirebaseTopic;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class NotificationService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public NotificationService(UserTokenRepository userTokenRepository) {
    }

    @PostConstruct
    public void initFirebaseConfig() {
        try {
            ClassPathResource classPathResource = new ClassPathResource("firebase.json");
            FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(classPathResource.getInputStream())).build();
            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            logger.error("Firebase initializing error: {}", e.getMessage());
        }
    }

    public void confirmPushNotification(Map<String, String> map, List<String> tokens) {
        String title = map.get("title");
        String body = map.get("body");
        send(title, body, map, tokens);
    }

    public void send(String title, String body, Map<String, String> map, List<String> tokens) {
        Notification notification = Notification.builder()
                .setBody(body)
                .setTitle(title)
                .build();

        MulticastMessage message = MulticastMessage.builder()
                .setNotification(notification)
                .putAllData(map)
                .addAllTokens(tokens)
                .build();

        try {
            BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
            logger.info("Notification sent: Success Count: {}, Failed Count: {}", response.getSuccessCount(), response.getFailureCount());
        } catch (FirebaseMessagingException e) {
            logger.info("Notification not send. {}", e.getMessage());
        }
    }


    public void subscribeToken(User currentUser, String firebaseToken, String lang) {
        try {
            TopicManagementResponse topicManagementResponse = FirebaseMessaging.getInstance().subscribeToTopic(List.of(firebaseToken), FirebaseTopic.getInstance(""));
            logger.info("User subscribe success: {}", topicManagementResponse.getSuccessCount());
        } catch (FirebaseMessagingException e) {
            logger.error("User subscribe error: {}", e.getMessage());
        }
    }

    public void unsubscribe(FirebaseRequest request, ReqHeader header) {
        try {
            TopicManagementResponse topicManagementResponse = FirebaseMessaging.getInstance().unsubscribeFromTopic(List.of(request.getFirebaseToken()), FirebaseTopic.getInstance(header.getLang()));
            logger.info("User subscribe success: {}", topicManagementResponse.getSuccessCount());
        } catch (FirebaseMessagingException e) {
            logger.error("User subscribe error: {}", e.getMessage());
        }
    }
}
