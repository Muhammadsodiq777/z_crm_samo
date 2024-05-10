package com.zulfiqor.z_crm_zulfiqor.repository;

import com.zulfiqor.z_crm_zulfiqor.model.entity.User;
import com.zulfiqor.z_crm_zulfiqor.model.entity.UserToken;
import com.zulfiqor.z_crm_zulfiqor.model.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    UserToken findByRefreshToken(String refreshToken);

    UserToken findByAccessToken(String token);

    List<UserToken> findByUserAndDeviceId(User user, String deviceId);
    UserToken findByUserAndDeviceIdAndStatus(User user, String deviceId, UserStatus status);
    List<UserToken> findByUserAndStatus(User user, UserStatus status);
}
