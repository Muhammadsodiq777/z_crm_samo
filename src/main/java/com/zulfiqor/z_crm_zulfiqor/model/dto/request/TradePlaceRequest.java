package com.zulfiqor.z_crm_zulfiqor.model.dto.request;

import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.TradePlaceStatus;

public record TradePlaceRequest(
    String name,
    String description,
    TradePlaceStatus status
) {
}
