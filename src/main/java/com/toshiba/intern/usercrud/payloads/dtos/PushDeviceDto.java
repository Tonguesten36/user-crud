package com.toshiba.intern.usercrud.payloads.dtos;

import lombok.Getter;

@Getter
public class PushDeviceDto {

    private int userId;
    private String token;
    private String deviceName;
    private String deviceUuid;

    private String os;
    private String osVersion;
    private int sdkVersion;

    private String app;
    private String appVersion;

    private String ip;
    private String userAgent;
}
