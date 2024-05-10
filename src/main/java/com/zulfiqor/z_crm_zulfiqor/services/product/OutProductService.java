package com.zulfiqor.z_crm_zulfiqor.services.product;

import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.SellProduct;
import com.zulfiqor.z_crm_zulfiqor.model.entity.User;

import java.util.List;

public interface OutProductService {

    BaseResponse<?> sellProducts(User currentUser, SellProduct sellProduct, String deviceId);

}
