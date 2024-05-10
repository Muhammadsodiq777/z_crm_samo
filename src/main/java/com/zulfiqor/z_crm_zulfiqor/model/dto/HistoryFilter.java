package com.zulfiqor.z_crm_zulfiqor.model.dto;

import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.ConfirmStatus;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.TradePlaceStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoryFilter extends Filter {
    private Double price;
    private String date; //
    private ConfirmStatus status; // CONFIRMED, NOT_CONFIRMED, REJECTED, IN_PROGRESS
    @NotNull
    private TradePlaceStatus placeStatus; // BUY, SELL
}
