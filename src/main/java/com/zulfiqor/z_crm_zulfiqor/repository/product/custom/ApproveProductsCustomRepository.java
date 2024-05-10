package com.zulfiqor.z_crm_zulfiqor.repository.product.custom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.ApproveProductsResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.response.history.TransactionsResponse;

import java.util.List;

public interface ApproveProductsCustomRepository {
    List<ApproveProductsResponse> getUnconfirmedProductsDetails(Long transactionId) throws JsonProcessingException;
}
