package com.zulfiqor.z_crm_zulfiqor.model.entity.product;

import com.zulfiqor.z_crm_zulfiqor.model.base.BaseEntity;
import com.zulfiqor.z_crm_zulfiqor.model.entity.product.parent.TradePlaces;
import com.zulfiqor.z_crm_zulfiqor.model.enums.stats.TradePlaceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PM_CONTRACT")
public class Contract extends BaseEntity {

    @Column(name = "CONTRACT_NUMBER")
    private String contractNumber;

    @ManyToOne
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    private TradePlaces place;

}
