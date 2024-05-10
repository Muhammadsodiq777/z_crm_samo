package com.zulfiqor.z_crm_zulfiqor.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordRequest {
    private String currentPassword;

    private String newPassword;
    private String confirmPassword;
}
