package com.zulfiqor.z_crm_zulfiqor.model.entity;

import com.zulfiqor.z_crm_zulfiqor.model.base.BaseEntity;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.NotificationDto;
import com.zulfiqor.z_crm_zulfiqor.model.enums.MessageType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "message")
@Getter
@Setter
public class MessageData extends BaseEntity {

    private String titleUz;
    private String titleRu;
    private String descriptionUz;
    private String descriptionRu;
    private String textUz;
    private String textRu;
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    @Column(name = "message_data_type")
    private MessageType messageDataType;
    private boolean action;
    private Long userId;

    public MessageData() {
    }

    public MessageData(NotificationDto notification) {
        this.titleRu = notification.getTitleRu();
        this.titleUz = notification.getTitleUz();
        this.descriptionRu = notification.getDescriptionRu();
        this.descriptionUz = notification.getDescriptionUz();
        this.textRu = notification.getTextRu();
        this.textUz = notification.getTextUz();
        this.imageUrl = notification.getImageUrl();
    }

    @Override
    public String toString() {
        return "Message = {" +
            "titleUz='" + titleUz + '\'' +
            ", titleRu='" + titleRu + '\'' +
            ", descriptionUz='" + descriptionUz + '\'' +
            ", descriptionRu='" + descriptionRu + '\'' +
            ", textUz='" + textUz + '\'' +
            ", textRu='" + textRu + '\'' +
            '}';
    }
}
