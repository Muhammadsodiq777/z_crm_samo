package com.zulfiqor.z_crm_zulfiqor.repository;

import com.zulfiqor.z_crm_zulfiqor.model.entity.NotificationSeen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationSeenRepository extends JpaRepository<NotificationSeen, Long> {
}
