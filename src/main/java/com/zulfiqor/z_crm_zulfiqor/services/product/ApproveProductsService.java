package com.zulfiqor.z_crm_zulfiqor.services.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.ConfirmStatus;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.TradePlaceStatus;

public interface ApproveProductsService {

    BaseResponse<?> getAllUnApprovedProducts() throws JsonProcessingException;

    BaseResponse<?> getAllUnApprovedProductDetails(TradePlaceStatus status, Long transactionId) throws JsonProcessingException;

    BaseResponse<?> approveProductsByTransactionId(ConfirmStatus status, Long transactionId);


}
