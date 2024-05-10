package com.zulfiqor.z_crm_zulfiqor.repository.product.custom;

import com.zulfiqor.z_crm_zulfiqor.model.dto.HistoryFilter;
import com.zulfiqor.z_crm_zulfiqor.model.dto.filter.DateFilter;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.InProducts;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.custom.CardDetailsCustomEntity;

import java.util.List;

public interface InProductCustomRepository {

    List<InProducts> getAllInProducts(HistoryFilter historyFilter);

    int countInProducts(HistoryFilter historyFilter);

    List<CardDetailsCustomEntity> getPresentProductsListForCard(DateFilter filter);
}
