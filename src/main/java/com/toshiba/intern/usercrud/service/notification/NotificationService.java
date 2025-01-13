package com.toshiba.intern.usercrud.service.notification;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.toshiba.intern.usercrud.entity.Notice;
import com.toshiba.intern.usercrud.entity.PushDevice;
import com.toshiba.intern.usercrud.payloads.dtos.PushDeviceDto;

public interface NotificationService {
    void sendNotification(Notice notice) throws FirebaseMessagingException;

    PushDevice registerFcmToken(PushDeviceDto pushDeviceDto);
}
