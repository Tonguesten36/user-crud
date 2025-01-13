package com.toshiba.intern.usercrud.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "push_devices")
public class PushDevice
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="token", unique = true, nullable = false)
    private String token;

    @Column(name="is_expired", nullable = false)
    private boolean isExpired = false;

    @Column(name="device_name", nullable = false, length = 100)
    private String deviceName;

    @Column(name="device_uuid", nullable = false, length = 50)
    private String deviceUuid;

    @Column(name="os", nullable = false, length = 50)
    private String os;

    @Column(name="os_version", nullable = false, length = 50)
    private String osVersion;

    @Column(name="sdk_version", nullable = false)
    private int sdkVersion;

    @Column(name="app", nullable = false, length = 50)
    private String app;

    @Column(name="app_version", nullable = false, length = 50)
    private String appVersion;

    @Column(name="ip", length = 50)
    private String ip;

    @Column(name="user_agent", length = 50)
    private String userAgent;

    @Column(name="token_timestamp")
    private Date tokenTimestamp;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User userRegisterDevice;

    public PushDevice() {

    }

    public PushDevice(String token, String deviceName, String deviceUuid, String os, String osVersion, int sdkVersion, String app, String appVersion) {
        this.token = token;
        this.deviceName = deviceName;
        this.deviceUuid = deviceUuid;
        this.os = os;
        this.osVersion = osVersion;
        this.sdkVersion = sdkVersion;
        this.app = app;
        this.appVersion = appVersion;
        this.tokenTimestamp = new Date();
    }

    public PushDevice(User user, String token, String deviceName, String deviceUuid, String os, String osVersion, int sdkVersion, String app, String appVersion, String ip, String userAgent) {
        this.userRegisterDevice = user;
        this.token = token;
        this.deviceName = deviceName;
        this.deviceUuid = deviceUuid;
        this.os = os;
        this.osVersion = osVersion;
        this.sdkVersion = sdkVersion;
        this.app = app;
        this.appVersion = appVersion;
        this.tokenTimestamp = new Date();
        this.ip = ip;
        this.userAgent = userAgent;
    }

    @Override
    public String toString(){
        return "Device's registered user: " + userRegisterDevice.getUsername()
                + "\n Token: " + token
                + "\n isExpired: " + isExpired
                + "\n Device Name: " + deviceName
                + "\n Device UUID: " + deviceUuid
                + "\n OS: " + os
                + "\n OS Version: " + osVersion
                + "\n App: " + app
                + "\n App Version: " + appVersion
                + "\n IP: " + ip
                + "\n User Agent: " + userAgent;
    }
}
