package com.zulfiqor.z_crm_zulfiqor.services.product;

import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.filter.DateFilter;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.TradePlaceStatus;

public interface StatisticsService {

    BaseResponse<?> getCardsList();

    BaseResponse<?> getCardDetailsList(DateFilter filter);
    BaseResponse<?> getPieChartDetails(TradePlaceStatus status, Integer year);
}
