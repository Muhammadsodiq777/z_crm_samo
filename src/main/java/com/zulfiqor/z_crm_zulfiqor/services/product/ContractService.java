package com.zulfiqor.z_crm_zulfiqor.services.product;

import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.ContractRequest;

public interface ContractService {

    BaseResponse<?> addContract(ContractRequest request);

 }
