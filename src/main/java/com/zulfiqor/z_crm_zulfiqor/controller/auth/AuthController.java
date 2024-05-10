package com.zulfiqor.z_crm_zulfiqor.controller.auth;

import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.LoginRequest;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.RefreshTokenRequest;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.RegisterRequest;
import com.zulfiqor.z_crm_zulfiqor.services.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("register")
    @Operation(summary = "Yangi user yaratish")
    public BaseResponse<?> register(@RequestBody RegisterRequest request) {
        return BaseResponse.success(authService.registerUser(request));
    }

    @PostMapping("log-in")
    @Operation(summary = "Userning login", description = "Qurilmadan foydalanish uchun token olish")
    public BaseResponse<?> login(@RequestBody LoginRequest request,
                                 @RequestHeader(name = "deviceId" ,required = false, defaultValue = "") String deviceId) {
        return authService.loginUser(request, deviceId);
    }

    @PostMapping("refresh-token")
    @Operation(summary = "Tokenni qayta olish", description = "Access Token eskirgan bo'lsa refresh token orqali access tokenn olinadi")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest, @RequestHeader(name = "deviceId", required = false, defaultValue = "") String deviceId) {
        BaseResponse<?> response = authService.refreshToken(refreshTokenRequest, deviceId);
        return ResponseEntity.ok(response);
    }
}
