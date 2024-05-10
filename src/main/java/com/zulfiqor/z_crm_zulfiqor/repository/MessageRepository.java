package com.zulfiqor.z_crm_zulfiqor.repository;

import com.zulfiqor.z_crm_zulfiqor.model.dto.MessageDto;
import com.zulfiqor.z_crm_zulfiqor.model.entity.MessageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageData, Long> {
    @Query(value = "select new com.zulfiqor.z_crm_zulfiqor.model.dto.MessageDto(mg.id,mg.createdDate,mg.titleUz,mg.titleRu, mg.descriptionUz,mg.descriptionRu, (case when mn.id is not null then true else false end) as seen) " +
        "from MessageData mg left join NotificationSeen mn on mg.id = mn.messageData.id and mn.userId = ?1  where mg.active = true and (mg.messageDataType = 'GENERAL' or (mg.messageDataType = 'PERSONAL' and mg.userId = ?1)) order by mg.id desc")
    List<MessageDto> findsAllByUserId(Long userId);

    @Query(value = "select mg from MessageData mg left join NotificationSeen mn on mg.id = mn.messageData.id and mn.userId = ?1  where mg.active = true and (mg.messageDataType = 'GENERAL' or (mg.messageDataType = 'PERSONAL' and mg.userId = ?1)) and mn.id is null")
    List<MessageData> findAllNotSeenList(Long userId);
}
