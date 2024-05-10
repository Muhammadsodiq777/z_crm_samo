package com.zulfiqor.z_crm_zulfiqor.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String fio;
    private String phoneNumber;
    private String password;
    private String confirmCode;
}
