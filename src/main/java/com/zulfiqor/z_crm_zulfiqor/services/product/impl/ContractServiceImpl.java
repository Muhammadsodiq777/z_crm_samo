package com.zulfiqor.z_crm_zulfiqor.services.product.impl;

import com.zulfiqor.z_crm_zulfiqor.mapper.product.ContractMapper;
import com.zulfiqor.z_crm_zulfiqor.model.dto.BaseResponse;
import com.zulfiqor.z_crm_zulfiqor.model.dto.request.ContractRequest;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.Contract;
import com.zulfiqor.z_crm_zulfiqor.repository.product.ContractRepository;
import com.zulfiqor.z_crm_zulfiqor.services.product.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractRepository repository;
    private final ContractMapper mapper;

    @Override
    public BaseResponse<?> addContract(ContractRequest request) {
        Contract contract = repository.findContractByContractNumber(request.contractNumber());
        if (contract == null)
            return BaseResponse.error(-1, "Bu raqam bilan kantrakt avvaldan mavjud");

        return Optional.of(mapper.mapToEntity(request))
                .map(c -> {
                    repository.save(c);
                    return BaseResponse.success();
                }).orElse(BaseResponse.error(-1, "Xatolik yuz berdi, Iltimos qayta urinib ko'ring"));
    }
}
