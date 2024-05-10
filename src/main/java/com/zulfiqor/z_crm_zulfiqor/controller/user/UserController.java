package com.zulfiqor.z_crm_zulfiqor.controller.user;

import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.AccountBlockedRequest;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.AddEmployeeRequest;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.ChangePasswordRequest;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.RoleRequest;
import com.zulfiqor.z_crm_zulfiqor.services.user.UserService;
import com.zulfiqor.z_crm_zulfiqor.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/employee")
public class UserController {

    private final UserService userService;
    private final SecurityUtils securityUtils;

    public UserController(UserService userService, SecurityUtils securityUtils) {
        this.userService = userService;
        this.securityUtils = securityUtils;
    }

    @GetMapping("role-list")
    @Operation(summary = "Foydalanuvchini id raqami bilan olish")
    public BaseResponse<?> getRoleList(){
        return userService.getRoleList();
    }

    @GetMapping("by/{id}")
    @Operation(summary = "Foydalanuvchini id raqami bilan olish")
    public BaseResponse<?> getUserById(@PathVariable(name = "id") Long id){
        return userService.getUserById(id);
    }

    @GetMapping("list")
    @Operation(summary = "Barcha userlarni olish")
    public BaseResponse<?> userList() {
        return BaseResponse.success(userService.getUserList());
    }

    @GetMapping("get-user")
    @Operation(summary = "User malumotlari va app servicelarni olish")
    public BaseResponse<?> getUser() {
        return BaseResponse.success(userService.getUser(securityUtils.getCurrentUser()));
    }

    @GetMapping("admin/get-user")
    @Operation(summary = "User malumotlari va app servicelarni olish")
    public BaseResponse<?> adminGetuser() {
        return BaseResponse.success(userService.getUserAdmin(securityUtils.getCurrentUser()));
    }

    @PostMapping("add-role")
    @Operation(summary = "Userga role berish")
    public ResponseEntity<BaseResponse<?>> addRoleToUUser(@RequestBody RoleRequest request) {

        BaseResponse<?> response = userService.giveRoleToUser(securityUtils.getCurrentUser(), request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("change-password")
    @Operation(summary = "User o'zining parolini o'zgartirishi")
    public BaseResponse<?> changePassword(@RequestBody ChangePasswordRequest request) {
        return userService.changePassword(securityUtils.getCurrentUser(), request);
    }

    @PostMapping("/blocked")
    @Operation(summary = "User bloklash")
    public BaseResponse<?> blockedUser(@RequestBody AccountBlockedRequest dto){
        return userService.blockedUser(securityUtils.getCurrentUser(), dto);
    }

    @PostMapping("unblocked")
    @Operation(summary = "Userni blokdan ochish")
    public ResponseEntity<?> unblockUser(@RequestBody AccountBlockedRequest request) {
        return ResponseEntity.ok(userService.unblockUser(securityUtils.getCurrentUser(), request));
    }

    @PostMapping("/add-user")
    @Operation(summary = "Yangi user yaratish qo'shish")
    public BaseResponse<?> addUser(@RequestBody AddEmployeeRequest dto){
        return userService.newEmployee(securityUtils.getCurrentUser(), dto);
    }

    @DeleteMapping("remove-role/{userId}/{roleId}")
    @Operation(summary = "Userdan roleni o'chirish")
    public ResponseEntity<BaseResponse<?>> removeRoleFromUser(@PathVariable(name = "userId") Long userId, @PathVariable(name = "roleId") Long roleId) {
        BaseResponse<?> response = userService.removeRoleFromUser(userId, roleId);
        return ResponseEntity.ok(response);
    }
}