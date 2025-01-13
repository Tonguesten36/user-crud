package com.toshiba.intern.usercrud.service.notification;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.toshiba.intern.usercrud.config.FirebaseProperties;
import com.toshiba.intern.usercrud.entity.Notice;

import com.toshiba.intern.usercrud.entity.PushDevice;
import com.toshiba.intern.usercrud.entity.User;
import com.toshiba.intern.usercrud.payloads.dtos.PushDeviceDto;
import com.toshiba.intern.usercrud.repository.PushDeviceRepository;
import com.toshiba.intern.usercrud.repository.UserRepository;
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

    public void sendNotification(Notice notice) throws FirebaseMessagingException {
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
//                .setCondition("'testing' in topics")
//                .setTopic("testing")
                .build();


        // Send a message to the device corresponding to the provided
        // registration token.
        String response = FirebaseMessaging.getInstance().send(message);
        // Response is a message ID string.
        System.out.println("Successfully sent message: " + response);
    }

    public void sendNotificationByUserId(int userId, Notice notice) throws FirebaseMessagingException {
        //Get all tokens associated with this userid tokens = [t1, t2]
        // For each token in the list
//        fo:
//             ) {
//            notice.setToken(t
//        }
        //     sendNotification(notice)
    }

    // TODO: Register Device Token
    public PushDevice registerFcmToken(PushDeviceDto pushDeviceDto) {

        // Check là cùng user và cùng device thì token mới lên thì đăng ký vào
        // Token cũ thì expired



        User user = userRepository.findById(pushDeviceDto.getUserId()).get();

        // Create new PushDevice object
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
        return pushDeviceRepository.save(newPushDevice);
    }


    public String getAccessToken() throws IOException {
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new FileInputStream(firebaseProperties.getGoogleCredentials()))
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        System.out.println(googleCredentials.getAccessToken().getTokenValue());
        return googleCredentials.getAccessToken().getTokenValue();
    }

    public PushDeviceRepository getPushDeviceRepository() {
        return pushDeviceRepository;
    }
}
