package com.zulfiqor.z_crm_zulfiqor.repository.product.custom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.ProductResponse;

import java.util.List;

public interface ProductSearchCustomRepository {

    List<ProductResponse> searchProductGroups(Integer isParent, String search) throws JsonProcessingException;

}
