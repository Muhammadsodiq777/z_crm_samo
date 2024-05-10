package com.zulfiqor.z_crm_zulfiqor.mapper.product;

import com.zulfiqor.z_crm_zulfiqor.model.dto.request.ContractRequest;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.Contract;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-10T18:38:43+0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2 (Amazon.com Inc.)"
)
@Component
public class ContractMapperImpl implements ContractMapper {

    @Override
    public Contract mapToEntity(ContractRequest request) {
        if ( request == null ) {
            return null;
        }

        Contract contract = new Contract();

        contract.setContractNumber( request.contractNumber() );

        return contract;
    }
}
