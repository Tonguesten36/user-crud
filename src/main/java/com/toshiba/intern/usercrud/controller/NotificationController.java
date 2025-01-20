package com.toshiba.intern.usercrud.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.toshiba.intern.usercrud.payloads.dtos.NoticeDto;
import com.toshiba.intern.usercrud.payloads.dtos.PushDeviceDto;
import com.toshiba.intern.usercrud.service.notification.NotificationServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/notif")
public class NotificationController {

    private final NotificationServiceImpl notificationService;


    @PostMapping("/send-notif")
    @Operation(summary = "Send notification to a specific device")
    public void sendNotification(@RequestBody NoticeDto noticeDto) throws FirebaseMessagingException {
        notificationService.sendNotification(noticeDto);
    }

    @PostMapping("/send-notif/{userId}")
    @Operation(summary = "Send notification to all devices associated with the userId")
    // Update lại API send notification hiện tại để input là send to user_id (thay vì dùng registration token như hiện tại)
    public void sendNotificationByUserId(@PathVariable("userId") int userId, @RequestBody NoticeDto noticeDto) throws FirebaseMessagingException {
        // Get all tokens of user ID
        notificationService.sendNotificationByUserId(userId, noticeDto);
    }

    // Register fcm là 1 API để save token gửi từ postman và store vào db
    @PostMapping("/register-fcm")
    @Operation(summary = "Register a device's FCM token")
    public void registerFcm(@RequestBody PushDeviceDto pushDeviceDto){
        notificationService.registerFcmToken(pushDeviceDto);
    }

    @GetMapping("/access-token")
    public void getAccessToken() throws IOException {
        notificationService.getAccessToken();
    }
}
