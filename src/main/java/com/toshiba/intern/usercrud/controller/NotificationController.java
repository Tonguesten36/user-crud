package com.toshiba.intern.usercrud.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.toshiba.intern.usercrud.entity.Notice;
import com.toshiba.intern.usercrud.payloads.dtos.PushDeviceDto;
import com.toshiba.intern.usercrud.service.notification.NotificationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/notif")
public class NotificationController {

    private final NotificationServiceImpl notificationService;


    @PostMapping("/send-notif/{userId}")
    public void sendNotificationByUserId(@PathVariable int userId){
        //Get all tokens of user ID
//        notificationService
    }

    // TODO: Update lại API send notification hiện tại để input là send to user_id (thay vì dùng registration token như hiện tại)
    @PostMapping("/send-notif")
    public void sendNotification(@RequestBody Notice notice) throws FirebaseMessagingException {
        notificationService.sendNotification(notice);
    }

    // TODO: Register fcm là 1 API để save token gửi từ postman và store vào db
    @PostMapping("/register-fcm")
    public void registerFcm(@RequestBody PushDeviceDto pushDeviceDto) throws FirebaseMessagingException, IOException {
        System.out.println(pushDeviceDto);
        System.out.println("FCM token registering");
        notificationService.registerFcmToken(pushDeviceDto);
        System.out.println("FCM token registered");
    }

    @GetMapping("/access-token")
    public void getAccessToken(@RequestBody PushDeviceDto pushDeviceDto) throws IOException {
        notificationService.getAccessToken();
    }
}
