package com.zulfiqor.z_crm_zulfiqor.services.product;

import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.SellProduct;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.Transactions;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.ConfirmStatus;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.TradePlaceStatus;

public interface TransactionService {
    Transactions createTransaction(String comment, TradePlaceStatus status, SellProduct product, String deviceId, Long contractId);

    Transactions updateTransaction(Transactions transactions, ConfirmStatus status);

    BaseResponse<?> getTransactionById(Long transactionId);
}
