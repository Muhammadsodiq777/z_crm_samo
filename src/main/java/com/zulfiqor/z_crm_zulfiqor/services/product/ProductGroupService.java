package com.zulfiqor.z_crm_zulfiqor.services.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.ProductGroupRequest;

public interface ProductGroupService {

    BaseResponse<?> addParentProductType(Long categoryId, ProductGroupRequest request);

    BaseResponse<?> updateParentProductType(Long typeId, ProductGroupRequest request);

    BaseResponse<?> searchParentProductType(String name);

    BaseResponse<?> deleteParentProductType(Long typeId);

    BaseResponse<?> addChildProductGroup(Long categoryId, ProductGroupRequest childRequest);

    BaseResponse<?> updateChildProductGroup(Long groupId, ProductGroupRequest childRequest);

    BaseResponse<?> searchFromProductGroup(String name);

    BaseResponse<?> deleteChildProductGroup(Long groupId);

    BaseResponse<?> getProductGroup(Long parentId);
}
