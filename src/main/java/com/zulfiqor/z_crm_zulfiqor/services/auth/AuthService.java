package com.zulfiqor.z_crm_zulfiqor.services.auth;

import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.LoginRequest;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.RefreshTokenRequest;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.RegisterRequest;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.SaveResponse;

public interface AuthService {
    SaveResponse registerUser(RegisterRequest request);

    BaseResponse<?> loginUser(LoginRequest request, String deviceId);

    BaseResponse<?> refreshToken(RefreshTokenRequest refreshTokenRequest, String deviceId);
}
