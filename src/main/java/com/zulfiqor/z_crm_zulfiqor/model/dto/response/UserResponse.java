package com.zulfiqor.z_crm_zulfiqor.model.dto.response;

import com.zulfiqor.z_crm_zulfiqor.model.entity.Roles;
import com.zulfiqor.z_crm_zulfiqor.model.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private String fio;
    private String phoneNumber;
    private String imageUrl;
    private boolean status;
    private List<Roles> roles;
}
