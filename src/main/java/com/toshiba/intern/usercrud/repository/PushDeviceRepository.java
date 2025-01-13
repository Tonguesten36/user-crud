package com.toshiba.intern.usercrud.repository;

import com.toshiba.intern.usercrud.entity.PushDevice;
import com.toshiba.intern.usercrud.payloads.dtos.PushDeviceDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PushDeviceRepository extends JpaRepository<PushDevice, Integer>
{
    @Query("SELECT pd.userRegisterDevice.username FROM PushDevice pd WHERE pd.token = :fcmToken")
    String findUsernameByToken(@Param("fcmToken") String fcmToken);

    @Query("SELECT pd.token FROM PushDevice pd WHERE pd.token = :fcmToken")
    boolean isFcmTokenExist(@Param("fcmToken") String fcmToken);

    @Query("SELECT pd.deviceName FROM PushDevice pd WHERE pd.token =: fcmToken")
    String findDeviceNameByToken(@Param("fcmToken") String fcmToken);
}
