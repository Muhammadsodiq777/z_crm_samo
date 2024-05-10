package com.zulfiqor.z_crm_zulfiqor.repository.product;

import com.zulfiqor.z_crm_zulfiqor.model.entity.product.OutProducts;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.Transactions;
import com.zulfiqor.z_crm_zulfiqor.repository.product.custom.OutProductsCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface OutProductRepository extends JpaRepository<OutProducts, Long>, OutProductsCustomRepository {
    List<OutProducts> findAllByTransactions(Transactions transactions);

    List<OutProducts> findAllByTransactionsAndActiveTrue(Transactions transactions);

    List<OutProducts> findAllByActiveIsTrueAndCreatedDateBetween(Date firstDayOfMonth, Date lastDayOfMonth);
}
