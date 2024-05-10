package com.zulfiqor.z_crm_zulfiqor.model.entity.product;

import com.zulfiqor.z_crm_zulfiqor.model.base.BaseEntity;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.parent.TradePlaces;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.ConfirmStatus;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.TradePlaceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PM_TRANSACTIONS")
public class Transactions extends BaseEntity {

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private TradePlaceStatus status;

    @Column(name = "confirm_status")
    @Enumerated(EnumType.STRING)
    private ConfirmStatus confirmStatus;

    @Column(name = "COMMENT")
    private String comment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TRADE_PLACES_ID")
    private TradePlaces tradePlaces;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CONTRACT_ID", referencedColumnName = "ID")
    private Contract contract;

    private Long createdBy;

    private Long updatedBy;
}
