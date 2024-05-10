package com.zulfiqor.z_crm_zulfiqor.model.dto.response.history;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.ConfirmStatus;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.TradePlaceStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionsResponse {
    private Long transactionId;
    private String fio;
    private String tradePlace;
    private ConfirmStatus status;
    private String createdDate; // kiritilgan yoki chiqarilgan vaqti
    private TradePlaceStatus tradeStatus;
}
