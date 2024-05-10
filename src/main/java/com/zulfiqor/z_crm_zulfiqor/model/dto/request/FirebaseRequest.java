package com.zulfiqor.z_crm_zulfiqor.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FirebaseRequest {
    @NotNull
    private String firebaseToken;
}
