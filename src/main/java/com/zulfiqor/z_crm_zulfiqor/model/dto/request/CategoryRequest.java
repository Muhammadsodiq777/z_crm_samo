package com.zulfiqor.z_crm_zulfiqor.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CategoryRequest(@NotNull
                              @NotEmpty
                              String name) {
}
