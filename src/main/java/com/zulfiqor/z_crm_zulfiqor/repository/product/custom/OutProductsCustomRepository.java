package com.zulfiqor.z_crm_zulfiqor.repository.product.custom;

import com.zulfiqor.z_crm_zulfiqor.model.dto.HistoryFilter;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.OutProducts;

import java.util.List;

public interface OutProductsCustomRepository {
    List<OutProducts> getAllOutProducts(HistoryFilter historyFilter);

    int countOutProducts(HistoryFilter historyFilter);
}
