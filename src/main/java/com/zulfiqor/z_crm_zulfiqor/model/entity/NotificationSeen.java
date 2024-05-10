package com.zulfiqor.z_crm_zulfiqor.model.entity;

import com.zulfiqor.z_crm_zulfiqor.model.base.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "notification_seen")
public class NotificationSeen extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "message_id", referencedColumnName = "id")
    private MessageData messageData;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    public NotificationSeen(MessageData messageData, Long userId) {
        this.messageData = messageData;
        this.userId = userId;
    }

    public NotificationSeen() {

    }

    public MessageData getMessageData() {
        return messageData;
    }

    public void setMessageData(MessageData messageData) {
        this.messageData = messageData;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
