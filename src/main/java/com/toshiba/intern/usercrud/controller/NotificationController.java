package com.toshiba.intern.usercrud.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.toshiba.intern.usercrud.payloads.dtos.NoticeDto;
import com.toshiba.intern.usercrud.payloads.dtos.PushDeviceDto;
import com.toshiba.intern.usercrud.service.notification.NotificationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/notif")
public class NotificationController {

    private final NotificationServiceImpl notificationService;


    @PostMapping("/send-notif")
    public void sendNotification(@RequestBody NoticeDto noticeDto) throws FirebaseMessagingException {
        notificationService.sendNotification(noticeDto);
    }

    @PostMapping("/send-notif/{userId}")
    // Update lại API send notification hiện tại để input là send to user_id (thay vì dùng registration token như hiện tại)
    public void sendNotificationByUserId(@PathVariable("userId") int userId, @RequestBody NoticeDto noticeDto) throws FirebaseMessagingException {
        // Get all tokens of user ID
        notificationService.sendNotificationByUserId(userId, noticeDto);
    }

    // Register fcm là 1 API để save token gửi từ postman và store vào db
    @PostMapping("/register-fcm")
    public void registerFcm(@RequestBody PushDeviceDto pushDeviceDto) throws FirebaseMessagingException, IOException {
        notificationService.registerFcmToken(pushDeviceDto);
    }

    @GetMapping("/access-token")
    public void getAccessToken() throws IOException {
        notificationService.getAccessToken();
    }
}
