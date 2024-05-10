package com.zulfiqor.z_crm_zulfiqor.model.dto;

import com.zulfiqor.z_crm_zulfiqor.model.entity.MessageData;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MessageDto {
    private Long id;
    private Date createdDate;
    private String titleUz;
    private String titleRu;
    private String descriptionUz;
    private String descriptionRu;
    private String textUz;
    private String textRu;
    private String imageUrl;
    private boolean seen;
    public MessageDto(Long id, Date createdDate, String titleUz, String titleRu, String descriptionUz, String descriptionRu, boolean seen) {
        this.id = id;
        this.createdDate = createdDate;
        this.titleUz = titleUz;
        this.titleRu = titleRu;
        this.descriptionUz = descriptionUz;
        this.descriptionRu = descriptionRu;
        this.seen = seen;
    }

    public MessageDto(MessageData messageData) {
        this.id = messageData.getId();
        this.createdDate = messageData.getCreatedDate();
        this.titleUz = messageData.getTitleUz();
        this.titleRu = messageData.getTitleRu();
        this.descriptionUz = messageData.getDescriptionUz();
        this.descriptionRu = messageData.getDescriptionRu();
        this.textUz = messageData.getTextUz();
        this.textRu = messageData.getTextRu();
        this.imageUrl = messageData.getImageUrl();
    }
}
