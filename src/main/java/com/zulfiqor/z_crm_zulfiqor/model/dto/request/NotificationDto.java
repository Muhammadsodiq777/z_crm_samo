package com.zulfiqor.z_crm_zulfiqor.model.dto.request;

import com.google.firebase.database.annotations.NotNull;
import com.zulfiqor.z_crm_zulfiqor.model.enums.MessageType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationDto {
    @NotNull
    private String titleUz;
    @NotNull
    private String titleRu;
    @NotNull
    private String textUz;
    @NotNull
    private String textRu;
    @NotNull
    private String descriptionUz;
    @NotNull
    private String descriptionRu;
    @NotNull
    private String imageUrl;
    @NotNull
    private MessageType messageType;
    private boolean firebase;
    private Long userId;
}
