package com.zulfiqor.z_crm_zulfiqor.services.product;

import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.InProductsRequest;

import java.util.List;

public interface InProductsService {

    BaseResponse<?> addProduct(InProductsRequest requests, String deviceId);

    BaseResponse<?> updateProduct(String id, InProductsRequest productListRequest);

    BaseResponse<?> getAllPresentProductsInWarehouse(Long id);

    BaseResponse<?> getInProductById(Long id);

}
