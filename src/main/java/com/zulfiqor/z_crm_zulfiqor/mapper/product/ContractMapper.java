package com.zulfiqor.z_crm_zulfiqor.mapper.product;

import com.zulfiqor.z_crm_zulfiqor.model.dto.request.ContractRequest;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.Contract;
import org.mapstruct.Mapper;

@Mapper
public interface ContractMapper {

    Contract mapToEntity(ContractRequest request);
}