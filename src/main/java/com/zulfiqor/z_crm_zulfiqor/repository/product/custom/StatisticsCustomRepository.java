package com.zulfiqor.z_crm_zulfiqor.repository.product.custom;

import com.zulfiqor.z_crm_zulfiqor.model.dto.filter.DateFilter;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.custom.CardDetailsCustomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StatisticsCustomRepository {

    Page<CardDetailsCustomEntity> getPresentProductsListForCard(DateFilter filter);

}
