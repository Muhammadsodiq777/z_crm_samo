package com.zulfiqor.z_crm_zulfiqor.repository.product;

import com.zulfiqor.z_crm_zulfiqor.model.entity.product.Transactions;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.ConfirmStatus;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.TradePlaceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transactions, Long> {

    /**
     * This method works when both fromDate and toDate is given
     * @return Page<Transaction>
     */
    Page<Transactions> findAllByStatusAndActiveTrueAndCreatedDateBetweenAndConfirmStatusNotOrderByCreatedDateDesc(
            TradePlaceStatus status, Date startDate, Date endDate, ConfirmStatus confirmStatus, Pageable pageable);

    /**
     *This method works for fromDate
     * @return Page<Transactions>
     */
    Page<Transactions> findAllByStatusAndActiveTrueAndCreatedDateGreaterThanEqualAndConfirmStatusNotOrderByCreatedDateDesc(
            TradePlaceStatus status, Date startDate, ConfirmStatus confirmStatus, Pageable pageable);

    /**
     * This method works for toDate
     * @return Page<Transactions>
     */
    Page<Transactions> findAllByStatusAndActiveTrueAndCreatedDateLessThanEqualAndConfirmStatusNotOrderByCreatedDateDesc(
            TradePlaceStatus status, Date endDate, ConfirmStatus confirmStatus, Pageable pageable);

    Page<Transactions> findAllByStatusAndActiveTrueAndConfirmStatusNotOrderByCreatedDateDesc(
            TradePlaceStatus status, ConfirmStatus confirmStatus, Pageable pageable);

    List<Transactions> findAllByConfirmStatusAndActiveTrue(ConfirmStatus confirmStatus);

    List<Transactions> findAllByStatusAndConfirmStatusAndActiveTrue(TradePlaceStatus status, ConfirmStatus confirmStatus);
}
