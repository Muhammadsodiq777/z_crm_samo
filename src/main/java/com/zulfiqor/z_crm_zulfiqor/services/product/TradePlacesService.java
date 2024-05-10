package com.zulfiqor.z_crm_zulfiqor.services.product;

import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.TradePlaceRequest;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.TradePlaceStatus;

public interface TradePlacesService {

    BaseResponse<?> searchTradePlaces(TradePlaceStatus status, String name);

    BaseResponse<?> addTradePlace(TradePlaceRequest request);

    BaseResponse<?> getAllTradePlacesByStatus(TradePlaceStatus status);
}
