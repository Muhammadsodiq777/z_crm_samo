package com.zulfiqor.z_crm_zulfiqor.repository.product;

import com.zulfiqor.z_crm_zulfiqor.model.entity.product.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    Contract findContractByContractNumber(String cNum);
}
