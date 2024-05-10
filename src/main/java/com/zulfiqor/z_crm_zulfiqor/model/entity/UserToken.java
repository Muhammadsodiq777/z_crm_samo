package com.zulfiqor.z_crm_zulfiqor.model.entity;

import com.zulfiqor.z_crm_zulfiqor.model.base.BaseEntity;
import com.zulfiqor.z_crm_zulfiqor.model.enums.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "user_tokens")
@Getter
@Setter
public class UserToken extends BaseEntity {
    @Column(columnDefinition = "TEXT")
    private String accessToken;

    private String refreshToken;

    private Date accessExpireDate;

    private Date refreshExpireDate;
    private String deviceId;

    @ManyToOne
    @JoinColumn(name = "user_device_id", referencedColumnName = "id")
    private Device device;

    private String firebaseToken;
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.ACTIVE;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public UserToken() {
    }

    public UserToken(String accessToken, String refreshToken, Date accessExpireDate, Date refreshExpireDate, String deviceId, String firebaseToken, User user) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessExpireDate = accessExpireDate;
        this.refreshExpireDate = refreshExpireDate;
        this.deviceId = deviceId;
        this.firebaseToken = firebaseToken;
        this.user = user;
    }
    public UserToken(String accessToken, String refreshToken, Date accessExpireDate, Date refreshExpireDate, String deviceId, User user) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessExpireDate = accessExpireDate;
        this.refreshExpireDate = refreshExpireDate;
        this.deviceId = deviceId;
        this.user = user;
    }
}
