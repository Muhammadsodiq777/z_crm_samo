package com.zulfiqor.z_crm_zulfiqor.services.notification;

import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.entity.User;

public interface PushNotificationService {

    BaseResponse<?> getAllMessage(User currentUser);

    BaseResponse<?> getMessageById(User currentUser, Long messageId);

    BaseResponse<?> readAllMessages(User currentUser);

    void confirmProduct(Long transactionId);
}
