package com.zulfiqor.z_crm_zulfiqor.controller.notification;

import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.ReqHeader;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.FirebaseRequest;
import com.zulfiqor.z_crm_zulfiqor.services.notification.PushNotificationService;
import com.zulfiqor.z_crm_zulfiqor.services.user.UserService;
import com.zulfiqor.z_crm_zulfiqor.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/notification")
public class PushNotificationController {

    private final UserService userService;
    private final SecurityUtils securityUtils;
    private final PushNotificationService pushNotificationService;

    public PushNotificationController(UserService userService, SecurityUtils securityUtils, PushNotificationService pushNotificationService) {
        this.userService = userService;
        this.securityUtils = securityUtils;
        this.pushNotificationService = pushNotificationService;
    }

    @PostMapping("firebase")
    @Operation(summary = "Firebase token subscribe")
    public BaseResponse<?> firebase(@RequestBody FirebaseRequest request,
                                    @RequestHeader(name = "deviceId", defaultValue = "") String deviceId,
                                    @RequestHeader(name = "lang", required = false, defaultValue = "ru") String lang) {
        ReqHeader reqHeader = new ReqHeader(deviceId, lang);
        return BaseResponse.success(userService.firebaseNotification(securityUtils.getCurrentUser(), request, reqHeader));
    }

    @PostMapping("unsubscribe")
    @Operation(summary = "Firebase token unsubscribe")
    public ResponseEntity<?> unsubscribeToken(@RequestBody @Valid FirebaseRequest request,
                                              @RequestHeader(name = "deviceId", defaultValue = "") String deviceId,
                                              @RequestHeader(name = "lang", defaultValue = "ru")String lang) {
        ReqHeader reqHeader = new ReqHeader(deviceId, lang);
        return ResponseEntity.ok(userService.unsubscribe(securityUtils.getCurrentUser(), request, reqHeader));
    }

    @GetMapping("all")
    @Operation(summary = "Hamma notification messagelarni olish userga oidlarini")
    public BaseResponse<?> getNotifications() {
        return pushNotificationService.getAllMessage(securityUtils.getCurrentUser());
    }

    @GetMapping("read/{id}")
    @Operation(summary = "Notificationni o'qilgan qilish")
    public BaseResponse<?> messageById(@PathVariable("id") Long messageId) {
        return pushNotificationService.getMessageById(securityUtils.getCurrentUser(), messageId);
    }

    @GetMapping("readAll")
    @Operation(summary = "Hamma Notificationni o'qilgan qilish")
    public ResponseEntity<?> readAll() {
        return ResponseEntity.ok(pushNotificationService.readAllMessages(securityUtils.getCurrentUser()));
    }
}
