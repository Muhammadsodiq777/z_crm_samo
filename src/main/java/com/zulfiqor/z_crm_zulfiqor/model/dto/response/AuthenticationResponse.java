package com.zulfiqor.z_crm_zulfiqor.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationResponse {

    private Long id;
    private String fio;
    private String accessToken;
    private String refreshToken;

    public AuthenticationResponse(Long id, String fio, String accessToken, String refreshToken) {
        this.id = id;
        this.fio = fio;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
