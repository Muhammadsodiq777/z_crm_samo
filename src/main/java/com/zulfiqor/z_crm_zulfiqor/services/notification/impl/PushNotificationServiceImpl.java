package com.zulfiqor.z_crm_zulfiqor.services.notification.impl;

import com.zulfiqor.z_crm_zulfiqor.exception.NotFoundException;
import com.zulfiqor.z_crm_zulfiqor.model.base.BaseEntity;
import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.MessageDto;
import com.zulfiqor.z_crm_zulfiqor.model.entity.Device;
import com.zulfiqor.z_crm_zulfiqor.model.entity.MessageData;
import com.zulfiqor.z_crm_zulfiqor.model.entity.NotificationSeen;
import com.zulfiqor.z_crm_zulfiqor.model.entity.User;
import com.zulfiqor.z_crm_zulfiqor.model.enums.MessageType;
import com.zulfiqor.z_crm_zulfiqor.model.enums.Role;
import com.zulfiqor.z_crm_zulfiqor.repository.DeviceRepository;
import com.zulfiqor.z_crm_zulfiqor.repository.MessageRepository;
import com.zulfiqor.z_crm_zulfiqor.repository.NotificationSeenRepository;
import com.zulfiqor.z_crm_zulfiqor.repository.UserRepository;
import com.zulfiqor.z_crm_zulfiqor.services.notification.NotificationService;
import com.zulfiqor.z_crm_zulfiqor.services.notification.PushNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PushNotificationServiceImpl implements PushNotificationService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final NotificationSeenRepository notificationSeenRepository;
    private final DeviceRepository deviceRepository;
    private final NotificationService notificationService;

    private static String CONFIRM_TITLE = "Yangi mahsulot";
    private static String CONFRM_BODY = "Omborga yangi mahsulot kelib qo'shildi. Ushbu mahsulotlarni tasdiqlashingizni so'raymiz";

    public PushNotificationServiceImpl(MessageRepository messageRepository,
                                       NotificationSeenRepository notificationSeenRepository,
                                       UserRepository userRepository,
                                       DeviceRepository deviceRepository, NotificationService notificationService) {
        this.messageRepository = messageRepository;
        this.notificationSeenRepository = notificationSeenRepository;
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
        this.notificationService = notificationService;
    }

    @Override
    public BaseResponse<?> getAllMessage(User currentUser) {
        List<MessageDto> message = messageRepository.findsAllByUserId(currentUser.getId());
        return BaseResponse.success(message);
    }

    @Override
    public BaseResponse<?> getMessageById(User currentUser, Long messageId) {
        Optional<MessageData> messageData = messageRepository.findById(messageId);
        if (messageData.isEmpty())
            throw new NotFoundException("Notification topilmadi");
        return BaseResponse.success(new MessageDto(messageData.get()));
    }

    @Override
    public BaseResponse<?> readAllMessages(User currentUser) {
        List<MessageData> messageData = messageRepository.findAllNotSeenList(currentUser.getId());
        for (MessageData notification: messageData) {
            notificationSeenRepository.save(new NotificationSeen(notification, currentUser.getId()));
        }
        return BaseResponse.success();
    }

    @Override
    public void confirmProduct(Long transactionId) {
        List<String> roles = new ArrayList<>();
        roles.add(Role.BOSS.name());
        roles.add(Role.ACCOUNTANT.name());
        roles.add(Role.ACCOUNTANT_CHIEF.name());
        List<User> users = userRepository.findAllByRoleName(roles);
        List<Device> devices = deviceRepository.findAllByUserIdIn(users.stream().map(BaseEntity::getId).toList());
        List<String> tokens = devices.stream().map(Device::getFirebaseToken).distinct().toList();
        Map<String, String> map = new HashMap<>();
        map.put("transactionId", String.valueOf(transactionId));
        map.put("title", CONFIRM_TITLE);
        map.put("body", CONFRM_BODY);
        List<MessageData> messages = new ArrayList<>();
        for (User user: users) {
            MessageData messageData = getMessageData(user);
            messages.add(messageData);
        }
        messageRepository.saveAll(messages);

        tokens = tokens.stream().filter(Objects::nonNull).collect(Collectors.toList());
        logger.info("Notification saved ID: {}", messages.size());
        if (tokens.isEmpty())
            return;
        notificationService.confirmPushNotification(map, tokens);
    }

    private MessageData getMessageData(User user) {
        MessageData messageData = new MessageData();
        messageData.setMessageDataType(MessageType.PERSONAL);
        messageData.setUserId(user.getId());
        messageData.setTitleUz(CONFIRM_TITLE);
        messageData.setTitleRu(CONFIRM_TITLE);
        messageData.setDescriptionRu(CONFRM_BODY);
        messageData.setDescriptionUz(CONFRM_BODY);
        messageData.setTextRu(CONFRM_BODY);
        messageData.setTextUz(CONFRM_BODY);
        messageData.setAction(true);
        return messageData;
    }
}
