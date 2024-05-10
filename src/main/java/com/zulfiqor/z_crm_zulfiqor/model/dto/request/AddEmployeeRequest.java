package com.zulfiqor.z_crm_zulfiqor.model.dto.request;

public record AddEmployeeRequest(
    String phoneNumber,
    String password,
    String fio,
    String role) {
}
