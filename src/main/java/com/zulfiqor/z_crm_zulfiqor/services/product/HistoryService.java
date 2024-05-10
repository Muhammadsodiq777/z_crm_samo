package com.zulfiqor.z_crm_zulfiqor.services.product;

import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.filter.HistoryFilter;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.TradePlaceStatus;

public interface HistoryService {
    BaseResponse<?> getHistory(TradePlaceStatus status, HistoryFilter filter);

    BaseResponse<?> getHistoryDetails(TradePlaceStatus status, Long transactionId);
}
