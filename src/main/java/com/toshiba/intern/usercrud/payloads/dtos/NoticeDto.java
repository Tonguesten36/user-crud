package com.toshiba.intern.usercrud.payloads.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDto implements Serializable
{
    /**
     * Subject notification on firebase
     */
    private String title;
    /**
     * Content notification on firebase
     */
    private String body;
    /**
     * url ảnh đại diện đơn hàng
     */
    private String image;
    /**
     * Map các data
     */
    private Map<String, String> data;
    /**
     * FCM token
     */
    private String fcmToken;

    public boolean hasData() {
        return data != null && !data.isEmpty();
    }

    public boolean hasImage() {
        return image != null && !image.isEmpty();
    }


}
