package com.zulfiqor.z_crm_zulfiqor.services.product;

import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.ProductNamesRequest;
import com.zulfiqor.z_crm_zulfiqor.model.entity.User;

public interface ProductService {
    BaseResponse<?> addProductNames(User currentUser, ProductNamesRequest request);
    BaseResponse<?> getByProductGroupId(Long id);
    BaseResponse<?> searProductsByNameOrCode(String param);
}
