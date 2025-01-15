package com.toshiba.intern.usercrud.service.notification;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.toshiba.intern.usercrud.payloads.dtos.NoticeDto;
import com.toshiba.intern.usercrud.payloads.dtos.PushDeviceDto;

public interface NotificationService {

    void sendNotificationByUserId(int userId, NoticeDto noticeDto) throws FirebaseMessagingException;
    void sendNotification(NoticeDto noticeDto) throws FirebaseMessagingException;

    void registerFcmToken(PushDeviceDto pushDeviceDto);
}
