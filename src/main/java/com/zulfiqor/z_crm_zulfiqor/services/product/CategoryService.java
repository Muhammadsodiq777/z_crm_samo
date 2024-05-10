package com.zulfiqor.z_crm_zulfiqor.services.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.CategoryRequest;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.CategoryResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.ParentProductResponse;

import java.util.List;

public interface CategoryService {
    BaseResponse<?> addCategory(CategoryRequest categoryRequest);

    BaseResponse<?> updateCategory(Long id, CategoryRequest categoryRequest);

    BaseResponse<List<CategoryResponse>> searchByName(String name);

    BaseResponse<List<CategoryResponse>> getAllCategoriesAndChildType();

    BaseResponse<?> deleteCategory(Long id);
}
