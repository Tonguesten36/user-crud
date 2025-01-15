package com.toshiba.intern.usercrud.repository;

import com.toshiba.intern.usercrud.entity.PushDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PushDeviceRepository extends JpaRepository<PushDevice, Integer>
{
    @Query("SELECT CASE WHEN COUNT(pd) > 0 THEN TRUE ELSE FALSE END FROM PushDevice pd WHERE pd.token = :fcmToken")
    boolean isFcmTokenExist(@Param("fcmToken") String fcmToken);

    @Query("SELECT pd FROM PushDevice pd WHERE pd.userRegisterDevice.id = :userId")
    List<PushDevice> findDevicesByUserId(@Param("userId") int userId);

    @Query("SELECT pd.token FROM PushDevice pd WHERE pd.userRegisterDevice.id = :userId")
    List<String> findAllTokensByUserId(@Param("userId") Integer userId);
}
