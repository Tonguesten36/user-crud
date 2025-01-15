package com.toshiba.intern.usercrud.service.notification;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.toshiba.intern.usercrud.config.FirebaseProperties;
import com.toshiba.intern.usercrud.payloads.dtos.NoticeDto;

import com.toshiba.intern.usercrud.entity.PushDevice;
import com.toshiba.intern.usercrud.entity.User;
import com.toshiba.intern.usercrud.payloads.dtos.PushDeviceDto;
import com.toshiba.intern.usercrud.repository.PushDeviceRepository;
import com.toshiba.intern.usercrud.repository.UserRepository;
import io.sentry.Sentry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final FirebaseProperties firebaseProperties;
    private final PushDeviceRepository pushDeviceRepository;
    private final UserRepository userRepository;

    public void sendNotificationByUserId(int userId, NoticeDto noticeDto) throws FirebaseMessagingException {
        // Get all tokens associated with this userid tokens = [t1, t2]
        List<String> fcmTokens = pushDeviceRepository.findAllTokensByUserId(userId);

        // Log tokens
        if (fcmTokens.isEmpty()) {
            System.out.println("No tokens found for user ID: " + userId);
            return;
        }

        // Send notification in 'notice' to each device with the token
        for (String fcmToken : fcmTokens) {
            System.out.println("FCM Token: " + fcmToken);
            noticeDto.setFcmToken(fcmToken);

            try{
                sendNotification(noticeDto);
            }
            catch (Exception e) {
                System.out.println("Error sending notification. Skipping this token");
            }
        }
    }

    public void sendNotification(NoticeDto notice) throws FirebaseMessagingException {
        // This registration token comes from the client FCM SDKs.
        String registrationToken = notice.getFcmToken();

        // See documentation on defining a message payload.
        Notification notification = Notification.builder()
                .setTitle(notice.getTitle())
                .setBody(notice.getBody())
                .setImage(notice.getImage())
                .build();

        Message message = Message.builder()
                .setNotification(notification)
                .setToken(registrationToken)
                .build();


        // Send a message to the device corresponding to the provided
        // registration token.
        String response = FirebaseMessaging.getInstance().send(message);
        // Response is a message ID string.
        System.out.println("Successfully sent message: " + response);
    }

    // TODO: Register Device Token
    public void registerFcmToken(PushDeviceDto pushDeviceDto) {

        User user = userRepository.findById(pushDeviceDto.getUserId()).get();

        // TODO: Fix oldPushDevice return null
        List<PushDevice> oldPushDevices = pushDeviceRepository.findDevicesByUserId(user.getId());

        try{
            // Check if the token already exist

            if(pushDeviceRepository.isFcmTokenExist(pushDeviceDto.getToken())){
                System.out.println("FCM token already exist");
                return;
            }

            for (PushDevice pushDevice : oldPushDevices) {
                // Check if the user_id and uuid already exist
                if(pushDevice.getDeviceUuid().equals(pushDeviceDto.getDeviceUuid())
                && pushDevice.getUserRegisterDevice().getId() == pushDeviceDto.getUserId()){
                    System.out.println("Deleting old FCM token...");
                    // Simply delete the row containing the old fcm token
                    // (the new one will be created below)
                    pushDeviceRepository.delete(pushDevice);
                }
            }

            // Create new PushDevice object
            System.out.println("Registering new FCM token...");
            PushDevice newPushDevice = new PushDevice(
                    user,
                    pushDeviceDto.getToken(),
                    pushDeviceDto.getDeviceName(),
                    pushDeviceDto.getDeviceUuid(),
                    pushDeviceDto.getOs(),
                    pushDeviceDto.getOsVersion(),
                    pushDeviceDto.getSdkVersion(),
                    pushDeviceDto.getApp(),
                    pushDeviceDto.getAppVersion(),
                    pushDeviceDto.getIp(),
                    pushDeviceDto.getUserAgent()
            );

            // Save the created object
            pushDeviceRepository.save(newPushDevice);
        }
        catch (Exception e) {
            System.out.println("Sentry has captured an exception.");
            Sentry.captureException(e);
//            e.printStackTrace();
        }
    }

    public void getAccessToken() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new FileInputStream(firebaseProperties.getGoogleCredentials()))
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        System.out.println(googleCredentials.getAccessToken().getTokenValue());
        googleCredentials.getAccessToken().getTokenValue();
    }
}
